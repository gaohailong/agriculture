package com.sxau.agriculture;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bugtags.library.Bugtags;

import cn.jpush.android.api.JPushInterface;

/**
 * 全局的application
 * @author 高海龙
 */
public class AgricultureApplication extends Application {
    private static final String TAG = "JPush";
    private static Context context;

    @Override
    public void onCreate() {
        Log.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();
        context = getApplicationContext();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        Bugtags.start("01edd61d98e9dc96753171559601a69a", this, Bugtags.BTGInvocationEventBubble);

        FavoBeansHelper.init(this);
    }

    public static Context getContext(){
        return context;
    }
}
