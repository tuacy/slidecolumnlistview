package com.tuacy.library;

public interface BaseRefreshHeader {

	/**
	 * 初始状态
	 */
	int STATE_NORMAL     = 0;
	/**
	 * 释放刷新
	 */
	int STATE_RELEASE    = 1;
	/**
	 * 正在刷新
	 */
	int STATE_REFRESHING = 2;
	/**
	 * 刷新完成
	 */
	int STATE_COMPLETE   = 3;

	/**
	 * 手指滑动
	 * @param delta 偏移量
	 */
	void onMove(float delta);

	/**
	 * 刷新完成，要自己在刷新完成的时候自动调用
	 */
	void onComplete();

	/**
	 * 释放手指
	 * @return 释放进入刷新状态
	 */
	boolean onRelease();

	/**
	 * 改变刷新状态
	 * @param state 刷新状态
	 */
	void onStateChange(int state);

}
