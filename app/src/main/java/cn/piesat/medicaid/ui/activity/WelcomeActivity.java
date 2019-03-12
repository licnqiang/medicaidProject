package cn.piesat.medicaid.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;

import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.util.FileUtil;
import cn.piesat.medicaid.util.ZipProgressUtil;

/**
 * @author lq
 * @fileName
 * @data on  2019/2/27 14:05
 * @describe TODO
 */
public class WelcomeActivity extends BaseActivity {
    String[] mPerms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /*界面跳转时间*/
    private long DELAY_TIME = 2000;
    private int mRequestCode = 0x1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        requestPer();
    }


    private void UnZip() throws IOException {
//        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        ZipProgressUtil.UnZipFile((FileInputStream) this.getResources().getAssets().open("map.zip"), FileUtil.getMapPath(), new ZipProgressUtil.ZipListener() {
            @Override
            public void zipSuccess() {
                Log.e("--------","----zipSuccess------");
            }

            @Override
            public void zipStart() {
                Log.e("--------","----zipStart------");
            }

            @Override
            public void zipProgress(int progress) {
                Log.e("--------","----zipProgress------"+progress);
            }

            @Override
            public void zipFail() {
                Log.e("--------","----zipFail------");
            }
        });
    }


    /**
     * 请求权限
     */
    private void requestPer() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            starMain();
        } else {
            ActivityCompat.requestPermissions(this, mPerms, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    starMain();
                } else {
                    showPermiDialog();
                }
            }
        }
    }

    /**
     * 提示手动开启权限
     */
    private void showPermiDialog() {
        new AlertDialog.Builder(WelcomeActivity.this)
                .setTitle("提示")
                .setCancelable(false)
                .setMessage("需要获取相应权限，否则您的应用将无法正常使用! 请手动开启权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", WelcomeActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        // Start for result
                        WelcomeActivity.this.startActivityForResult(intent, mRequestCode);
                    }
                })
                .create().show();
    }

    /**
     * 两秒跳转
     */
    private void starMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 启动登录窗口
                toActivity(MainActivity.class);
                // 关闭启动画面
                finish();
            }
        }, DELAY_TIME);
    }
}
