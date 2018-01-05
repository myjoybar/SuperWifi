package me.joybar.superwifi.data.db.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import me.joybar.superwifi.data.WifiCustomInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WIFI_CUSTOM_INFO".
*/
public class WifiCustomInfoDao extends AbstractDao<WifiCustomInfo, Long> {

    public static final String TABLENAME = "WIFI_CUSTOM_INFO";

    /**
     * Properties of entity WifiCustomInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SSIDName = new Property(1, String.class, "SSIDName", false, "SSIDNAME");
        public final static Property ConfigKeyPwd = new Property(2, String.class, "configKeyPwd", false, "CONFIG_KEY_PWD");
        public final static Property Enable = new Property(3, boolean.class, "enable", false, "ENABLE");
        public final static Property BSSID = new Property(4, String.class, "BSSID", false, "BSSID");
        public final static Property SSID = new Property(5, String.class, "SSID", false, "SSID");
        public final static Property IpAddress = new Property(6, String.class, "ipAddress", false, "IP_ADDRESS");
        public final static Property NetworkID = new Property(7, int.class, "networkID", false, "NETWORK_ID");
        public final static Property LinkSpeed = new Property(8, int.class, "linkSpeed", false, "LINK_SPEED");
        public final static Property Level = new Property(9, int.class, "level", false, "LEVEL");
        public final static Property Rssi = new Property(10, int.class, "rssi", false, "RSSI");
    }


    public WifiCustomInfoDao(DaoConfig config) {
        super(config);
    }
    
    public WifiCustomInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WIFI_CUSTOM_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SSIDNAME\" TEXT," + // 1: SSIDName
                "\"CONFIG_KEY_PWD\" TEXT," + // 2: configKeyPwd
                "\"ENABLE\" INTEGER NOT NULL ," + // 3: enable
                "\"BSSID\" TEXT," + // 4: BSSID
                "\"SSID\" TEXT," + // 5: SSID
                "\"IP_ADDRESS\" TEXT," + // 6: ipAddress
                "\"NETWORK_ID\" INTEGER NOT NULL ," + // 7: networkID
                "\"LINK_SPEED\" INTEGER NOT NULL ," + // 8: linkSpeed
                "\"LEVEL\" INTEGER NOT NULL ," + // 9: level
                "\"RSSI\" INTEGER NOT NULL );"); // 10: rssi
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WIFI_CUSTOM_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WifiCustomInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String SSIDName = entity.getSSIDName();
        if (SSIDName != null) {
            stmt.bindString(2, SSIDName);
        }
 
        String configKeyPwd = entity.getConfigKeyPwd();
        if (configKeyPwd != null) {
            stmt.bindString(3, configKeyPwd);
        }
        stmt.bindLong(4, entity.getEnable() ? 1L: 0L);
 
        String BSSID = entity.getBSSID();
        if (BSSID != null) {
            stmt.bindString(5, BSSID);
        }
 
        String SSID = entity.getSSID();
        if (SSID != null) {
            stmt.bindString(6, SSID);
        }
 
        String ipAddress = entity.getIpAddress();
        if (ipAddress != null) {
            stmt.bindString(7, ipAddress);
        }
        stmt.bindLong(8, entity.getNetworkID());
        stmt.bindLong(9, entity.getLinkSpeed());
        stmt.bindLong(10, entity.getLevel());
        stmt.bindLong(11, entity.getRssi());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WifiCustomInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String SSIDName = entity.getSSIDName();
        if (SSIDName != null) {
            stmt.bindString(2, SSIDName);
        }
 
        String configKeyPwd = entity.getConfigKeyPwd();
        if (configKeyPwd != null) {
            stmt.bindString(3, configKeyPwd);
        }
        stmt.bindLong(4, entity.getEnable() ? 1L: 0L);
 
        String BSSID = entity.getBSSID();
        if (BSSID != null) {
            stmt.bindString(5, BSSID);
        }
 
        String SSID = entity.getSSID();
        if (SSID != null) {
            stmt.bindString(6, SSID);
        }
 
        String ipAddress = entity.getIpAddress();
        if (ipAddress != null) {
            stmt.bindString(7, ipAddress);
        }
        stmt.bindLong(8, entity.getNetworkID());
        stmt.bindLong(9, entity.getLinkSpeed());
        stmt.bindLong(10, entity.getLevel());
        stmt.bindLong(11, entity.getRssi());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public WifiCustomInfo readEntity(Cursor cursor, int offset) {
        WifiCustomInfo entity = new WifiCustomInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // SSIDName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // configKeyPwd
            cursor.getShort(offset + 3) != 0, // enable
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // BSSID
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // SSID
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ipAddress
            cursor.getInt(offset + 7), // networkID
            cursor.getInt(offset + 8), // linkSpeed
            cursor.getInt(offset + 9), // level
            cursor.getInt(offset + 10) // rssi
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WifiCustomInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSSIDName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setConfigKeyPwd(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEnable(cursor.getShort(offset + 3) != 0);
        entity.setBSSID(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSSID(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIpAddress(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNetworkID(cursor.getInt(offset + 7));
        entity.setLinkSpeed(cursor.getInt(offset + 8));
        entity.setLevel(cursor.getInt(offset + 9));
        entity.setRssi(cursor.getInt(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WifiCustomInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WifiCustomInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WifiCustomInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
