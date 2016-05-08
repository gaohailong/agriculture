package com.sxau.agriculture.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 下拉刷新的swiprefershlayout
 * @author 崔志泽
 */
public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener{

    //滑动到最下面时的上拉操作
    private int Touchslop;
    private ListView listView;
    //上拉监听器，到了最底部的上拉加载操作




    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
