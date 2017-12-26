package me.joybar.superwifi;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.joybar.library.common.log.L;
import com.joybar.library.tracker.TrackerUtil;

import java.util.ArrayList;
import java.util.List;

import me.joybar.superwifi.application.MyApplication;
import me.joybar.superwifi.base.BaseActivity;
import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.utils.ActivityUtils;
import me.joybar.superwifi.utils.WifiPasswordManager;
import me.joybar.superwifi.utils.WifiUtil;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar((Toolbar) findViewById(R.id.toolbar),false);
        TrackerUtil.sendScreenName(TAG);
        TrackerUtil.sentEvent("PAGE","enter page MainActivity");
        initFragment();
        supportPostponeEnterTransition();

    }



    private void initFragment(){
        FragmentPassWordList fragment =
                (FragmentPassWordList) getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (fragment == null) {
            // Create the fragment
            fragment = FragmentPassWordList.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.fl_container);
        }

    }


private void test(){
    List<ScanResult> WifiScanResults = WifiUtil.getWifiScanResults(MyApplication.getInstance().getApplicationContext());
    List<WifiCustomInfo> listWifiInfo = WifiUtil.getWifiInfoList(MyApplication.getInstance().getApplicationContext());
    L.d(TAG,listWifiInfo);

    WifiCustomInfo wifiCustomInfo = WifiUtil.getConnectedWifiInfo(MyApplication.getInstance().getApplicationContext());
    L.d(TAG,wifiCustomInfo);

    int wifi = wifiCustomInfo.getRssi();//获取wifi信号强度
    String wifiStrength  ="";
    if (wifi > -50 && wifi < 0) {//最强
        wifiStrength = "最强";
    } else if (wifi > -70 && wifi < -50) {//较强
        wifiStrength = "较强";
    } else if (wifi > -80 && wifi < -70) {//较弱
        wifiStrength = "较弱";
    } else if (wifi > -100 && wifi < -80) {//微弱
        wifiStrength = "微弱";
    }

    L.d(TAG,"wifiStrength="+wifiStrength);

    new Thread(new Runnable() {
        @Override
        public void run() {
            ArrayList<WifiCustomInfo> list = new WifiPasswordManager().readWifiConfigFile();
            L.d(TAG,list);
        }
    }).start();
}



}
