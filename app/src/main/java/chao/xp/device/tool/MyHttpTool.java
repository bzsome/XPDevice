package chao.xp.device.tool;

import java.io.InputStream;
import java.net.*;
import java.util.Properties;

/**
 * 记录程序的运行环境(java版)
 * Created by chao on 2018/2/25.
 */

public class MyHttpTool {
    private final static String webUrl = "http://www.bzchao.com/ip/test/?";

    //获取运行环境信息，只获取一次
    static {
        upload(getSystemInfo());
    }

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
     * 获取系统信息
     *
     * @return
     */
    private static String getSystemInfo() {
        String info = "系统信息：";//系统信息
        try {
            Properties props = System.getProperties(); // 系统属性
            String java = "Java的安装路径:" + props.getProperty("java.home");
            String version = "操作系统的版本:" + props.getProperty("os.version");
            String user = "用户的主目录:" + props.getProperty("user.home");

            info = java + "&" + version + "&" + user;
        } catch (Exception e) {
            System.out.println("获取系统信息失败！");
            e.printStackTrace();
        }
        return info;
    }
}
