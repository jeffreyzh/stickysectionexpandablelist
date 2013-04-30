package com.example.expandablelistsample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class SampleActivity extends Activity {
	ArrayList<Object> headers = new ArrayList<Object>();
	ArrayList<ArrayList<Object>> children = new ArrayList<ArrayList<Object>>();
	IQStickyFlexibleListAdapter iQStickySectionAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IQStickyFlexibleListView stickyFlexibleListView = new IQStickyFlexibleListView(
				this);
		setContentView(stickyFlexibleListView);
		iQStickySectionAdapter = new IQStickyFlexibleListAdapter(this, stickyFlexibleListView);
		stickyFlexibleListView.setAdapter(iQStickySectionAdapter);

		headers.add("0");
		ArrayList<Object> item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");

		headers.add("1");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");

		headers.add("2");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("3");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("4");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("5");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("6");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("7");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("8");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("9");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("10");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("11");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("12");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("13");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("14");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("15");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("16");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");
		headers.add("17");
		item = new ArrayList<Object>();
		children.add(item);
		item.add("A");
		item.add("B");
		item.add("C");
		item.add("D");
		item.add("E");
		item.add("F");
		item.add("G");

		iQStickySectionAdapter.addAllGroups(headers, children);

		stickyFlexibleListView
				.setOnFlexibleChildClickListener(new OnFlexibleChildClickListener() {

					@Override
					public void onChildClicked(View v, int group, int position) {
						Log.e("CHILD CLICKED", "group=" + group + "  position="
								+ position);

					}
				});
		stickyFlexibleListView
				.setOnFlexibleHeaderClickListener(new OnFlexibleHeaderClickListener() {

					@Override
					public void onHeaderClicked(View v, int position) {
						Log.e("HEADER CLICKED", "  position=" + position);
					}
				});
		stickyFlexibleListView
				.setOnFlexibleMoreClickListener(new OnFlexibleMoreClickListener() {

					@Override
					public void onMoreClicked(View v, int group, int position) {
						Log.e("MORE CLICKED", "group=" + group + "  position="
								+ position);
						ArrayList<Object> newList = children.get(group);
						newList.add("TEST1");
						newList.add("TEST2");
						newList.add("TEST3");
						newList.add("TEST4");
						newList.add("TEST5");
						iQStickySectionAdapter.notifyDataSetChanged();

					}
				});
		stickyFlexibleListView.setEnableMore(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sample, menu);
		return true;
	}

}
