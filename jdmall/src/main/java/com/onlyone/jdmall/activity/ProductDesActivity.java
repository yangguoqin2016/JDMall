package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.ProductDetailBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDesActivity extends AppCompatActivity {

    @Bind(R.id.tv_desc)
    TextView  mTvDesc;
    @Bind(R.id.iv_desc)
    ImageView mIvDesc;
    private ProductDetailBean.ProductEntity mProdutBean;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
    }

    private void init() {
        Intent intent = getIntent();
        mProdutBean = (ProductDetailBean.ProductEntity) intent.getSerializableExtra("bean");
    }

    private void initView() {
        setContentView(R.layout.activity_product_des);
        ButterKnife.bind(this);
        mTvDesc.setText(mProdutBean.name + "的图文详情");
        mTvDesc.setTextSize(20);

        //加载大图片
        loadBigImage();
    }

    private void loadBigImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.long_pic, options);

        int resWidth = getResources().getDisplayMetrics().widthPixels;
        int resHeight = getResources().getDisplayMetrics().heightPixels;
        int calcSampleSize = calculateInSampleSize(options,resWidth,resHeight);
        options.inSampleSize = calcSampleSize;

       // options.inSampleSize = 4;

        System.out.println("缩放比例="+options.inSampleSize);

        //请求分配内存
        options.inJustDecodeBounds = false;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.long_pic, options);
        mIvDesc.setImageBitmap(mBitmap);
    }

    private int calculateInSampleSize(BitmapFactory.Options options,int resWidth,int resHeight){

        int width = options.outWidth;
        int height = options.outHeight;

        int insampleSize = 1;

        if(height>resHeight || width>resWidth){
            //计算实际宽高与目标宽高的比
            int heightRatio = Math.round((float)height/(float)resHeight);
            int widthRatio = Math.round((float)width/(float)resWidth);

            insampleSize = heightRatio<widthRatio?heightRatio:widthRatio;
        }
        return  insampleSize;
    }
    @OnClick(R.id.back)
    public void onClick() {
        mBitmap.recycle();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mBitmap.recycle();
    }
}
