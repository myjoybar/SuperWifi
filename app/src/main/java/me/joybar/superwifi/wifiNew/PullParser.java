package me.joybar.superwifi.wifiNew;

import android.text.TextUtils;
import android.util.Xml;

import com.joybar.library.common.log.L;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.joybar.superwifi.data.WifiCustomInfo;

/**
 * Created by joybar on 2017/12/22.
 */

public class PullParser {
	private static final String TAG = "PullParser";

	public static  List<WifiCustomInfo> pull2xml(InputStream is) throws Exception {
		List<WifiCustomInfo> list = null;
		WifiCustomInfo wifiCustomInfo = null;
		//创建xmlPull解析器
		XmlPullParser parser = Xml.newPullParser();
		///初始化xmlPull解析器
		parser.setInput(is, "utf-8");
		//读取文件的类型
		int type = parser.getEventType();

		L.d(TAG,"parser.getText()-----="+parser.getText());
		//无限判断文件类型进行读取
		while (type != XmlPullParser.END_DOCUMENT) {
			L.d(TAG,"type="+type);
			switch (type) {
				//开始标签
				case XmlPullParser.START_TAG:
					if ("Network".equals(parser.getName())) {
						list = new ArrayList<>();
					} else if ("WifiConfiguration".equals(parser.getName())) {
						wifiCustomInfo = new WifiCustomInfo();
					} else if ("string".equals(parser.getName())) {

						L.d(TAG,"parser.getText()="+parser.getText());
						L.d(TAG,"parser.toString()="+parser.toString());

						String ConfigKey = parser.getAttributeValue(null, "ConfigKey");
						L.d(TAG,"ConfigKey="+ConfigKey);

						String SSID = parser.getAttributeValue(null,"SSID");
						if(!TextUtils.isEmpty(SSID)){
							L.d(TAG,"SSID not empty="+SSID);
							String SSIDName = parser.nextText();
							wifiCustomInfo.setSSIDName(SSIDName);
						}else{
							L.d(TAG,"SSID is empty=");
						}
						String PreSharedKey = parser.getAttributeValue(null,"PreSharedKey");

						if(!TextUtils.isEmpty(PreSharedKey)){
							L.d(TAG,"PreSharedKey not empty="+PreSharedKey);
							String pwd = parser.nextText();
							wifiCustomInfo.setConfigKeyPwd(pwd);
						}else{
							L.d(TAG,"PreSharedKey is empty=");
						}
					}
					break;
				//结束标签
				case XmlPullParser.END_TAG:
					if ("NetworkList".equals(parser.getName())) {
						list.add(wifiCustomInfo);
					}
					break;
			}
			//继续往下读取标签类型
			type = parser.next();
		}
		return list;
	}
}
