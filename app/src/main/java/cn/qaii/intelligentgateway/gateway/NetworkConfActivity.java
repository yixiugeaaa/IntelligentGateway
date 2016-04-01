package cn.qaii.intelligentgateway.gateway;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import java.io.PrintWriter;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseSwipeActivity;
import cn.qaii.intelligentgateway.frame.Socket.SocketThread;
import cn.qaii.intelligentgateway.frame.util.StringUtil;
import cn.qaii.intelligentgateway.frame.util.ToastHelper;
import cn.qaii.intelligentgateway.zxing.MipcaActivityCapture;

public class NetworkConfActivity extends BaseSwipeActivity implements View.OnClickListener,SocketThread.SocketInterface {

    private final static int SCANNIN_GREQUEST_CODE = 1;
    private PrintWriter mOut=null;
    private SocketThread mSocketThread;
    private int mRecvNum=0;

    private Handler myHandler=new Handler(){
        public void handleMessage(Message msg) {
            String recvStr = (String) msg.obj;
            if(recvStr.regionMatches(2,"CORECT",0,6)){
                ToastHelper.toastShort(mContext, "连接成功");
                mRecvNum++;
                if(mRecvNum==1) {
                    Intent intent = new Intent(mContext,MipcaActivityCapture.class);
                    startActivity(intent);
                }
            }else if("SOCKET_CON_FAILD".equals(recvStr)){
                ToastHelper.toastShort(mContext,SocketThread.SOCKET_CON_FAILD);
            }else {
                ToastHelper.toastShort(mContext,"连接失败");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_config);
        findViewById(R.id.btn_next_step).setOnClickListener(this);
        mSocketThread=new SocketThread(myHandler,this);
        mSocketThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_next_step:
                if(mOut!=null) {
                    String name = ((EditText) findViewById(R.id.et_ssid)).getText().toString().trim();
                    String pwd = ((EditText) findViewById(R.id.et_pwd)).getText().toString().trim();
                    String pwdStr = "FF" + name + "FA" + pwd + "FE";
                    String sendStr = StringUtil.sumStr(pwdStr);
                    mOut.println(sendStr);
                }else {
                    ToastHelper.toastShort(mContext,"请确认是否连接网关!");
                }
                Intent intent = new Intent(mContext,MipcaActivityCapture.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void getPrintWriter(PrintWriter out) {
        this.mOut=out;
    }

}
