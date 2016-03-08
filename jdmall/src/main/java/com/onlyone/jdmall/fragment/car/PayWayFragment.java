package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.view.View;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.car
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 20:41
 * @描述: 支付方式的Fragment
 */
public class PayWayFragment extends BaseFragment<Object> {
    private MainActivity mMainActivity;
    @Override
    public void onAttach(Context context) {
        mMainActivity = (MainActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        View topBar = View.inflate(mMainActivity, R.layout.inflate_topbar_payway, null);
        mMainActivity.setTopBarView(topBar);
        super.onResume();
    }

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View contentView = View.inflate(mMainActivity, R.layout.inflate_payway, null);
        return contentView;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }
}
