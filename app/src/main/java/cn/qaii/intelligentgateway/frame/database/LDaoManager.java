package cn.qaii.intelligentgateway.frame.database;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import cn.qaii.intelligentgateway.frame.constant.LContext;
import cn.qaii.intelligentgateway.frame.util.LLogger;

public class LDaoManager {
	
	private static DBHelper database;
	private static ConnectionSource connectionSource;
	
	public static boolean init(Context context) {
		database = new DBHelper(context);
		connectionSource = new AndroidConnectionSource(database);
		return true;
	}
	
	public synchronized static <D extends Dao<T, ?>, T> D createDao(Class<T> clazz) {////////////////////////////////////////////////////////
		try {
			if(connectionSource == null) {
				init(LContext.context);
			}
			return DaoManager.createDao(connectionSource, clazz);
		} catch (Exception e) {
			LLogger.e("创建dao时发生异常" + e);
			return null;
		}
	}
}
