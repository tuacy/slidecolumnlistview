package com.tuacy.example.perfect.divider;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuacy.example.R;
import com.tuacy.udlrslidelistview.UDLRSlideAdapter;

import java.util.List;


public class DividerAdapter extends UDLRSlideAdapter<String> {


	public DividerAdapter(Context context, List<List<String>> data) {
		super(context, data);
	}

	@Override
	public int getItemViewTitleHeight() {
		return (int) mContext.getResources().getDimension(R.dimen.board_item_title_height);
	}

	@Override
	public int getItemViewContentHeight() {
		return (int) mContext.getResources().getDimension(R.dimen.board_item_normal_height);
	}

	@Override
	public LinearLayout.LayoutParams getColumnViewParams(int position, int columnIndex, int columnCount) {
		if (columnIndex < mSlideStartColumn) {
			return new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.board_item_fixed_column_width),
												 ViewGroup.LayoutParams.MATCH_PARENT);
		} else {
			if (columnCount <= 4) {
				return new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
			} else {
				return new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.board_item_slide_column_width),
													 ViewGroup.LayoutParams.MATCH_PARENT);
			}

		}
	}

	@Override
	public View getColumnView(int position, int columnIndex, int columnCount, LinearLayout columnLayout) {
		if (columnIndex < mSlideStartColumn) {
			return LayoutInflater.from(mContext).inflate(R.layout.item_stare_fixed_column_cell, columnLayout, false);

		} else {
			return LayoutInflater.from(mContext).inflate(R.layout.item_stare_slide_column_cell, columnLayout, false);

		}
	}

	@Override
	public void convertColumnViewData(int position,
									  int columnIndex,
									  View columnView,
									  View rowView,
									  String columnData,
									  List<String> columnDataList) {
		String columnText = columnDataList.get(columnIndex);
		TextView textView;
		if (columnIndex < mSlideStartColumn) {
			textView = (TextView) columnView.findViewById(R.id.text_fixed_cell_item);
		} else {
			textView = (TextView) columnView.findViewById(R.id.text_slide_cell_item);
		}
		textView.setText(columnText);
		if (position != 0) {
			if (position % 3 == 0) {
				textView.setTextColor(getColor(mContext, R.color.stare_radio_1));
			} else if (position % 3 == 1) {
				textView.setTextColor(getColor(mContext, R.color.stare_radio_2));
			} else {
				textView.setTextColor(getColor(mContext, R.color.stare_radio_3));
			}
		}
	}

	@Override
	public void convertRowViewData(int position, View rowView, List<String> columnDataList) {
		if (position % 2 == 0) {
			rowView.setBackgroundResource(R.color.stare_item_1);
		} else {
			rowView.setBackgroundResource(R.color.stare_item_2);
		}
	}

	public static int getColor(Context context, @ColorRes int colorId) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
			//noinspection deprecation
			return context.getResources().getColor(colorId);
		} else {
			return context.getColor(colorId);
		}
	}

}
