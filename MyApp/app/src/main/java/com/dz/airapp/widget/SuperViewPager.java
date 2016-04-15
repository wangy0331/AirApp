/*
 *	Copyright (c) 2013, Yulong Information Technologies
 *	All rights reserved.
 *  
 *  @author: Robot
 *	@email: feng88724@126.com
 */
package com.dz.airapp.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Overrider ViewPager
 * @author Robot
 * @date Sep 3, 2013
 */
public class SuperViewPager extends ViewPager {

	/**
	 * @param context
	 * @param attrs
	 */
	public SuperViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 */
	public SuperViewPager(Context context) {
		super(context);
	}

	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
	   if(v != this && v instanceof ViewPager) {
	      return true;
	   }
	   return super.canScroll(v, checkV, dx, x, y);
	}
}