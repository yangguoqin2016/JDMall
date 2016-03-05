package com.onlyone.jdmall.fragment;

import android.view.View;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 10:48
 * @描述: RadioGroup里面的搜索
 */
public class SearchFragment extends  BaseFragment<Object> {
    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        //1.得到TopBar.
        View topBar = View.inflate(ResUtil.getContext(),R.layout.inflate_topbar_search,null);

        //2.首先先拿到Fragment关联的Activity
        MainActivity mainActivity = (MainActivity) getActivity();

        //3.得到MainActivity,再设置TopBar的Ui
        mainActivity.setTopBarView(topBar);

        //搜索页面
        View contentView = View.inflate(ResUtil.getContext(), R.layout.inflate_search, null);
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
