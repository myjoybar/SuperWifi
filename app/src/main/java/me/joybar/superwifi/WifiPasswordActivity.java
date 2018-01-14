package me.joybar.superwifi;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.joybar.library.tracker.TrackerUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.joybar.superwifi.base.BaseActivity;
import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.ga.GAType;
import me.joybar.superwifi.utils.anni.TextSharedElementCallback;

import static me.joybar.superwifi.utils.WifiConfigParse.NO_PASSWORD_TEXT;

public class WifiPasswordActivity extends BaseActivity {

	private static final String TAG = "WifiPasswordActivity";

	@BindView(R.id.wifi_title)
	TextView tvWifiTitle;

	@BindView(R.id.wifi_pwd)
	TextView tvWifiPwd;

	@BindView(R.id.back)
	ImageButton mToolbarBack;

	public static void launch(Context context, WifiCustomInfo wifiCustomInfo) {
		Intent intent = new Intent(context, WifiPasswordActivity.class);
		intent.putExtra(WifiCustomInfo.TAG, wifiCustomInfo);
		context.startActivity(intent);
	}

	public static Intent getStartIntent(Context context, WifiCustomInfo wifiCustomInfo) {
		Intent starter = new Intent(context, WifiPasswordActivity.class);
		starter.putExtra(WifiCustomInfo.TAG, wifiCustomInfo);
		return starter;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_pwd);
		ButterKnife.bind(this);
		WifiCustomInfo wifiCustomInfo = getIntent().getParcelableExtra(WifiCustomInfo.TAG);
		init(wifiCustomInfo);
		setToolbarAnni();
		setContentAnni();

	}

	private void init(WifiCustomInfo wifiCustomInfo) {
		tvWifiTitle.setText(wifiCustomInfo.getSSIDName());
	//	tvWifiTitle.setTextColor(ContextCompat.getColor(this, ColorSelector.getRandomColorID()));
		tvWifiPwd.setText(wifiCustomInfo.getConfigKeyPwd());
	//	tvWifiPwd.setTextColor(ContextCompat.getColor(this, ColorSelector.PURPLE.getColorId()));

	}

	@OnClick(R.id.wifi_pwd)
	void copy(View view) {
		if(!NO_PASSWORD_TEXT.equals(tvWifiPwd.getText().toString().trim())){
			ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
			cmb.setText(tvWifiPwd.getText().toString().trim());
			Snackbar.make(tvWifiPwd,WifiPasswordActivity.this.getString(R.string.copy_success), Snackbar.LENGTH_LONG).show();
			TrackerUtil.sentEvent(TAG, GAType.CLICK_COPY_WITH_PWD);
		}else{
			TrackerUtil.sentEvent(TAG, GAType.CLICK_COPY_WITH_NOPWD);
		}


	}
	@OnClick(R.id.back)
	void finish(View view) {
		onBackPressed();
		ViewCompat.animate(mToolbarBack).scaleX(0f).scaleY(0f).alpha(0f).setDuration(100).start();
		ViewCompat.animate(tvWifiPwd)
				.scaleX(.7f)
				.scaleY(.7f)
				.alpha(0f)
				.setInterpolator(new FastOutSlowInInterpolator())
				.start();

	}
	private void setToolbarAnni() {
		int categoryNameTextSize = getResources().getDimensionPixelSize(R.dimen.category_item_text_size);
		int paddingStart = getResources().getDimensionPixelSize(R.dimen.spacing_double);
		final int startDelay = getResources().getInteger(R.integer.toolbar_transition_duration);
		ActivityCompat.setEnterSharedElementCallback(this, new TextSharedElementCallback(categoryNameTextSize, paddingStart) {
			@Override
			public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
				super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
				mToolbarBack.setScaleX(0f);
				mToolbarBack.setScaleY(0f);
			}

			@Override
			public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
				super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
				// Make sure to perform this animation after the transition has ended.
				ViewCompat.animate(mToolbarBack).setStartDelay(startDelay).scaleX(1f).scaleY(1f).alpha(1f);
			}
		});
	}

	private void setContentAnni(){
		ViewCompat.animate(tvWifiPwd)
				.scaleX(1)
				.scaleY(1)
				.alpha(1)
				.setInterpolator( new FastOutSlowInInterpolator())
				.setStartDelay(300)
				.start();
	}

}
