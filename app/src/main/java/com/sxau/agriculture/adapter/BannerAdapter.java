package com.sxau.agriculture.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * 图片轮播的adapter
 *
 * @author 崔志泽
 */
public class BannerAdapter extends PagerAdapter {
    private ArrayList<ImageView> views;
    private Context context;

    public BannerAdapter(ArrayList<ImageView> views, Context context) {
        this.views = views;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index = position % views.size();
//        ViewParent parent = views.get(index).getParent();
//        if (parent != null) {
//            container.removeView(views.get(index));
//        }
        ((ViewPager) container).addView(views.get(index));
        return views.get(index);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position % views.size()));
    }
}
