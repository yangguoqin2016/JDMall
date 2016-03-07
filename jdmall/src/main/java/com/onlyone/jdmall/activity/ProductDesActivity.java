package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.ProductDetailBean;

public class ProductDesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_des);
        TextView tv = (TextView) findViewById(R.id.tv_desc);
        Intent intent = getIntent();
        ProductDetailBean.ProductEntity bean = (ProductDetailBean.ProductEntity) intent.getSerializableExtra("bean");
        tv.setText(bean.toString());


    }
}
