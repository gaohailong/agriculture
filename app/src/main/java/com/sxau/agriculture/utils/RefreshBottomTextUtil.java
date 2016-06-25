package com.sxau.agriculture.utils;

import android.view.View;
import android.widget.TextView;

/**
 * 下拉刷新底部文字
 *
 * @author 高海龙
 */
public class RefreshBottomTextUtil {
    /**
     * 改变底部文字的方法
     *
     * @param tv_more 要改变的控件
     * @param type    传入要改变的类型
     */
    public static void setTextMore(TextView tv_more, int type) {
        class myClickListener implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                //什么也不做了
            }
        }
        myClickListener myClickListener = new myClickListener();
        switch (type) {
            case ConstantUtil.LOAD_MORE:
                tv_more.setText("点击加载更多...");
                break;
            case ConstantUtil.LOAD_FAIL:
                tv_more.setText("数据加载失败");
                break;
            case ConstantUtil.LOAD_OVER:
                tv_more.setText("没有更多了");
                //设置不能再点击
                tv_more.setOnClickListener(myClickListener);
                tv_more.setFocusable(false);
                tv_more.setSelected(false);
                tv_more.setClickable(false);
//                tv_more.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }
}
