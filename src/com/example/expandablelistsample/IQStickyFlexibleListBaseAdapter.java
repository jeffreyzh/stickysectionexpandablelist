/*
 * Copyright (c) 2013 Inkoniq
 * All Rights Reserved.
 * @since 22-Feb-2013 
 * @author Pushpan
 */
package com.example.expandablelistsample;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * @author Pushpan
 */
@SuppressLint("UseSparseArrays")
public abstract class IQStickyFlexibleListBaseAdapter extends BaseAdapter
		implements OnClickListener {

	private Context context;
	private boolean clickedFromHeader = false;
	private IQStickyFlexibleListView stickyFlexibleListView;

	/**
	 * @param of
	 *            type null
	 * @return context of type Context getter function for context
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            of type Context
	 * @return of type null setter function for context
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	private ArrayList<Object> headers = new ArrayList<Object>();
	private ArrayList<ArrayList<Object>> children = new ArrayList<ArrayList<Object>>();

	private ArrayList<IQFliexibleListItem> list = new ArrayList<IQFliexibleListItem>();
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Boolean> expandStates = new HashMap<Integer, Boolean>();
	private HashMap<Integer, Boolean> moreStates = new HashMap<Integer, Boolean>();
	private OnFlexibleHeaderClickListener onFlexibleHeaderClickListener;
	private OnFlexibleChildClickListener onFlexibleChildClickListener;
	private OnFlexibleMoreClickListener onFlexibleMoreClickListener;
	private boolean enableMore;

	/**
	 * @param context2
	 * @param listView
	 * @return of type IQStickyFlexibleListBaseAdapter Constructor function
	 * @since 29-Apr-2013
	 * @author Pushpan
	 */
	public IQStickyFlexibleListBaseAdapter(Context context,
			IQStickyFlexibleListView stickyFlexibleListView) {
		this.context = context;
		this.stickyFlexibleListView = stickyFlexibleListView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 * 
	 * @since 02-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public final int getCount() {
		return calculateActualCountAndAdjustPositions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 * 
	 * @since 02-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public final Object getItem(int position) {
		return (list != null && list.size() > position) ? list.get(position)
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 * 
	 * @since 02-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 * 
	 * @since 02-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {
		IQFliexibleListItem item = (IQFliexibleListItem) getItem(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.section_item, null);
		}
		LinearLayout groupLayout = (LinearLayout) convertView
				.findViewById(R.id.groupLayout);
		LinearLayout childLayout = (LinearLayout) convertView
				.findViewById(R.id.childLayout);
		LinearLayout moreLayout = (LinearLayout) convertView
				.findViewById(R.id.moreLayout);

		switch (item.getType()) {
		case IQFliexibleListItem.TYPE_HEADER:
			groupLayout.setVisibility(View.VISIBLE);
			childLayout.setVisibility(View.GONE);
			moreLayout.setVisibility(View.GONE);
			groupLayout.setOnClickListener(this);
			groupLayout.setTag(position);
			if (groupLayout.getChildCount() == 0) {
				groupLayout.addView(
						getGroupView(item.getActualGroupIndex(), null,
								expandStates.get(item.getActualGroupIndex())),
						new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT));
			} else {
				getGroupView(item.getActualGroupIndex(),
						groupLayout.getChildAt(0),
						expandStates.get(item.getActualGroupIndex()));
			}
			break;
		case IQFliexibleListItem.TYPE_ITEM:
			groupLayout.setVisibility(View.GONE);
			childLayout.setVisibility(View.VISIBLE);
			moreLayout.setVisibility(View.GONE);
			childLayout.setOnClickListener(this);
			childLayout.setTag(position);
			if (childLayout.getChildCount() == 0) {
				childLayout.addView(
						getChildView(item.getActualGroupIndex(),
								item.getActualChildIndex(), null),
						new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT));
			} else {
				getChildView(item.getActualGroupIndex(),
						item.getActualChildIndex(), childLayout.getChildAt(0));
			}
			break;
		case IQFliexibleListItem.TYPE_MORE:
			groupLayout.setVisibility(View.GONE);
			childLayout.setVisibility(View.GONE);
			moreLayout.setVisibility(View.VISIBLE);
			moreLayout.setOnClickListener(this);
			moreLayout.setTag(position);
			if (moreLayout.getChildCount() == 0) {
				moreLayout.addView(
						getMoreView(item.getActualGroupIndex(),
								item.getActualChildIndex(), null),
						new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT));
			} else {
				getMoreView(item.getActualGroupIndex(),
						item.getActualChildIndex(), moreLayout.getChildAt(0));
			}
			break;
		}
		return convertView;
	}

	/**
	 * This should be overidden by the decendent class to return the view for a
	 * child
	 * 
	 * @param actualGroupIndex
	 * @param actualGroupIndex2
	 * @param childAt
	 *            of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public abstract View getChildView(int groupPosition, int childPosition,
			View convertView);

	/**
	 * *This should be overidden by the decendent class to return the view for a
	 * header
	 * 
	 * @param groupPosition
	 * @param convertView
	 * @param isExpanded
	 * @return of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public abstract View getGroupView(int groupPosition, View convertView,
			boolean isExpanded);

	/**
	 * *This should be overidden by the decendent class to return the view for a
	 * more view
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @param convertView
	 * @return of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public abstract View getMoreView(int groupPosition, int childPosition,
			View convertView);

	/**
	 * returns the number of total items
	 * 
	 * @return of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	private int calculateActualCountAndAdjustPositions() {
		int count = 0;
		for (int i = 0; i < headers.size(); i++) {
			count++;
			if (expandStates.get(i)) {
				count += children.get(i).size();
				if (isEnableMore() && moreStates.get(i)) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Adds all the header & their children, the the size of header list & child
	 * list should be equal so that a list of children can be associated with
	 * each header, if there is no children for a particular header then the
	 * arraylist of children should contain 0 items
	 * 
	 * @param headers
	 * @param children
	 *            of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void addAllGroups(ArrayList<Object> headers,
			ArrayList<ArrayList<Object>> children) {
		if (headers != null && children != null) {
			if (headers.size() == children.size()) {
				this.headers = headers;
				this.children = children;
				for (int i = 0; i < headers.size(); i++) {
					expandStates.put(i, false);
					if (isEnableMore()) {
						moreStates.put(i, true);
					}
				}
				notifyDataSetChanged();
			}
		}
	}

	public void addGroup(Object header, ArrayList<Object> arrayList) {
		if (header != null) {
			int actualGroupIndex = headers.size();
			expandStates.put(actualGroupIndex, false);
			moreStates.put(actualGroupIndex, true);
			headers.add(header);
			arrayList = (arrayList != null) ? arrayList
					: new ArrayList<Object>();
			children.add(arrayList);
			notifyDataSetChanged();
		}
	}

	/**
	 * adjusts the views in the list according to the data on the list
	 * 
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	private void adjustViews() {
		list.clear();
		for (int i = 0; i < headers.size(); i++) {
			Object header = headers.get(i);
			if (header != null) {
				int actualGroupIndex = i;
				list.add(new IQFliexibleListItem(
						IQFliexibleListItem.TYPE_HEADER, header,
						actualGroupIndex, -1));
				if (expandStates.get(actualGroupIndex)) {
					ArrayList<Object> child = children.get(i);
					for (int j = 0; j < child.size(); j++) {
						list.add(new IQFliexibleListItem(
								IQFliexibleListItem.TYPE_ITEM, child,
								actualGroupIndex, j));
					}
					// added more button
					if (isEnableMore() && moreStates.get(actualGroupIndex)) {
						list.add(new IQFliexibleListItem(
								IQFliexibleListItem.TYPE_MORE, null,
								actualGroupIndex, -1));
					}
				}
			}
		}
		calculateActualCountAndAdjustPositions();
		super.notifyDataSetChanged();
	}

	/**
	 * removes a particular group from the list
	 * 
	 * @param groupPosition
	 *            of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void removeGroup(int groupPosition) {
		// adjust expanded & more states
		for (int i = groupPosition; i < (headers.size() - 1); i++) {
			expandStates.put(i, expandStates.get(i + 1));
			moreStates.put(i, moreStates.get(i + 1));
		}
		headers.remove(groupPosition);
		children.remove(groupPosition);
		notifyDataSetChanged();
	}

	/**
	 * expands the item at the the position if its a header & returns true
	 * otherwise does nothing & returns false, it will also return false if the
	 * view is already expanded
	 * 
	 * @param position
	 * @return of type
	 * @since 02-Mar-2013
	 * @author Pushpan
	 */
	public final boolean expand(int position) {
		boolean performedExpansion = false;
		IQFliexibleListItem item = (IQFliexibleListItem) getItem(position);
		if (item.getType() == IQFliexibleListItem.TYPE_HEADER) {
			int actualGroupIndex = item.getActualGroupIndex();
			boolean isExpanded = expandStates.get(actualGroupIndex);
			if (isExpanded) {
				performedExpansion = false;
			} else {
				ArrayList<IQFliexibleListItem> childList = new ArrayList<IQFliexibleListItem>();
				for (int i = 0; i < children.get(actualGroupIndex).size(); i++) {
					childList.add(new IQFliexibleListItem(
							IQFliexibleListItem.TYPE_ITEM, children.get(
									actualGroupIndex).get(i), actualGroupIndex,
							i));
				}
				// added more button
				if (isEnableMore() && moreStates.get(actualGroupIndex)) {
					childList.add(new IQFliexibleListItem(
							IQFliexibleListItem.TYPE_MORE, null,
							actualGroupIndex, -1));
				}
				list.addAll(position + 1, childList);

				expandStates.put(actualGroupIndex, true);
				performedExpansion = true;
			}
		}
		return performedExpansion;
	}

	/**
	 * collapse the item at the the position if its a header & returns true
	 * otherwise does nothing & returns false, it will also return false if the
	 * view is already collapse
	 * 
	 * @param position
	 * @return of type
	 * @since 02-Mar-2013
	 * @author Pushpan
	 */
	public final boolean collapse(final int position) {
		boolean performedCollapse = false;
		IQFliexibleListItem item = (IQFliexibleListItem) getItem(position);
		if (item.getType() == IQFliexibleListItem.TYPE_HEADER) {
			int actualGroupIndex = item.getActualGroupIndex();
			boolean isExpanded = expandStates.get(actualGroupIndex);
			if (!isExpanded) {
				performedCollapse = false;
			} else {
				int totalItemsTobeRemoved = 0;
				totalItemsTobeRemoved = children.get(actualGroupIndex).size();
				for (int i = 0; i < totalItemsTobeRemoved; i++) {
					list.remove(actualGroupIndex + 1);
				}
				// removed more button
				if (isEnableMore() && moreStates.get(actualGroupIndex)) {
					list.remove(actualGroupIndex + 1);
				}
				expandStates.put(actualGroupIndex, false);
				performedCollapse = true;
			}
		}
		if (clickedFromHeader) {
			stickyFlexibleListView.removeHeader();
			clickedFromHeader = false;
			stickyFlexibleListView.postDelayed(new Runnable() {
				@Override
				public void run() {
					stickyFlexibleListView.setSelection(position);
				}
			}, 100);

		}
		return performedCollapse;
	}

	/**
	 * returns the expanded status of the header
	 * 
	 * @param position
	 * @return of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public boolean isExpanded(int position) {
		return expandStates.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.expandablelistsample.OnFlexibleHeaderClickListener#
	 * onHeaderClicked(android.view.View, int)
	 * 
	 * @since 04-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		IQFliexibleListItem item = (IQFliexibleListItem) getItem(position);
		switch (v.getId()) {
		case R.id.groupLayout:
			View parent = (View) v.getParent().getParent();
			if (parent != null && parent.getId() == R.id.headerLayout) {
				clickedFromHeader = true;
			}
			if (isExpanded(item.getActualGroupIndex())) {
				collapse(position);
			} else {
				expand(position);
			}
			if (getOnFlexibleHeaderClickListener() != null) {
				getOnFlexibleHeaderClickListener().onHeaderClicked(v,
						item.getActualGroupIndex());
			}
			clickedFromHeader = false;
			break;
		case R.id.childLayout:
			if (getOnFlexibleChildClickListener() != null) {
				getOnFlexibleChildClickListener().onChildClicked(v,
						item.getActualGroupIndex(), item.getActualChildIndex());
			}

			break;
		case R.id.moreLayout:
			if (getOnFlexibleMoreClickListener() != null) {
				getOnFlexibleMoreClickListener().onMoreClicked(v,
						item.getActualGroupIndex(), item.getActualChildIndex());
			}
			break;
		}

		notifyDataSetChanged();
	}

	/**
	 * @param position
	 * @return of type
	 * @since 04-Mar-2013
	 * @author Pushpan
	 */
	public boolean isHeader(int position) {
		IQFliexibleListItem item = (IQFliexibleListItem) getItem(position);
		return item.getType() == IQFliexibleListItem.TYPE_HEADER;
	}

	/**
	 * returns the actual group index of the header
	 * 
	 * @param position
	 * @return of type
	 * @since 04-Mar-2013
	 * @author Pushpan
	 */
	public int getGroupIndex(int position) {
		IQFliexibleListItem item = (IQFliexibleListItem) getItem(position);
		return item.getActualGroupIndex();
	}

	/**
	 * returns the current index of the header item on the displaying list
	 * 
	 * @param actualGroupIndex
	 * @return of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public int getCurrentIndexOfHeader(int actualGroupIndex) {
		for (int i = 0; i < list.size(); i++) {
			IQFliexibleListItem item = (IQFliexibleListItem) getItem(i);
			if (item.getType() == IQFliexibleListItem.TYPE_HEADER
					&& item.getActualGroupIndex() == actualGroupIndex) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param of
	 *            type null
	 * @return onFlexibleHeaderClickListener of type
	 *         OnFlexibleHeaderClickListener getter function for
	 *         onFlexibleHeaderClickListener
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public OnFlexibleHeaderClickListener getOnFlexibleHeaderClickListener() {
		return onFlexibleHeaderClickListener;
	}

	/**
	 * @param onFlexibleHeaderClickListener
	 *            of type OnFlexibleHeaderClickListener
	 * @return of type null setter function for onFlexibleHeaderClickListener
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setOnFlexibleHeaderClickListener(
			OnFlexibleHeaderClickListener onFlexibleHeaderClickListener) {
		this.onFlexibleHeaderClickListener = onFlexibleHeaderClickListener;
	}

	/**
	 * @param of
	 *            type null
	 * @return onFlexibleChildClickListener of type OnFlexibleChildClickListener
	 *         getter function for onFlexibleChildClickListener
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public OnFlexibleChildClickListener getOnFlexibleChildClickListener() {
		return onFlexibleChildClickListener;
	}

	/**
	 * @param onFlexibleChildClickListener
	 *            of type OnFlexibleChildClickListener
	 * @return of type null setter function for onFlexibleChildClickListener
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setOnFlexibleChildClickListener(
			OnFlexibleChildClickListener onFlexibleChildClickListener) {
		this.onFlexibleChildClickListener = onFlexibleChildClickListener;
	}

	/**
	 * @param of
	 *            type null
	 * @return onFlexibleMoreClickListener of type OnFlexibleMoreClickListener
	 *         getter function for onFlexibleMoreClickListener
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public OnFlexibleMoreClickListener getOnFlexibleMoreClickListener() {
		return onFlexibleMoreClickListener;
	}

	/**
	 * @param onFlexibleMoreClickListener
	 *            of type OnFlexibleMoreClickListener
	 * @return of type null setter function for onFlexibleMoreClickListener
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setOnFlexibleMoreClickListener(
			OnFlexibleMoreClickListener onFlexibleMoreClickListener) {
		this.onFlexibleMoreClickListener = onFlexibleMoreClickListener;
	}

	/**
	 * returns the associated data item for a particular header
	 * 
	 * @param groupPosition
	 * @return of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public Object getGroupItemAt(int groupPosition) {
		return (headers != null && headers.size() > groupPosition) ? headers
				.get(groupPosition) : null;
	}

	/**
	 * returns the associated data item for a particular child
	 * 
	 * @param groupPosition
	 * @param childPosition
	 * @return of type
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public Object getChildItemAt(int groupPosition, int childPosition) {
		ArrayList<Object> childList = (children != null && children.size() > groupPosition) ? children
				.get(groupPosition) : null;
		if (childList != null && childList.size() > childPosition) {
			return childList.get(childPosition);
		}
		return null;
	}

	/**
	 * @param of
	 *            type null
	 * @return enableMore of type boolean getter function for enableMore
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public boolean isEnableMore() {
		return enableMore;
	}

	/**
	 * @param enableMore
	 *            enables a feture where use can click a more button on child
	 *            views to add more items of type boolean
	 * @return of type null setter function for enableMore
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setEnableMore(boolean enableMore) {
		this.enableMore = enableMore;
		if (enableMore && headers != null) {
			for (int i = 0; i < headers.size(); i++) {
				if (isEnableMore()) {
					moreStates.put(i, true);
				}
			}
		}
	}

	/**
	 * clears all the list
	 * 
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void clearAllItems() {
		if (list != null) {
			list.clear();
			headers.clear();
			children.clear();
			expandStates.clear();
			moreStates.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#notifyDataSetChanged()
	 * 
	 * @since 05-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public void notifyDataSetChanged() {
		adjustViews();
	}

}
