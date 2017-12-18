package me.joybar.superwifi.utils;

import com.joybar.library.common.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.joybar.superwifi.data.WifiEntry;

/**
 * Created by joybar on 2017/12/18.
 */

public class WifiPasswordManager {

	private static final String TAG = "WifiPasswordManager";
	String[] mLocationList = {"/data/misc/wifi/wpa_supplicant.conf", "/data/wifi/bcm_supp.conf", "/data/misc/wifi/wpa.conf"};

	String path = "/data/wifi/bcm_supp.conf";

	//Split entire path to Path & Filename
	String mFileName = path.substring(path.lastIndexOf("/") + 1);
	String mPath = path.substring(0, path.lastIndexOf("/") + 1);


	final String SSID = "ssid";
	final String WPA_PSK = "psk";
	final String WEP_PSK = "wep_key0";
	final String ENTRY_START = "network={";
	final String ENTRY_END = "}";
	final String NO_PASSWORD_TEXT = "no password";

	public ArrayList<WifiEntry> readFile() {

		ArrayList<WifiEntry> listWifi = new ArrayList<>();
		BufferedReader bufferedReader = null;

		try {

			if (false) {

				Process suProcess = Runtime.getRuntime().exec("su -c /system/bin/cat " + mPath + mFileName);
				try {
					suProcess.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				bufferedReader = new BufferedReader(new InputStreamReader(suProcess.getInputStream()));
				String testString = bufferedReader.readLine();

				if (testString == null) {
					//Show Error Dialog
					Logger.d(TAG, "testString is empty");
					return new ArrayList<>();
				}

			} else {

				//Check for file in all known locations
				for (int i = 0; i < mLocationList.length; i++) {

					Process suProcess = Runtime.getRuntime().exec("su -c /system/bin/cat " + mLocationList[i]);
					try {
						suProcess.waitFor();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					bufferedReader = new BufferedReader(new InputStreamReader(suProcess.getInputStream()));
					String testString = bufferedReader.readLine();

					if (testString != null) {
						Logger.d(TAG, "testString="+testString);
						break;

					} else if (i == mLocationList.length - 1) {
						//Show Error Dialog

						//if (mRootAccess) {
						if (RootCheck.canRunRootCommands()) {
							Logger.d(TAG, "有 root 权限");
						} else {
							Logger.d(TAG, "没有 root 权限");
						}
						return new ArrayList<>();
					}
				}
			}

			Logger.d(TAG, bufferedReader);
			if (bufferedReader == null) {
				return new ArrayList<>();
			}



			StringBuffer buffer = new StringBuffer();
			String line1 = " ";
			while ((line1 = bufferedReader.readLine()) != null){
				buffer.append(line1);
			}

			Logger.d(TAG, buffer);

			String line;
			String title = "";
			String password = "";

			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(ENTRY_START)) {

					while (!line.contains(ENTRY_END)) {
						line = bufferedReader.readLine();

						if (line.contains(SSID)) {
							title = line.replace(SSID, "").replace("=", "").replace("\"", "").replace(" ", "");
						}

						if (line.contains(WPA_PSK)) {

							password = line.replace(WPA_PSK, "").replace("=", "").replace("\"", "").replace(" ", "");

						} else if (line.contains(WEP_PSK)) {

							password = line.replace(WEP_PSK, "").replace("=", "").replace("\"", "").replace(" ", "");
						}

					}


					if (password.equals("")) {
						password = NO_PASSWORD_TEXT;
					}

					WifiEntry current = new WifiEntry(title, password);
					Logger.d(TAG, current);
					listWifi.add(current);

					title = "";
					password = "";
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return listWifi;
	}
}


