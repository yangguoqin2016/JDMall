package com.onlyone.jdmall.fragment.mine;

import android.view.View;

import com.onlyone.jdmall.bean.AddressBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.SuperBaseFragment;

/**
 * @Author Never
 * @Date 2016/3/7 14:45
 * @Desc ${地址管理页面}
 */
public class AddressManagerFragment extends SuperBaseFragment<AddressBean>{
    @Override
    protected String getUrl() {
        //http://localhost:8080/market/addresslist
        return Url.ADDRESS_SERVER;
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    protected AddressBean parseJson(String jsonStr) {
        return null;
    }

    @Override
    protected View loadSuccessView() {
        return null;
    }

    @Override
    protected void refreshSuccessView(AddressBean data) {

    }
}
