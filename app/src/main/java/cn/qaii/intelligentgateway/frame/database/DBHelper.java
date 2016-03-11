package cn.qaii.intelligentgateway.frame.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.intelligentgateway.model.LiveInfo;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	public static final String DATABASE_NAME = "larry";

	public static int DATABASE_VERSION = 5;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, LiveInfo.class);
		} catch (SQLException e) {
			LLogger.e("Unable to create datbases : " + DATABASE_NAME + " \n " + e);
		}
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			TableUtils.dropTable(connectionSource, LiveInfo.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			LLogger.e("Unable to upgrade database from version " + oldVer
					+ " to new " + newVer + " \n " + e);
		}

	}

}
