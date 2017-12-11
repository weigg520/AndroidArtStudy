package com.example.a75213.myapplication.chapter02.ipc;

import android.text.TextUtils;
import android.util.Log;

import com.example.a75213.myapplication.constant.Constant;
import com.example.a75213.myapplication.utils.FileTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by 75213 on 2017/12/9.
 */

public class UserManager implements Serializable {
    public int sUserId = 1;
    private static final int serialVersionUID = 0;

    /**
     * 序列化过程模拟
     */
    public static void setUser(UserManager user){
        try {
            File file = null;
            //  1  ：判断sdcard是否存在
            if (FileTools.isSDCardAvailable()) {
                // 创建文件夹
                FileTools.createFileDir(Constant.FILESAVEPATH);
                // 创建文件
                //判断文件名是否为空
                file = new File(Constant.FILESAVEPATH + "cache.txt");
            }
            ObjectOutputStream out = null;
                out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(user);
                Log.e("测试" , Constant.FILESAVEPATH + "cache.txt");
                out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 反序列化过程模拟
     */
    public static UserManager getUser(){
        UserManager newUser = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(Constant.FILESAVEPATH + "cache.txt")));
            try {
                newUser = (UserManager)in.readObject();
                in.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUser;
    }
}
