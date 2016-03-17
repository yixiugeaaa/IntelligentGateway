package cn.qaii.intelligentgateway.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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




    private Handler myHandler=new Handler(){
        public void handleMessage(Message msg) {
            String recvStr = (String) msg.obj;
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

    private void initView(View view){
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe);
        mSwipeLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setMode(SwipeRefreshLayout.Mode.PULL_FROM_START);
        mSwipeLayout.setOnRefreshListener(this);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                        socket.connect(new InetSocketAddress("192.168.1.1", 8000),1000);
                        BufferedReader recvBuf;
                        recvBuf = new BufferedReader(new InputStreamReader(
                                socket.getInputStream(), "gb2312"));
                            recvStr = recvBuf.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message();
                        msg.obj = recvStr;// 发送当前需要更新的窗口的NUM
                        myHandler.sendMessage(msg);
                    }
                }
        ).start();
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
                Socket socket = new Socket();
                PrintWriter out;
                try {
                    socket.connect(new InetSocketAddress("192.168.1.1", 8000),1000);
                    out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "gb2312")),
                            true);
                    out.println("FF" + name + "FA" + pwd + "FECRC");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
