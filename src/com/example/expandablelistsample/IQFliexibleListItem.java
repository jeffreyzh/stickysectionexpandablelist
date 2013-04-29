/*
 * Copyright (c) 2013 Inkoniq
 * All Rights Reserved.
 * @since 02-Mar-2013 
 * @author Pushpan
 */
package com.example.expandablelistsample;

/**
 * @author Pushpan
 */
public class IQFliexibleListItem {
	public static final int TYPE_HEADER = 0;
	public static final int TYPE_ITEM = 1;
	public static final int TYPE_MORE = 2;
	private int type;
	private Object item;
	private int actualGroupIndex;
	private int actualChildIndex;

	public IQFliexibleListItem(int type, Object item, int actualGroupIndex,
			int actualChildIndex) {
		this.setType(type);
		this.setItem(item);
		this.setActualGroupIndex(actualGroupIndex);
		this.setActualChildIndex(actualChildIndex);
	}

	/**
	 * @param of
	 *            type null
	 * @return type of type int getter function for type
	 * @since 02-Mar-2013
	 * @author Pushpan
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            of type int
	 * @return of type null setter function for type
	 * @since 02-Mar-2013
	 * @author Pushpan
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @param of
	 *            type null
	 * @return item of type Object getter function for item
	 * @since 02-Mar-2013
	 * @author Pushpan
	 */
	public Object getItem() {
		return item;
	}

	/**
	 * @param item
	 *            of type Object
	 * @return of type null setter function for item
	 * @since 02-Mar-2013
	 * @author Pushpan
	 */
	public void setItem(Object item) {
		this.item = item;
	}

	/**
	 * @param of
	 *            type null
	 * @return actualGroupIndex of type int getter function for actualGroupIndex
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public int getActualGroupIndex() {
		return actualGroupIndex;
	}

	/**
	 * @param actualGroupIndex
	 *            of type int
	 * @return of type null setter function for actualGroupIndex
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setActualGroupIndex(int actualGroupIndex) {
		this.actualGroupIndex = actualGroupIndex;
	}

	/**
	 * @param of
	 *            type null
	 * @return actualChildIndex of type int getter function for actualChildIndex
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public int getActualChildIndex() {
		return actualChildIndex;
	}

	/**
	 * @param actualChildIndex
	 *            of type int
	 * @return of type null setter function for actualChildIndex
	 * @since 05-Mar-2013
	 * @author Pushpan
	 */
	public void setActualChildIndex(int actualChildIndex) {
		this.actualChildIndex = actualChildIndex;
	}
}
