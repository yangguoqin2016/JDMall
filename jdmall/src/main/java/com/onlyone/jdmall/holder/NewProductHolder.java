package com.onlyone.jdmall.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.HotProductBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.holder
 * 创建者: LiuKe
 * 创建时间:  2016/3/5 16:24
 * 描述: ${TODO}
 */
public class NewProductHolder extends BaseHolder<HotProductBean.ProductBean> {

    @Bind(R.id.hot_product_item_pic)
    ImageView mHotProductItemPic;
    @Bind(R.id.hot_product_item_name)
    TextView  mHotProductItemName;
    @Bind(R.id.hot_product_item_market_price)
    TextView  mHotProductItemMarketPrice;
    @Bind(R.id.hot_product_item_price)
    TextView  mHotProductItemPrice;
    private View mRootView;


    @Override
    public View initHolderView() {
        mRootView = View.inflate(ResUtil.getContext(), R.layout.new_product_item, null);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void setDataAndRefreshUI(final HotProductBean.ProductBean data) {

        mHotProductItemName.setText(data.name);
        mHotProductItemMarketPrice.setText(data.marketPrice + "");
        mHotProductItemPrice.setText(data.price + "");

        String url = Url.ADDRESS_SERVER + data.pic;
        Picasso.with(ResUtil.getContext()).load(url).into(mHotProductItemPic);


    }
}
