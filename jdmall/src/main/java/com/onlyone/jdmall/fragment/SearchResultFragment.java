package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.application.MyApplication;
import com.onlyone.jdmall.pager.LoadListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 13:33
 * @描述: ${TODO}
 */
public class SearchResultFragment extends BaseFragment<Object> {
    @Bind(R.id.searchresult_sale_jiantou)
    ImageView mSearchresultSaleJiantou;
    @Bind(R.id.searchresult_sale)
    LinearLayout mSearchresultSale;
    @Bind(R.id.searchresult_price_jiantou)
    ImageView mSearchresultPriceJiantou;
    @Bind(R.id.searchresult_price)
    LinearLayout mSearchresultPrice;
    @Bind(R.id.searchresult_evaluate_jiantou)
    ImageView mSearchresultEvaluateJiantou;
    @Bind(R.id.searchresult_evaluate)
    LinearLayout mSearchresultEvaluate;
    @Bind(R.id.searchresult_date_jiantou)
    ImageView mSearchresultDateJiantou;
    @Bind(R.id.searchresult_date)
    LinearLayout mSearchresultDate;
    @Bind(R.id.searchresult_lv)
    ListView mSearchresultLv;

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View view = View.inflate(MyApplication.sGlobalContext, R.layout.inflate_searchresult, null);

        return view;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);


    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        mSearchresultLv.setAdapter(new MyAdapter());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class MyAdapter extends BaseAdapter {


        @Bind(R.id.item_searchresult_iv)
        ImageView mItemSearchresultIv;
        @Bind(R.id.item_searchresult_tv_title)
        TextView mItemSearchresultTvTitle;
        @Bind(R.id.item_searchresult_tv_price)
        TextView mItemSearchresultTvPrice;
        @Bind(R.id.item_searchresult_tv_oldprice)
        TextView mItemSearchresultTvOldprice;
        @Bind(R.id.item_searchresult_tv_valuation)
        TextView mItemSearchresultTvValuation;

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHoler vh = null;
            if (convertView == null) {

                convertView = View.inflate(MyApplication.sGlobalContext, R.layout.item_searchresult, null);
                vh = new ViewHoler();
                ButterKnife.bind(this, convertView);
                vh.demo = mItemSearchresultIv;
                vh.title = mItemSearchresultTvTitle;
                vh.price = mItemSearchresultTvPrice;
                vh.oldprice = mItemSearchresultTvOldprice;

                convertView.setTag(vh);
            } else {
                vh = (ViewHoler) convertView.getTag();


            }

            return convertView;
        }
    }

    class ViewHoler {

        TextView title, price, oldprice, valuation;
        ImageView demo;
    }
}
