package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.onlyone.jdmall.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        TextView tv = (TextView) findViewById(R.id.product_detail_tv);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        tv.setText(name);
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
    }
}
