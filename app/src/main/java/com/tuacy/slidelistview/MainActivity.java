package com.tuacy.slidelistview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tuacy.slidelistview.columndivider.ColumnDividerActivity;
import com.tuacy.slidelistview.refresh.RefreshActivity;
import com.tuacy.slidelistview.rowdivider.RowDividerActivity;
import com.tuacy.slidelistview.score.ScoreActivity;
import com.tuacy.slidelistview.weight.WeightActivity;


public class MainActivity extends AppCompatActivity {

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		findViewById(R.id.button_weight).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WeightActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_score).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ScoreActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_row_divider).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RowDividerActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_column_divider).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ColumnDividerActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_column_refresh).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RefreshActivity.startUp(mContext);
			}
		});
	}

	private void initEvent() {

	}

	private void initData() {
	}

}
