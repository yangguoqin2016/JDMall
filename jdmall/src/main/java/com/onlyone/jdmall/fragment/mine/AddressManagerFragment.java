package com.onlyone.jdmall.fragment.mine;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.AddressBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.fragment.car.AddressModifyFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author Never
 * @Date 2016/3/7 14:45
 * @Desc ${地址管理页面}
 */
public class AddressManagerFragment extends BaseFragment<AddressBean> {

    private static final String ADDRESS_ADD_FRAGMENT       = "address_add_fragment";
    private static final String TAG_ADDRESSMODIFY_FRAGMENT = "tag_addressmodify_fragment";
    private View         mSucessView;
    private MainActivity mMainActivity;
    private View         mTopBar;
    private AddressBean  mAddressBean;
    /*给四个不同的界面设置标记*/
    private static final int ADDRESS_LIST_EMPTY   = 0;
    private static final int ADDRESS_LIST_ADD     = 1;
    private static final int ADDRESS_LIST         = 2;
    private static final int ADDRESS_LIST_MANAGER = 3;
    private static final int ADDRESS_LIST_MODIFY  = 4;
    private static       int mCurrentPager        = -1;
    private AddressAddFragment mAddressAddFragment;

    @Override
    protected View loadSuccessView() {
        mSucessView = View.inflate(ResUtil.getContext(), R.layout.mine_address_manager, null);

        return mSucessView;
    }

    @Override
    protected void loadData(final LoadListener<AddressBean> listener) {
        RequestQueue queue = NetUtil.getRequestQueue();

        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                AddressBean bean = gson.fromJson(s, AddressBean.class);
                listener.onSuccess(bean);

            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        };
        String url = Url.ADDRESS_CONTACT_ADDRESS;

        StringRequest request = new StringRequest(Request.Method.GET, url, success, error) {
            /*添加请求头*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                //                map.put("userid", "20428");
                SPUtil spUtil = new SPUtil(ResUtil.getContext());
                String userid = spUtil.getLong(SP.USERID, 0) + "";
                map.put("userid", userid);
                return map;
            }
        };
        queue.add(request);
    }

    @Override
    protected void handleError(Exception e) {

        Toast.makeText(ResUtil.getContext(), e.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void refreshSuccessView(AddressBean data) {
        /*判断用户有没有添加地址*/
        /*if(data.addressList.size()==0){
            showEmptyView();
            return;
        }*/
        mAddressBean = data;
        ListView lvAddresses = (ListView) mSucessView.findViewById(R.id.mine_address_lv_container);
        lvAddresses.setAdapter(new AddressAdapter());
        /*设置点击监听*/
        lvAddresses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressBean.AddressList addressList = mAddressBean.addressList.get(position);
                Log.d("AddressManagerFragment", "data==" + addressList.addressDetail);
                AddressModifyFragment addressModifyFragment = new AddressModifyFragment(addressList);
                changeFragment(addressModifyFragment, TAG_ADDRESSMODIFY_FRAGMENT);

            }
        });
    }

    /*private void showEmptyView() {

    }*/

    @Override
    public void onResume() {

        mMainActivity = (MainActivity) getActivity();
        mTopBar = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_address_manager, null);
        mMainActivity.setTopBarView(mTopBar);
        mMainActivity.setHideTopBar(false);

        /*设置点击监听*/
        topBarItemListen();

        super.onResume();
    }

    private void topBarItemListen() {
        TextView tvBack = (TextView) mTopBar.findViewById(R.id.topbar_addrmng_back);
        TextView tvAdd = (TextView) mTopBar.findViewById(R.id.topbar_addrmng_add);

        TopBarItemListener listener = new TopBarItemListener();
        tvBack.setOnClickListener(listener);
        tvAdd.setOnClickListener(listener);
    }

    class TopBarItemListener implements View.OnClickListener {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.topbar_addrmng_back:
                    //                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    /*transaction.add(R.id.fl_content_container, new MineFragment());
                    transaction.commit();*/
                    ((HolderFragment)getParentFragment()).goBack();
                    break;

                case R.id.topbar_addrmng_add:

                    mAddressAddFragment = new AddressAddFragment();
                    /*
                    FragmentManager manager = mMainActivity.getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.fl_content_container, addressAddFragment);
                    transaction.addToBackStack("aaa");

                    transaction.commit();

                    FrameLayout rootView = mLoadPager.getRootView();
                    rootView.removeAllViews();
                    //                    rootView.addView(addressAddFragment.loadSuccessView());
                    rootView.addView(addressAddFragment.loadSuccessView());*/
                    if (mAddressAddFragment == null) {
                        mAddressAddFragment = new AddressAddFragment();
                    }
                    changeFragment(mAddressAddFragment, ADDRESS_ADD_FRAGMENT);

                    break;
            }
        }
    }

    private void changeFragment(BaseFragment fragment, String tag) {
        /*FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_content_container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();*/
        HolderFragment parentFragment = (HolderFragment) getParentFragment();
        parentFragment.goForward(fragment);
    }


    /*地址管理适配器*/
    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mAddressBean != null || mAddressBean.addressList.size() != 0) {
                return mAddressBean.addressList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mAddressBean != null || mAddressBean.addressList.size() != 0) {
                return mAddressBean.addressList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ResUtil.getContext(), R.layout.item_address, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            /*赋值*/
            AddressBean.AddressList addressInfo = mAddressBean.addressList.get(position);
            holder.mItemAddressTvName.setText(addressInfo.name);
            holder.mItemAddressTvNum.setText(addressInfo.phoneNumber);
            holder.mItemAddressTvPosition.setText(addressInfo.addressDetail);

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_address_tv_name)
            TextView mItemAddressTvName;
            @Bind(R.id.item_address_tv_num)
            TextView mItemAddressTvNum;
            @Bind(R.id.item_address_tv_position)
            TextView mItemAddressTvPosition;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
