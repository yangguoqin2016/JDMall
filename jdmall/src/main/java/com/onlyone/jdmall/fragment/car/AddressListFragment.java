package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.adapter.MyBaseAdapter;
import com.onlyone.jdmall.bean.AddressBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.SuperBaseFragment_v2;
import com.onlyone.jdmall.holder.AddressListHolder;
import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.car
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 12:24
 * @描述: 地址列表的Fragment
 */
public class AddressListFragment extends SuperBaseFragment_v2<AddressBean> implements View.OnClickListener {
    private static final String KEY_USERID = "userid";
    @Bind(R.id.topbar_tv_back)
    TextView mTopbarTvBack;
    @Bind(R.id.topbar_tv_add)
    TextView mTopbarTvAdd;
    private SPUtil                        mSPUtil;
    private MainActivity                  mMainActivity;
    private View                          mTopBar;
    private ListView                      mListView;
    private List<AddressBean.AddressList> mDatas;

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
        //TODO:这个是请求头的userid,必须先登录了才能请求到数据
        headersMap.put(KEY_USERID, mSPUtil.getString(SP.USERID, null));
//        headersMap.put(KEY_USERID, "20428");
        return headersMap;
    }

    /**
     * 失败时候的回调
     *
     * @param e 异常
     */
    @Override
    protected void handleError(Exception e) {
        initTopBar();
        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText("加载数据失败,请检查下你的网络..");
        tv.setTextSize(DensityUtil.dip2Px(20));
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        rootView.addView(tv);
    }

    /**
     * 解析json数据
     *
     * @param jsonStr
     * @return
     */
    @Override
    protected AddressBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, AddressBean.class);
    }

    /**
     * 成功时候的回调
     *
     * @return
     */
    @Override
    protected View loadSuccessView() {
        initTopBar();
        mTopbarTvAdd.setText("管理地址");
        mListView = new ListView(mMainActivity);
        return mListView;
    }

    /**
     * 初始化TopBar
     * 顺带设置点击事件
     * @return
     */
    private void initTopBar() {
        mTopBar = View.inflate(mMainActivity, R.layout.inflate_topbar_addresslist, null);
        ButterKnife.bind(this, mTopBar);
        mMainActivity.setTopBarView(mTopBar);
        mTopbarTvAdd.setOnClickListener(this);
        mTopbarTvBack.setOnClickListener(this);
    }

    /**
     * 加载成功,刷新UI
     *
     * @param addressBean 数据
     */
    @Override
    protected void refreshSuccessView(AddressBean addressBean) {
        //TODO:需要判断请求回来的数据,来更新相应的UI
        mDatas = addressBean.addressList;
        //排序,让默认的地址在第一个
        Collections.sort(mDatas);
        mListView.setAdapter(new AddressListAdapter(mDatas));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //TODO:事件逻辑实现
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        	case R.id.topbar_tv_back://返回

        		break;
            case R.id.topbar_tv_add://新增地址 / 管理地址
                break;
        	default:
        		break;
        }
    }

    class AddressListAdapter extends MyBaseAdapter<AddressBean.AddressList>{
        BaseHolder holder = null;
        public AddressListAdapter(List<AddressBean.AddressList> datas) {
            super(datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                holder = new AddressListHolder();
                convertView = holder.mHolderView;
            }else{
                holder = (BaseHolder) convertView.getTag();
            }
            AddressBean.AddressList addressList = mDatas.get(position);
            holder.setDataAndRefreshUI(addressList);
            return convertView;
        }
    }
}
