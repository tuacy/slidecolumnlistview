package com.tuacy.library;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;


public class SlideListView extends ListView implements AbsListView.OnScrollListener {

	private static final int   TYPE_REFRESH_CONTENT_VIEW   = 0x100;//刷新type
	private static final int   TYPE_LOAD_MORE_CONTENT_VIEW = 0x101;//加载更多type
	private static final int   SNAP_VELOCITY               = 500;//速度
	private static final int   TYPE_SLIDING_NONE           = 0;
	private static final int   TYPE_SLIDING_HORIZONTAL     = 1;
	private static final int   TYPE_SLIDING_VERTICAL       = 2;
	/**
	 * 拖动阻力系数
	 */
	private static final float RESISTANCE_COEFFICIENT      = 0.3f;

	private boolean                            mEnableRefresh;
	private RefreshHeader                      mRefreshHeader;
	private boolean                            mEnableLoadMore;
	private boolean                            mIsLoadingMore;
	private boolean                            mIsNoMore;
	private LoadMoreFooter      mLoadMoreFooter;
	private Scroller            mScroller;
	private VelocityTracker     mVelocityTracker;
	private int                 mTouchSlop;
	private float               mLastMotionDownX;
	private float               mLastMotionDownY;
	private float               mLastMotionX;
	private float               mLastMotionY;
	private int                 mSlidingMode;
	private List<SlideListener> mSlideListenerList;
	private DataObserver        mDataObserver;
	private WrapAdapter         mWrapAdapter;
	private OnRefreshListener   mRefreshListener;

	public SlideListView(Context context) {
		this(context, null);
	}

