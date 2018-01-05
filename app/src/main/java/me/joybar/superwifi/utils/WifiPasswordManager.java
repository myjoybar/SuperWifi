package me.joybar.superwifi.utils;

import com.joybar.library.common.log.L;
import com.stericson.RootShell.RootShell;
import com.stericson.RootShell.execution.Command;

import java.util.ArrayList;

import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.utils.root.RootCheck;

import static com.stericson.RootShell.RootShell.commandWait;

/**
 * Created by joybar on 2017/12/18.
 */

public class WifiPasswordManager {

	private static final String TAG = "WifiPasswordManager";
	String[] mLocationList = {"/data/misc/wifi/wpa_supplicant.conf", "/data/wifi/bcm_supp.conf", "/data/misc/wifi/wpa.conf"};

	String path = "/data/wifi/bcm_supp.conf";

	String[] mOreoLocationList = {"/data/misc/wifi/WifiConfigStore.xml"};


	//Split entire path to Path & Filename
	String mFileName = path.substring(path.lastIndexOf("/") + 1);
	String mPath = path.substring(0, path.lastIndexOf("/") + 1);

	final String BSSID = "bssid";
	final String SSID = "ssid";
	final String WPA_PSK = "psk";
	final String WEP_PSK = "wep_key0";
	final String ENTRY_START = "network={";
	final String ENTRY_END = "}";
	public  final String NO_PASSWORD_TEXT = "no password";

	public ArrayList<WifiCustomInfo> readWifiConfigFile() {

		if (android.os.Build.VERSION.SDK_INT >= 26) {
			return readOreoFiles();
		} else {
			return readFile();
		}
	}

	public ArrayList<WifiCustomInfo> readOreoFiles() {
		ArrayList<WifiCustomInfo> result = new ArrayList<>();
		boolean mRootAccess = RootCheck.canRunRootCommands();
		if (!mRootAccess) {
			//cancel(true);
			return null;
		} else {
			for (String oreoLocation : mOreoLocationList) {
				ArrayList<WifiCustomInfo> currentFile = readOreoFile(oreoLocation);
				if ((currentFile != null) && (!currentFile.isEmpty())) {
					result.addAll(currentFile);
				}
			}
		}
		return result;

	}


	private ArrayList<WifiCustomInfo> readOreoFile(String configLocation) {
		ArrayList<WifiCustomInfo> result = new ArrayList<>();
		if (RootShell.exists(configLocation)) {
			L.d(TAG, "RootShell.exists(configLocation)");
			ArrayList<String> fileLines = executeForResult("su -c /system/bin/cat " + configLocation);
			if (fileLines == null) {
				L.d(TAG, "fileLines is null");
				return result;
			}
			StringBuilder buffer = new StringBuilder();
			for (String thisLine : fileLines) {
				buffer.append(thisLine).append("\n");
			}
			return WifiConfigParse.readOreoFile(buffer);
		}
		return result;
	}


	private ArrayList<WifiCustomInfo> readFile() {

		L.d(TAG, "<26");
		boolean mRootAccess = RootCheck.canRunRootCommands();
		if (!mRootAccess) {
			return null;
		}
		ArrayList<WifiCustomInfo> listWifi = new ArrayList<>();
		try {
			String configFileName = "";
			for (String testFile : mLocationList) {
				if (RootShell.exists(testFile)) {
					configFileName = testFile;
					break;
				}
			}
			if (configFileName.length() > 0) {
				String title = "";
				String password = "";
				boolean processingEntry = false;
				ArrayList<String> fileLines = executeForResult("su -c /system/bin/cat " + configFileName);
				for (String line : fileLines) {
					if (processingEntry) {
						if (line.contains(ENTRY_END)) {
							// Finished with this entry now - clean up the data and add it to the list.
							if (password.equals("")) {
								password = NO_PASSWORD_TEXT;
							}

							WifiCustomInfo current = new WifiCustomInfo(title, password);
							listWifi.add(current);
							// clear the current entry variables.
							title = "";
							password = "";
							processingEntry = false;
						} else {
							// still processing the current entry; check for valid data.
							if (line.contains(SSID) && !line.contains(BSSID)) {
								title = line.replace(SSID, "").replace("=", "").replace("\"", "").replace(" ", "");
							}
							if (password.length() == 0) {
								if (line.contains(WPA_PSK)) {
									password = line.replace(WPA_PSK, "").replace("=", "").replace("\"", "").replace(" ", "");
								} else if (line.contains(WEP_PSK)) {
									password = line.replace(WEP_PSK, "").replace("=", "").replace("\"", "").replace(" ", "");
								}
							}
						}
					} else {
						processingEntry = line.contains(ENTRY_START);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listWifi;
	}

	private static ArrayList<String> executeForResult(String cmd) {
		final ArrayList<String> results = new ArrayList<>();
		Command command = new Command(3, false, cmd) {
			@Override
			public void commandOutput(int id, String line) {
				results.add(line);
				super.commandOutput(id, line);
			}
		};
		try {
			RootShell.getShell(true).add(command);
			commandWait(RootShell.getShell(true), command);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return results;
	}

}


