package com.example.supermarket.app;

import android.app.Application;
import android.util.Log;

public class App extends Application {

    private static final String TAG = "App";

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate: App Started");

        //needs to run once to generate it
        Log.i(TAG, "onCreate: Setting Dagger");
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }


    public AppComponent getComponent() {
        return component;
    }

}