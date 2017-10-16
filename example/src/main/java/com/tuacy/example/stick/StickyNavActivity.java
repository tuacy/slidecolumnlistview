package com.tuacy.example.stick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andview.refreshview.XRefreshView;
import com.tuacy.example.R;
import com.tuacy.example.perfect.normal.PerfectNormalAdapter;
import com.tuacy.udlrslidelistview.UDLRSlideListView;

import java.util.ArrayList;
import java.util.List;


public class StickyNavActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, StickyNavActivity.class));
	}

	private UDLRSlideListView mListView;
	private XRefreshView      mRefreshView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_sticky_nav_activity);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mRefreshView = (XRefreshView) findViewById(R.id.refresh_view);
		initRefreshView(mRefreshView);
		mRefreshView.setMoveForHorizontal(true);
		mListView = (UDLRSlideListView) findViewById(R.id.stick_nav_list_layout_id);
	}

	private void initEvent() {

	}

	private void initData() {
		mListView.setAdapter(new PerfectNormalAdapter(this, obtainDraggableData()));

	}

	private void initRefreshView(XRefreshView ptrFrame) {
		ptrFrame.setPullRefreshEnable(true);
		ptrFrame.setPullLoadEnable(true);
		ptrFrame.setAutoRefresh(false);
		ptrFrame.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
			@Override
			public void onRefresh() {
				//TODO:
				mRefreshView.stopLoadMore();
				mRefreshView.stopRefresh();
			}

			@Override
			public void onLoadMore(boolean isSilence) {
				//TODO:
				mRefreshView.stopLoadMore();
				mRefreshView.stopRefresh();
			}
		});
	}

	private List<List<String>> obtainDraggableData() {
		List<String> title = new ArrayList<>();
		title.add("姓名");
		title.add("语文");
		title.add("数学");
		title.add("英语");

		List<String> column1 = new ArrayList<>();
		column1.add("吴一");
		column1.add("100");
		column1.add("100");
		column1.add("100");

		List<String> column2 = new ArrayList<>();
		column2.add("吴一");
		column2.add("100");
		column2.add("100");
		column2.add("100");

		List<String> column3 = new ArrayList<>();
		column3.add("吴一");
		column3.add("100");
		column3.add("100");
		column3.add("100");

		List<String> column4 = new ArrayList<>();
		column4.add("吴一");
		column4.add("100");
		column4.add("100");
		column4.add("100");

		List<String> column5 = new ArrayList<>();
		column5.add("吴一");
		column5.add("100");
		column5.add("100");
		column5.add("100");

		List<String> column6 = new ArrayList<>();
		column6.add("吴一");
		column6.add("100");
		column6.add("100");
		column6.add("100");

		List<String> column7 = new ArrayList<>();
		column7.add("吴一");
		column7.add("100");
		column7.add("100");
		column7.add("100");

		List<String> column8 = new ArrayList<>();
		column8.add("吴一");
		column8.add("100");
		column8.add("100");
		column8.add("100");

		List<String> column9 = new ArrayList<>();
		column9.add("吴一");
		column9.add("100");
		column9.add("100");
		column9.add("100");

		List<String> column10 = new ArrayList<>();
		column10.add("吴一");
		column10.add("100");
		column10.add("100");
		column10.add("100");

		List<String> column11 = new ArrayList<>();
		column11.add("吴一");
		column11.add("100");
		column11.add("100");
		column11.add("100");

		List<String> column12 = new ArrayList<>();
		column12.add("吴一");
		column12.add("100");
		column12.add("100");
		column12.add("100");

		List<List<String>> content = new ArrayList<>();
		content.add(title);
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

		return content;
	}
}
