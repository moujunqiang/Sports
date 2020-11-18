package com.android.sports.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.android.sports.R;
import com.android.sports.commmon.utils.Conn;
import com.android.sports.commmon.utils.MySp;
import com.android.sports.db.DataManager;
import com.android.sports.db.RealmHelper;
import com.android.sports.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
登录
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.submit)
    Button btLogin;
    @BindView(R.id.tv_register)
    TextView btReg;
    @BindView(R.id.uname)
    EditText etUsername;
    @BindView(R.id.pword)
    EditText etPsd;

    private DataManager dataManager = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        dataManager = new DataManager(new RealmHelper());


    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.submit, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.submit:
                hideSoftKeyBoard();
                if (isInput(etUsername, etPsd)) {
                    login(etUsername.getText().toString(), etPsd.getText().toString());
                }
                break;
            case R.id.tv_register:
                hideSoftKeyBoard();
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                break;

            default:
                break;
        }
    }


    /**
     * 判断输入框内容是否为空
     * @param e
     * @return
     */
    private boolean isInput(EditText... e) {
        for (EditText anE : e) {
            if (TextUtils.isEmpty(anE.getText())) {
                ToastUtils.showShort(anE.getHint().toString());
                return false;
            }
        }
        return true;
    }

    /**
     * 登录
     */
    public void login(String account, String psd) {
        btLogin.setEnabled(false);
        showLoadingView();
        new Handler().postDelayed(() -> {
            dismissLoadingView();
            btLogin.setEnabled(true);
                if (dataManager.checkAccount(account, psd))
                    loginSuccess(account, psd);
                else
                    ToastUtils.showShort("账号或密码错误!");
        }, Conn.Delayed);
    }

    private void loginSuccess(String account, String psd) {
        SPUtils.getInstance().put(MySp.ISLOGIN, true);

        SPUtils.getInstance().put(MySp.USERID, account.substring(8));

        SPUtils.getInstance().put(MySp.PHONE, account);
        SPUtils.getInstance().put(MySp.PASSWORD, psd);

        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        ToastUtils.showShort("恭喜您,登录成功...");

        finish();
    }



    @Override
    protected void onDestroy() {
        if (null != dataManager)
            dataManager.closeRealm();
        super.onDestroy();
    }
}
