package com.onlyone.jdmall.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.UIUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/7 23:45
 * @描述: ${TODO}
 */
public class MineUserFeedbackFragment extends BaseFragment<Object> implements View.OnClickListener {
    private MainActivity    mMainActivity;
    private FragmentManager mManager;
    private View            mTopBarView;

    @Override
    public void onResume() {
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_sendway, null);
        TextView tvTopTitle = (TextView) mTopBarView.findViewById(R.id.mine_tv_top_title);
        tvTopTitle.setText("用户反馈");
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(mTopBarView);
        mMainActivity.setHideTopBar(false);
        super.onResume();
    }

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View successView = View.inflate(ResUtil.getContext(), R.layout.mine_user_feedback, null);
        Button btnFeedback = (Button) successView.findViewById(R.id.user_feedback_btn);
        btnFeedback.setOnClickListener(this);
        return successView;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(ResUtil.getContext(),"谢谢您的宝贵意见，我们会做到更好",Toast.LENGTH_SHORT).show();
        UIUtil.getUiHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMainActivity = (MainActivity) getActivity();
                mManager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = mManager.beginTransaction();
                Fragment fragment = mManager.findFragmentByTag(MineFragment.TAG_USERFEEDBACK_FRAGMENT);
                transaction.remove(fragment);
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
                transaction.commit();
            }
        }, 1500);
    }
}
