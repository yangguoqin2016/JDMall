package com.onlyone.jdmall.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.application.MyApplication;
import com.onlyone.jdmall.bean.SearchResultBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 13:33
 * @描述: ${TODO}
 */
public class SearchResultFragment extends SuperBaseFragment<SearchResultBean> {
    @Bind(R.id.searchresult_sale_jiantou)
    ImageView    mSearchresultSaleJiantou;
    @Bind(R.id.searchresult_sale)
    LinearLayout mSearchresultSale;
    @Bind(R.id.searchresult_price_jiantou)
    ImageView    mSearchresultPriceJiantou;
    @Bind(R.id.searchresult_price)
    LinearLayout mSearchresultPrice;
    @Bind(R.id.searchresult_evaluate_jiantou)
    ImageView    mSearchresultEvaluateJiantou;
    @Bind(R.id.searchresult_evaluate)
    LinearLayout mSearchresultEvaluate;
    @Bind(R.id.searchresult_date_jiantou)
    ImageView    mSearchresultDateJiantou;
    @Bind(R.id.searchresult_date)
    LinearLayout mSearchresultDate;
    @Bind(R.id.searchresult_lv)
    ListView     mSearchresultLv;
    private SearchResultBean mResultData;

    @Override
    protected void refreshSuccessView(SearchResultBean data) {

        mResultData = data;
        if (data == null ||data.productList.size() == 0){
            // TODO: 2016/3/5 返回空界面
            Toast.makeText(ResUtil.getContext(),"empty",Toast.LENGTH_SHORT).show();

        }else{
            //返回成功界面奶粉
            mSearchresultLv.setAdapter(new MyAdapter());
        }
    }

    @Override
    protected View loadSuccessView() {
        View view = View.inflate(MyApplication.sGlobalContext, R.layout.inflate_searchresult, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected String getUrl() {
        //TODO:
        String defaultParams = "&page=1&pageNum=10&orderby=priceDown";
        String key = "奶粉";
        Log.d("SearchResultFragment",Url.ADDRESS_SEARCH_BYKEY+"?keyword="+key+defaultParams);
//        return Url.ADDRESS_SEARCH_BYKEY+"?keyword="+key+defaultParams;
//        return "http://188.188.5.57:8080/market/search?keyword=%E5%A5%B6%E7%B2%89&page=1&pageNum=10";
        return "http://10.0.3.2:8080/market/search?keyword=%E5%A5%B6%E7%B2%89&page=1&pageNum=10";
//        return "http://localhost:8080/market/search?keyword=奶粉&page=1&pageNum=10&orderby=priceDown";
    }

    @Override
    protected void handleError(Exception e) {
        Toast.makeText(ResUtil.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected SearchResultBean parseJson(String jsonStr) {

        return null;
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
            if(mResultData != null){
                return mResultData.productList.size();
            }
            return 0;
        }

        @Override
        public SearchResultBean.ProductList getItem(int position) {
            if(mResultData.productList!=null){
                return mResultData.productList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHoler vh = null;
            if (convertView == null) {

                convertView = View.inflate(MyApplication.sGlobalContext, R.layout.item_searchresult, null);
                vh = new ViewHoler();
                ButterKnife.bind(this, convertView);
                /*vh.demo = mItemSearchresultIv;
                vh.title = mItemSearchresultTvTitle;
                vh.price = mItemSearchresultTvPrice;
                vh.oldprice = mItemSearchresultTvOldprice;*/

                convertView.setTag(vh);
            } else {
                vh = (ViewHoler) convertView.getTag();
            }
            /*给每个条目赋值*/
            SearchResultBean.ProductList productInfo = mResultData.productList.get(position);
//            vh.demo = productInfo.pic;
            vh.title.setText(productInfo.name);
            vh.price.setText(productInfo.price+"");
            vh.oldprice.setText(productInfo.marketPrice+"");
            String uri = Url.ADDRESS_HOME+"/"+productInfo.pic;
            Picasso.with(ResUtil.getContext()).load(uri).into(vh.demo);

            return convertView;
        }
    }

    class ViewHoler {

        TextView title, price, oldprice, valuation;
        ImageView demo;
    }
}
