package chao.xp.device;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.*;

import chao.xp.device.tool.*;

public class MainActivity extends Activity {

    //IMEI修改输入框
    private EditText imeitx;
    private TextView tvReal, tvAlter, tvRes;
    private Button btnSave, btnAlter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imeitx = (EditText) findViewById(R.id.edt_imei);
        tvReal = (TextView) findViewById(R.id.tv_real);
        tvAlter = (TextView) findViewById(R.id.tv_alter);
        tvRes = (TextView) findViewById(R.id.tv_res);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnAlter = (Button) findViewById(R.id.btn_alter);

        //点击按钮，将数据保存到SharedPreference中
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                tvAlter.setText(getAlterIMEI());
            }
        });
        btnAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRes.setText("劫持结果:" + toastMessage());
            }
        });
    }

    /**
     * 将数据展示在EditText里面
     */
    @Override
    protected void onResume() {
        super.onResume();
        tvReal.setText(getRealIMEI());
        tvAlter.setText(getAlterIMEI());
        //上传程序运行环境信息
        AndHttpTool.upSystemInfo(this);
    }


    public void save() {
        try {
            MyFileStore.put(MyConstant.ALTER_IMEI, imeitx.getText().toString());
        } catch (Exception e) {
            MyHttpTool.upload("error save():" + e.toString());
        }
    }

    //   劫持测试函数
    public String toastMessage() {
        return "我未被劫持";
    }

    //读取真实IMEI
    public String getRealIMEI() {
        TelephonyManager phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "getRealIMEI() 权限检查失败！", Toast.LENGTH_SHORT).show();
            return "未授权，无法获得IMEI";
        }
        return phone.getDeviceId();
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
        return "未设置IMEI";
    }
}