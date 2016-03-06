package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/5 19:46
 * @描述: ${TODO}
 */
public class MineAboutFragment extends Fragment{

    private MainActivity mMainActivity;

    @Override
    public void onResume() {
        View topBarView = View.inflate(ResUtil.getContext(),R.layout.inflate_topbar_about,null);

        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setHideTopBar(true);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(ResUtil.getContext(), R.layout.mine_about,null);
        return rootView;
    }
}
