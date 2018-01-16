package me.joybar.superwifi.application;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.joybar.library.common.log.L;
import com.joybar.library.common.log.LogLevel;
import com.joybar.library.common.wiget.SnackBarUtils;
import com.joybar.library.net.retrofit.config.RetrofitConfig;
import com.joybar.library.tracker.TrackerConfig;
import com.joybar.library.tracker.TrackerUtil;

import io.fabric.sdk.android.Fabric;
import me.joybar.superwifi.BuildConfig;
import me.joybar.superwifi.R;
import me.joybar.superwifi.config.SDKConfig;

/**
 * Created by joybar on 17/12/2017.
 */

public class SuperWIfiApplication extends Application {

	private static SuperWIfiApplication INSTANCE = null;
	private static final String TAG = "MyApplication";
	public static SuperWIfiApplication getInstance() {
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
		initRetrofit();
		initSnackBarStyle();
	}

	private void intiTracker() {
		if (BuildConfig.IS_DEBUG_TYPE) {
			TrackerUtil.createTracker(TrackerConfig.TYPE_GOOGLE, this, SDKConfig.GA_DEBUG_TRACK_ID);
		} else {
			TrackerUtil.createTracker(TrackerConfig.TYPE_GOOGLE, this, SDKConfig.GA_RELEASE_TRACK_ID);
		}
	}

	private void initLog() {
		if (!BuildConfig.IS_DEBUG_TYPE) {
			L.setLogEnable(false);
		}
		L.setLogLevel(LogLevel.TYPE_VERBOSE);
	}

	private void initFabric() {
		Fabric.with(this, new Crashlytics());
	}

	private void initRetrofit() {
		if (BuildConfig.IS_DEBUG_TYPE) {
			RetrofitConfig.setBaseUrl("http://106.14.139.72:8198/superwifi/");
			RetrofitConfig.setDebug(true);
		}else{
			RetrofitConfig.setDebug(false);
			RetrofitConfig.setBaseUrl("http://106.14.139.72:8199/superwifi/");
		}
	}

	private void initSnackBarStyle() {
		SnackBarUtils.setTextColor(ContextCompat.getColor(this, R.color.white));
		SnackBarUtils.setBgColor(ContextCompat.getColor(this, R.color.colorPrimary));
	}

}
