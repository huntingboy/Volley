package com.nomad.app;

import android.app.Application;

import com.nomad.network.NetworkManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(getApplicationContext());
    }
}
