package me.joybar.superwifi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import me.joybar.superwifi.base.BaseActivity;

public class AboutUsActivity extends BaseActivity {


	public static void launch(Context context) {
		Intent intent = new Intent(context, AboutUsActivity.class);
		context.startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		initToolbar((Toolbar) findViewById(R.id.toolbar), false);
	}
}
