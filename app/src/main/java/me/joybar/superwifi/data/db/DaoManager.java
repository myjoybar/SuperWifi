package me.joybar.superwifi.data.db;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import me.joybar.superwifi.data.db.greendao.gen.DaoMaster;
import me.joybar.superwifi.data.db.greendao.gen.DaoSession;

/**
 * Created by joybar on 2018/1/5.
 */

public class DaoManager {

	private static final String TAG = "DaoManager";
	private static final String DB_NAME = "db_wifi_data";
	private Context context;
	private static DaoMaster mDaoMaster;
	private static DaoMaster.DevOpenHelper mHelper;
	private static DaoSession mDaoSession;

	static class DaoManagerHolder{
		private static DaoManager INSTANCE  = new DaoManager();
	}

	public static DaoManager getInstance(){
		return DaoManagerHolder.INSTANCE;
	}

	public void init(Context context){
		this.context = context;
	}

	/**
	 * 判断是否有存在数据库，如果没有则创建
	 */
	/**
	 * 判断是否有存在数据库，如果没有则创建
	 * @return
	 */
	public DaoMaster getDaoMaster(){
		if(mDaoMaster == null) {
			DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
			mDaoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return mDaoMaster;
	}

	/**
	 * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
	 * @return
	 */
	public DaoSession getDaoSession(){
		if(mDaoSession == null){
			if(mDaoMaster == null){
				mDaoMaster = getDaoMaster();
			}
			mDaoSession = mDaoMaster.newSession();
		}
		return mDaoSession;
	}

	/**
	 * 打开输出日志，默认关闭
	 */
	public void setDebug(){
		QueryBuilder.LOG_SQL = true;
		QueryBuilder.LOG_VALUES = true;
	}

	/**
	 * 关闭所有的操作，数据库开启后，使用完毕要关闭
	 */
	public void closeConnection(){
		closeHelper();
		closeDaoSession();
	}

	public void closeHelper(){
		if(mHelper != null){
			mHelper.close();
			mHelper = null;
		}
	}

	public void closeDaoSession(){
		if(mDaoSession != null){
			mDaoSession.clear();
			mDaoSession = null;
		}
	}
}
