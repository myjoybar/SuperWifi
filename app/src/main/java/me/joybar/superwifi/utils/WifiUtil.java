package me.joybar.superwifi.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

import me.joybar.superwifi.data.WifiCustomInfo;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by joybar on 2017/12/18.
 */

public class WifiUtil {

	private static final String TAG = "WifiUtil";

	public static List<ScanResult> getWifiScanResults(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();    //当前wifi连接信息
		List<ScanResult> scanResults = wifiManager.getScanResults();//搜索到的设备列表
		return scanResults;
	}


	private boolean enable;
	private String BSSID;
	private String SSID;
	private String ipAddress;
	private int networkID;

	private int linkSpeed;
	private int level;
	private int rssi;


	public static List<WifiCustomInfo> getWifiInfoList(Context context) {
		List<ScanResult> WifiScanResults = getWifiScanResults(context);
		if(null !=WifiScanResults){
			List<WifiCustomInfo> listWifiInfo = new ArrayList<>();
			for (ScanResult scanResult : WifiScanResults) {

				WifiCustomInfo wifiCustomInfo = new WifiCustomInfo();


				wifiCustomInfo.setBSSID(scanResult.BSSID);
				wifiCustomInfo.setSSID(scanResult.SSID);
				wifiCustomInfo.setIpAddress("");
				wifiCustomInfo.setNetworkID(0);
				wifiCustomInfo.setLinkSpeed(0);
				wifiCustomInfo.setLevel(scanResult.level);
				wifiCustomInfo.setRssi(0);

				listWifiInfo.add(wifiCustomInfo);

			}
			return listWifiInfo;
		}
		return null;
	}


	public static WifiCustomInfo getConnectedWifiInfo(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();    //当前wifi连接信息
		WifiCustomInfo wifiCustomInfo = new WifiCustomInfo();
		wifiCustomInfo.setEnable(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
		wifiCustomInfo.setSSID(wifiInfo.getSSID());
		wifiCustomInfo.setNetworkID(wifiInfo.getNetworkId());
		wifiCustomInfo.setLinkSpeed(wifiInfo.getLinkSpeed());
		wifiCustomInfo.setRssi(wifiInfo.getRssi());
		wifiCustomInfo.setIpAddress(wifiInfo.getIpAddress()+"");
		return wifiCustomInfo;

	}


}
