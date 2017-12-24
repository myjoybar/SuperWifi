package me.joybar.superwifi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.joybar.library.tracker.TrackerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.joybar.superwifi.base.BaseActivity;
import me.joybar.superwifi.data.WifiCustomInfo;

public class WifiPasswordActivity extends BaseActivity {

    private static final String TAG = "WifiPasswordActivity";

    @BindView(R.id.wifi_name)
    TextView name;

    public static void  launch(Context context, WifiCustomInfo wifiCustomInfo){
        Intent intent = new Intent(context,WifiPasswordActivity.class);
        intent.putExtra(WifiCustomInfo.TAG,wifiCustomInfo);
        context.startActivity(intent);
    }

    public static Intent getStartIntent(Context context, WifiCustomInfo wifiCustomInfo) {
        Intent starter = new Intent(context,WifiPasswordActivity.class);
        starter.putExtra(WifiCustomInfo.TAG,wifiCustomInfo);
        return starter;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_pwd);
        ButterKnife.bind(this);
        initToolbar((Toolbar) findViewById(R.id.toolbar),true);
        TrackerUtil.sendScreenName(TAG);
        TrackerUtil.sentEvent("PAGE","enter page MainActivity");
        WifiCustomInfo wifiCustomInfo = getIntent().getParcelableExtra(WifiCustomInfo.TAG);
        name.setText(wifiCustomInfo.getSSIDName());
    }






}
