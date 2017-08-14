package com.tuacy.example;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tuacy.example.history.HistoryMainActivity;
import com.tuacy.example.perfect.RefreshMainActivity;

public class MainActivity extends AppCompatActivity {

	private Context mContext;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		initView();
		initEvent();
		initData();
	}

	private void initView() {

	}

	private void initEvent() {
		findViewById(R.id.button_history_version).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HistoryMainActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_new_version).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RefreshMainActivity.startUp(mContext);
			}
		});
	}

	private void initData() {

	}
}
