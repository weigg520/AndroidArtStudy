package com.example.a75213.myapplication.chapter02.scoket.tcp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by 75213 on 2017/12/15.
 */

public class TCPServerService extends Service {
    private static final String TAG = TCPServerService.class.getSimpleName();

    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[]{
            "你好啊 ，哈哈",
            "请问你叫什么名字阿？",
            "今天北京天气不错阿，帅哥!",
            "你知道吗？我可是可以和多个人聊天哦~",
            "给你讲个笑话吧：据说爱笑的人运气不会太差喔~"
    };

    private class TcpService implements Runnable{
        @SuppressWarnings("resource")
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听本地8688端口
                serverSocket = new ServerSocket(8688);
            }catch (IOException e){
                System.err.println("establish tcp service failed,port:8868");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestoryed){
                try {
                    //接受客户端前期
                    final Socket client = serverSocket.accept();
                    System.out.println("accept");
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        //用于接受客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())) , true);
        out.println("欢迎来到聊天室");
        while (!mIsServiceDestoryed){
            String str = in.readLine();
            System.out.println("msg from client:" + str);
            if (str == null){
                //客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            System.out.println("send : " + msg);
        }
        System.out.println("client quit.");
        //关闭流
        in.close();
        out.close();
        client.close();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
