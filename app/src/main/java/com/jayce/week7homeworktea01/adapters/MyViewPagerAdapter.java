package com.jayce.week7homeworktea01.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 会函 on 2016/11/11.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    private String[] titles = new String[5];

    public MyViewPagerAdapter(FragmentManager manager, List<Fragment> fragments, String[] titles) {
        super(manager);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
