package me.joybar.superwifi.utils;

import me.joybar.superwifi.BuildConfig;

/**
 * Created by joybar on 2018/1/9.
 */

public class AppUtil {

	public static int getVersionCode() {
		return BuildConfig.VERSION_CODE;
	}

	public static String getVersionName() {
		return BuildConfig.VERSION_NAME;
	}
}
