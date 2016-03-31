package cn.qaii.intelligentgateway.frame.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * cn.qaii.intelligentgateway.frame.util
 * 功能说明
 * Created by xiuge on 2016/3/30 20:58.
 */
public class NetworkStateUtil {


    public static boolean isNetworkAccess(Context context ) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isWifi(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isConnected();
    }


    /**
     * 是否使用移动网络
     * @param
     * @return
     */
    public static boolean isMobile(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo.isConnected();
    }
}
