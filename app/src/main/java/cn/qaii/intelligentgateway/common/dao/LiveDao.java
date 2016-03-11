package cn.qaii.intelligentgateway.common.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import cn.qaii.intelligentgateway.frame.database.LDaoManager;
import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.intelligentgateway.model.LiveInfo;


/**
 * 直播列表数据数据库操作类
 * @author larry
 *
 */
public class LiveDao {
	
	private Dao<LiveInfo, Integer> liveDao;
	
	public LiveDao() {
		liveDao = LDaoManager.createDao(LiveInfo.class);
	}
	
	/**
	 * 保存直播间
	 * @param liveInfo
	 */
	public void createLive(LiveInfo liveInfo){
		try {
			liveDao.create(liveInfo);
		} catch (SQLException e) {
			LLogger.e("创建数据类型时异常" + e);
		}
	}
	
	/**
	 * 删除指定直播间
	 * @param info
	 */
	public void deleteLive(final LiveInfo info){
		try {
			DeleteBuilder<LiveInfo, Integer> deleteBuilder = liveDao.deleteBuilder();
			deleteBuilder.where().eq("id", info.getId());
			deleteBuilder.delete();
		} catch (SQLException e) {
			LLogger.e("删除自收藏时发生异常 ： " + e);
		}
	}
	
	/**
	 * 批量保存直播间
	 * @param infos
	 */
	public void syncData(final List<LiveInfo> infos) {
		if(infos == null || infos.size() == 0)
			return;
		try {
			liveDao.callBatchTasks(new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					liveDao.deleteBuilder().delete();
					for(LiveInfo info : infos) {
						liveDao.create(info);
					}
					return null;
				}});
		} catch (Exception e) {
			LLogger.e("保存数据类型时发生异常 ： " + e);
		}
	}

}
