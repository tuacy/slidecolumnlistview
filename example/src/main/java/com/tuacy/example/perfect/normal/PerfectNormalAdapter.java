package com.tuacy.example.perfect.normal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuacy.example.R;
import com.tuacy.udlrslidelistview.UDLRSlideAdapter;

import java.util.List;


public class PerfectNormalAdapter extends UDLRSlideAdapter<String> {

	public PerfectNormalAdapter(Context context) {
		super(context);
	}

	public PerfectNormalAdapter(Context context, List<List<String>> data) {
		super(context, data);
	}

	@Override
	public int getItemViewTitleHeight() {
		return (int) mContext.getResources().getDimension(R.dimen.item_height);
	}

	@Override
	public int getItemViewContentHeight() {
		return (int) mContext.getResources().getDimension(R.dimen.item_height);
	}

	@Override
	public LinearLayout.LayoutParams getColumnViewParams(int position, int columnIndex, int columnCount) {
		if (columnIndex < mSlideStartColumn) {
			return new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.item_fixed_column_width),
												 ViewGroup.LayoutParams.MATCH_PARENT);
		} else {
			return new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
		}
	}

	@Override
	public View getColumnView(int position, int columnIndex, int columnCount, LinearLayout columnLayout) {
		if (columnIndex < mSlideStartColumn) {
			return LayoutInflater.from(mContext).inflate(R.layout.item_draggable_fixed_column_cell, columnLayout, false);

		} else {
			return LayoutInflater.from(mContext).inflate(R.layout.item_draggable_slide_column_cell, columnLayout, false);

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
		if (columnIndex < mSlideStartColumn) {
			TextView textView = (TextView) columnView.findViewById(R.id.text_fixed_cell_item);
			textView.setText(columnText);
		} else {
			TextView textView = (TextView) columnView.findViewById(R.id.text_slide_cell_item);
			textView.setText(columnText);
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("tuacy", "onClick");
				}
			});
		}
		if (position == 0) {
			rowView.setBackgroundColor(mContext.getResources().getColor(R.color.color_even));
		} else {
			rowView.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
		}
	}

	@Override
	public void convertRowViewData(int position, View rowView, List<String> columnDataList) {

	}

}
