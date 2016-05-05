package com.sxau.agriculture;

import android.app.Application;
import android.content.Context;

/**
 * Created by gaohailong on 2016/4/6.
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
