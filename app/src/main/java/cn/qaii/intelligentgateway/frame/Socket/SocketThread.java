package cn.qaii.intelligentgateway.frame.Socket;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import cn.qaii.intelligentgateway.frame.constant.LCommands;
import cn.qaii.intelligentgateway.frame.constant.LConstants;
import cn.qaii.intelligentgateway.frame.util.StringUtil;

/**
 * cn.qaii.intelligentgateway.frame.Socket
 * 功能说明
 * Created by xiuge on 2016/3/31 14:36.
 */
public class SocketThread extends Thread {
    public static final String SOCKET_CON_FAILD="网关连接失败，请返回重试重试";
    private Handler mHandler;
    private SocketInterface mSi;

    public SocketThread(Handler handler, SocketInterface si) {
        this.mHandler = handler;
        this.mSi = si;
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        String recvStr = null;
        try {
            socket.connect(new InetSocketAddress(LConstants.GATEWAY_IP, LConstants.PORT), 1000);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "gb2312")), true);
            BufferedReader recvBuf = new BufferedReader(new InputStreamReader(socket.getInputStream(), "gb2312"));
            sleep(1000);
            String content = LCommands.BASE_IP;
            String ipStr = "FFIP" + content + "FE";
            String sendIp = StringUtil.sumStr(ipStr);
            out.println(sendIp);//发送服务器IP
            mSi.getPrintWriter(out);
            int recvNum = 0;
            while (true) {
                if (recvBuf != null) {
                    if (recvNum > 1) {
                        socket.close();
                        recvBuf.close();
                        out.close();
                        return;
                    } else {
                        recvStr = recvBuf.readLine();
                        if (recvStr != null) {
                            recvNum++;
                            Message msg = new Message();
                            msg.obj = recvStr;
                            mHandler.sendMessage(msg);
                        }
                    }

                }
            }
        } catch (Exception e) {
            if(e instanceof IOException){
                Message msg = new Message();
                msg.obj = "SOCKET_CON_FAILD";
                mHandler.sendMessage(msg);
            }

        }


    }

    public interface SocketInterface {
        public void getPrintWriter(PrintWriter out);
    }
}
