package me.joybar.superwifi;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joybar.library.common.app.AppMarketUtil;
import com.joybar.library.common.log.L;
import com.joybar.library.common.wiget.SnackBarUtils;
import com.joybar.library.common.wiget.ToastUtil;
import com.joybar.library.io.sp.SPOneDayLimitTimesUtils;
import com.joybar.library.net.retrofit.RetrofitClient;
import com.joybar.library.tracker.TrackerUtil;
import com.joybar.libupdate.UpdateDialog;
import com.joybar.libupdate.data.UpdateInfo;
import com.joybar.libupdate.iml.IConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.joybar.superwifi.application.SuperWIfiApplication;
import me.joybar.superwifi.base.BaseActivity;
import me.joybar.superwifi.data.BaseResult;
import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.ga.GAType;
import me.joybar.superwifi.utils.ActivityUtils;
import me.joybar.superwifi.utils.AppUtil;
import me.joybar.superwifi.utils.WifiPasswordManager;
import me.joybar.superwifi.utils.WifiUtil;
import service.ApiService;

public class MainActivity extends BaseActivity {

	public static final String UPDATE_TIP_TIMES = "update_tip_times";

	@BindView(R.id.progress)
	ProgressBar progressBar;

	@BindView(R.id.tv_error)
	TextView tvError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initToolbar((Toolbar) findViewById(R.id.toolbar), false);
		//checkUpdate();
		SnackBarUtils.showLong((Toolbar) findViewById(R.id.toolbar),this.getString(R.string.grant_root));
		initFragment();
	}

	private void checkUpdate() {


		int updateTipTimes = SPOneDayLimitTimesUtils.getAlreadyRecordTimesByRecordTAG(SuperWIfiApplication.getInstance().getApplicationContext(),
				UPDATE_TIP_TIMES);
		if(updateTipTimes<1){
			ApiService api = RetrofitClient.getClient().create(ApiService.class);
			api.getUpdateInfo().subscribeOn(Schedulers.io()) //在IO线程进行网络请求
					.observeOn(AndroidSchedulers.mainThread()) //回到主线程去处理请求结果
					.subscribe(new Observer<BaseResult<UpdateInfo>>() {
						@Override
						public void onSubscribe(Disposable d) {
							L.d("onSubscribe");
						}

						@Override
						public void onNext(BaseResult<UpdateInfo> baseResult) {
							final UpdateInfo updateInfo = baseResult.getData();
							TrackerUtil.sentEvent(TAG, GAType.REQUEST_SUCCESS);
							if (updateInfo.getVersionCode() > AppUtil.getVersionCode()) {
								UpdateDialog updateDialog = new UpdateDialog(MainActivity.this, updateInfo.getUpdateTitle(), updateInfo
										.getUpdateContentList(), updateInfo.isForceUpdate());
								updateDialog.setMyOnTouchingLetterChangedListener(new IConfirmDialog() {
									@Override
									public void doClick() {
										TrackerUtil.sentEvent(TAG, GAType.CLICK_OK);
										AppMarketUtil.gotoAppShop(mContext, "me.joybar.superwifi", new AppMarketUtil.OpenAppMarketCallback() {
											@Override
											public void openResultCallback(boolean success) {
												ToastUtil.showLong(mContext, success + "");
											}
										});
									}

									@Override
									public void doCancel() {
										TrackerUtil.sentEvent(TAG, GAType.CLICK_CANCEL);
										if (updateInfo.isForceUpdate()) {
											ToastUtil.showLong(mContext, "请更新到最新版本");
											finish();
										} else {
											initFragment();
										}
									}
								});
								updateDialog.show();
							} else {
								initFragment();
							}
						}

						@Override
						public void onError(Throwable e) {
							progressBar.setVisibility(View.GONE);
							tvError.setVisibility(View.VISIBLE);
							TrackerUtil.sentEvent(TAG, GAType.REQUEST_FAIL);

						}

						@Override
						public void onComplete() {
							SPOneDayLimitTimesUtils.saveRecordTimesByAdRecordTAG(SuperWIfiApplication.getInstance().getApplicationContext(),UPDATE_TIP_TIMES);
						}
					});
		}else{
			initFragment();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_feedback:
				Snackbar.make(findViewById(R.id.toolbar),MainActivity.this.getString(R.string.contact_us_with_email), Snackbar.LENGTH_LONG).show();
				TrackerUtil.sentEvent(TAG, GAType.CLICK_SUPPORT);
				break;
//			case R.id.menu_about_us:
//				AboutUsActivity.launch(mActivity);
//				break;
			default:
				break;
		}
		return true;
	}

	private void initFragment() {
		FragmentPassWordList fragment = (FragmentPassWordList) getSupportFragmentManager().findFragmentById(R.id.fl_container);
		if (fragment == null) {
			// Create the fragment
			fragment = FragmentPassWordList.newInstance();
			ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_container);
		}

	}


	private void test() {
		List<ScanResult> WifiScanResults = WifiUtil.getWifiScanResults(SuperWIfiApplication.getInstance().getApplicationContext());
		List<WifiCustomInfo> listWifiInfo = WifiUtil.getWifiInfoList(SuperWIfiApplication.getInstance().getApplicationContext());
		L.d(TAG, listWifiInfo);

		WifiCustomInfo wifiCustomInfo = WifiUtil.getConnectedWifiInfo(SuperWIfiApplication.getInstance().getApplicationContext());
		L.d(TAG, wifiCustomInfo);

		int wifi = wifiCustomInfo.getRssi();//获取wifi信号强度
		String wifiStrength = "";
		if (wifi > -50 && wifi < 0) {//最强
			wifiStrength = "最强";
		} else if (wifi > -70 && wifi < -50) {//较强
			wifiStrength = "较强";
		} else if (wifi > -80 && wifi < -70) {//较弱
			wifiStrength = "较弱";
		} else if (wifi > -100 && wifi < -80) {//微弱
			wifiStrength = "微弱";
		}

		L.d(TAG, "wifiStrength=" + wifiStrength);

		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<WifiCustomInfo> list = new WifiPasswordManager().readWifiConfigFile();
				L.d(TAG, list);
			}
		}).start();
	}


}
