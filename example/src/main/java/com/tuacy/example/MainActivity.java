package com.tuacy.example;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tuacy.example.columndivider.ColumnDividerActivity;
import com.tuacy.example.refresh.RefreshActivity;
import com.tuacy.example.rowdivider.RowDividerActivity;
import com.tuacy.example.score.ScoreActivity;
import com.tuacy.example.weight.WeightActivity;

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
