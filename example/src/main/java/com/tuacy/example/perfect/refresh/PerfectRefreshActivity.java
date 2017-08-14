package com.tuacy.example.perfect.refresh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andview.refreshview.XRefreshView;
import com.tuacy.example.R;
import com.tuacy.example.perfect.title.PerfectTitleAdapter;
import com.tuacy.udlrslidelistview.UDLRSlideListView;

import java.util.ArrayList;
import java.util.List;

public class PerfectRefreshActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, PerfectRefreshActivity.class));
	}

	private XRefreshView      mRefreshView;
	private UDLRSlideListView mListView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_refresh_activity);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mRefreshView = (XRefreshView) findViewById(R.id.refresh_view);
		initRefreshView(mRefreshView);
		mRefreshView.setMoveForHorizontal(true);
		mListView = (UDLRSlideListView) findViewById(R.id.udls_list_view_refresh);
	}

	private void initEvent() {

	}

	private void initData() {
		mListView.setAdapter(new PerfectTitleAdapter(this, obtainDraggableData()));

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
		title.add("年龄|身高");
		title.add("152cm");
		title.add("156cm");
		title.add("160cm");
		title.add("164cm");
		title.add("168cm");
		title.add("172cm");
		title.add("176cm");
		title.add("180cm");
		title.add("184cm");
		title.add("188cm");

		List<String> column1 = new ArrayList<>();
		column1.add("19");
		column1.add("50");
		column1.add("56");
		column1.add("58");
		column1.add("60");
		column1.add("62");
		column1.add("64");
		column1.add("66");
		column1.add("68");
		column1.add("70");
		column1.add("72");
		List<String> column2 = new ArrayList<>();
		column2.add("21");
		column2.add("51");
		column2.add("53");
		column2.add("54");
		column2.add("55");
		column2.add("57");
		column2.add("60");
		column2.add("62");
		column2.add("65");
		column2.add("69");
		column2.add("72");
		List<String> column3 = new ArrayList<>();
		column3.add("23");
		column3.add("52");
		column3.add("53");
		column3.add("55");
		column3.add("56");
		column3.add("58");
		column3.add("60");
		column3.add("63");
		column3.add("66");
		column3.add("70");
		column3.add("73");
		List<String> column4 = new ArrayList<>();
		column4.add("25");
		column4.add("52");
		column4.add("54");
		column4.add("55");
		column4.add("57");
		column4.add("59");
		column4.add("61");
		column4.add("63");
		column4.add("67");
		column4.add("71");
		column4.add("74");
		List<String> column5 = new ArrayList<>();
		column5.add("27");
		column5.add("52");
		column5.add("54");
		column5.add("55");
		column5.add("57");
		column5.add("59");
		column5.add("61");
		column5.add("64");
		column5.add("67");
		column5.add("71");
		column5.add("74");
		List<String> column6 = new ArrayList<>();
		column6.add("29");
		column6.add("53");
		column6.add("55");
		column6.add("56");
		column6.add("57");
		column6.add("59");
		column6.add("61");
		column6.add("64");
		column6.add("67");
		column6.add("71");
		column6.add("74");
		List<String> column7 = new ArrayList<>();
		column7.add("19");
		column7.add("50");
		column7.add("56");
		column7.add("58");
		column7.add("60");
		column7.add("62");
		column7.add("64");
		column7.add("66");
		column7.add("68");
		column7.add("70");
		column7.add("72");
		List<String> column8 = new ArrayList<>();
		column8.add("21");
		column8.add("51");
		column8.add("53");
		column8.add("54");
		column8.add("55");
		column8.add("57");
		column8.add("60");
		column8.add("62");
		column8.add("65");
		column8.add("69");
		column8.add("72");
		List<String> column9 = new ArrayList<>();
		column9.add("23");
		column9.add("52");
		column9.add("53");
		column9.add("55");
		column9.add("56");
		column9.add("58");
		column9.add("60");
		column9.add("63");
		column9.add("66");
		column9.add("70");
		column9.add("73");
		List<String> column10 = new ArrayList<>();
		column10.add("25");
		column10.add("52");
		column10.add("54");
		column10.add("55");
		column10.add("57");
		column10.add("59");
		column10.add("61");
		column10.add("63");
		column10.add("67");
		column10.add("71");
		column10.add("74");
		List<String> column11 = new ArrayList<>();
		column11.add("27");
		column11.add("52");
		column11.add("54");
		column11.add("55");
		column11.add("57");
		column11.add("59");
		column11.add("61");
		column11.add("64");
		column11.add("67");
		column11.add("71");
		column11.add("74");
		List<String> column12 = new ArrayList<>();
		column12.add("29");
		column12.add("53");
		column12.add("55");
		column12.add("56");
		column12.add("57");
		column12.add("59");
		column12.add("61");
		column12.add("64");
		column12.add("67");
		column12.add("71");
		column12.add("74");
		List<String> column100 = new ArrayList<>();
		column100.add("19");
		column100.add("50");
		column100.add("56");
		column100.add("58");
		column100.add("60");
		column100.add("62");
		column100.add("64");
		column100.add("66");
		column100.add("68");
		column100.add("70");
		column100.add("72");
		List<String> column200 = new ArrayList<>();
		column200.add("21");
		column200.add("51");
		column200.add("53");
		column200.add("54");
		column200.add("55");
		column200.add("57");
		column200.add("60");
		column200.add("62");
		column200.add("65");
		column200.add("69");
		column200.add("72");
		List<String> column300 = new ArrayList<>();
		column300.add("23");
		column300.add("52");
		column300.add("53");
		column300.add("55");
		column300.add("56");
		column300.add("58");
		column300.add("60");
		column300.add("63");
		column300.add("66");
		column300.add("70");
		column300.add("73");
		List<String> column400 = new ArrayList<>();
		column400.add("25");
		column400.add("52");
		column400.add("54");
		column400.add("55");
		column400.add("57");
		column400.add("59");
		column400.add("61");
		column400.add("63");
		column400.add("67");
		column400.add("71");
		column400.add("74");
		List<String> column500 = new ArrayList<>();
		column500.add("27");
		column500.add("52");
		column500.add("54");
		column500.add("55");
		column500.add("57");
		column500.add("59");
		column500.add("61");
		column500.add("64");
		column500.add("67");
		column500.add("71");
		column500.add("74");
		List<String> column600 = new ArrayList<>();
		column600.add("29");
		column600.add("53");
		column600.add("55");
		column600.add("56");
		column600.add("57");
		column600.add("59");
		column600.add("61");
		column600.add("64");
		column600.add("67");
		column600.add("71");
		column600.add("74");
		List<String> column700 = new ArrayList<>();
		column700.add("19");
		column700.add("50");
		column700.add("56");
		column700.add("58");
		column700.add("60");
		column700.add("62");
		column700.add("64");
		column700.add("66");
		column700.add("68");
		column700.add("70");
		column700.add("72");
		List<String> column800 = new ArrayList<>();
		column800.add("21");
		column800.add("51");
		column800.add("53");
		column800.add("54");
		column800.add("55");
		column800.add("57");
		column800.add("60");
		column800.add("62");
		column800.add("65");
		column800.add("69");
		column800.add("72");
		List<String> column900 = new ArrayList<>();
		column900.add("23");
		column900.add("52");
		column900.add("53");
		column900.add("55");
		column900.add("56");
		column900.add("58");
		column900.add("60");
		column900.add("63");
		column900.add("66");
		column900.add("70");
		column900.add("73");
		List<String> column1000 = new ArrayList<>();
		column1000.add("25");
		column1000.add("52");
		column1000.add("54");
		column1000.add("55");
		column1000.add("57");
		column1000.add("59");
		column1000.add("61");
		column1000.add("63");
		column1000.add("67");
		column1000.add("71");
		column1000.add("74");
		List<String> column1100 = new ArrayList<>();
		column1100.add("27");
		column1100.add("52");
		column1100.add("54");
		column1100.add("55");
		column1100.add("57");
		column1100.add("59");
		column1100.add("61");
		column1100.add("64");
		column1100.add("67");
		column1100.add("71");
		column1100.add("74");
		List<String> column1200 = new ArrayList<>();
		column1200.add("29");
		column1200.add("53");
		column1200.add("55");
		column1200.add("56");
		column1200.add("57");
		column1200.add("59");
		column1200.add("61");
		column1200.add("64");
		column1200.add("67");
		column1200.add("71");
		column1200.add("74");

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
		content.add(column100);
		content.add(column200);
		content.add(column300);
		content.add(column400);
		content.add(column500);
		content.add(column600);
		content.add(column700);
		content.add(column800);
		content.add(column900);
		content.add(column1000);
		content.add(column1100);
		content.add(column1200);
		return content;
	}
}
