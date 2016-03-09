package com.onlyone.jdmall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.LoginOrRegistBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExtraLoginActivity extends AppCompatActivity {

    @Bind(R.id.login_tv_regist)
    TextView    mLoginTvRegist;
    @Bind(R.id.rb_bottom_home)
    RadioButton mRbBottomHome;
    @Bind(R.id.rb_bottom_search)
    RadioButton mRbBottomSearch;
    @Bind(R.id.rb_bottom_band)
    RadioButton mRbBottomBand;
    @Bind(R.id.rb_bottom_car)
    RadioButton mRbBottomCar;
    @Bind(R.id.rb_bottom_mine)
    RadioButton mRbBottomMine;
    @Bind(R.id.login_et_username)
    EditText    mLoginEtUsername;
    @Bind(R.id.login_et_password)
    EditText    mLoginEtPassword;
    @Bind(R.id.login_btn)
    Button      mLoginBtn;
    @Bind(R.id.login_remember_cb)
    CheckBox    mLoginRememberCb;
    @Bind(R.id.login_tv_forgetpwd)
    TextView    mLoginTvForgetpwd;
    @Bind(R.id.bottom_rg)
    RadioGroup  mBottomRg;


    private LoginOrRegistBean mLoginBean;
    private String            mUsername;
    private String            mPassword;
    private SPUtil            mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_extra_login);
        ButterKnife.bind(this);
        mBottomRg.check(R.id.rb_bottom_mine);

        mSp = new SPUtil(ResUtil.getContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.login_tv_regist, R.id.login_btn, R.id.extra_login_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv_regist:
                Toast.makeText(this, "小样,别处去注册吧~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_btn:
                login();
                break;

            case R.id.extra_login_back:
                finish();
                break;
        }
    }

    /**
     * 根据给定账号密码登录
     */
    private void login() {

        mUsername = mLoginEtUsername.getText().toString().trim();
        mPassword = mLoginEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(ResUtil.getContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(ResUtil.getContext());

        // String url = "http://10.0.2.2:8080/market/login?";
        String url = Url.ADDRESS_SERVER + "/login?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                mLoginBean = gson.fromJson(s, LoginOrRegistBean.class);

                if (mLoginBean.response.equals("login")) {
                    Toast.makeText(ResUtil.getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    mSp.putString(SP.USERNAME, mUsername);
                    //登录成功,保存userid
                    mSp.putLong(SP.USERID, mLoginBean.userInfo.userid);
                    mSp.putBoolean(SP.ISLOGINSUCCESS, true);

                    /*--------------------返回原商品详情页-----------------*/
                    finish();
                } else {
                    Toast.makeText(ResUtil.getContext(), "帐号或密码有误...", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ResUtil.getContext(), "登录失败...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //加入参数,用户名及密码
                params.put("username", mUsername);
                params.put("password", mPassword);

                return params;
            }
        };


        queue.add(request);

        boolean checked = mLoginRememberCb.isChecked();
        mSp.putBoolean("checked", checked);
        //记住密码已勾选
        if (checked) {
            //保存密码
            mSp.putString(SP.PASSWORD, mPassword);
        }
    }


}
