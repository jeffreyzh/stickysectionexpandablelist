/*
 * Copyright (c) 2013 Inkoniq
 * All Rights Reserved.
 * @since 05-Mar-2013 
 * @author Pushpan
 */
package com.example.expandablelistsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Pushpan
 * @param <T>
 */
public class IQStickyFlexibleListAdapter extends
		IQStickyFlexibleListBaseAdapter {

	/**
	 * @param context
	 * @return of type IQStickyFlexibleListAdapter Constructor function
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public IQStickyFlexibleListAdapter(Context context, IQStickyFlexibleListView stickyFlexibleListView) {
		super(context, stickyFlexibleListView);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.expandablelistsample.IQStickyFlexibleListBaseAdapter#
	 * getChildView(int, int, android.view.View)
	 * 
	 * @since 05-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			View convertView) {
		String item = (String) getChildItemAt(groupPosition, childPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.child_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.textView);
		textView.setText(item);
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.expandablelistsample.IQStickyFlexibleListBaseAdapter#
	 * getGroupView(int, android.view.View, boolean)
	 * 
	 * @since 05-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public View getGroupView(int groupPosition, View convertView,
			boolean isExpanded) {
		String item = (String) getGroupItemAt(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.group_header, null);
		}
		ImageView imageExpand = (ImageView) convertView
				.findViewById(R.id.imageExpand);
		imageExpand.setImageResource(isExpanded ? R.drawable.ic_collapse
				: R.drawable.ic_expand);
		TextView textView = (TextView) convertView.findViewById(R.id.textView);
		textView.setText(item);
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.expandablelistsample.IQStickyFlexibleListBaseAdapter#getMoreView
	 * (int, int, android.view.View)
	 * 
	 * @since 05-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public View getMoreView(int groupPosition, int childPosition,
			View convertView) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.more_item, null);
		}
		return convertView;
	}

	/* (non-Javadoc)
	 * @see com.example.expandablelistsample.IQStickyFlexibleListBaseAdapter#isGroupClickable(int)
	 * @since 30-Apr-2013
	 * @author Pushpan 
	 */
	@Override
	public boolean isGroupClickable(int position) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.example.expandablelistsample.IQStickyFlexibleListBaseAdapter#isChildClickable(int, int)
	 * @since 30-Apr-2013
	 * @author Pushpan 
	 */
	@Override
	public boolean isChildClickable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
