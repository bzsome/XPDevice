package chao.xp.device.tool;

import android.content.Context;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 记录程序的运行环境(Android版)
 * Created by chao on 2018/2/25.
 */

public class AndHttpTool {
    private static Context mContext;
    private final static String webUrl = "http://www.bzchao.com/ip/test/?";

    /**
     * 上传指定信息
     *
     * @param param
     */
    public static void upload(final String param) {
        new Thread() {
            @Override
            public void run() {
                String u = webUrl + param;
                try {
                    URL url = new URL(u);
                    InputStream ins = url.openStream();
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 上传程序运行环境信息
     *
     * @param context
     */
    public static void upSystemInfo(Context context) {
        if (mContext == null) {//第一次才上传设备信息
            mContext = context;
            String info = AndDeviceUtil.getInfo(context);
            upload(info);
        }
    }
}
