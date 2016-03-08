package com.onlyone.jdmall.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.view
 * 创建者: LiuKe
 * 创建时间:  2016/3/7 10:54
 * 描述: ${TODO}
 */
public class ProductDialog extends Dialog {


    @Bind(R.id.product_dialog_pic)
    ImageView mProductDialogPic;
    @Bind(R.id.product_dialog_price)
    TextView  mProductDialogPrice;
    @Bind(R.id.product_dialog_price_save)
    TextView  mProductDialogPriceSave;
    @Bind(R.id.product_dialog_dismiss)
    ImageView mProductDialogDismiss;
    @Bind(R.id.product_dialog_addcar)
    TextView  mProductDialogAddcar;
    @Bind(R.id.product_dialog_buy)
    TextView  mProductDialogBuy;
    @Bind(R.id.product_dialog_num_reduce)
    ImageView mProductDialogNumReduce;
    @Bind(R.id.product_dialog_num_edit)
    EditText  mProductDialogNumEdit;
    @Bind(R.id.product_dialog_num_add)
    ImageView mProductDialogNumAdd;

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
        ButterKnife.bind(this);
    }

    @OnClick({R.id.product_dialog_dismiss, R.id.product_dialog_addcar, R.id.product_dialog_buy, R.id.product_dialog_num_reduce, R.id.product_dialog_num_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_dialog_dismiss:
                dismiss();
                break;
            case R.id.product_dialog_addcar:
                break;
            case R.id.product_dialog_buy:
                break;
            case R.id.product_dialog_num_reduce:
                break;
            case R.id.product_dialog_num_add:
                break;
        }
    }

}
