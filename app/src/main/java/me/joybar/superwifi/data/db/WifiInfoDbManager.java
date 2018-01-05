package me.joybar.superwifi.data.db;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.data.db.greendao.gen.WifiCustomInfoDao;

/**
 * Created by joybar on 2018/1/5.
 */

public class WifiInfoDbManager {

	private static final String TAG ="WifiInfoDbUtil";
	private DaoManager mManager;

	public WifiInfoDbManager(Context context){
		mManager = DaoManager.getInstance();
		mManager.init(context);
	}

	/**
	 * 插入一条数据
	 * @param wifiCustomInfo
	 * @return
	 */
	public boolean insertWifiData(WifiCustomInfo wifiCustomInfo){
		boolean flag = false;
		flag = mManager.getDaoSession().getWifiCustomInfoDao().insert(wifiCustomInfo) == -1 ? false : true;
		return flag;
	}

	/**
	 * 插入多条数据，在子线程操作
	 * @param wifiCustomInfoList
	 * @return
	 */
	public boolean insertWifiDataList(final List<WifiCustomInfo> wifiCustomInfoList) {
		boolean flag = false;
		try {
			mManager.getDaoSession().runInTx(new Runnable() {
				@Override
				public void run() {
					for (WifiCustomInfo wifiCustomInfo : wifiCustomInfoList) {
						mManager.getDaoSession().insertOrReplace(wifiCustomInfo);
					}
				}
			});
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 修改一条数据
	 * @param wifiCustomInfo
	 * @return
	 */
	public boolean updateWifiData(WifiCustomInfo wifiCustomInfo){
		boolean flag = false;
		try {
			mManager.getDaoSession().update(wifiCustomInfo);
			flag = true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除单条记录
	 * @param wifiCustomInfo
	 * @return
	 */
	public boolean deleteWifiData(WifiCustomInfo wifiCustomInfo){
		boolean flag = false;
		try {
			//按照id删除
			mManager.getDaoSession().delete(wifiCustomInfo);
			flag = true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除所有记录
	 * @return
	 */
	public boolean deleteAll(){
		boolean flag = false;
		try {
			//按照id删除
			mManager.getDaoSession().deleteAll(WifiCustomInfo.class);
			flag = true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查询所有记录
	 * @return
	 */
	public List<WifiCustomInfo> queryAllWifiData(){
		return mManager.getDaoSession().loadAll(WifiCustomInfo.class);
	}
	/**
	 * 根据主键id查询记录
	 * @param key
	 * @return
	 */
	public WifiCustomInfo queryWifiDataById(long key){
		return mManager.getDaoSession().load(WifiCustomInfo.class, key);
	}

	/**
	 * 使用native sql进行查询操作
	 */
	public List<WifiCustomInfo> queryWifiDataNativeSql(String sql, String[] conditions){
		return mManager.getDaoSession().queryRaw(WifiCustomInfo.class, sql, conditions);
	}

	/**
	 * 使用queryBuilder进行查询
	 * @return
	 */
	public List<WifiCustomInfo> queryMeiziByQueryBuilder(long id){
		QueryBuilder<WifiCustomInfo> queryBuilder = mManager.getDaoSession().queryBuilder(WifiCustomInfo.class);
		return queryBuilder.where(WifiCustomInfoDao.Properties.Id.eq(id)).list();
	}


}
