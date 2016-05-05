package com.sxau.agriculture;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext(){
        return context;
    }
}
