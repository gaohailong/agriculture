package com.sxau.agriculture;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;

/**
 * 全局的application
 * @author 高海龙
 */
public class AgricultureApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Bugtags.start("01edd61d98e9dc96753171559601a69a", this, Bugtags.BTGInvocationEventBubble);
    }

    public static Context getContext(){
        return context;
    }
}
