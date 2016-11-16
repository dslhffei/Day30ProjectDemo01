package com.jayce.week7homeworktea01.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 会函 on 2016/11/14.
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<ImageView> headerImages;

    public MyPagerAdapter(List<ImageView> headerImags) {
        this.headerImages = headerImags;
    }

    @Override
    public int getCount() {
        return headerImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = headerImages.get(position);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(headerImages.get(position));
    }
}
