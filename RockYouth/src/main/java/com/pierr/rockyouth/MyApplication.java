package com.pierr.rockyouth;


import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;



public class MyApplication extends Application {
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {

        super.onCreate();

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {

        Log.d(MainActivity.TAG, "initialize image loader");

        ImageLoader.getInstance().init(context);

    }
}