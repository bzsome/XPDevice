package chao.xp.device.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by chao on 2018/3/9.
 */

public class DeviceHook {
    public DeviceHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        try {
            Class<?> classBuild = XposedHelpers.findClass("android.os.Build",
                    loadPackageParam.classLoader);

            XposedHelpers.setStaticObjectField(classBuild, "SERIAL",
                    "aabbccdd"); // 串口序列号

            XposedHelpers.findField(android.os.Build.class, "BOARD").set(null, "iPhone");
            XposedBridge.log("已修改设备信息");
        } catch (IllegalAccessException e) {
            System.out.println("error aaaaaaaa");
            e.printStackTrace();
        }
    }
}
