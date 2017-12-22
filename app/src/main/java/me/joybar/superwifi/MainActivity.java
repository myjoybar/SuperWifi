package me.joybar.superwifi;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.joybar.library.common.log.L;
import com.joybar.library.tracker.TrackerUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import me.joybar.superwifi.application.MyApplication;
import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.utils.WifiUtil;
import me.joybar.superwifi.wifiNew.PullParser;
import me.joybar.superwifi.wifiNew.Root;
import me.joybar.superwifi.wifiNew.WIfi2;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.tv_wifi_name);
        tv.setText(stringFromJNI());

        TrackerUtil.sendScreenName(TAG);
        TrackerUtil.sentEvent("PAGE","enter page MainActivity");
        TrackerUtil.sentEvent("PAGE","enter page MainActivity222");
        TrackerUtil.sentEvent("PAGE","enter page MainActivity333");
        TrackerUtil.sentEvent("PAGE","enter page MainActivity444");
        TrackerUtil.sentEvent("PAGE","enter page MainActivity555");
       // forceCrash();
        Log.i(TAG,"AAAA");
        L.d(TAG,"AAAA");

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

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ArrayList<WifiCustomInfo> list = new WifiPasswordManager().readWifiConfigFile();
//                L.d(TAG,list);
//            }
//        }).start();


        boolean flag_root = Root.isRoot();
        L.d("TAG", "check");
        L.d(TAG,"flag_root="+flag_root);


       if(flag_root){
           String wiFiConfigStr = WIfi2.getWifiConfigFileString(WIfi2.CAT+WIfi2.oreoFilePath[0]);
          // L.d("TAG", "wiFiConfigStr="+wiFiConfigStr);
           WIfi2.saveWifiConfigToFile(wiFiConfigStr);

           InputStream inputStream = String2InputStream(wiFiConfigStr);
           try {
               L.d(TAG,"start");
               PullParser.pull2xml(inputStream);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }else{
           L.d(TAG,"没有root");
       }



    }


    public static InputStream String2InputStream(String str) {
        ByteArrayInputStream stream = null;
        try {
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stream;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public void forceCrash() {
        throw new RuntimeException("This is a crash");
    }

}
