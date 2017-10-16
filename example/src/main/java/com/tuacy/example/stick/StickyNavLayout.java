package com.tuacy.example.stick;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.tuacy.example.R;


public class StickyNavLayout extends LinearLayout {

	private ViewGroup mHeaderView;
	private ListView  mListView;

	private int      mTouchSlop;
	private int      mHeaderViewHeight;
	private Scroller mScroller;
	private boolean  mIsHeaderViewHidden;

	public StickyNavLayout(Context context) {
		this(context, null);
	}

	public StickyNavLayout(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StickyNavLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mScroller = new Scroller(getContext());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHeaderView = (ViewGroup) findViewById(R.id.stick_nav_header_layout_id);
		mListView = (ListView) findViewById(R.id.stick_nav_list_layout_id);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		ViewGroup.LayoutParams params = mListView.getLayoutParams();
		params.height = getMeasuredHeight();
		setMeasuredDimension(getMeasuredWidth(), mHeaderView.getMeasuredHeight() + mListView.getMeasuredHeight());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
	}

	private float mDownY;
	private float mLastY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = mDownY = ev.getY();
				break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				final int yDiff = (int) Math.abs(y - mDownY);
				if ((!mIsHeaderViewHidden && y - mDownY < 0 && yDiff >= mTouchSlop) ||
					(mIsHeaderViewHidden && y - mDownY > 0 && mListView.getFirstVisiblePosition() == 0 && yDiff >= mTouchSlop)) {
					return true;
				}
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				final int deltaY = (int) (y - mLastY);
				scrollDistanceBy(-deltaY);
				mLastY = y;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				if (getScrollY() < mHeaderViewHeight / 2) {
					mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
				} else {
					mScroller.startScroll(0, getScrollY(), 0, mHeaderViewHeight - getScrollY());
				}
				invalidate();
				break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			if (getScrollY() >= mHeaderViewHeight) {
				mIsHeaderViewHidden = true;
			} else if (getScrollY() <= 0) {
				mIsHeaderViewHidden = false;
			}
			notifyListDataRefresh();
			postInvalidate();
		}
	}

	private void scrollDistanceBy(int deltaY) {
		scrollBy(0, deltaY);
		((BaseAdapter)(mListView.getAdapter())).notifyDataSetChanged();
		//避免有些情况滑到范围之外去
		if (getScrollY() >= mHeaderViewHeight) {
			scrollBy(0, mHeaderViewHeight - getScrollY());
			mIsHeaderViewHidden = true;
		}
		if (getScrollY() <= 0) {
			scrollBy(0, -getScrollY());
			mIsHeaderViewHidden = false;
		}
		notifyListDataRefresh();
	}

	private void scrollDistanceTo(int deltaY) {
		if (getScrollY() >= mHeaderViewHeight) {
			scrollTo(0, mHeaderViewHeight);
			mIsHeaderViewHidden = true;
		} else if (getScrollY() <= 0) {
			scrollTo(0, 0);
			mIsHeaderViewHidden = false;
		} else {
			scrollTo(0, deltaY);
		}
		notifyListDataRefresh();
	}

	private void notifyListDataRefresh() {
		if (mListView.getAdapter() != null &&  mListView.getAdapter() instanceof BaseAdapter) {
			((BaseAdapter)(mListView.getAdapter())).notifyDataSetChanged();
		}
	}
}
