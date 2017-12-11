package com.example.a75213.myapplication.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 描述：文件读写操作类
 * 作者：dc on 2017/9/26 17:53
 * 邮箱：597210600@qq.com
 */
public class FileTools {

    /**
     * @descriptoin	写内容到文件
     * @author	dc
     * @param append 是否追加
     * @date 2017/9/26 17:59
     * @return
     */
    public static void writeFile(String fileDir, String fileName, String text, boolean append){

        File file = null;
        //  1  ：判断sdcard是否存在
        if(isSDCardAvailable()){
            // 创建文件夹
            createFileDir(fileDir);
            // 创建文件
            //判断文件名是否为空

            if (TextUtils.isEmpty(fileName)) {
                file = new File(fileDir + "app_log.txt");
            } else {
                file = new File(fileDir + fileName);
            }

        }

        RandomAccessFile raf = null;
        FileOutputStream fileOutputStream = null;

        if(append){
            //如果为追加则在原来的基础上继续写文件
            try {
                raf = new RandomAccessFile(file, "rw");
                byte[] textByte = text.getBytes();
                raf.seek(file.length());  // 追加到这个位置去写文件
                raf.write(textByte);
                raf.write("\r\n".getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                fileOutputStream = new FileOutputStream(fileName);
                byte[] textByte = text.getBytes();
                fileOutputStream.write(textByte);
                fileOutputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @descriptoin	在指定位置追加文本内容
     * @author	dc
     * @param fileDir 文件夹目录
     *                @param fileName 文件名称
     *                                @param text 追加的文本内容
     *                                            @param resourceLen 需要追加的位置
     * @date 2017/9/27 9:08
     * @return
     */
    public static boolean writeRespleText(String fileDir, String fileName,String text, long resourceLen){
        File file = new File(fileDir + fileName);
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file,"rw");
            randomAccessFile.seek(resourceLen);
            randomAccessFile.write(text.getBytes());
            randomAccessFile.write("\r\n".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(null != randomAccessFile){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }




    /** 判断SD卡是否挂载 */
    public static boolean isSDCardAvailable()
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * 创建文件夹
     * @param fileDir
     */
    public static void createFileDir(String fileDir){
        File destDir = new File(fileDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }


}
