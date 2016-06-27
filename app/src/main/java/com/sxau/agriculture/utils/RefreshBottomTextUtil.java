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
        switch (type) {
            case ConstantUtil.LOAD_MORE:
                tv_more.setText("点击加载更多...");
                break;
            case ConstantUtil.LOAD_FAIL:
                tv_more.setText("数据加载失败");
                break;
            case ConstantUtil.LOADINDG:
                tv_more.setText("正在加载...");
                break;
            case ConstantUtil.LOAD_OVER:
                tv_more.setText(ConstantUtil.NO_MORE);
                break;
            default:
                break;
        }

    }
}
