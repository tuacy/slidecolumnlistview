package com.tuacy.library;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * 可滑动列的布局，横向排列
 */
public class SlideColumnWrap extends LinearLayout {

	public SlideColumnWrap(Context context) {
		this(context, null);
	}

	public SlideColumnWrap(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideColumnWrap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	private void initView() {
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	/**
	 * 获取所有view的总宽度，所有的view都是横向排列的，我们的知道所有view的总的长度，才好去判断滑动的距离
	 *
	 * @return 宽度
	 */
	public int getRealityWidth() {
		int realityWidth = 0;
		for (int index = 0; index < getChildCount(); index++) {
			realityWidth += getChildAt(index).getWidth();
		}
		return realityWidth;
	}

	/**
	 * TODO:让每次滑动停止的时候都停留在列的开始位置
	 * @param direction：滑动的方向，左滑还是右滑
	 * @return 为了到达列的开始位置还需要滑动的距离
	 */
	public int getSlideToColumnEdgeMoveX(int direction) {
		//TODO:
		return 0;
	}
}
