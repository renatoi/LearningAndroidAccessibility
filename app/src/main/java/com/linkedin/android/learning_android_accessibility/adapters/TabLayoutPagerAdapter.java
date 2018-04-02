package com.linkedin.android.learning_android_accessibility.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.linkedin.android.learning_android_accessibility.fragments.TabLayoutFragment;

/**
 * A simple pager adapter
 */
public class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 3;
    private final String[] mData;

    public TabLayoutPagerAdapter(FragmentManager fm, String[] data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return TabLayoutFragment.newInstance(mData[position]);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}