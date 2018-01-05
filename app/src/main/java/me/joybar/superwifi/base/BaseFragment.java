package me.joybar.superwifi.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by joybar on 2018/1/5.
 */

public class BaseFragment extends Fragment {
	public static String TAG;
	protected BaseActivity mActivity;

	//获取宿主Activity
	protected BaseActivity getHostActivity() {
		return mActivity;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (BaseActivity) activity;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getSimpleName();
	}

}
