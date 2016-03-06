package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.onlyone.jdmall.bean.FavoriteBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author Never
 * @Date 2016/3/6 15:31
 * @Desc ${TODO}
 */
public class MineFavoriteFragment extends BaseFragment<FavoriteBean> {

//    @Bind(R.id.mine_favorite_lv_container)
    ListView mMineFavoriteLvContainer;
    private FavoriteBean mFavoriteBean;

    /*请求成功的回调*/
    @Override
    protected void refreshSuccessView(FavoriteBean data) {

        /*保存数据到本类*/
        mFavoriteBean = data;
        mMineFavoriteLvContainer.setAdapter(new FavoriteAdapter());

    }

    @Override
    protected View loadSuccessView() {
        View view = View.inflate(ResUtil.getContext(), R.layout.mine_favorite, null);
//        ButterKnife.bind(this, view);
        mMineFavoriteLvContainer = (ListView) view.findViewById(R.id.mine_favorite_lv_container);
        return view;
    }

    @Override
    protected void loadData(final LoadListener<FavoriteBean> listener) {
        RequestQueue queue = NetUtil.getRequestQueue();

        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                FavoriteBean bean = gson.fromJson(s, FavoriteBean.class);
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
            /*添加请求头*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("userid", "20428");
                return map;
            }
        };
        queue.add(request);
    }

    /*出现异常的toast*/
    @Override
    protected void handleError(Exception e) {
        Toast.makeText(ResUtil.getContext(), "unknown error has occured:" + e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class FavoriteAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mFavoriteBean != null) {
                return mFavoriteBean.productList.size();
            }
            return 0;
        }

        @Override
        public FavoriteBean.ProductInfo getItem(int position) {
            if (mFavoriteBean != null) {
                return mFavoriteBean.productList.get(position);
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
                convertView = View.inflate(ResUtil.getContext(), R.layout.item_favorite, null);
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            /*赋值*/
            FavoriteBean.ProductInfo productInfo = mFavoriteBean.productList.get(position);
            /*商品名*/
            holder.mItemFavoriteTvName.setText(productInfo.name);
            /*商品数量*/
            holder.mItemFavoriteTvCount.setText("1");// TODO: 2016/3/6
            /*单价*/
            holder.mItemFavoriteTvPrice.setText("单价: "+productInfo.price);
            /*小计*/
            holder.mItemFavoriteTvSum.setText("小计"+productInfo.price);
            /*图片url*/
            String url =Url.ADDRESS_SERVER+productInfo.pic;
            Picasso.with(ResUtil.getContext())
                    .load(url)
                    .error(R.mipmap.brand_1)
                    .into(holder.mItemFavoriteIv);
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_favorite_iv)
            ImageView mItemFavoriteIv;
            @Bind(R.id.item_favorite_tv_name)
            TextView  mItemFavoriteTvName;
            @Bind(R.id.item_favorite_tv_count)
            TextView  mItemFavoriteTvCount;
            @Bind(R.id.item_favorite_tv_price)
            TextView  mItemFavoriteTvPrice;
            @Bind(R.id.item_favorite_tv_sum)
            TextView  mItemFavoriteTvSum;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}

