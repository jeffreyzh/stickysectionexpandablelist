/*
 * Copyright (c) 2013 Inkoniq
 * All Rights Reserved.
 * @since 22-Feb-2013 
 * @author Pushpan
 */
package com.example.expandablelistsample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * @author Pushpan
 */
public class IQStickyFlexibleListView extends RelativeLayout implements
		OnScrollListener {
	private ListView expandableListView;
	private LinearLayout headerViewContainer;
	private View headerView;
	private int stickyGroupIndex = -1;
	private int headerViewHeight;
	private OnFlexibleHeaderClickListener onFlexibleHeaderClickListener;
	private OnFlexibleChildClickListener onFlexibleChildClickListener;
	private OnFlexibleMoreClickListener onFlexibleMoreClickListener;
	private boolean enableMore;

	// private HashMap<Integer, View> groupCacheList = new HashMap<Integer,
	// View>();

	/**
	 * @param context
	 * @return of type IQStickyFlexibleListView Constructor function
	 * @since 22-Feb-2013
	 * @author Pushpan
	 */
	public IQStickyFlexibleListView(Context context) {
		super(context);
		initialize();
	}

	/**
	 * @param context
	 * @param attrs
	 * @return of type IQStickyFlexibleListView Constructor function
	 * @since 22-Feb-2013
	 * @author Pushpan
	 */
	public IQStickyFlexibleListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 * @return of type IQStickyFlexibleListView Constructor function
	 * @since 22-Feb-2013
	 * @author Pushpan
	 */
	public IQStickyFlexibleListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	/**
	 * of type
	 * 
	 * @since 22-Feb-2013
	 * @author Pushpan
	 */
	private void initialize() {
		inflate(getContext(), R.layout.expandable_section_list, this);
		expandableListView = (ListView) findViewById(R.id.expandablelist);
		expandableListView.setOnScrollListener(this);
		headerViewContainer = (LinearLayout) findViewById(R.id.headerLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.
	 * AbsListView, int, int, int)
	 * 
	 * @since 04-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		View firstVisibleView = expandableListView.getChildAt(0);
		// is the view is available
		if (firstVisibleView != null) {
			// if items are greater than 1 then check is needed
			if (visibleItemCount > 1) {
				// check whether its a header
				if (checkIfHeader(firstVisibleItem)) {
					int firstVisibleViewTop = firstVisibleView.getTop();
					// check if exactly visible on border
					if (firstVisibleViewTop == 0) {
						// remove previous header if any
						removeHeader();
					}// check if partially visible
					else if (isPartiallyVisible(firstVisibleView,
							firstVisibleItem)) {
						// check if sicky header is required
						if (checkIfHeader(firstVisibleItem + 1)) {
							// sticky header not required as its not expanded
							// Remove header in case its there
							if (headerView != null) {
								removeHeader();
							}
						} else {
							// header is expanded has child views
							int groupIndex = getRelatedGroupIndex(firstVisibleItem);
							if (groupIndex != stickyGroupIndex) {
								switchToHeader(groupIndex);
							} else {
								// header already added
							}
						}
					}

				}// its a child
				else {
					// check if the child is followed by a group header
					View secondVisibleView = expandableListView.getChildAt(1);
					// check if the view is a groupheader or not & the child is
					// the last child of the last header
					if (checkIfHeader(firstVisibleItem + 1)) {
						// the next item is a header so the current header need
						// to squize if the child is partially visible
						if (isPartiallyVisible(firstVisibleView,
								firstVisibleItem)) {

							int groupIndex = getRelatedGroupIndex(firstVisibleItem);
							// check if there is any header already
							if (stickyGroupIndex != groupIndex) {
								// no header so the previous header need to be
								// added
								switchToHeader(groupIndex);
							}
							autoScrollHeader(secondVisibleView);
						}
					} else {
						// its child view so nothing required
						// reset the header view with its full height. Its a way
						// around the problem where the header gets partially
						// visible if the scroll is very fast
						if (headerView != null) {
							LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) headerView
									.getLayoutParams();
							if (params.topMargin != 0) {
								params.topMargin = 0;
								headerViewContainer.requestLayout();
							}
						}
					}

				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android
	 * .widget.AbsListView, int)
	 * 
	 * @since 04-Mar-2013
	 * 
	 * @author Pushpan
	 */
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param firstVisibleView
	 * @return of type
	 * @since 23-Feb-2013
	 * @author Pushpan
	 */
	private boolean checkIfHeader(int position) {
		IQStickyFlexibleListBaseAdapter adapter = (IQStickyFlexibleListBaseAdapter) expandableListView
				.getAdapter();
		return adapter.isHeader(position);
	}

	/**
	 * of type
	 * 
	 * @since 23-Feb-2013
	 * @author Pushpan
	 */
	public void removeHeader() {
		if (headerView != null) {
			headerViewContainer.removeView(headerView);
		}
		headerView = null;
		stickyGroupIndex = -1;
	}

	/**
	 * @param view
	 * @return of type
	 * @since 23-Feb-2013
	 * @author Pushpan
	 */
	private boolean isPartiallyVisible(View view, int firstVisibleItem) {
		return view != null && view.getTop() < 0 && view.getBottom() >= 0;
	}

	/**
	 * @param position
	 * @return of type
	 * @since 23-Feb-2013
	 * @author Pushpan
	 */
	private int getRelatedGroupIndex(int position) {
		IQStickyFlexibleListBaseAdapter adapter = (IQStickyFlexibleListBaseAdapter) expandableListView
				.getAdapter();
		return adapter.getGroupIndex(position);
	}

	/**
	 * @param firstVisibleView
	 *            of type
	 * @since 23-Feb-2013
	 * @author Pushpan
	 */
	private void switchToHeader(int actualGroupIndex) {
		removeHeader();
		IQStickyFlexibleListBaseAdapter adapter = (IQStickyFlexibleListBaseAdapter) expandableListView
				.getAdapter();
		int currentIndex = adapter.getCurrentIndexOfHeader(actualGroupIndex);
		if (currentIndex != -1) {
			headerView = adapter.getView(currentIndex, null, this);
			headerViewContainer.addView(headerView,
					new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
			headerViewContainer.requestLayout();
			stickyGroupIndex = actualGroupIndex;
			if (headerViewHeight == 0) {
				postDelayed(new Runnable() {

					@Override
					public void run() {
						headerViewHeight = headerViewContainer.getHeight();
					}
				}, 200);
			}
		}

		headerViewContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("HEADER", "Click");
			}
		});
	}

	private void autoScrollHeader(View visibleView) {
		// removing all the padding due to the previous cells
		int headerViewTop = visibleView.getTop();
		if (headerViewTop <= headerViewHeight) {
			// reached the 1st header
			// start contracting header
			if (headerView != null) {
				LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) headerView
						.getLayoutParams();
				params.topMargin = (headerViewTop - headerViewHeight);
				headerViewContainer.requestLayout();
			}
		}
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
		IQStickyFlexibleListBaseAdapter adapter = (IQStickyFlexibleListBaseAdapter) expandableListView
				.getAdapter();
		if (adapter != null) {
			adapter.setOnFlexibleHeaderClickListener(onFlexibleHeaderClickListener);
		}
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
		IQStickyFlexibleListBaseAdapter adapter = (IQStickyFlexibleListBaseAdapter) expandableListView
				.getAdapter();
		if (adapter != null) {
			adapter.setOnFlexibleChildClickListener(onFlexibleChildClickListener);
		}
	}

	/**
	 * @param IQStickyFlexibleListBaseAdapter
	 *            of type
	 * @since 04-Mar-2013
	 * @author Pushpan
	 */
	public void setAdapter(IQStickyFlexibleListBaseAdapter adapter) {
		expandableListView.setAdapter(adapter);
		if (adapter != null) {
			adapter.setOnFlexibleChildClickListener(onFlexibleChildClickListener);
			adapter.setOnFlexibleHeaderClickListener(onFlexibleHeaderClickListener);
			adapter.setOnFlexibleMoreClickListener(onFlexibleMoreClickListener);
			setEnableMore(isEnableMore());
		}
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
	 *            of type boolean
	 * @return of type null setter function for enableMore
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setEnableMore(boolean enableMore) {
		this.enableMore = enableMore;
		IQStickyFlexibleListBaseAdapter adapter = (IQStickyFlexibleListBaseAdapter) expandableListView
				.getAdapter();
		if (adapter != null) {
			adapter.setEnableMore(enableMore);
		}
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
		IQStickyFlexibleListBaseAdapter adapter = (IQStickyFlexibleListBaseAdapter) expandableListView
				.getAdapter();
		if (adapter != null) {
			adapter.setOnFlexibleMoreClickListener(onFlexibleMoreClickListener);
		}
	}
	
	public void setSelection(int index){
		expandableListView.setSelection(index);
	}
}
