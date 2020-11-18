package com.android.sports.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.android.sports.MyApplication;
import com.android.sports.R;
import com.android.sports.commmon.utils.MySp;
import com.android.sports.commmon.utils.UIHelper;
import com.android.sports.ui.BaseActivity;
import com.android.sports.ui.permission.PermissionHelper;
import com.android.sports.ui.permission.PermissionListener;
import com.android.sports.ui.weight.CountDownProgressView;

import butterknife.BindView;

/**
 * 描述: 闪屏界面
 * 类名: SplashActivity
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.im_url)
    ImageView imUrl;

    // 要申请的权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (ImmersionBar.hasNavigationBar(this)) {
            ImmersionBar.with(this).transparentNavigationBar().init();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        imUrl.setImageResource(R.mipmap.app_icon);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 获取权限
                    PermissionHelper.requestPermissions(SplashActivity.this, PERMISSIONS_STORAGE, new PermissionListener() {
                        @Override
                        public void onPassed() {
                            startActivity();
                        }
                    });
                } else {
                    startActivity();
                }
            }
        }, 3000);

    }

    @Override
    public void initListener() {

    }

    public void startActivity() {
        if (SPUtils.getInstance().getBoolean(MySp.ISLOGIN)) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

}
