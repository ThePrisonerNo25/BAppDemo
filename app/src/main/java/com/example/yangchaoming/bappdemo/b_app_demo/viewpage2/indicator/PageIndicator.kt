package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2.indicator

import androidx.viewpager2.widget.ViewPager2


interface PageIndicator  {

    /**
     * Bind the indicator to a ViewPager.
     *
     * @param view
     */
    fun setViewPager(view: ViewPager2)

    /**
     * Bind the indicator to a ViewPager.
     *
     * @param view
     * @param initialPosition
     */
    fun setViewPager(view: ViewPager2, initialPosition: Int)

    /**
     *
     * Set the current page of both the ViewPager and indicator.
     *
     *
     * This **must** be used if you need to set the page before
     * the views are drawn on screen (e.g., default start page).
     *
     * @param item
     */
    fun setCurrentItem(item: Int)

    /**
     * Set a page change listener which will receive forwarded events.
     *
     * @param listener
     */
    fun setOnPageChangeListener(listener: ViewPager2.OnPageChangeCallback?)

    /**
     * Notify the indicator that the fragment list has changed.
     */
    fun notifyDataSetChanged()
}