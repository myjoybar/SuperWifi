package me.joybar.superwifi.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.joybar.library.common.log.LogLevel;
import com.joybar.library.common.log.Logger;
import com.joybar.library.tracker.TrackerConfig;
import com.joybar.library.tracker.TrackerUtil;

import io.fabric.sdk.android.Fabric;
import me.joybar.superwifi.BuildConfig;
import me.joybar.superwifi.config.SDKConfig;

/**
 * Created by joybar on 17/12/2017.
 */

public class MyApplication extends Application {

    private static MyApplication INSTANCE = null;
    private static final String TAG = "MyApplication";

    public static MyApplication getInstance() {
        Log.d(TAG, "get application");
        return INSTANCE;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intiTracker();
        initLog();
        initFabric();
    }

    private void intiTracker() {
        if (BuildConfig.IS_DEBUG_TYPE) {
            TrackerUtil.createTracker(TrackerConfig.TYPE_GOOGLE, this, SDKConfig.GA_DEBUG_TRACK_ID);
        } else {
            TrackerUtil.createTracker(TrackerConfig.TYPE_GOOGLE, this, SDKConfig
                    .GA_RELEASE_TRACK_ID);

        }
    }

    private void initLog() {
        if (BuildConfig.IS_DEBUG_TYPE) {
            Logger.setLogEnable(false);
        }
        Logger.setLogLevel(LogLevel.TYPE_VERBOSE);
    }

    private void initFabric() {
        Fabric.with(this, new Crashlytics());

    }

}
