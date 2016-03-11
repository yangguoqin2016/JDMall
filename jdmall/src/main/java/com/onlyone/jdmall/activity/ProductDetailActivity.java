package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
import com.onlyone.jdmall.bean.ProductDetailBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Serialize;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.onlyone.jdmall.utils.SerializeUtil;
import com.onlyone.jdmall.utils.UserLoginUtil;
import com.onlyone.jdmall.view.ProductDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ProductDetailActivity extends AppCompatActivity {

    @Bind(R.id.product_detail_topbar_back)
    ImageView mProductDetailTopbarBack;
    @Bind(R.id.product_detail_topbar_share)
    ImageView mProductDetailTopbarShare;
    @Bind(R.id.product_detail_store)
    TextView  mProductDetailStore;
    @Bind(R.id.product_detail_addcar)
    TextView  mProductDetailAddcar;
    @Bind(R.id.product_detail_buy)
    TextView  mProductDetailBuy;
    @Bind(R.id.product_detail_pic_viewpager)
    ViewPager mProductDetailPicViewpager;
    @Bind(R.id.product_detail_name)
    TextView  mProductDetailName;
    @Bind(R.id.product_detail_price)
    TextView  mProductDetailPrice;
    @Bind(R.id.product_detail_marketprice)
    TextView  mProductDetailMarketprice;
    @Bind(R.id.product_detail_stars)
    RatingBar mProductDetailStars;
    @Bind(R.id.product_detail_left_time)
    TextView  mProductDetailLeftTime;

    /*--------------------静态只能用findViewById找-----------------*/
    //  @Bind(R.id.product_detail_selected_color_size)
    public static TextView mProductDetailSelectedColorSize;
    //  @Bind(R.id.product_detail_please_select)
    public static TextView mProductDetailPleaseSelect;
    /*--------------------静态控件-----------------*/

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

    public static int mProductNum = 0;  //选中商品数量

    public static ProductDetailBean.ProductEntity.ProductPropertyBean[] mPropertyBeanArr = new ProductDetailBean.ProductEntity.ProductPropertyBean[2];
    private ProductDialog mProductDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPropertyBeanArr[0] = new ProductDetailBean.ProductEntity.ProductPropertyBean();
        mPropertyBeanArr[1] = new ProductDetailBean.ProductEntity.ProductPropertyBean();

        init();
        initView();
        initData();
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
/*--------------------fbc查找控件-----------------*/
        mProductDetailSelectedColorSize = (TextView) findViewById(R.id.product_detail_selected_color_size);
        mProductDetailPleaseSelect = (TextView) findViewById(R.id.product_detail_please_select);
/*--------------------fbc查找控件-----------------*/
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
                mProductDialog = new ProductDialog(ProductDetailActivity.this, mProductBean);
                //数据加载成功刷新UI
                refreshUI();

                //保存浏览的历史记录
                saveBrowseOrStoreHistory(mProductBean, Serialize.TAG_BROWSE);
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
     * 1.将商品浏览的记录保存并序列化
     * 2.商品收藏时将商品信息序列化
     *
     * @param productBean
     */
    private void saveBrowseOrStoreHistory(ProductDetailBean.ProductEntity productBean, String tag) {
        //1.只有登录状态才保存历史浏览记录
        if (!UserLoginUtil.isLogin()) {
            return;
        }
        //2.获取当前登录用户名
        String userName = UserLoginUtil.getLoginUser();

        //3.序列化取出保存的集合
        String keyTag = userName + "_" + tag;
        ArrayList<ProductDetailBean.ProductEntity> list = SerializeUtil.serializeObject(keyTag);
        if (list == null) {
            list = new ArrayList<>();
        } else {
            //重复商品不添加到历史记录
            for (ProductDetailBean.ProductEntity productEntity : list) {
                if (productBean.id == productEntity.id) {
                    return;
                }
            }
        }
        //4.集合序列化,tag为当前登录用户名
        list.add(productBean);
        SerializeUtil.deserializeObject(keyTag, list);

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
        mLimitBuyCountView.start(mProductBean.leftTime * 1000);

        //设置图片轮播图
        mPicUrls = mProductBean.pics;
        mProductDetailPicViewpager.setAdapter(new ProductPicAdapter());

        if (mPicUrls == null || mPicUrls.size() == 0) {
            mProductDetailViewpagerIndicator.setText("0/0");
        } else {
            mProductDetailViewpagerIndicator.setText(1 + "/" + mPicUrls.size());
        }

        //设置监听事件
        initListener();
    }

    /**
     * 设置监听事件
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

    /*###############当ProductDialog中的颜色与尺寸发生改变时就调用该方法##############*/
    /*###################################*/
    public static void showSelectedColorAndSize() {

        boolean isColorSelected = mPropertyBeanArr[0].isSelected;
        boolean isSizeSelected = mPropertyBeanArr[1].isSelected;

       // System.out.println("isColorSelected=" + isColorSelected);
       // System.out.println("isSizeSelected=" + isSizeSelected);

        String color = mPropertyBeanArr[0].v;
        String size = mPropertyBeanArr[1].v;
        if (isColorSelected && isSizeSelected) {
            mProductDetailPleaseSelect.setText("已选择");
            mProductDetailSelectedColorSize.setText(color + " " + size);
        }

        if (isColorSelected && !isSizeSelected) {
            mProductDetailPleaseSelect.setText("请选择");
            mProductDetailSelectedColorSize.setText("尺寸");
        }

        if (!isColorSelected && isSizeSelected) {
            mProductDetailPleaseSelect.setText("请选择");
            mProductDetailSelectedColorSize.setText("颜色");
        }

        if (!isColorSelected && !isSizeSelected) {
            mProductDetailPleaseSelect.setText("请选择");
            mProductDetailSelectedColorSize.setText("颜色　尺寸");
        }
    }

    /*#############################*/
    /*###################################*/

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
                showShare();
                break;
            case R.id.product_detail_store:
                if(!UserLoginUtil.isLogin()) {
                    Toast.makeText(this, "您还未登录~", Toast.LENGTH_SHORT).show();
                    switchToLoginActivity();
                }else{

                //    saveBrowseOrStoreHistory(mProductBean, Serialize.TAG_STORE);
                    storeProduct();
                }
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

    /**
     * 收藏商品
     */
    private void storeProduct() {
        if(mProductBean == null){
            return;
        }
        String url = Url.ADDRESS_SERVER+"/product/favorites?pId="+mProductBean.id;
        RequestQueue queue = NetUtil.getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println("s====="+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String result  = (String) jsonObject.get("response");
                    if("addfavorites".equals(result)) {
                        Toast.makeText(ProductDetailActivity.this, "收藏成功~", Toast.LENGTH_SHORT).show();
                    }else {
                        String error_code = (String) jsonObject.get("error_code");
                        if("1535".equals(error_code)) {
                            Toast.makeText(ProductDetailActivity.this, "当前商品已添加过收藏", Toast.LENGTH_SHORT).show();
                        }
                        if("1533".equals(error_code)){

                            Toast.makeText(ProductDetailActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ProductDetailActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();

                SPUtil spUtil = new SPUtil(ResUtil.getContext());
                String userid = spUtil.getLong(SP.USERID,0)+"";
                headers.put("userid", userid);
                return headers;
            }

        };

        queue.add(request);

    }

    //TODO:立即购买进入支付页面
    private void buyNow() {

        if(!UserLoginUtil.isLogin()) {
            //还未登录
            switchToLoginActivity();

        }else{

            Toast.makeText(ResUtil.getContext(), "立即进入结算中心购买", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取商品属性并添加购物车
     */
    private void addToCar() {

        boolean isColorSelected = mPropertyBeanArr[0].isSelected;
        boolean isSizeSelected = mPropertyBeanArr[1].isSelected;

        if (!isColorSelected && isSizeSelected) {
            Toast.makeText(this, "请选择颜色", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isColorSelected && !isSizeSelected) {
            Toast.makeText(this, "请选择尺寸", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isColorSelected && !isSizeSelected) {
            Toast.makeText(this, "请选择颜色和尺寸", Toast.LENGTH_SHORT).show();
            return;
        }

        CarModel carModel = CarModel.getInstance();
        //1.获取商品颜色,尺寸
        int[] productPros = {mPropertyBeanArr[0].id, mPropertyBeanArr[1].id};  //{颜色,尺寸}

        //2.获取登录用户名
        if (!UserLoginUtil.isLogin()) {
            //跳转登录页面
            switchToLoginActivity();

        } else {
            String userName = UserLoginUtil.getLoginUser();

            if(mProductNum == 0){
                mProductNum = 1;
            }
            for (int i = 0; i < mProductNum; i++) {

                carModel.addToCar(userName, mProductId, productPros);
            }
            Toast.makeText(this, "已加入购物车", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 弹出对话框选择颜色与尺寸
     */
    private void selectColorAndSize() {
        mProductDialog.show();
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
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 跳转到ExtraLoginActivity
     */
    public void switchToLoginActivity(){

       startActivity(new Intent(this,ExtraLoginActivity.class));

    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mProductBean.name);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }


}
