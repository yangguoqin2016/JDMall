package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

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

        View brandView = View.inflate(ResUtil.getContext(), R.layout.inflate_brand, null);

        return brandView;
    }

    @Override
    protected void loadData(LoadListener listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View topBrand = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_brand, null);
        MainActivity mainActivity = (MainActivity) getActivity();
        //设置状态栏
        mainActivity.setTopBarView(topBrand);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
