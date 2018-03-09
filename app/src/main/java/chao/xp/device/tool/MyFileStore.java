package chao.xp.device.tool;

import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.util.*;

/**
 * Created by chao on 2018/3/7.
 */

public class MyFileStore {
    private final static String fileName = "myfile.txt";//文件名
    private final static String fileDir = "chaoApp";//文件夹名

    /**
     * 获得储存文件名(同时初始化文件)
     *
     * @return
     */
    private static String getFileName() {

        File file = new File(getSDPath() + "/" + fileDir);
        if (!file.exists()) {
            Log.d("文件夹不存在：", file.toString());
            file.mkdir();
        }
        file = new File(getSDPath() + "/" + fileDir + "/" + fileName);
        if (!file.exists()) {
            Log.d("文件不存在：", file.toString());
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d("新建文件", "新建失败!" + file.toString());
            }
        }
        return file.getPath();
    }

    /**
     * 保存键值
     *
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        String filePath = getFileName();
        Properties pt = new Properties();
        File file = new File(filePath);

        try {
            pt.load(new FileInputStream(filePath));
            pt.setProperty(key, value);
            pt.store(new FileOutputStream(filePath), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得Key的值
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String filePath = getFileName();
        Properties pt = new Properties();
        try {
            pt.load(new FileInputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pt.getProperty(key);
    }

    /**
     * 获得sdcard卡路径
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
