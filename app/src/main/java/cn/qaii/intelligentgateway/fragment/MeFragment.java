package cn.qaii.intelligentgateway.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseFragment;
import cn.qaii.viewutil_lib.swipe.SwipeRefreshLayout.OnRefreshListener;

/**
 * cn.qaii.intelligentgateway
 * 功能说明
 * Created by xiuge on 2016/3/16 10:34.
 */
public class MeFragment extends BaseFragment implements OnRefreshListener,View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me,null);
        //initView(view);
        //initEvent();
        return view;

    }
    /*private void initEvent(){
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }*/
//    private void initView(View view){
//
//        setting=(LinearLayout)view.findViewById(R.id.setting);
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }
}
