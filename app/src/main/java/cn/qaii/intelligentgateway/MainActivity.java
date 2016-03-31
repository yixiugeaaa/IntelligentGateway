package cn.qaii.intelligentgateway;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.actionbarsherlock.view.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.qaii.intelligentgateway.base.BaseActivity;
import cn.qaii.intelligentgateway.fragment.HomeFragment;
import cn.qaii.intelligentgateway.fragment.LinkageFragment;
import cn.qaii.intelligentgateway.fragment.MeFragment;
import cn.qaii.intelligentgateway.fragment.ShopFragment;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest;
import cn.qaii.intelligentgateway.frame.util.NetworkStateUtil;
import cn.qaii.intelligentgateway.frame.util.PrefConstants;
import cn.qaii.intelligentgateway.frame.util.PrefUtils;
import cn.qaii.intelligentgateway.frame.util.ToastHelper;
import cn.qaii.intelligentgateway.gateway.AddDeviceFragment;
import cn.qaii.intelligentgateway.gateway.http.GatewayRequest;
import cn.qaii.intelligentgateway.user.http.UserRequest;
import cn.qaii.viewutil_lib.navigation.NavigationView;
import cn.qaii.viewutil_lib.view.LoadHelper;

/**
 * MainActivity
 * (功能说明)
 * Created by xiuge on 2016/3/16 10:29.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private AddDeviceFragment mAddDeviceFragment;
    private LinkageFragment mLinkageFragment;
    private HomeFragment mHomeFragment;
    private ShopFragment mShopFragment;
    private MeFragment mMeFragment;

    private List<NavigationView> mTabIndicators = new ArrayList<NavigationView>();



    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GatewayRequest.GET_DEVICE_SUCCESS:

                case UserRequest.LOGIN_SUCCESS:
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadHelper.dismiss();
                            showAddDevice();
                        }
                    }, 1500);
                    break;


                case UserRequest.GET_USER_INFO_SUCCESS:
                    Map<String, Object> map = (Map<String, Object>)msg.obj;
                    if(map != null){

                    }
                    LoadHelper.dismiss();
                    break;
                case LHttpRequest.REQUEST_FAILED:
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadHelper.dismiss();
                            showAddDevice();
                        }
                    }, 1500);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showAddDevice();
        NetworkStateUtil.isWifi(mContext);
        //new GatewayRequest(mContext,mHandler).getGatewayInfo();
        //new UserRequest(mContext,mHandler).login("15689953880","123456");
        //LoadHelper.show(mContext);
    }

    private void initView() {
        NavigationView naviView_home = (NavigationView) findViewById(R.id.id_indicator_home);
        mTabIndicators.add(naviView_home);
        NavigationView naviView_linkage = (NavigationView) findViewById(R.id.id_indicator_linkage);
        mTabIndicators.add(naviView_linkage);
        NavigationView naviView_shop = (NavigationView) findViewById(R.id.id_indicator_shop);
        mTabIndicators.add(naviView_shop);
        NavigationView naviView_me = (NavigationView) findViewById(R.id.id_indicator_me);
        mTabIndicators.add(naviView_me);

        naviView_home.setOnClickListener(this);
        naviView_linkage.setOnClickListener(this);
        naviView_shop.setOnClickListener(this);
        naviView_me.setOnClickListener(this);

        naviView_home.setIconAlpha(1.0f);
    }

    private void showAddDevice(){
        hideFrag();
        mFTransaction = mFManager.beginTransaction();
        if (mAddDeviceFragment == null) {
            mAddDeviceFragment = new AddDeviceFragment();
            mFTransaction.add(R.id.fragment_container, mAddDeviceFragment, "addDevice");
        }
        mFTransaction.show(mAddDeviceFragment);
        mFTransaction.commitAllowingStateLoss();
        if(mBarTintManager != null){
            mBarTintManager.setStatusBarTintResource(R.color.actionbar_color);
        }
    }

    private void showHome(){
        hideFrag();
        mFTransaction = mFManager.beginTransaction();
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            mFTransaction.add(R.id.fragment_container, mHomeFragment, "home");
        }
        mFTransaction.show(mHomeFragment);
        mFTransaction.commitAllowingStateLoss();
        if(mBarTintManager != null){
            mBarTintManager.setStatusBarTintResource(R.color.actionbar_color);
        }
    }

    private void showLinkage(){
        hideFrag();
        mFTransaction = mFManager.beginTransaction();
        if (mLinkageFragment == null) {
            mLinkageFragment = new LinkageFragment();
            mFTransaction.add(R.id.fragment_container, mLinkageFragment, "linkage");
        }
        mFTransaction.show(mLinkageFragment);
        mFTransaction.commitAllowingStateLoss();
        if(mBarTintManager != null){
            mBarTintManager.setStatusBarTintResource(R.color.actionbar_color);
        }
    }

    private void showShop(){
        hideFrag();
        mFTransaction = mFManager.beginTransaction();
        if (mShopFragment == null) {
            mShopFragment = new ShopFragment();
            mFTransaction.add(R.id.fragment_container, mShopFragment, "shop");
        }
        mFTransaction.show(mShopFragment);
        mFTransaction.commitAllowingStateLoss();
        if(mBarTintManager != null){
            mBarTintManager.setStatusBarTintResource(R.color.actionbar_color);
        }
    }

    private void showMe(){
        hideFrag();
        mFTransaction = mFManager.beginTransaction();
        if (mMeFragment == null) {
            mMeFragment = new MeFragment();
            mFTransaction.add(R.id.fragment_container, mMeFragment, "me");
        }
        mFTransaction.show(mMeFragment);
        mFTransaction.commitAllowingStateLoss();
        if(mBarTintManager != null){
            mBarTintManager.setStatusBarTintResource(R.color.actionbar_color);
        }
    }

    private void hideFrag() {
        mAddDeviceFragment=((AddDeviceFragment) mFManager.findFragmentByTag("addDevice"));
        mHomeFragment = ((HomeFragment) mFManager.findFragmentByTag("home"));
        mLinkageFragment=((LinkageFragment)mFManager.findFragmentByTag("linkage"));
        mShopFragment = ((ShopFragment) mFManager.findFragmentByTag("shop"));
        mMeFragment = ((MeFragment) mFManager.findFragmentByTag("me"));
        mFTransaction = mFManager.beginTransaction();
        if (mAddDeviceFragment != null)
            mFTransaction.hide(mAddDeviceFragment);
        if (mHomeFragment != null)
            mFTransaction.hide(mHomeFragment);
        if (mLinkageFragment != null)
            mFTransaction.hide(mLinkageFragment);
        if (mShopFragment != null)
            mFTransaction.hide(mShopFragment);
        if (mMeFragment != null)
            mFTransaction.hide(mMeFragment);
        mFTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        clickTab(v);
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }


    private void clickTab(View v) {
        switch (v.getId()) {
            case R.id.id_indicator_home:
                resetOtherTabs();
                mTabIndicators.get(0).setIconAlpha(1.0f);
                if (PrefUtils.getPrefBoolean(mContext,PrefConstants.IS_BINDED,false))
                    showHome();
                else
                    showAddDevice();
                break;
            case R.id.id_indicator_linkage:
                resetOtherTabs();
                mTabIndicators.get(1).setIconAlpha(1.0f);
                showLinkage();
                break;
            case R.id.id_indicator_shop:
                resetOtherTabs();
                mTabIndicators.get(2).setIconAlpha(1.0f);
                showShop();
                break;
            case R.id.id_indicator_me:
                resetOtherTabs();
                mTabIndicators.get(3).setIconAlpha(1.0f);
                showMe();
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastHelper.toastShort(mContext, "再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
            exitAPP();
        }
    }
}
