package cn.qaii.intelligentgateway.gateway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseSwipeActivity;
import cn.qaii.intelligentgateway.frame.util.NetworkStateUtil;
import cn.qaii.intelligentgateway.frame.util.ToastHelper;

public class SearchGatewayActivity extends BaseSwipeActivity {

    private Button btn_next_step;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gateway);
        initView();
    }

    private void initView(){
        btn_next_step=(Button)findViewById(R.id.btn_next_step);
        btn_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkStateUtil.isWifi(mContext)) {
                    Intent intent = new Intent(mContext, NetworkConfActivity.class);
                    startActivity(intent);
                }else{
                    ToastHelper.toastLong(mContext,"您当前没有打开Wifi网络，请连上网关设备再点击下一步");
                }
            }
        });
    }

}
