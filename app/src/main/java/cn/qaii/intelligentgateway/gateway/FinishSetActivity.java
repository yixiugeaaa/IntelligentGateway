package cn.qaii.intelligentgateway.gateway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.qaii.intelligentgateway.MainActivity;
import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseSwipeActivity;

public class FinishSetActivity extends BaseSwipeActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_set);
        findViewById(R.id.btn_next_step).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_next_step:
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
