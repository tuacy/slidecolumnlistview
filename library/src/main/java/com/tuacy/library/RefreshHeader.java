package com.tuacy.library;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RefreshHeader extends LinearLayout implements BaseRefreshHeader {

	private static final int ROTATE_ANIM_DURATION = 200;//动画持续时间

	/**
	 * 内部容器
	 */
	private RelativeLayout mContainer;
	/**
	 * 指示箭头
	 */
	private ImageView      mArrowImageView;
	/**
	 * 提示文字
	 */
	private TextView       mHintTextView;
	/**
	 * 进度提示
	 */
	private ProgressBar    mProgressBar;
	/**
	 * 向下旋转动画
	 */
	private Animation      mRotateDownAnim;
	/**
	 * 向上旋转动画
	 */
	private Animation      mRotateUpAnim;
	/**
	 * 当前头部状态
	 */
	private int            mState;
	/**
	 * 刷新头部的高度
	 */
	private int            mMeasuredHeight;
	private Handler        mHandler;

	public RefreshHeader(Context context) {
		this(context, null);
	}

	public RefreshHeader(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mState = STATE_NORMAL;
		mContainer = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_header, this, false);
		setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		/**
		 * 刚开始的时候高度设置为0,这样刚开始的时候下拉刷新的View存在，但是看不到
		 */
		addView(mContainer, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));

		mArrowImageView = (ImageView) mContainer.findViewById(R.id.PullToRefresh_Header_ArrowImageView);
		mHintTextView = (TextView) mContainer.findViewById(R.id.PullToRefresh_Header_HintTextView);
		mProgressBar = (ProgressBar) mContainer.findViewById(R.id.PullToRefresh_Header_ProgressBar);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
											0.5f);//RELATIVE_TO_SELF是相对
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);

		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);

		/**
		 * 获取刷新头部的标准高度
		 */
		measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mMeasuredHeight = getMeasuredHeight();

		mHandler = new Handler();
	}

	@Override
	public void onMove(float delta) {
		if (getVisibleHeight() > 0 || delta > 0) {
			setVisibleHeight((int) delta + getVisibleHeight());
			if (getVisibleHeight() > mMeasuredHeight) {
				/**
				 * 达到了是否刷新的高度,设置状态为释放刷新
				 */
				onStateChange(STATE_RELEASE);
			} else {
				onStateChange(STATE_NORMAL);
			}
		}
	}

	@Override
	public void onComplete() {
		onStateChange(STATE_COMPLETE);//将状态设置为 刷新完成
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				reset();//延迟进行重置
			}
		}, 200);
	}

	@Override
	public boolean onRelease() {
		boolean refresh = false;//标志位，判断是否是在刷新触发用户的接口回调事件

		int height = getVisibleHeight();
		if (height == 0) {
			refresh = false;
		}

		if (height >= mMeasuredHeight && mState == STATE_RELEASE) {
			/**
			 * 将状态设置为刷新状态
			 */
			onStateChange(STATE_REFRESHING);
			refresh = true;
		}

		if (mState != STATE_REFRESHING) {
			smoothScrollTo(0);
		} else {
			smoothScrollTo(mMeasuredHeight);
		}

		return refresh;
	}

	@Override
	public void onStateChange(int state) {
		if (mState == state) {//注意state是最新状态，mState是上一次的状态
			return;
		}

		switch (state) {
			case STATE_NORMAL:
				mArrowImageView.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);
				mHintTextView.setText(R.string.pull_refresh_normal);
				if (mState == STATE_RELEASE) {
					mArrowImageView.clearAnimation();
					mArrowImageView.startAnimation(mRotateDownAnim);//将箭头转向下
				}
				break;
			case STATE_RELEASE:
				mArrowImageView.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.GONE);

				if (mState == STATE_NORMAL) {//从普通状态转变为滑动释放状态
					mArrowImageView.clearAnimation();
					mArrowImageView.startAnimation(mRotateUpAnim);//将箭头转向上
				}
				mHintTextView.setText(R.string.pull_refresh_release);
				break;
			case STATE_REFRESHING:
				mArrowImageView.clearAnimation();
				mArrowImageView.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.VISIBLE);
				smoothScrollTo(mMeasuredHeight);//将头部高度设置为标准高度
				mHintTextView.setText(R.string.pull_refresh_refreshing);
				break;
			case STATE_COMPLETE:
				mArrowImageView.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.GONE);
				mHintTextView.setText(R.string.pull_refresh_complete);
				break;
		}
		mState = state;
	}

	/**
	 * 通过动画来设置，view的高度
	 *
	 * @param destHeight 高度
	 */
	private void smoothScrollTo(int destHeight) {
		ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				setVisibleHeight((int) animation.getAnimatedValue());
			}
		});
		animator.setDuration(300).start();
	}

	/**
	 * 获取刷新头部的实时高度
	 *
	 * @return 实时高度
	 */
	public int getVisibleHeight() {
		return mContainer.getLayoutParams().height;
	}

	/**
	 * 实时改变刷新头部的实时高度
	 *
	 * @param height 实时高度
	 */
	public void setVisibleHeight(int height) {
		if (height < 0) {
			height = 0;
		}
		LayoutParams params = (LayoutParams) mContainer.getLayoutParams();
		params.height = height;
		mContainer.setLayoutParams(params);
	}

	/**
	 * 重置刷新头部的状态 将刷新头部的状态重置为隐藏
	 */
	public void reset() {
		smoothScrollTo(0);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onStateChange(STATE_NORMAL);
			}
		}, 500);
	}

	public int getState() {
		return mState;
	}
}
