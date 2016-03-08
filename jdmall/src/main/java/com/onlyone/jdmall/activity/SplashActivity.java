package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private static final long ANIMATION_DELAY = 2000;

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

        //1.旋转
        RotateAnimation rotate = new RotateAnimation(0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(ANIMATION_DELAY);

        //2.缩放
        ScaleAnimation scale = new ScaleAnimation(0f,
                1f,
                0f,
                1f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(ANIMATION_DELAY);

        //3.透明度
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(ANIMATION_DELAY);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        mSplashRl.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
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
