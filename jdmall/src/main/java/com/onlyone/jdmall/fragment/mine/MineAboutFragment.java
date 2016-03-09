package com.onlyone.jdmall.fragment.mine;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/5 19:46
 * @描述: ${TODO}
 */
public class MineAboutFragment extends BaseFragment<Object> implements View.OnClickListener {

    private MainActivity mMainActivity;
    private View         mTopBarView;
    private TextView mAboutBack;

    @Override
    public void onResume() {
        Log.d("aaa", "MineAboutFragment----onResume");
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_about, null);
        mAboutBack = (TextView) mTopBarView.findViewById(R.id.mine_tv_about_back);
        mAboutBack.setOnClickListener(this);
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
        View rootView = View.inflate(ResUtil.getContext(),R.layout.mine_about,null);
        return rootView;
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
        /*FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(MineFragment.TAG_MINEABOUT_FRAGMENT);
        transaction.remove(fragment);
        mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
        transaction.commit();*/
        ((HolderFragment)getParentFragment()).goBack();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("aaa", "MineAboutFragment----onResume");
        mMainActivity.setHideTopBar(true);
    }
}
