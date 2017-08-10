package com.tuacy.library;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadMoreFooter extends LinearLayout {

	public static final int STATE_LOADING  = 0;//标志正在加载中
	public static final int STATE_COMPLETE = 1;//标志加载完成
	public static final int STATE_NO_MORE  = 2;//标志没有更多内容

	private TextView    mHintTextView;
	private ProgressBar mProgressBar;
	private int         mMeasureHeight;

	public LoadMoreFooter(Context context) {
		this(context, null);
	}

	public LoadMoreFooter(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoadMoreFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.layout_load_more_footer, this, true);
		mHintTextView = (TextView) findViewById(R.id.text_load_more_hint);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_load_more);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mMeasureHeight = getMeasuredHeight();
	}

	public void onStateChange(int state) {
		switch (state) {
			case STATE_LOADING:
				mProgressBar.setVisibility(View.VISIBLE);
				mHintTextView.setText(R.string.load_more_loading);
				break;
			case STATE_COMPLETE:
				mProgressBar.setVisibility(View.GONE);
				mHintTextView.setText(R.string.load_more_complete);
				break;
			case STATE_NO_MORE:
				mProgressBar.setVisibility(View.GONE);
				mHintTextView.setText(R.string.load_more_no_more);
				break;
		}
	}

	public int getMeasureHeight() {
		return mMeasureHeight;
	}
}
