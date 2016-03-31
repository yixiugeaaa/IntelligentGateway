package cn.qaii.intelligentgateway.gateway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseSwipeActivity;

public class SwitchGatewayActivity extends BaseSwipeActivity {

    private Button btn_new_step;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_gateway);
        initView();
    }

    private void initView(){
        btn_new_step=(Button)findViewById(R.id.btn_next_step);
        btn_new_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,SearchGatewayActivity.class);
                startActivity(intent);
            }
        });
    }

}
