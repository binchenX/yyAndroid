package com.pierr.rockyouth;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.pierr.rockyouth.activity.MainActivity;


public class MyApplication extends Application {
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {

        super.onCreate();

        initImageLoader(getApplicationContext());
    }

    private static void initImageLoader(Context context) {

        Log.d(MainActivity.TAG, "initialize image loader");

        ImageLoader.getInstance().init(context);

    }
}