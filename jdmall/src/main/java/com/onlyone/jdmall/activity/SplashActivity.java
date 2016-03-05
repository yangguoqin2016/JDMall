package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_rl)
    RelativeLayout mSplashRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initAnimation();
        initData();
    }

    private void initAnimation() {
        AlphaAnimation alpah = new AlphaAnimation(1.0f, 0);
        alpah.setDuration(2000);
        mSplashRl.startAnimation(alpah);
        alpah.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(ResUtil.getContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    private void initData() {

    }
}
