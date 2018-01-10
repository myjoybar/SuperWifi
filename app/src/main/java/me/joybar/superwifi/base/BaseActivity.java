package me.joybar.superwifi.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.joybar.library.tracker.TrackerUtil;

/**
 * Created by joybar on 4/19/16.
 */
public class BaseActivity extends ToolbarActivity {

    public static String TAG;
    public Context mContext;
    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mContext = this.getApplicationContext();
        mActivity = this;
        TrackerUtil.sendScreenName(TAG);
        TrackerUtil.sentEvent("PAGE", TAG);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
