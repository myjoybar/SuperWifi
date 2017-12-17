package me.joybar.superwifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.joybar.library.tracker.TrackerUtil;

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
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        TrackerUtil.sendScreenName(TAG);
        TrackerUtil.sentEvent("PAGE","enter page MainActivity");
        forceCrash();
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
