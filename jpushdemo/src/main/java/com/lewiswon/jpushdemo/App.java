package com.lewiswon.jpushdemo;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lewis on 16/6/1.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(getApplicationContext());
        JPushInterface.setDebugMode(true);
    }
}
