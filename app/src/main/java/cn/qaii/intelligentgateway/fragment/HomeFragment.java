package cn.qaii.intelligentgateway.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseFragment;
import cn.qaii.viewutil_lib.swipe.SwipeRefreshLayout;

/**
 * cn.qaii.intelligentgateway
 * 功能说明
 * Created by xiuge on 2016/3/16 10:34.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{

    private SwipeRefreshLayout mSwipeLayout;

    private Handler myHandler=new Handler(){
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,null);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {

    }


    @Override
    public void onRefresh(){

    }

    @Override
    public void onClick(View v) {

    }
}
