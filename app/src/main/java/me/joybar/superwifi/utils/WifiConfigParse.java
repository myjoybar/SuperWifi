package me.joybar.superwifi.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import me.joybar.superwifi.data.WifiCustomInfo;

/**
 * Created by joybar on 2017/12/19.
 */

public class WifiConfigParse {

	public static final String NO_PASSWORD_TEXT = "no password";

	public static ArrayList<WifiCustomInfo> readOreoFile(StringBuilder buffer) {

		ArrayList<WifiCustomInfo> result = new ArrayList<>();

		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(buffer.toString()));
			while (parser.next() != XmlPullParser.END_DOCUMENT) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				if (parser.getName().equalsIgnoreCase("NetworkList")) {
					// Process the <Network> entries in the list
					result.addAll(readNetworkList(parser));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return result;
	}


	/****************** Oreo Helper Methods: BEGIN ******************/
	public static ArrayList<WifiCustomInfo> readNetworkList(XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<WifiCustomInfo> result = new ArrayList<>();
		parser.require(XmlPullParser.START_TAG, null, "NetworkList");
		boolean doLoop = true;
		while (doLoop) {
			try {
				parser.next();
				String tagName = parser.getName();
				if (tagName == null) {
					tagName = "";
				}
				doLoop = (!tagName.equalsIgnoreCase("NetworkList"));
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}

				if (tagName.equals("Network")) {
					WifiCustomInfo newWifi = readNetworkEntry(parser);
					if (newWifi.getSSIDName().length() != 0) {
						result.add(newWifi);
					}
				} else {
					skip(parser);
				}
			} catch (Exception e) {
				Log.e("LoadData.NetworkList", e.getMessage());
				doLoop = false;
			}
		}
		return result;
	}


	// Parses a "Network" entry
	public static WifiCustomInfo readNetworkEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "Network");
		WifiCustomInfo result = new WifiCustomInfo("", NO_PASSWORD_TEXT);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tagName = parser.getName();
			// Starts by looking for the entry tag
			if (tagName.equals("WifiConfiguration")) {
				result = readWiFiConfig(parser, result);
//            } else if (tagName.equals("WifiEnterpriseConfiguration")) {
//                result.setTyp(WifiObject.TYP_ENTERPRISE);
			} else {
				skip(parser);
			}
		}
		return result;
	}

	// Parses a "WifiConfiguration" entry
	public static WifiCustomInfo readWiFiConfig(XmlPullParser parser, WifiCustomInfo result) throws XmlPullParserException, IOException {
		try {
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String tagName = parser.getName();
				String name = parser.getAttributeValue(null, "name");
				if (name.equals("SSID") && !tagName.equalsIgnoreCase("null")) {
					result.setSSIDName(readTag(parser, tagName));
				} else if (name.equals("PreSharedKey") && !tagName.equalsIgnoreCase("null")) {
					String newPwd = readTag(parser, tagName);
					if (newPwd.length() > 0) {
						result.setConfigKeyPwd(newPwd);
//                  result.setTyp(WifiObject.TYP_WPA);
					}
				} else if (name.equals("WEPKeys") && !tagName.equalsIgnoreCase("null")) {
//                  result.setTyp(WifiObject.TYP_WEP);
					if (tagName.equalsIgnoreCase("string-array")) {
						try {
							int numQty = Integer.parseInt(parser.getAttributeValue(null, "num"));
							int loopQty = 0;
							while ((parser.next() != XmlPullParser.END_DOCUMENT) && (loopQty < numQty)) {
								String innerTagName = parser.getName();
								if ((innerTagName != null) && innerTagName.equalsIgnoreCase("item")) {
									loopQty++;
									String newPwd = parser.getAttributeValue(null, "value");
									if (newPwd.length() > 0) {
										result.setConfigKeyPwd(newPwd);
									}
								}
							}
						} catch (Exception error) {
							parser.getName();
						}
					} else {
						String newPwd = readTag(parser, tagName);
						if (newPwd.length() > 0) {
							result.setConfigKeyPwd(readTag(parser, tagName));
						}
					}
				} else {
					skip(parser);
				}
			}
		} catch (Exception error) {
			Log.e("LoadData.readWiFiConfig", error.getMessage() + "\n\nParser: " + parser.getText());
		}
		return result;
	}

	// Return the text for a specified tag.
	public static String readTag(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, tagName);
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, null, tagName);
		if (tagName.equalsIgnoreCase("string") && Character.toString(result.charAt(0)).equals("\"")) {
			result = result.substring(1, result.length() - 1);
		}
		return result;
	}

	// Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
	// if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
	// finds the matching END_TAG (as indicated by the value of "depth" being 0).
	public static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
			}
		}
	}


}
