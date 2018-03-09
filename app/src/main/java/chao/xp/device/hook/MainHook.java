package chao.xp.device.hook;


import android.telephony.TelephonyManager;

import chao.xp.device.MyConstant;
import chao.xp.device.tool.MyFileStore;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.*;

public class MainHook implements IXposedHookLoadPackage {
    private static String nowPackageName = "chao.xp.device";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        XposedBridge.log("--------device handleLoadPackage");
        XposedBridge.log("目标包名：" + lpparam.packageName);
        new DeviceHook(lpparam);

        myHookFun(lpparam);

        if (!lpparam.packageName.contains(nowPackageName)) {
            XposedBridge.log(lpparam.packageName + "，非本应用，劫持IMEI");
            String vIMEI = getAlterIMEI();
            if (vIMEI != null && vIMEI.length() >= 5) {
                HookMethod(TelephonyManager.class, "getDeviceId", vIMEI);
            } else {
                XposedBridge.log("未设置IMEI 不劫持");
            }
        } else {
            XposedBridge.log("获取IMEI，是本应用，返回真实IMEI");
        }
    }

    private void HookMethod(final Class cl, final String method,
                            final String result) {
        try {
            XposedHelpers.findAndHookMethod(cl, method, new Object[]{new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    param.setResult(result);
                }

            }});
        } catch (Throwable e) {
        }
    }

    /**
     * 读取修改后的IMEI
     *
     * @return
     */
    public static String getAlterIMEI() {
        //获得Sharedpreference保存的数据
        String vIMEI = MyFileStore.get(MyConstant.ALTER_IMEI);
        if (vIMEI != null && vIMEI.length() > 5) {
            return vIMEI;
        }
        return null;
    }

    /**
     * 劫持某个函数
     */
    private static void myHookFun(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //劫持指定函数
        if (lpparam.packageName.contains(nowPackageName)) {
            Class clazz = lpparam.classLoader.loadClass(nowPackageName + ".MainActivity");
            XposedHelpers.findAndHookMethod(clazz, "toastMessage", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("MainHook 成功劫持");
                    param.setResult("你已被劫持");
                }
            });
        }
    }
}