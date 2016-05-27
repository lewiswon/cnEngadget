package com.lewiswon.android_mooc;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by Administrator on 05/27/2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.init(getApplicationContext());
    }
}