	public SlideListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
	}

	private void initData() {
		mScroller = new Scroller(getContext());
		mEnableRefresh = false;
		mEnableLoadMore = false;
		mIsLoadingMore = false;
		mIsNoMore = false;
		mSlidingMode = TYPE_SLIDING_NONE;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();//大于getScaledTouchSlop这个距离时才认为是触发事件
		mDataObserver = new DataObserver();
		mRefreshHeader = new RefreshHeader(getContext());
		mLoadMoreFooter = new LoadMoreFooter(getContext());
		setOnScrollListener(this);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (!(adapter instanceof SlideBaseAdapter)) {
			throw new IllegalArgumentException("adapter should abstract SlideBaseAdapter");
		}
		addOnSlideListener((SlideBaseAdapter) adapter);
		mWrapAdapter = new WrapAdapter((SlideBaseAdapter) adapter);//使用内部Adapter包装用户的Adapter
		super.setAdapter(mWrapAdapter);
		adapter.registerDataSetObserver(mDataObserver);//注册Adapter数据监听器
		mDataObserver.onChanged();
	}

	@Override
	public ListAdapter getAdapter() {
		if (mWrapAdapter != null) {
			return mWrapAdapter.mAdapter;
		}
		return null;
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				// 首先停止滚动
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
				mSlidingMode = TYPE_SLIDING_NONE;
				mLastMotionX = x;
				mLastMotionY = y;
				mLastMotionDownX = x;
				mLastMotionDownY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				final int xDiff = (int) Math.abs(x - mLastMotionDownX);
				final int yDiff = (int) Math.abs(y - mLastMotionDownY);
				if (mSlidingMode == TYPE_SLIDING_NONE) {
					if (xDiff > mTouchSlop && xDiff > yDiff) {
						return true;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean handler = false;
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();//跟踪触摸事件滑动的帮助类
		}
		mVelocityTracker.addMovement(ev);
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
//				// 首先停止滚动
//				if (!mScroller.isFinished()) {
//					mScroller.abortAnimation();
//				}
//				mSlidingMode = TYPE_SLIDING_NONE;
//				handler = false;
//				mLastMotionX = x;
//				mLastMotionY = y;
//				mLastMotionDownX = x;
//				mLastMotionDownY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				final int xDiff = (int) Math.abs(x - mLastMotionDownX);
				final int yDiff = (int) Math.abs(y - mLastMotionDownY);
				if (mSlidingMode == TYPE_SLIDING_NONE) {
					if (xDiff > mTouchSlop && xDiff > yDiff) {
						mSlidingMode = TYPE_SLIDING_HORIZONTAL;
					} else if (yDiff > mTouchSlop && yDiff > xDiff) {
						mSlidingMode = TYPE_SLIDING_VERTICAL;
					}
				}

				if (mSlidingMode == TYPE_SLIDING_HORIZONTAL) {
					final int deltaX = (int) (mLastMotionX - x);//滑动的距离
					prepareSlideMove(deltaX);
					mLastMotionX = x;
					mLastMotionY = y;
					handler = true;
				} else if (mSlidingMode == TYPE_SLIDING_VERTICAL && canPullRefresh()) {
					if (isRefreshViewVisible()) {
						final int deltaY = (int) (y - mLastMotionY);
						mLastMotionX = x;
						mLastMotionY = y;
						mRefreshHeader.onMove(deltaY * RESISTANCE_COEFFICIENT);
						if (mRefreshHeader.getVisibleHeight() > 0 && mRefreshHeader.getState() <= RefreshHeader.STATE_RELEASE) {
							/**
							 * 这个时候，就不要让外部去处理这个事件了,要不然滑动会乱的，自己处理掉就好了
							 */
							handler = true;
						}
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mSlidingMode == TYPE_SLIDING_HORIZONTAL) {
					final VelocityTracker velocityTracker = mVelocityTracker;
					velocityTracker.computeCurrentVelocity(1000);//1000毫秒移动了多少像素
					int velocityX = (int) velocityTracker.getXVelocity();//当前的速度
					if (canSlideHorizontal()) {
						if (Math.abs(velocityX) < SNAP_VELOCITY) {
							prepareSlideToColumnEdge();
						} else {
							prepareFling(-velocityX);
						}
					}
					if (mVelocityTracker != null) {
						mVelocityTracker.recycle();
						mVelocityTracker = null;
					}
					ev.setAction(MotionEvent.ACTION_CANCEL);
					super.onTouchEvent(ev);
					return true;
				} else if (mSlidingMode == TYPE_SLIDING_VERTICAL && canPullRefresh()) {
					if (isRefreshViewVisible() && mEnableRefresh) {
						if (mRefreshHeader.onRelease() && mRefreshListener != null) {
							mRefreshListener.onPullRefresh();
						}
					}
				}
				mSlidingMode = TYPE_SLIDING_NONE;
				break;
			case MotionEvent.ACTION_CANCEL:
				mSlidingMode = TYPE_SLIDING_NONE;
				if (mVelocityTracker != null) {
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
				break;
		}
		return handler || super.onTouchEvent(ev);
	}


	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			prepareSlideSet(mScroller.getCurrX());
			postInvalidate();
		}
	}

	/**
	 * 判断是否可以滑动
	 */
	protected boolean canSlideHorizontal() {
		for (int i = 0; i < getChildCount(); i++) {
			View itemView = getChildAt(i);
			SlideColumnWrap slideView = (SlideColumnWrap) itemView.findViewById(R.id.column_draggable_item_drag_id);
			if (slideView == null) {
				// 这个时候是refresh item 或者 load more item
				continue;
			}
			//最多可以滑动的距离
			int maxScrollX = slideView.getRealityWidth() - slideView.getWidth();
			if (slideView.getScrollX() < maxScrollX && slideView.getScrollX() > 0) {
				return true;
			}
		}
		return false;
	}

	private int getMaxScrollX() {
		for (int i = 0; i < getChildCount(); i++) {
			View itemView = getChildAt(i);
			SlideColumnWrap slideView = (SlideColumnWrap) itemView.findViewById(R.id.column_draggable_item_drag_id);
			if (slideView == null) {
				// 这个时候是refresh item 或者 load more item
				continue;
			}
			return slideView.getRealityWidth() - slideView.getWidth();
		}
		return 0;
	}

	private int getCurrentScrollX() {
		for (int i = 0; i < getChildCount(); i++) {
			View itemView = getChildAt(i);
			SlideColumnWrap slideView = (SlideColumnWrap) itemView.findViewById(R.id.column_draggable_item_drag_id);
			if (slideView == null) {
				// 这个时候是refresh item 或者 load more item
				continue;
			}
			return slideView.getScrollX();
		}
		return 0;
	}

	private void prepareSlideSet(int setX) {
		boolean notifyListener = false;
		for (int i = 0; i < getChildCount(); i++) {
			View itemView = getChildAt(i);
			SlideColumnWrap slideView = (SlideColumnWrap) itemView.findViewById(R.id.column_draggable_item_drag_id);
			if (slideView == null) {
				// 这个时候是refresh item 或者 load more item
				continue;
			}
			//最多可以滑动的距离
			int maxScrollX = slideView.getRealityWidth() - slideView.getWidth();
			if (slideView.getScrollX() <= maxScrollX && slideView.getScrollX() >= 0) {
				slideView.scrollTo(setX, 0);
				//避免有些情况滑到范围之外去
				if (slideView.getScrollX() > maxScrollX) {
					slideView.scrollTo(maxScrollX, 0);
				}
				if (slideView.getScrollX() < 0) {
					slideView.scrollTo(0, 0);
				}
			}
			if (!notifyListener) {
				notifySlideListener(slideView.getScrollX());
				notifyListener = true;
			}
		}
	}

	private void prepareSlideMove(int moveX) {
		boolean notifyListener = false;
		for (int i = 0; i < getChildCount(); i++) {
			View itemView = getChildAt(i);
			SlideColumnWrap slideView = (SlideColumnWrap) itemView.findViewById(R.id.column_draggable_item_drag_id);
			if (slideView == null) {
				// 这个时候是refresh item 或者 load more item
				continue;
			}
			//最多可以滑动的距离
			int maxScrollX = slideView.getRealityWidth() - slideView.getWidth();
			if (slideView.getScrollX() <= maxScrollX && slideView.getScrollX() >= 0) {
				slideView.scrollBy(moveX, 0);
				//避免有些情况滑到范围之外去
				if (slideView.getScrollX() > maxScrollX) {
					slideView.scrollBy(maxScrollX - slideView.getScrollX(), 0);
				}
				if (slideView.getScrollX() < 0) {
					slideView.scrollBy(-slideView.getScrollX(), 0);
				}
			}
			if (!notifyListener) {
				notifySlideListener(slideView.getScrollX());
				notifyListener = true;
			}
		}
	}

	private void prepareSlideToColumnEdge() {
		//TODO:
	}

	private void prepareFling(int velocityX) {
		mScroller.fling(getCurrentScrollX(), 0, velocityX, 0, 0, getMaxScrollX(), 0, 0);
		invalidate();
	}

	private void notifySlideListener(int setX) {
		if (mSlideListenerList != null && !mSlideListenerList.isEmpty()) {
			for (SlideListener listener : mSlideListenerList) {
				listener.onColumnSlideListener(setX);
			}
		}
	}

	public void addOnSlideListener(SlideListener listener) {
		if (mSlideListenerList == null) {
			mSlideListenerList = new ArrayList<>();
		}
		mSlideListenerList.add(listener);
	}

	public void removeOnSlideListener(SlideListener listener) {
		if (mSlideListenerList != null && mSlideListenerList.contains(listener)) {
			mSlideListenerList.remove(listener);
		}
	}

	public void setPullRefreshEnable(boolean enable) {
		if (mEnableRefresh != enable) {
			if (mWrapAdapter != null) {
				mWrapAdapter.notifyDataSetChanged();
			}
		}
		mEnableRefresh = enable;
	}

	public void setPushLoadMoreEnable(boolean enable) {
		if (mEnableLoadMore != enable) {
			if (mWrapAdapter != null) {
				mWrapAdapter.notifyDataSetChanged();
			}
		}
		mEnableLoadMore = enable;
	}

	public void setOnRefreshListener(OnRefreshListener listener) {
		mRefreshListener = listener;
	}

	private boolean isRefreshViewVisible() {
		return mRefreshHeader.getParent() != null;
	}

	/**
	 * 是否可以下拉刷新
	 *
	 * @return 是否可以下拉刷新
	 */
	private boolean canPullRefresh() {
		return mEnableRefresh && mRefreshListener != null;
	}

	/**
	 * 是否可以上拉加载
	 *
	 * @return 是否可以上拉加载
	 */
	private boolean canLoadMore() {
		return mEnableLoadMore && mRefreshListener != null;
	}

	/**
	 * 下拉刷新完成，外部调用
	 */
	public void refreshComplete() {
		mRefreshHeader.onComplete();
	}

	/**
	 * 上拉加载更多完成，外部调用
	 */
	public void loadMoreComplete() {
		mIsLoadingMore = false;
		mLoadMoreFooter.onStateChange(LoadMoreFooter.STATE_COMPLETE);
	}

	/**
	 * 设置没有更多数据，外部调用
	 *
	 * @param noMoreData 是否没有跟多数据
	 */
	public void setNoMoreData(boolean noMoreData) {
		mIsNoMore = noMoreData;
		mIsLoadingMore = false;
		mLoadMoreFooter.onStateChange(noMoreData ? LoadMoreFooter.STATE_NO_MORE : LoadMoreFooter.STATE_LOADING);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && mRefreshHeader.getState() < BaseRefreshHeader.STATE_REFRESHING &&
			canLoadMore() && !mIsLoadingMore && !mIsNoMore) {
			//获取最后一个可见项
			int lastPosition = getLastVisiblePosition();
			if (lastPosition == getCount() - 1) {
				mIsLoadingMore = true;
				mLoadMoreFooter.onStateChange(LoadMoreFooter.STATE_LOADING);
				if (mRefreshListener != null) {
					mRefreshListener.onPushLoadMore();
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}


	private class WrapAdapter extends BaseAdapter {

		private SlideBaseAdapter mAdapter;

		WrapAdapter(SlideBaseAdapter adapter) {
			mAdapter = adapter;
		}

		@Override
		public int getCount() {
			int count = mAdapter.getCount();
			if (mEnableRefresh) {
				count++;
			}

			if (mEnableLoadMore) {
				count++;
			}
			return count;
		}

		@Override
		public Object getItem(int i) {
			return null;
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public int getItemViewType(int position) {
			if (mEnableRefresh && position == 0) {
				return TYPE_REFRESH_CONTENT_VIEW;
			}
			if (mEnableLoadMore && position == getCount() - 1) {
				return TYPE_LOAD_MORE_CONTENT_VIEW;
			}
			return mAdapter.getItemViewType(position);
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			if (getItemViewType(i) == TYPE_REFRESH_CONTENT_VIEW) {
				return mRefreshHeader;
			} else if (getItemViewType(i) == TYPE_LOAD_MORE_CONTENT_VIEW) {
				return mLoadMoreFooter;
			}
			if (view != null && (view instanceof RefreshHeader || view instanceof LoadMoreFooter)) {
				view = null;
			}
			return mAdapter.getView(mEnableRefresh ? i - 1 : i, view, viewGroup);//其它为自定义Adapter里面的Item类型
		}
	}

	private class DataObserver extends DataSetObserver {//Adapter数据监听器 与 WrapAdapter联动作用


		DataObserver() {
			super();
		}

		@Override
		public void onChanged() {
			if (mWrapAdapter != null) {
				mWrapAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void onInvalidated() {
			if (mWrapAdapter != null) {
				mWrapAdapter.notifyDataSetInvalidated();
			}
		}
	}
}
