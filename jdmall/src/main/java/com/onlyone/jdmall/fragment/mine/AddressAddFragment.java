package com.onlyone.jdmall.fragment.mine;

import android.view.View;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @Author Never
 * @Date 2016/3/8 9:33
 * @Desc ${新增地址界面}
 */
public class AddressAddFragment extends BaseFragment {

    public  View         mAddView;
    private View         mTopBarView;
    private MainActivity mMainActivity;

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        mAddView = View.inflate(ResUtil.getContext(), R.layout.mine_address_add, null);

        return mAddView;
    }

    @Override
    public void onResume() {
        mMainActivity = (MainActivity) getActivity();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_addressadd, null);
        mMainActivity.setTopBarView(mTopBarView);
        mMainActivity.setHideTopBar(false);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void loadData(LoadListener listener) {

    }

    @Override
    protected void handleError(Exception e) {

    }
}
