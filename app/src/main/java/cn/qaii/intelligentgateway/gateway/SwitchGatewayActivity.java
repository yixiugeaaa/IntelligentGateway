package cn.qaii.intelligentgateway.gateway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseSwipeActivity;
import cn.qaii.intelligentgateway.base.ImagePagerActivity;

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
        final ArrayList<String> list=new ArrayList<String>();
        list.add(0,"https://gss1.bdstatic.com/5eN1dDebRNRTm2_p8IuM_a/res/img/richanglogo168_24.png");
        list.add(1,"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        list.add(2,"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1772187980,756000946&fm=80");
        btn_new_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(mContext,SearchGatewayActivity.class);
                startActivity(intent);*/
                openImageReview(mContext,0,list);
            }
        });
    }

    protected void openImageReview(Context context, int position,ArrayList<String> list) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, list);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

}
