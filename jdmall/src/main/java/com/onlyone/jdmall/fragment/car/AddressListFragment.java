package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.AddressBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.SuperBaseFragment_v2;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.car
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 12:24
 * @描述: 地址列表的Fragment
 */
public class AddressListFragment extends SuperBaseFragment_v2<AddressBean> {
    private static final String KEY_USERID = "userid";
    private SPUtil       mSPUtil;
    private MainActivity mMainActivity;
    private View         mTopBar;
    private ListView mListView;

    @Override
    public void onAttach(Context context) {
        mMainActivity = (MainActivity) context;
        super.onAttach(context);
    }


    @Override
    protected String getUrl() {
        return Url.ADDRESS_ADDRESSLIST;
    }

    @Override
    protected Map<String, String> getHeadersMap() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(KEY_USERID, mSPUtil.getString(SP.USERID, null));
        return headersMap;
    }

    /**
     * 失败时候的回调
     * @param e 异常
     */
    @Override
    protected void handleError(Exception e) {

    }

    /**
     * 解析json数据
     * @param jsonStr
     * @return
     */
    @Override
    protected AddressBean parseJson(String jsonStr) {
        return null;
    }

    /**
     * 成功时候的回调
     * @return
     */
    @Override
    protected View loadSuccessView() {
        mTopBar = initTopBar();
        mListView = new ListView(mMainActivity);
        return mListView;
    }

    /**
     * 初始化TopBar
     * @return
     */
    private View initTopBar() {
        return View.inflate(mMainActivity, R.layout.inflate_topbar_addresslist, null);
    }

    /**
     * 加载成功,刷新UI
     * @param data 数据
     */
    @Override
    protected void refreshSuccessView(AddressBean data) {

    }
}
