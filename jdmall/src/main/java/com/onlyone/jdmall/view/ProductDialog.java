package com.onlyone.jdmall.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.onlyone.jdmall.R;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.view
 * 创建者: LiuKe
 * 创建时间:  2016/3/7 10:54
 * 描述: ${TODO}
 */
public class ProductDialog extends Dialog {


    public ProductDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ProductDialog(Context context) {
        //设置窗口属性
        this(context, R.style.ProductDialogStyle);

        //拿到窗口属性
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        //设置窗口对齐方式
        attributes.gravity = Gravity.BOTTOM;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口属性
        getWindow().setAttributes(attributes);
    }

    /**
     * 显示弹出窗口的View
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_dialog_layout);
    }
}
