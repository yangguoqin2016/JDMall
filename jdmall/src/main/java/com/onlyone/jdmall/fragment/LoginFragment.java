package com.onlyone.jdmall.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
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
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.progressbutton.CircularProgressButton;
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
    EditText               mLoginEtUsername;
    @Bind(R.id.login_et_password)
    EditText               mLoginEtPassword;
    @Bind(R.id.login_btn)
    CircularProgressButton mLoginBtn;
    @Bind(R.id.login_remember_cb)
    CheckBox               mLoginRememberCb;
    @Bind(R.id.login_tv_forgetpwd)
    TextView               mLoginTvForgetpwd;

    private MainActivity      mMainActivity;
    private LoginOrRegistBean mLoginBean;
    private String            mUsername;
    private String            mPassword;
    private SPUtil            mSp;
    private TextView          mLoginTvRegist;
    private ValueAnimator     mWidthAnimation;

    @Override
    public void onStart() {
        super.onStart();
        mMainActivity = (MainActivity) getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void refreshSuccessView(LoginOrRegistBean data) {
        mLoginBtn.setOnClickListener(this);

        mLoginTvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HolderFragment) getParentFragment()).goForward(new RegisterFragment());
            }
        });

    }


    @Override
    protected View loadSuccessView() {
        View loginView = View.inflate(ResUtil.getContext(), R.layout.inflate_login, null);
        ButterKnife.bind(this, loginView);
        //帐号及密码的回显
        String username = mSp.getString(SP.USERNAME, null);
        String password = mSp.getString(SP.PASSWORD, null);
        boolean checked = mSp.getBoolean("checked", false);
        if (checked) {
            mLoginEtUsername.setText(username);
            mLoginEtPassword.setText(password);
            mLoginRememberCb.setChecked(checked);
        }
        return loginView;
    }

    @Override
    protected void loadData(LoadListener<LoginOrRegistBean> listener) {
        listener.onSuccess(null);

    }


    @Override
    protected void handleError(Exception e) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSp = new SPUtil(ResUtil.getContext());
        View topBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_login, null);
        MainActivity activity = (MainActivity) getActivity();
        //设置登录界面的状态栏
        activity.setTopBarView(topBarView);
        mLoginTvRegist = (TextView) topBarView.findViewById(R.id.login_tv_regist);


        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //mMainActivity.setHideTopBar(true);
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

              // String url = "http://10.0.2.2:8080/market/login?";
        String url = Url.ADDRESS_SERVER + "/login?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                mLoginBean = gson.fromJson(s, LoginOrRegistBean.class);

                if (mLoginBean.response.equals("login")) {

                    if (mLoginBtn.getProgress() == 0) {
                        simulateSuccessProgress(mLoginBtn);
                    } else {
                        mLoginBtn.setProgress(0);

                    }
                    mWidthAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Toast.makeText(ResUtil.getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            mSp.putString(SP.USERNAME, mUsername);
                            //登录成功,保存userid
                            mSp.putLong(SP.USERID, mLoginBean.userInfo.userid);
                            mSp.putBoolean(SP.ISLOGINSUCCESS, true);
                            mMainActivity.mRgBottomNav.check(R.id.rb_bottom_home);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                } else {
                    Toast.makeText(ResUtil.getContext(), "帐号或密码有误...", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (mLoginBtn.getProgress() == 0) {
                    simulateErrorProgress(mLoginBtn);
                } else {
                    mLoginBtn.setProgress(0);
                }
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

    private void changeFragment() {
		((HolderFragment)getParentFragment()).goBack();
    }

    /**
     * 成功
     */
    private void simulateSuccessProgress(final CircularProgressButton button) {
        mWidthAnimation = ValueAnimator.ofInt(1, 100);
        mWidthAnimation.setDuration(1500);
        mWidthAnimation.setRepeatCount(1);
        mWidthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mWidthAnimation
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        button.setProgress(value);
                    }
                });
        mWidthAnimation.start();
    }

    /**
     * 失败
     */
    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        button.setProgress(value);
                        if (value == 99) {
                            button.setProgress(-1);
                        }
                    }
                });
        widthAnimation.start();
    }
}
