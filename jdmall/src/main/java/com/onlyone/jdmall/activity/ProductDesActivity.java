package com.onlyone.jdmall.activity;

import android.content.Intent;
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

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.tv_desc)
    TextView  mTvDesc;
    private ProductDetailBean.ProductEntity mProdutBean;

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
        mTvDesc.setText(mProdutBean.toString());
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
