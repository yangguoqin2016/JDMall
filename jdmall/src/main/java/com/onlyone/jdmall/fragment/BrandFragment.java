package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Administrator
 * @创建时间: 2016/3/5 15:21
 * @描述: ${TODO}
 */
public class BrandFragment extends BaseFragment {


    @Override
    protected void refreshSuccessView(Object data) {
    }

    @Override
    protected View loadSuccessView() {

        View topBrand = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_brand, null);
        MainActivity mainActivity = (MainActivity) getActivity();
        //设置状态栏
        mainActivity.setTopBarView(topBrand);

        View brandView = View.inflate(ResUtil.getContext(), R.layout.inflate_brand, null);
        ButterKnife.bind(this, brandView);

        return brandView;
    }

    @Override
    protected void loadData(LoadListener listener) {

    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
