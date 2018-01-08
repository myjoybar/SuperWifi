package me.joybar.superwifi;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.joybar.library.common.log.L;
import com.joybar.library.net.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import me.joybar.superwifi.application.MyApplication;
import me.joybar.superwifi.base.BaseActivity;
import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.utils.ActivityUtils;
import me.joybar.superwifi.utils.WifiPasswordManager;
import me.joybar.superwifi.utils.WifiUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class MainActivity extends BaseActivity {

	private static final String TAG = "MainActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initToolbar((Toolbar) findViewById(R.id.toolbar), false);
		checkUpdate();
		//initFragment();

	}


	private void checkUpdate() {
		L.d("checkUpdate");


		final ApiService api = RetrofitClient.getClient().create(ApiService.class);
		Call<String> call = api.getBaidu();
		call.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				L.d("response="+response.body());
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				L.d("onFailure");
			}
		});




//		final ApiService api = RetrofitClient.getClient().create(ApiService.class);
//		Call<UpdateInfo> call = api.getUpdateInfo1();
//		call.enqueue(new Callback<UpdateInfo>() {
//			@Override
//			public void onResponse(Call<UpdateInfo> call, Response<UpdateInfo> response) {
//				L.d("response="+response.body());
//			}
//
//			@Override
//			public void onFailure(Call<UpdateInfo> call, Throwable t) {
//				L.d("onFailure");
//
//			}
//		});



//		api.getUpdateInfo().subscribeOn(Schedulers.io()) //在IO线程进行网络请求
//				.observeOn(AndroidSchedulers.mainThread()) //回到主线程去处理请求结果
//				.subscribe(new Observer<UpdateInfo>() {
//					@Override
//					public void onSubscribe(Disposable d) {
//						L.d("onSubscribe");
//					}
//
//					@Override
//					public void onNext(UpdateInfo updateInfo) {
//						L.d("onNext"+updateInfo.toString());
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						L.d("onError");
//					}
//
//					@Override
//					public void onComplete() {
//						L.d("onComplete");
//					}
//				});

//		OkHttpClient mOkHttpClient = new OkHttpClient();
//		final Request request = new Request.Builder()
//				.url("http://106.14.139.72:8198/superwifi/updateinfo")
//				.build();
//		Call call = mOkHttpClient.newCall(request);
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				L.d("onFailure");
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				L.d("onResponse"+response.toString());
//			}
//		});


//        UpdateDialog updateDialog = new UpdateDialog(MainActivity.this, "发现新版本，点击确定系统将自动开始下载安装", "升级提示","",isForceUpdate);
//        updateDialog.setMyOnTouchingLetterChangedListener(new IConfirmDialog() {
//            @Override
//            public void doClick() {
//
//                // 直接安装安卓6.0.1 有bug, 跳转到应用宝
//                String qqdownloader = "com.tencent.android.qqdownloader";
//                String pkgName = "com.xiaoyu.right";
//                String url = "http://a.app.qq.com/o/simple.jsp?pkgname="+pkgName;
//
//                MarketUtil.launchAppDetail(getApplicationContext(), pkgName, qqdownloader, url);
//
//                // ApkUpdateUtils.download(LaunchActivity.this, updateInfo.getDownload_url(), getResources().getString(R.string.app_name));
//            }
//
//            @Override
//            public void doCancel() {
//                if(isForceUpdate){
//                    Logger.d(TAG,"强制升级");
//                    ToastUtil.toastShortShow("请更新到最新版本");
//                    finish();
//                }else{
//                    Logger.d(TAG,"不是强制升级");
//                    mPresenter.checkHasLogin();
//                }
//
//            }
//        });
//        updateDialog.show();
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
		List<ScanResult> WifiScanResults = WifiUtil.getWifiScanResults(MyApplication.getInstance().getApplicationContext());
		List<WifiCustomInfo> listWifiInfo = WifiUtil.getWifiInfoList(MyApplication.getInstance().getApplicationContext());
		L.d(TAG, listWifiInfo);

		WifiCustomInfo wifiCustomInfo = WifiUtil.getConnectedWifiInfo(MyApplication.getInstance().getApplicationContext());
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
