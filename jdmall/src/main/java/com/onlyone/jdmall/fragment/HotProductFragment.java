package com.onlyone.jdmall.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.HotProductBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 项目名: JDMall
 * 包名: com.onlyone.jdmall.fragment
 * 创建者: LiuKe
 * 创建时间: 2016/3/5 12:31
 * 描述:用于展示热门单品的Fragment
 */
public class HotProductFragment extends BaseFragment<List<HotProductBean.ProductBean>> {

    ListView mHotProductListView;

    @Override
    protected void refreshSuccessView(List<HotProductBean.ProductBean> data) {
        mHotProductListView.setAdapter(new HotProductAdapter(data));
    }

    @Override
    protected View loadSuccessView() {
        View hotProductView = View.inflate(ResUtil.getContext(), R.layout.hot_product, null);
        mHotProductListView = (ListView) hotProductView.findViewById(R.id.hot_product_list_view);
        return hotProductView;
    }

    @Override
    protected void loadData(final LoadListener<List<HotProductBean.ProductBean>> listener) {
        //网络请求热门单品数据
        //http://localhost:8080/market/hotproduct?page=1&pageNum=15&orderby=saleDown
        //上拉加载更多改变页数即可
        String url = Url.ADDRESS_SERVER+"/hotproduct?page=1&pageNum=15&orderby=saleDown";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                HotProductBean bean = gson.fromJson(s, HotProductBean.class);
                List<HotProductBean.ProductBean> datas = bean.productList;
                listener.onSuccess(datas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });

        Volley.newRequestQueue(ResUtil.getContext()).add(request);
    }

    @Override
    protected void handleError(Exception e) {
        Toast.makeText(ResUtil.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    private class HotProductAdapter extends BaseAdapter {
        List<HotProductBean.ProductBean> mDatas;
        public HotProductAdapter(List<HotProductBean.ProductBean> data) {
            mDatas = data;
        }

        @Override
        public int getCount() {
            if(mDatas != null){
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if(mDatas != null){
                mDatas.get(position);
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
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(ResUtil.getContext(),R.layout.hot_product_item,null);
                holder.ivPic = (ImageView) convertView.findViewById(R.id.hot_product_item_pic);
                holder.tvName = (TextView) convertView.findViewById(R.id.hot_product_item_name);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.hot_product_item_price);
                holder.tvMarketPrice = (TextView) convertView.findViewById(R.id.hot_product_item_market_price);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            HotProductBean.ProductBean hotProductBean = mDatas.get(position);
            holder.tvName.setText(hotProductBean.name);
            holder.tvMarketPrice.setText(hotProductBean.marketPrice+"");
            holder.tvPrice.setText(hotProductBean.price+"");

            String url = Url.ADDRESS_SERVER+hotProductBean.pic;
            Picasso.with(ResUtil.getContext()).load(url).into(holder.ivPic);

            return convertView;
        }

        class ViewHolder{
            ImageView ivPic;
            TextView tvName;
            TextView tvPrice;
            TextView tvMarketPrice;
        }

    }
}
