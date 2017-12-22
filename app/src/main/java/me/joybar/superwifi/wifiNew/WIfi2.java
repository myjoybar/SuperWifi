package me.joybar.superwifi.wifiNew;

import com.joybar.library.common.log.L;
import com.joybar.library.io.file.FileUtil;
import com.joybar.library.io.file.SDCardUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

/**
 * Created by joybar on 2017/12/21.
 */

public class WIfi2 {

	private static final String TAG = "WIfi2";

	public static String[] commonFilePath = {"/data/misc/wifi/wpa_supplicant.conf", "/data/wifi/bcm_supp.conf", "/data/misc/wifi/wpa.conf"};
	public static String[] oreoFilePath = {"/data/misc/wifi/WifiConfigStore.xml"};
	public static String CAT = "cat ";


	public static boolean isRoot() {
		return Root.isRoot();
	}

	public static void  saveWifiConfigToFile(String str) {
		String saveFilePath = getSaveWifiConfigFilePath();
		FileUtil.saveFile(str,saveFilePath);
	}

	public static String getSaveWifiConfigFilePath() {
		if (SDCardUtil.isSDCardEnable()) {
			String saveFilePath = SDCardUtil.getSDCardPath();
			String saveFileName = "wifi/wifiConfig.txt";
			return saveFilePath + saveFileName;
		}
		return null;
	}


	public static String getWifiConfigFileString(String filePath) {
		String wifiInfoStr = null;
		Process process = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;
		StringBuffer sBuffer = new StringBuffer();
		//读取该文件
		try {
			//	filePath=cat /data/misc/wifi/WifiConfigStore.xml
			process = Runtime.getRuntime().exec("su");
			outputStream = new DataOutputStream(process.getOutputStream());
			inputStream = new DataInputStream(process.getInputStream());
			outputStream.writeBytes(filePath + "\n");
			outputStream.writeBytes("exit\n");
			outputStream.flush();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				//	fileOutputStream.write((temp + "\n").getBytes());
				sBuffer.append(temp);
			}

			wifiInfoStr = sBuffer.toString();
			reader.close();
			inputStream.close();
			process.waitFor();
		} catch (Exception e) {

			L.d("TAG", "e=" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) outputStream.close();
				if (inputStream != null) inputStream.close();
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return wifiInfoStr;
	}

}
