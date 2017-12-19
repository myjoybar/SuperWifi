package me.joybar.superwifi.data;

import android.os.Parcel;
import android.os.Parcelable;


public class WifiCustomInfo implements Parcelable {


    private String SSIDName;
    private String configKeyPwd;

    private boolean enable;
    private String BSSID;
    private String SSID;
    private String ipAddress;
    private int networkID;

    private int linkSpeed;
    private int level;
    private int rssi;


    public WifiCustomInfo() {}

    public WifiCustomInfo(String SSIDName, String configKeyPwd) {
        this.SSIDName = SSIDName;
        this.configKeyPwd = configKeyPwd;
    }

    public String getSSIDName() {
        return SSIDName;
    }

    public void setSSIDName(String SSIDName) {
        this.SSIDName = SSIDName;
    }

    public String getConfigKeyPwd() {
        return configKeyPwd;
    }

    public void setConfigKeyPwd(String configKeyPwd) {
        this.configKeyPwd = configKeyPwd;
    }

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
        return "WifiEntry{" + "SSIDName='" + SSIDName + '\'' + ", configKeyPwd='" + configKeyPwd + '\'' + ", enable=" + enable + ", BSSID='" +
                BSSID + '\'' + ", SSID='" + SSID + '\'' + ", ipAddress='" + ipAddress + '\'' + ", networkID=" + networkID + ", linkSpeed=" +
                linkSpeed + ", level=" + level + ", rssi=" + rssi + '}';
    }

    public static final Creator<WifiCustomInfo> CREATOR = new Creator<WifiCustomInfo>() {
        @Override
        public WifiCustomInfo createFromParcel(Parcel in) {
            return new WifiCustomInfo(in);
        }

        @Override
        public WifiCustomInfo[] newArray(int size) {
            return new WifiCustomInfo[size];
        }
    };


    protected WifiCustomInfo(Parcel in) {
        SSIDName = in.readString();
        configKeyPwd = in.readString();
        enable = in.readByte()!=0;
        BSSID = in.readString();
        SSID = in.readString();

        ipAddress = in.readString();
        networkID = in.readInt();
        linkSpeed = in.readInt();
        level = in.readInt();
        rssi = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(SSIDName);
        dest.writeString(configKeyPwd);
        dest.writeByte((byte)(enable ?1:0));
        dest.writeString(BSSID);
        dest.writeString(SSID);

        dest.writeString(ipAddress);
        dest.writeInt(networkID);
        dest.writeInt(linkSpeed);
        dest.writeInt(level);
        dest.writeInt(rssi);

    }
}
