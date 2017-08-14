package com.tuacy.example.history;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tuacy.example.R;
import com.tuacy.example.history.columndivider.ColumnDividerActivity;
import com.tuacy.example.perfect.normal.PerfectNormalActivity;
import com.tuacy.example.history.refresh.RefreshActivity;
import com.tuacy.example.history.rowdivider.RowDividerActivity;
import com.tuacy.example.history.score.ScoreActivity;
import com.tuacy.example.history.weight.WeightActivity;

public class HistoryMainActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, HistoryMainActivity.class));
	}

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_main);
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

		findViewById(R.id.button_perfect).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PerfectNormalActivity.startUp(mContext);
			}
		});
	}

	private void initEvent() {

	}

	private void initData() {
	}
}
