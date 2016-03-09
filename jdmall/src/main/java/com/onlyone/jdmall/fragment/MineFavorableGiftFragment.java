package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Administrator
 * @创建时间: 2016/3/7 17:01
 * @描述: ${TODO}
 */
public class MineFavorableGiftFragment extends SuperBaseFragment {
    private MainActivity mActivity;
    private TextView mFavorableGiftTv;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    protected void loadData(LoadListener listener) {

        listener.onSuccess(null);
    }

    @Override
    protected Object parseJson(String jsonStr) {

        return null;
    }

    @Override
    protected View loadSuccessView() {
        View view = View.inflate(ResUtil.getContext(), R.layout.mine_favorable_gift, null);
        return view;
    }

    @Override
    protected void refreshSuccessView(Object data) {
        mFavorableGiftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_content_container,new MineFragment());
                transaction.commit();*/
                ((HolderFragment)getParentFragment()).goBack();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View favorableGiftTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate__topbar_favorable_gift, null);
        mFavorableGiftTv = (TextView) favorableGiftTopBarView.findViewById(R.id.favorable_gift_tv);
        mActivity = (MainActivity) getActivity();
        mActivity.setTopBarView(favorableGiftTopBarView);
        mActivity.setHideTopBar(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
