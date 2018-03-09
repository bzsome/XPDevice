package chao.xp.device.tool;

import android.content.Context;

import java.net.*;

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
                try {
                    // 对内容编码，防止内容中有空格等字符导致崩溃
                    String params = URLEncoder.encode(param, "UTF-8");
                    String u = webUrl + params;
                    URL url = new URL(u);// 根据链接（字符串格式），生成一个URL对象
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();// 打开URL
                    urlConnection.setRequestMethod("GET");
                    urlConnection.getInputStream();
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
