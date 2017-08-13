package com.tuacy.example.refresh;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tuacy.example.R;

import com.tuacy.library.SlideData;
import com.tuacy.library.SlideWrap;
import com.tuacy.library.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class RefreshActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, RefreshActivity.class));
	}

	private Context        mContext;
	private SlideWrap      mListView;
	private RefreshAdapter mAdapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh);
		mContext = this;
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mListView = (SlideWrap) findViewById(R.id.draggable_list_refresh);
		mListView.setTitleBackground(mContext.getResources().getColor(R.color.colorAccent));
		/**
		 * 刷新
		 */
		mListView.getListView().setPullRefreshEnable(true);
		mListView.getListView().setPushLoadMoreEnable(true);
		mListView.getListView().setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onPullRefresh() {
				mAdapter.setData(obtainDraggableData());
				mListView.getListView().refreshComplete();
			}

			@Override
			public void onPushLoadMore() {
				mAdapter.appendContent(appendData());
				mListView.getListView().loadMoreComplete();
			}
		});
	}

	private void initEvent() {
		mAdapter = new RefreshAdapter(this, obtainDraggableData());
		mListView.setAdapter(mAdapter);

	}

	private void initData() {

	}

	private SlideData obtainDraggableData() {
		SlideData data = new SlideData();
		List<String> title = new ArrayList<>();
		title.add("区域");
		title.add("人口");
		title.add("财政");
		title.add("教育");
		title.add("学校");

		List<String> column1 = new ArrayList<>();
		column1.add("江西");
		column1.add("100");
		column1.add("100");
		column1.add("100");
		column1.add("100");

		List<String> column2 = new ArrayList<>();
		column2.add("萍乡");
		column2.add("100");
		column2.add("100");
		column2.add("100");
		column2.add("100");

		List<String> column3 = new ArrayList<>();
		column3.add("宜春");
		column3.add("100");
		column3.add("100");
		column3.add("100");
		column3.add("100");

		List<String> column4 = new ArrayList<>();
		column4.add("高安");
		column4.add("100");
		column4.add("100");
		column4.add("100");
		column4.add("100");

		List<String> column5 = new ArrayList<>();
		column5.add("九江");
		column5.add("100");
		column5.add("100");
		column5.add("100");
		column5.add("100");

		List<String> column6 = new ArrayList<>();
		column6.add("吉安");
		column6.add("100");
		column6.add("100");
		column6.add("100");
		column6.add("100");

		List<String> column7 = new ArrayList<>();
		column7.add("南丰");
		column7.add("100");
		column7.add("100");
		column7.add("100");
		column7.add("100");

		List<String> column8 = new ArrayList<>();
		column8.add("石脑");
		column8.add("100");
		column8.add("100");
		column8.add("100");
		column8.add("100");

		List<String> column9 = new ArrayList<>();
		column9.add("南昌");
		column9.add("100");
		column9.add("100");
		column9.add("100");
		column9.add("100");

		List<String> column10 = new ArrayList<>();
		column10.add("上栗");
		column10.add("100");
		column10.add("100");
		column10.add("100");
		column10.add("100");

		List<String> column11 = new ArrayList<>();
		column11.add("丰城");
		column11.add("100");
		column11.add("100");
		column11.add("100");
		column11.add("100");

		List<String> column12 = new ArrayList<>();
		column12.add("万载");
		column12.add("100");
		column12.add("100");
		column12.add("100");
		column12.add("100");

		List<List<String>> content = new ArrayList<>();
		content.add(column1);
		content.add(column2);
		content.add(column3);
		content.add(column4);
		content.add(column5);
		content.add(column6);
		content.add(column7);
		content.add(column8);
		content.add(column9);
		content.add(column10);
		content.add(column11);
		content.add(column12);
		content.add(column1);
		content.add(column2);
		content.add(column3);
		content.add(column4);
		content.add(column5);
		content.add(column6);
		content.add(column7);
		content.add(column8);
		content.add(column9);
		content.add(column10);
		content.add(column11);
		content.add(column12);
		data.setContent(content);
		data.setTitle(title);

		return data;
	}

	private List<List<String>> appendData() {
		List<List<String>> content = new ArrayList<>();
		List<String> column1 = new ArrayList<>();
		column1.add("江西");
		column1.add("100");
		column1.add("100");
		column1.add("100");
		column1.add("100");

		List<String> column2 = new ArrayList<>();
		column2.add("萍乡");
		column2.add("100");
		column2.add("100");
		column2.add("100");
		column2.add("100");

		List<String> column3 = new ArrayList<>();
		column3.add("宜春");
		column3.add("100");
		column3.add("100");
		column3.add("100");
		column3.add("100");
		content.add(column1);
		content.add(column2);
		content.add(column3);
		return content;
	}
}
