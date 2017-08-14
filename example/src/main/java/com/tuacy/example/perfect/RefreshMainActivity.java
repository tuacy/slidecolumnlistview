package com.tuacy.example.perfect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tuacy.example.R;
import com.tuacy.example.perfect.normal.PerfectNormalActivity;
import com.tuacy.example.perfect.refresh.PerfectRefreshActivity;
import com.tuacy.example.perfect.title.PerfectTitleActivity;


public class RefreshMainActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, RefreshMainActivity.class));
	}

	private Context mContext;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh_main);
		mContext = this;
		initView();
		initEvent();
		initData();
	}

	private void initView() {

	}

	private void initEvent() {
		findViewById(R.id.button_perfect_normal).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PerfectNormalActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_perfect_title).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PerfectTitleActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_perfect_refresh).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PerfectRefreshActivity.startUp(mContext);
			}
		});
	}

	private void initData() {

	}
}
