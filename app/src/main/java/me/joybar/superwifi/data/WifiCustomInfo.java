package me.joybar.superwifi.data;

import android.support.annotation.Keep;

/**
 * Created by joybar on 2017/12/18.
 */

@Keep
public class WifiCustomInfo {

	public static int USELESS_NUM = -1000;
	private boolean enable;
	private String BSSID;
	private String SSID;
	private String ipAddress;
	private int networkID;

	private int linkSpeed;
	private int level;
	private int rssi;


	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getBSSID() {
		return BSSID;
	}

	public void setBSSID(String BSSID) {
		this.BSSID = BSSID;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String SSID) {
		this.SSID = SSID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getNetworkID() {
		return networkID;
	}

	public void setNetworkID(int networkID) {
		this.networkID = networkID;
	}

	public int getLinkSpeed() {
		return linkSpeed;
	}

	public void setLinkSpeed(int linkSpeed) {
		this.linkSpeed = linkSpeed;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	@Override
	public String toString() {
		return "WifiCustomInfo{" + "enable=" + enable + ", BSSID='" + BSSID + '\'' + ", SSID='" + SSID + '\'' + ", ipAddress='" + ipAddress + '\'' +
				", networkID=" + networkID + ", linkSpeed=" + linkSpeed + ", level=" + level + ", rssi=" + rssi + '}';
	}
}
