package com.xiaomomo.visitshop.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaomomo.visitshop.MainActivity;
import com.xiaomomo.visitshop.R;
import com.xiaomomo.visitshop.bean.LoginResponse;
import com.xiaomomo.visitshop.bean.User;
import com.xiaomomo.visitshop.okhttp.CommOkHttpClient;
import com.xiaomomo.visitshop.okhttp.OkHttpCallBack;
import com.xiaomomo.visitshop.okhttp.OkHttpRequest;
import com.xiaomomo.visitshop.utils.CommonUtils;
import com.xiaomomo.visitshop.utils.HttpUrl;
import com.xiaomomo.visitshop.utils.SharePreferenceUtil;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lihuanxing on 2017/7/8.
 */

public class LoginActivity extends BaseActivity {

    private final static Logger LOGGER = Logger.getLogger("LoginActivity");

    private Button mLoginBtn;
    private TextInputLayout mNameLayout;
    private TextInputLayout mPasswordLayout;
    private EditText mNameEdit;
    private EditText mPasswordEdit;
    private RelativeLayout mLoadingView;

    private String mUserName;
    private String mUserPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        SQLiteDatabase database = Connector.getDatabase();
        if (checkLogin()) {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        } else {
            initStatusBarColor();
        }

    }

    private void setListener() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    public void login() {
        if (checkData()) {
            //进行网络请求
            mLoadingView.setVisibility(View.VISIBLE);
            OkHttpClient client = new OkHttpClient();
            HashMap<String, String> hashParams = new HashMap<>();
            hashParams.put("userid", mUserName);
            hashParams.put("password", mUserPassword);
            CommOkHttpClient.getInstance().postRequest(
                    HttpUrl.Login, hashParams, new OkHttpCallBack() {
                        @Override
                        public void onSuccess(String response) {
                            Gson gson = new Gson();
                            LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                            mLoadingView.setVisibility(View.GONE);
                            if (loginResponse.getCode() == 0) {
                                SharePreferenceUtil.setShareString(getApplicationContext(),
                                        "userid", loginResponse.getBody().getUserid());
                                DataSupport.deleteAll(User.class);
                                User user = new User();
                                user.setUserId(loginResponse.getBody().getUserid());
                                user.setNickName(loginResponse.getBody().getNickname());
                                user.setSex(loginResponse.getBody().getSex());
                                user.setJob(loginResponse.getBody().getJob());
                                user.setArea(loginResponse.getBody().getArea());
                                user.setPhoneNum(loginResponse.getBody().getPhonenum());
                                user.setImg(loginResponse.getBody().getImg());
                                //保存数据到数据库中
                                user.save();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                LOGGER.info("onSuccess:" + loginResponse.toString());
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.login_name_psd_failure_toast),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            LOGGER.info("onFailure");
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.login_network_error),
                                    Toast.LENGTH_LONG).show();
                        }
                    });


        }
    }


    /**
     * 检查输入数据的合法性
     */
    private boolean checkData() {
        mUserName = mNameEdit.getText().toString().trim();
        mUserPassword = mPasswordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(mUserName)) {
            mNameLayout.setError("用户名不能为空");
            return false;
        }

        if (mUserName.length() < 0 || mUserName.length() > 8) {
            mNameLayout.setError("请输入8位以内的用户名");
            return false;
        }

        if (TextUtils.isEmpty(mUserPassword)) {
            mPasswordLayout.setError("密码不能为空");
            return false;
        }

        return true;
    }

    /**
     * 判断是否已经登录
     *
     * @return
     */
    private boolean checkLogin() {
        //往数据库查找数据
        List<User> users = DataSupport.findAll(User.class);
        if (null != users && users.size() > 0) {
            return true;
        }
        return false;
    }

    private void initView() {
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mNameLayout = (TextInputLayout) findViewById(R.id.name_input_layout);
        mNameEdit = (EditText) findViewById(R.id.name_edit);
        mPasswordLayout = (TextInputLayout) findViewById(R.id.psd_input_layout);
        mPasswordEdit = (EditText) findViewById(R.id.pwd_edit);
        mLoadingView = (RelativeLayout) findViewById(R.id.loading_include);

        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNameLayout.setErrorEnabled(false);
            }
        });

        mPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPasswordLayout.setErrorEnabled(false);
            }
        });
    }

    /**
     * 初始化状态栏颜色透明，和背景色一致
     */
    private void initStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
