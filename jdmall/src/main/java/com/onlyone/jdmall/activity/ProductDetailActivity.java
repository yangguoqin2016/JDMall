package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.onlyone.jdmall.bean.ProductDetailBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.onlyone.jdmall.utils.SerializeUtil;
import com.onlyone.jdmall.view.ProductDialog;
import com.onlyone.jdmall.view.RatioLayout;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

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
    @Bind(R.id.limit_buy_count_view)
    CountdownView  mLimitBuyCountView;

    private int                             mProductId;
    private ProductDetailBean.ProductEntity mProductBean;
    private List<String>                    mPicUrls;

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

                //保存浏览的历史记录
                saveBrowseHistory(mProductBean);
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
     * 将商品浏览的记录保存并序列化
     *
     * @param productBean
     */
    private void saveBrowseHistory(ProductDetailBean.ProductEntity productBean) {
        //1.只有登录状态才保存历史浏览记录
        if (!isLogin()) {
            return;
        }
        //2.获取当前登录用户名
        String userName = getLoginUser();

        //3.序列化取出保存的集合
        HashSet<ProductDetailBean.ProductEntity> set = SerializeUtil.serializeObject(userName);
        if (set == null) {
            set = new HashSet<>();
        } else {
            //重复商品不添加到历史记录
            for (ProductDetailBean.ProductEntity productEntity : set) {
                if (productBean.id == productEntity.id) {
                    return;
                }
            }
        }
        //4.集合序列化,tag为当前登录用户名
        set.add(productBean);
        SerializeUtil.deserializeObject(userName, set);

    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        //轮播图滑动监听
        mProductDetailPicViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = position + 1;
                int total = mProductDetailPicViewpager.getAdapter().getCount();
                mProductDetailViewpagerIndicator.setText(index + "/" + total);
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

        //剩余抢购时间倒计时
        mLimitBuyCountView.start(mProductBean.leftTime*1000);

        //设置图片轮播图
        mPicUrls = mProductBean.pics;
        mProductDetailPicViewpager.setAdapter(new ProductPicAdapter());

        if (mPicUrls == null || mPicUrls.size() == 0) {
            mProductDetailViewpagerIndicator.setText("0/0");
        } else {
            mProductDetailViewpagerIndicator.setText(1 + "/" + mPicUrls.size());
        }
    }


    /**
     * 监听控件点击事件
     *
     * @param view
     */
    @OnClick({R.id.product_detail_topbar_back, R.id.product_detail_topbar_share, R.id.product_detail_store, R.id.product_detail_addcar, R.id.product_detail_buy, R.id.product_detail_select_color_size, R.id.product_detail_enter_des})
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
            case R.id.product_detail_enter_des:
                //进入详细描述界面
                Intent intent = new Intent(this, ProductDesActivity.class);
                intent.putExtra("bean", mProductBean);
                startActivity(intent);
                break;
        }
    }

    //TODO:立即购买进入支付页面
    private void buyNow() {

        Toast.makeText(this, "立即购买,id=" + mProductId, Toast.LENGTH_SHORT).show();
    }

    //TODO:获取商品属性并添加购物车
    private void addToCar() {
        CarModel carModel = CarModel.getInstance();
        //1.获取商品颜色,尺寸
        int[] productPros = {1, 2};  //{颜色,尺寸}

        //2.获取登录用户名
        String userName;
        if (isLogin()) {
            userName = getLoginUser();
        } else {
            Toast.makeText(this, "尚未登录...", Toast.LENGTH_SHORT).show();
            return;
        }

        carModel.addToCar(userName, mProductId, productPros);
        Toast.makeText(this, "已加入购物车", Toast.LENGTH_SHORT).show();

    }

    //TODO:选择商品颜色,尺寸
    private void selectColorAndSize() {
        //弹出对话框口选择产品
        ProductDialog dialog = new ProductDialog(this);
        dialog.show();
    }

    /**
     * 判断当前是否有用户登录
     *
     * @return
     */
    private boolean isLogin() {
        SPUtil spUtil = new SPUtil(this);
        String userName = spUtil.getString(SP.USERNAME, "");
        return !TextUtils.isEmpty(userName);
    }

    /**
     * 获取登录用户名
     *
     * @return
     */
    private String getLoginUser() {
        if (isLogin()) {
            SPUtil spUtil = new SPUtil(this);
            return spUtil.getString(SP.USERNAME, "");
        } else {
            return null;
        }
    }

    private class ProductPicAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mPicUrls == null || mPicUrls.size() == 0) {
                return 1;   //显示默认图片
            } else {

                return mPicUrls.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView iv = new ImageView(ResUtil.getContext());

            if (mPicUrls == null || mPicUrls.size() == 0) {
                iv.setImageResource(R.mipmap.guide);
                container.addView(iv);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                return iv;
            }
            //http://localhost:8080/market/images/product/detail/c3.jpg
            String url = mPicUrls.get(position);
            url = Url.ADDRESS_SERVER + "/" + url;
            Picasso.with(ResUtil.getContext()).load(url).error(R.mipmap.guide).into(iv);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            //解决图片压缩失真问题
            RatioLayout rl = new RatioLayout(ResUtil.getContext());
            rl.setCurState(RatioLayout.RELATIVE_WIDTH);
            float ratio = 224 / 340f;
            rl.setRatio(ratio);
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            rl.addView(iv, params);

            container.addView(rl);

            return rl;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
