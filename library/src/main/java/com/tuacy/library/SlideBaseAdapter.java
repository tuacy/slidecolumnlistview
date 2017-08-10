package com.tuacy.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.List;

public abstract class SlideBaseAdapter extends BaseAdapter implements SlideListener {

	/**
	 * 数据改变监听
	 */
	public interface OnDataChangeListener {

		void onAdapterDataChange();
	}

	/**
	 * list view 水平滑动到的位置(getView里面convertView不复用的时候要滑动到制定的位置)
	 */
	private   int                  mColumnSlideTo;
	protected Context              mContext;
	private   SlideData            mData;
	/**
	 * 从哪个column位置开始可以滑动
	 */
	protected int                  mSlideColumnStart;
	private   OnDataChangeListener mOnDataChangeListener;

	public SlideBaseAdapter(Context context) {
		this(context, null);
	}

	public SlideBaseAdapter(Context context, SlideData data) {
		mContext = context;
		mData = data;
		mSlideColumnStart = 0;
	}

	public void setData(SlideData data) {
		mData = data;
		mColumnSlideTo = 0;
		notifyDataSetChanged();
	}

	public SlideData getData() {
		return mData;
	}

	public void setContent(List<List<String>> content) {
		if (mData == null) {
			mData = new SlideData();
		}
		mData.setContent(content);
		notifyDataSetChanged();
	}

	public void appendContent(List<List<String>> content) {
		if (mData == null) {
			mData = new SlideData();
		}
		if (mData.getContent() == null) {
			mData.setContent(content);
		} else {
			List<List<String>> history = mData.getContent();
			for (List<String> item : content) {
				history.add(item);
			}
			mData.setContent(history);
		}
		notifyDataSetChanged();

	}

	public void setSlideColumnStart(int start) {
		mSlideColumnStart = start;
		mColumnSlideTo = 0;
		notifyDataSetChanged();
	}

	public void setOnDataChangeListener(OnDataChangeListener listener) {
		mOnDataChangeListener = listener;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		// 检查标题的column的个数和内容的column个数是否对应
		if (mData != null) {
			if (mData.getTitle() != null && mData.getContent() != null) {
				for (List<String> item : mData.getContent()) {
					if (item == null) {
						throw new IllegalArgumentException("invalid content column (one column null)");
					}
					if (item.size() != mData.getTitle().size()) {
						throw new IllegalArgumentException("tile column and content column mismatching");
					}
				}
			}
		}
		if (mOnDataChangeListener != null) {
			mOnDataChangeListener.onAdapterDataChange();
		}
	}

	@Override
	public int getCount() {
		if (mData == null || mData.getContent() == null) {
			return 0;
		}
		return mData.getContent().size();
	}

	@Override
	public List<String> getItem(int position) {
		return mData.getContent().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		List<String> itemData = getItem(position);
		SlideViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_column_draggable_wrap, parent, false);
			//设置item高度
			AbsListView.LayoutParams params = (AbsListView.LayoutParams) convertView.getLayoutParams();
			params.height = getItemViewHeight();
			convertView.setLayoutParams(params);
			holder = new SlideViewHolder(convertView, position);
			LinearLayout fixedLayout = (LinearLayout) convertView.findViewById(R.id.column_draggable_item_fixed_id);
			SlideColumnWrap slideLayout = (SlideColumnWrap) convertView.findViewById(
				R.id.column_draggable_item_drag_id);
			//组合每一行的View,包含两部分，一个是固定的LinearLayout,一个是可滑动的LinearLayout
			if (itemData != null && !itemData.isEmpty()) {
				for (int index = 0; index < itemData.size(); index++) {
					View columnView;
					if (index < mSlideColumnStart) {
						columnView = getFixedColumnView(position, index, itemData.size(), fixedLayout);
						columnView.setLayoutParams(getColumnWidth(position, index, itemData.size()));
						fixedLayout.addView(columnView);
					} else {
						columnView = getSlideColumnView(position, index, itemData.size(), slideLayout);
						columnView.setLayoutParams(getColumnWidth(position, index, itemData.size()));
						slideLayout.addView(columnView);
					}
					holder.addColumnView(index, columnView);
				}
			}
			convertView.setTag(holder);
		} else {
			holder = (SlideViewHolder) convertView.getTag();
			//更新下holder position的位置
			holder.setPosition(position);
		}
		//复用的时候不能滑倒指定位置
		SlideColumnWrap slideLayout = (SlideColumnWrap) holder.getConvertView()
															  .findViewById(R.id.column_draggable_item_drag_id);
		slideLayout.scrollTo(mColumnSlideTo, 0);

		if (itemData != null && !itemData.isEmpty()) {
			for (int index = 0; index < itemData.size(); index++) {
				if (holder.getColumnView(index) != null) {
					convertColumnViewData(position, index, holder.getColumnView(index), convertView, itemData.get(index), itemData);
				}
			}
		}
		return convertView;
	}

	public int getColumnSlideTo() {
		return mColumnSlideTo;
	}

	@Override
	public void onColumnSlideListener(int setX) {
		mColumnSlideTo = setX;
	}

	/**
	 * list view每一个item的高度
	 *
	 * @return item height
	 */
	public abstract int getItemViewHeight();

	/**
	 * 行里面，每个column的宽度
	 *
	 * @param position    item position
	 * @param columnIndex column下标
	 * @param columnCount 总数
	 * @return column params
	 */
	public abstract LinearLayout.LayoutParams getColumnWidth(int position, int columnIndex, int columnCount);

	/**
	 * 获取固定column view
	 *
	 * @param position          item position
	 * @param columnIndex       column 下标
	 * @param columnCount       总数
	 * @param fixedColumnLayout 固定column的父布局
	 * @return column view
	 */
	public abstract View getFixedColumnView(int position, int columnIndex, int columnCount, LinearLayout fixedColumnLayout);

	/**
	 * 获取可滑动的column view
	 *
	 * @param position          item position
	 * @param columnIndex       column 下标
	 * @param columnCount       总数
	 * @param slideColumnLayout 可滑动column的父布局
	 * @return column view
	 */
	public abstract View getSlideColumnView(int position, int columnIndex, int columnCount, SlideColumnWrap slideColumnLayout);

	/**
	 * 绑定数据
	 *
	 * @param position       item position
	 * @param columnIndex    column 下标
	 * @param columnView     column view
	 * @param rowView        row view
	 * @param columnData     adapter data
	 * @param columnDataList column list data
	 */
	public abstract void convertColumnViewData(int position,
											   int columnIndex,
											   View columnView,
											   View rowView,
											   String columnData,
											   List<String> columnDataList);

}
