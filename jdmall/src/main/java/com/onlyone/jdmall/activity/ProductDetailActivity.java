package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {

    @Bind(R.id.product_detail_topbar_back)
    ImageView      mProductDetailTopbarBack;
    @Bind(R.id.product_detail_topbar_share)
    ImageView      mProductDetailTopbarShare;
    @Bind(R.id.product_detail_store)
    TextView       mProductDetailStore;
    @Bind(R.id.product_detail_addcar)
    TextView       mProductDetailAddcar;
    @Bind(R.id.product_detail_buy)
    TextView       mProductDetailBuy;
    @Bind(R.id.hot_product_pic_viewpager)
    ViewPager      mHotProductPicViewpager;
    @Bind(R.id.product_detail_name)
    TextView       mProductDetailName;
    @Bind(R.id.product_detail_price)
    TextView       mProductDetailPrice;
    @Bind(R.id.product_detail_marketprice)
    TextView       mProductDetailMarketprice;
    @Bind(R.id.product_detail_stars)
    RatingBar      mProductDetailStars;
    @Bind(R.id.product_detail_left_time)
    TextView       mProductDetailLeftTime;
    @Bind(R.id.product_detail_selected_color_size)
    TextView       mProductDetailSelectedColorSize;
    @Bind(R.id.product_detail_select_color_size)
    RelativeLayout mProductDetailSelectColorSize;

    private int mProductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initView();
        initData();
    }

    private void init() {
        //从商品条目跳转到详情界面,获取传入的商品id
        Intent intent = getIntent();
        mProductId = intent.getIntExtra("id", 1);
    }

    /**
     * 初始化View
     */
    private void initView() {
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 根据传递过来的商品id请求数据
     */
    private void initData() {

    }

    @OnClick({R.id.product_detail_topbar_back, R.id.product_detail_topbar_share, R.id.product_detail_store, R.id.product_detail_addcar, R.id.product_detail_buy, R.id.product_detail_select_color_size})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_detail_topbar_back:
                finish();
                break;
            case R.id.product_detail_topbar_share:
                Toast.makeText(this, "分享..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_detail_store:
                Toast.makeText(this, "收藏..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_detail_addcar:
                //添加购物车
                addToCar();
                break;
            case R.id.product_detail_buy:
                //立即购买商品
                buyNow();
                break;
            case R.id.product_detail_select_color_size:
                //选择商品颜色与尺寸
                selectColorAndSize();
                break;
        }
    }

    private void buyNow() {
        Toast.makeText(this, "立即购买,id="+mProductId, Toast.LENGTH_SHORT).show();
    }

    private void addToCar() {
        Toast.makeText(this, "加入购物车", Toast.LENGTH_SHORT).show();
    }

    private void selectColorAndSize() {
        Toast.makeText(this, "选择商品尺寸颜色", Toast.LENGTH_SHORT).show();
    }
}
