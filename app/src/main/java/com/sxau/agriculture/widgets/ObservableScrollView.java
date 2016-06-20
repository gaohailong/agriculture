package com.sxau.agriculture.widgets;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuebin on 15/9/23.
 */
public class ObservableScrollView extends NestedScrollView {

    private List<OnScrollListener> listenerList = new ArrayList<>();

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        for (OnScrollListener listener : listenerList) {
            listener.onScroll(t - oldt);
        }
    }

    public void addOnScrollListener(OnScrollListener listener) {
        listenerList.add(listener);
    }

    public interface OnScrollListener {

        void onScroll(int distance);
    }

}
