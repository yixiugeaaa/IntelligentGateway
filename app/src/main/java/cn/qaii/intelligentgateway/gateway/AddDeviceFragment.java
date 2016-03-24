package cn.qaii.intelligentgateway.gateway;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseFragment;

/**
 * cn.qaii.intelligentgateway
 * 功能说明
 * Created by xiuge on 2016/3/16 10:34.
 */
public class AddDeviceFragment extends BaseFragment{

    private Button btn_add_device;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_device,null);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        btn_add_device=(Button)view.findViewById(R.id.btn_add_device);
        btn_add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,SwitchGatewayActivity.class);
                startActivity(intent);
            }
        });
    }
}
