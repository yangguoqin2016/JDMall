package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.LoginOrRegistBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.home
 * @创建者: Administrator
 * @创建时间: 2016/3/5 21:46
 * @描述: ${TODO}
 */
public class LoginFragment extends BaseFragment<LoginOrRegistBean> implements View.OnClickListener {

    @Bind(R.id.login_et_username)
    EditText mLoginEtUsername;
    @Bind(R.id.login_et_password)
    EditText mLoginEtPassword;
    @Bind(R.id.login_btn)
    Button   mLoginBtn;
    @Bind(R.id.login_remember_cb)
    CheckBox mLoginRememberCb;
    @Bind(R.id.login_tv_forgetpwd)
    TextView mLoginTvForgetpwd;

    private LoadListener<LoginOrRegistBean> mListener;
    private LoginOrRegistBean               mLoginBean;
    private String                          mUsername;
    private String                          mPassword;
    private SPUtil                          mSp;

    @Override
    protected void refreshSuccessView(LoginOrRegistBean data) {

    }

    @Override
    protected View loadSuccessView() {
        View loginView = View.inflate(ResUtil.getContext(), R.layout.inflate_login, null);
        ButterKnife.bind(this, loginView);

        return loginView;
    }

    @Override
    protected void loadData(LoadListener<LoginOrRegistBean> listener) {
        mListener = listener;
        mLoginBtn.setOnClickListener(this);


    }

    @Override
    protected void handleError(Exception e) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSp = new SPUtil(ResUtil.getContext());
        //帐号及密码的回显
        String username = mSp.getString("username", null);
        String password = mSp.getString("password", null);
        boolean checked = mSp.getBoolean("checked", false);
        if (checked) {
            mLoginEtUsername.setText(username);
            mLoginEtPassword.setText(password);
            mLoginRememberCb.setChecked(checked);
        }

        View topBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_login, null);
        MainActivity activity = (MainActivity) getActivity();
        //设置登录界面的状态栏
        activity.setTopBarView(topBarView);
        ButterKnife.bind(this, topBarView);
        return topBarView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        mUsername = mLoginEtUsername.getText().toString().trim();
        mPassword = mLoginEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(ResUtil.getContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        RequestQueue queue = Volley.newRequestQueue(ResUtil.getContext());

//        String url = "http://10.0.2.2:8080/market/login?";
        String url = Url.ADDRESS_SERVER+"/login?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                mLoginBean = gson.fromJson(s, LoginOrRegistBean.class);
                mListener.onSuccess(mLoginBean);
                if(mLoginBean.response.equals("login")){
                    Toast.makeText(ResUtil.getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResUtil.getContext(),"帐号或密码有误...",Toast.LENGTH_SHORT).show();
                    return;
                }


//                Log.d(TAG, mLoginBean.response);
//                Log.d(TAG, "" + mLoginBean.userInfo.userid);

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
            //保存帐号及密码
            mSp.putString("username", mUsername);
            mSp.putString("password", mPassword);
        }
    }
}
