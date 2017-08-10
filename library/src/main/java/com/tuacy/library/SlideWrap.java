package com.tuacy.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 既可以左右滑动，有可以上下滑动的ListView
 * ColumnDraggableLayout布局包含两部分，header标题懒,ListView内容
 */
public class SlideWrap extends LinearLayout
	implements SlideBaseAdapter.OnDataChangeListener, SlideListener {

	/**
	 * 头部布局(标题栏)
	 */
	private SlideHeaderLayout mHeaderView;
	/**
	 * list view(内容部分)
	 */
	private SlideListView     mListView;
	/**
	 * list view adapter
	 */
	private SlideBaseAdapter  mAdapter;
	/**
	 * 从哪一列开始可以滑动（一部分列是可滑动的）
	 */
	private int               mDraggableColumnStart;
	/**
	 * 标题栏的高度
	 */
	private int               mTileHeight;
	/**
	 * 标题内容list的形式
	 */
	private List<String>      mTitle;

	public SlideWrap(Context context) {
		this(context, null);
	}

	public SlideWrap(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideWrap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttrs(context, attrs);
		initView();
		initData();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.layout_draggable_wrap, this, true);
	}

	/**
	 * 自定义属性的获取
	 */
	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SlideWrap);
		mDraggableColumnStart = attributes.getInt(R.styleable.SlideWrap_column_draggable_start, 0);
		mTileHeight = attributes.getDimensionPixelOffset(R.styleable.SlideWrap_column_title_height, dip2px(getContext(), 32));
		attributes.recycle();
	}

	private void initData() {

	}

	/**
	 * 设置标题栏的View
	 */
	private void reloadTitleView() {
		if (mTitle == null || mTitle.isEmpty()) {
			mHeaderView.setVisibility(GONE);
		} else {
			mHeaderView.setVisibility(VISIBLE);
			mHeaderView.getFixedLayout().removeAllViews();
			mHeaderView.getSlideLayout().removeAllViews();
			for (int index = 0; index < mTitle.size(); index++) {
				TextView textView = new TextView(mHeaderView.getFixedLayout().getContext());
				LayoutParams params = mAdapter.getColumnWidth(0, index, mTitle.size());
				params.height = mTileHeight;
				textView.setLayoutParams(params);
				textView.setGravity(Gravity.CENTER);
				textView.setText(mTitle.get(index));
				textView.setBackgroundResource(R.drawable.shader_title_column);
				if (index < mDraggableColumnStart) {
					mHeaderView.getFixedLayout().addView(textView);
				} else {
					mHeaderView.getSlideLayout().addView(textView);
				}
			}
		}
	}

	/**
	 * 设置标题栏的内容
	 *
	 * @param title 标题栏的内容
	 */
	private void setTitle(List<String> title) {
		if (mTitle == null) {
			mTitle = title;
			reloadTitleView();
		} else {
			if (title == null) {
				mTitle = null;
				reloadTitleView();
			} else {
				if (mTitle.size() != title.size()) {
					mTitle = title;
					reloadTitleView();
				} else {
					boolean change = false;
					for (int index = 0; index < mTitle.size(); index++) {
						if (!mTitle.get(index).equals(title.get(index))) {
							change = true;
							reloadTitleView();
						}
					}
					if (change) {
						mTitle = title;
						reloadTitleView();
					}
				}
			}
		}
		if (mAdapter != null) {
			onColumnSlideListener(mAdapter.getColumnSlideTo());
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHeaderView = (SlideHeaderLayout) findViewById(R.id.column_draggable_header_id);
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.height = mTileHeight;
		mHeaderView.setLayoutParams(params);
		mHeaderView.setVisibility(GONE);
		mListView = (SlideListView) findViewById(R.id.column_draggable_list_id);
		mListView.addOnSlideListener(this);
	}

	/**
	 * 设置
	 * @param adapter
	 */
	public void setAdapter(SlideBaseAdapter adapter) {
		mAdapter = adapter;
		if (mAdapter != null) {
			mAdapter.setSlideColumnStart(mDraggableColumnStart);
			mAdapter.setOnDataChangeListener(this);
		}
		mListView.setAdapter(adapter);
		onAdapterDataChange();
	}

	public SlideBaseAdapter getAdapter() {
		return mAdapter;
	}

	public SlideListView getListView() {
		return mListView;
	}

	public void setTitleBackground(int color) {
		if (mHeaderView != null) {
			mHeaderView.setBackgroundColor(color);
		}
	}


	@Override
	public void onAdapterDataChange() {
		SlideData data = mAdapter.getData();
		if (data == null) {
			setTitle(null);
		} else {
			setTitle(data.getTitle());
		}
	}

	private static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@Override
	public void onColumnSlideListener(int setX) {
		mHeaderView.getSlideLayout().scrollTo(setX, 0);
	}
}
