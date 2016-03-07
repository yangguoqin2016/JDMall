package com.onlyone.jdmall.fragment.mine;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

    private View         mSucessView;
    private MainActivity mMainActivity;
    private View         mTopBar;
    private AddressBean  mAddressBean;

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
                String userid = spUtil.getLong(SP.USERID,0)+"";
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
        mAddressBean = data;
        ListView lvAddresses = (ListView) mSucessView.findViewById(R.id.mine_address_lv_container);
        lvAddresses.setAdapter(new AddressAdapter());
    }

    @Override
    public void onResume() {
        mMainActivity = (MainActivity) getActivity();
        mTopBar = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_address_manager, null);
        mMainActivity.setTopBarView(mTopBar);
        mMainActivity.setHideTopBar(false);
        super.onResume();
    }

    /*地址管理适配器*/
    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mAddressBean != null || mAddressBean.addressList.size() != 0) {
                Log.d("AddressManagerFragment", mAddressBean.toString() + "---"/* + mAddressBean.addressInfos.size()*/);
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
            }else{
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

    /*@Override
    protected String getUrl() {
        //http://localhost:8080/market/addresslist
        return Url.ADDRESS_CONTACT_ADDRESS;
    }

    @Override
    protected void handleError(Exception e) {

        Toast.makeText(ResUtil.getContext(), e.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected AddressBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        AddressBean addressBean = gson.fromJson(jsonStr, AddressBean.class);
        return addressBean;
    }

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
        String url = Url.ADDRESS_FAVORITE + "?" + "page=0&pageNum=10";

        StringRequest request = new StringRequest(Request.Method.GET, url, success, error) {
            /*添加请求头*//*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                //                map.put("userid", "20428");
                SPUtil spUtil = new SPUtil(ResUtil.getContext());
                String userid = spUtil.getLong(SP.USERID,0)+"";
                map.put("userid", userid);
                return map;
            }
        };
        queue.add(request);
    }

    @Override
    protected void refreshSuccessView(AddressBean data) {
        mAddressBean = data;
        ListView lvAddresses = (ListView) mSucessView.findViewById(R.id.mine_address_lv_container);
        lvAddresses.setAdapter(new AddressAdapter());
    }

    @Override
    public void onResume() {
        mMainActivity = (MainActivity) getActivity();
        mTopBar = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_address_manager, null);
        mMainActivity.setTopBarView(mTopBar);
        mMainActivity.setHideTopBar(false);
        super.onResume();
    }

    *//*地址管理适配器*//*
    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mAddressBean != null || mAddressBean.addressInfos.size() != 0) {
                Log.d("AddressManagerFragment",mAddressBean.toString()+"---"+mAddressBean.addressInfos.size());
                return mAddressBean.addressInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mAddressBean != null || mAddressBean.addressInfos.size() != 0) {
                return mAddressBean.addressInfos.get(position);
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
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            *//*赋值*//*
            AddressBean.AddressInfo addressInfo = mAddressBean.addressInfos.get(position);
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
    }*/
}
