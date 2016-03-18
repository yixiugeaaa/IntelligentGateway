package cn.qaii.intelligentgateway.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseFragment;
import cn.qaii.intelligentgateway.frame.util.StringUtil;
import cn.qaii.intelligentgateway.frame.util.ToastHelper;
import cn.qaii.viewutil_lib.swipe.SwipeRefreshLayout;

/**
 * cn.qaii.intelligentgateway
 * 功能说明
 * Created by xiuge on 2016/3/16 10:34.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{

    private SwipeRefreshLayout mSwipeLayout;
    Socket socket = new Socket();
    String recvStr = null;
    PrintWriter out;
    BufferedReader recvBuf;

    private Handler myHandler=new Handler(){
        public void handleMessage(Message msg) {
            String recvStr = (String) msg.obj;
            if(recvStr.regionMatches(2,"CORECT",0,6)){
                ToastHelper.toastLong(mContext,"连接成功");
            }else{
                ToastHelper.toastLong(mContext,"连接失败");
            }
            ((TextView)getView().findViewById(R.id.recv)).setText(recvStr);
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
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe);
        mSwipeLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setMode(SwipeRefreshLayout.Mode.PULL_FROM_START);
        mSwipeLayout.setOnRefreshListener(this);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
        view.findViewById(R.id.btn_content).setOnClickListener(this);

        try {
            socket.connect(new InetSocketAddress("192.168.1.1", 8000), 1000);

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "gb2312")),
                    true);
            recvBuf = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "gb2312"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread() {
            @Override
            public void run() {
                while (true){
                    Log.e("aa","ssss");
                    try {
                        if(recvBuf!=null) {
                            recvStr = recvBuf.readLine();
                            if (recvStr!=null){
                                Message msg = new Message();
                                msg.obj = recvStr;// 发送当前需要更新的窗口的NUM
                                myHandler.sendMessage(msg);
                            }else
                                return;
                        }else{
                            Message msg = new Message();
                            msg.obj = "未连接";// 发送当前需要更新的窗口的NUM
                            myHandler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_send:
                String name= ((EditText)getView().findViewById(R.id.name)).getText().toString().trim();
                String pwd=((EditText)getView().findViewById(R.id.pwd)).getText().toString().trim();
                String pwdStr="FF" + name + "FA" + pwd + "FE";
                String sendStr=StringUtil.sumStr(pwdStr);
                out.println(sendStr);
                break;
            case R.id.btn_content:
                String content=((EditText)getView().findViewById(R.id.content_send)).getText().toString().trim();
                String ipStr="FFIP"+content+"FE";
                String sendIp=StringUtil.sumStr(ipStr);
                out.println(sendIp);
        }
    }
}
