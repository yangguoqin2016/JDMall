package com.onlyone.jdmall.fragment;

import android.view.View;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 13:33
 * @描述: ${TODO}
 */
public class SearchResultFragment extends BaseFragment<Object> {
    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View contentView = View.inflate(ResUtil.getContext(), R.layout.inflate_searchresult, null);
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
