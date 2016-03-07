package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.adapter.BasePagerAdapter;
import com.onlyone.jdmall.bean.ProductDetailBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.onlyone.jdmall.view.ProductDialog;
import com.onlyone.jdmall.view.RatioLayout;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

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
    @Bind(R.id.product_detail_pic_viewpager)
    ViewPager      mProductDetailPicViewpager;
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
    @Bind(R.id.product_detail_buy_limit)
    TextView       mProductDetailBuyLimit;
    @Bind(R.id.product_detail_viewpager_indicator)
    TextView       mProductDetailViewpagerIndicator;

    private int                             mProductId;
    private ProductDetailBean.ProductEntity mProductBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initView();
        initData();
        initListener();
    }

    private void init() {
        Intent intent = getIntent();
         mProductId = intent.getIntExtra("id", 1);
//        Random random = new Random();
//        mProductId = random.nextInt(30)+1;   //假数据
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
        //http://localhost:8080/market/product?pId=1
        String url = Url.ADDRESS_SERVER + "/product?pId=" + mProductId;
        RequestQueue queue = NetUtil.getRequestQueue();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                ProductDetailBean productDetailBean = gson.fromJson(s, ProductDetailBean.class);
                mProductBean = productDetailBean.product;

                //数据加载成功刷新UI
                refreshUI();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProductDetailActivity.this, "数据加载错误", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    /**
     * 初始化监听事件
     */
    private void initListener(){
        //轮播图滑动监听
        mProductDetailPicViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = position+1;
                int total = mProductDetailPicViewpager.getAdapter().getCount();
                mProductDetailViewpagerIndicator.setText(index+"/"+total);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 记载数据后刷新视图
     */
    private void refreshUI() {
        //商品姓名
        mProductDetailName.setText(mProductBean.name);
        //商品会员价格
        mProductDetailPrice.setText("￥" + mProductBean.price);
        //商品市场价格
        mProductDetailMarketprice.setText("￥" + mProductBean.marketPrice);
        //商品评分
        mProductDetailStars.setRating(mProductBean.score);
        //商品单件限购量
        mProductDetailBuyLimit.setText(mProductBean.buyLimit + "件");

        //1.剩余抢购时间倒计时
        refreshLeftTime();

        //2.设置图片轮播图
        List<String> picUrls = mProductBean.pics;
        mProductDetailPicViewpager.setAdapter(new ProductPicAdapter(picUrls));
        mProductDetailViewpagerIndicator.setText(1+"/"+picUrls.size());
    }


    /**
     * //TODO:剩余抢购时间倒计时处理
     */
    private void refreshLeftTime() {
        mProductDetailLeftTime.setText("剩余抢购时间" + mProductBean.leftTime + "秒");
    }


    /**
     * 监听控件点击事件
     *
     * @param view
     */
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
        Toast.makeText(this, "立即购买,id=" + mProductId, Toast.LENGTH_SHORT).show();
    }

    private void addToCar() {
        CarModel carModel = CarModel.getInstance();
        Random random = new Random();
        //1.获取商品颜色,尺寸
        int[] productPros = {1,2};  //{颜色,尺寸}

        //2.获取登录用户名
        SPUtil spUtil = new SPUtil(this);
        String userName = spUtil.getString(SP.USERNAME, "");
        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "尚未登录...", Toast.LENGTH_SHORT).show();
            return;
        }

        carModel.addToCar(userName,mProductId,productPros);
        Toast.makeText(this, "已加入购物车", Toast.LENGTH_SHORT).show();

    }

    private void selectColorAndSize() {
        //弹出对话框口选择产品
        ProductDialog dialog = new ProductDialog(this);
        dialog.show();
    }


    private class ProductPicAdapter extends BasePagerAdapter<String> {
        List<String> urls;

        public ProductPicAdapter(List<String> data) {
            super(data);
            urls = data;
        }

        @Override
        public View initView(int position) {
            //http://localhost:8080/market/images/product/detail/c3.jpg
            String url = urls.get(position);
            url = Url.ADDRESS_SERVER + "/" + url;

            ImageView iv = new ImageView(ResUtil.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(ResUtil.getContext()).load(url).into(iv);

            //解决图片压缩失真问题
            RatioLayout rl = new RatioLayout(ResUtil.getContext());
            rl.setCurState(RatioLayout.RELATIVE_WIDTH);
            float ratio = 224/340f;
            rl.setRatio(ratio);
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
            rl.addView(iv, params);

            return rl;
        }
    }

}
