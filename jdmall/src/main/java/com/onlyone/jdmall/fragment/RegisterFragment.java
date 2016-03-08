package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.onlyone.jdmall.constance.SP;
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
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Administrator
 * @创建时间: 2016/3/6 14:42
 * @描述: ${TODO}
 */
public class RegisterFragment extends BaseFragment<LoginOrRegistBean> implements View.OnClickListener {

    @Bind(R.id.regist_et_password)
    EditText mRegistEtPassword;
    @Bind(R.id.regist_confrim_password)
    EditText mRegistConfrimPassword;
    @Bind(R.id.regist_btn)
    Button   mRegistBtn;
    @Bind(R.id.regist_et_username)
    EditText mRegistEtUsername;
    private LoadListener<LoginOrRegistBean> mListener;

    private String mUsername;
    private String mPassword;
    private String mConfirmPwd;
    LoginOrRegistBean mRegistBean;
    private SPUtil   mSp;
    private TextView mRegistBack;

    @Override
    protected void refreshSuccessView(LoginOrRegistBean data) {

    }

    @Override
    protected View loadSuccessView() {
        View registView = View.inflate(ResUtil.getContext(), R.layout.inflate_regist, null);
        ButterKnife.bind(this, registView);
        return registView;
    }

    @Override
    protected void loadData(LoadListener<LoginOrRegistBean> listener) {
        listener.onSuccess(null);
        mRegistBtn.setOnClickListener(this);
        mRegistBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSp = new SPUtil(ResUtil.getContext());
        //设置注册的状态栏
        View topBarRegistView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_regist, null);
        mRegistBack = (TextView) topBarRegistView.findViewById(R.id.regist_back);
        ((MainActivity) getActivity()).setTopBarView(topBarRegistView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        //获取输入的信息
        mUsername = mRegistEtUsername.getText().toString().trim();
        mPassword = mRegistEtPassword.getText().toString().trim();
        mConfirmPwd = mRegistConfrimPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(ResUtil.getContext(), "用户名及密码不能为空...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mPassword.equals(mConfirmPwd)) {
            Toast.makeText(ResUtil.getContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(ResUtil.getContext());

        //        String url = "http://10.0.2.2:8080/market/register?";
        String url = Url.ADDRESS_SERVER + "/register?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                mRegistBean = gson.fromJson(s, LoginOrRegistBean.class);
                if (mRegistBean.response.equals("register")) {
                    Toast.makeText(ResUtil.getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    SystemClock.sleep(1500);
                    //注册成功,保存userid
                    mSp.putLong(SP.USERID, mRegistBean.userInfo.userid);
                    changeFragment();


                } else {
                    Toast.makeText(ResUtil.getContext(), "该用户名已被注册...", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ResUtil.getContext(), "注册失败...", Toast.LENGTH_SHORT).show();

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

    }

    private void changeFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(RegisterFragment.this);
        transaction.add(R.id.fl_content_container, new LoginFragment());
        transaction.commit();
    }
}
