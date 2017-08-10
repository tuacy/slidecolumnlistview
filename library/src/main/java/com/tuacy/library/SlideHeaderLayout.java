package com.tuacy.library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * 标题栏布局，会一直存在在顶部,两部分组成一个是不能滑动的一些列，一个是可滑动的一些列
 */
public class SlideHeaderLayout extends LinearLayout {

	/**
	 * 不能滑动的列，显示在左边
	 */
	private LinearLayout    mFixedLayout;
	/**
	 * 可滑动的列，显示在右边
	 */
	private SlideColumnWrap mSlideLayout;

	public SlideHeaderLayout(Context context) {
		this(context, null);
	}

	public SlideHeaderLayout(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideHeaderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_column_draggable_wrap, this, true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mFixedLayout = (LinearLayout) findViewById(R.id.column_draggable_item_fixed_id);
		mSlideLayout = (SlideColumnWrap) findViewById(R.id.column_draggable_item_drag_id);
	}

	/**
	 * 获取可滑动的列的父布局
	 *
	 * @return 可滑动列的父布局
	 */
	public SlideColumnWrap getSlideLayout() {
		return mSlideLayout;
	}

	/**
	 * 获取不可滑动的列的父布局
	 *
	 * @return 不可滑动列的父布局
	 */
	public LinearLayout getFixedLayout() {
		return mFixedLayout;
	}
}
