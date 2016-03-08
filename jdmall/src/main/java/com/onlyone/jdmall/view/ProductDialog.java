package com.onlyone.jdmall.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.ProductDetailBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.UserLoginUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    @Bind(R.id.product_dialog_buy_limit)
    TextView  mProductDialogBuyLimit;

    private ProductDetailBean.ProductEntity mPoductBean;

    public ProductDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ProductDialog(Context context) {
        super(context);
    }

    public ProductDialog(Context context, ProductDetailBean.ProductEntity productBean) {
        this(context, R.style.ProductDialogStyle);
        mPoductBean = productBean;

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

        initView();
    }

    private void initView() {
        //1.设置商品图标
        List<String> pics = mPoductBean.pics;
        if (pics.size() == 0) {
            mProductDialogPic.setImageResource(R.mipmap.guide);
        } else {
            String url = Url.ADDRESS_SERVER + pics.get(0);
            Picasso.with(ResUtil.getContext()).load(url).into(mProductDialogPic);
        }

        //2.设置商品价格
        mProductDialogPrice.setText("￥" + mPoductBean.price);
        float savedMoney = mPoductBean.marketPrice - mPoductBean.price;
        mProductDialogPriceSave.setText("节省" + savedMoney + "元");

        //3.设置限购数量
        mProductDialogBuyLimit.setText("(单品限购" + mPoductBean.buyLimit + "件)");

    }

    @OnClick({R.id.product_dialog_dismiss, R.id.product_dialog_addcar, R.id.product_dialog_buy, R.id.product_dialog_num_reduce, R.id.product_dialog_num_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_dialog_dismiss:
                dismiss();
                break;
            case R.id.product_dialog_addcar:
                addToCar();
                break;
            case R.id.product_dialog_buy:
                break;
            case R.id.product_dialog_num_reduce:
                reduceNum();
                break;
            case R.id.product_dialog_num_add:
                addNum();
                break;
        }
    }

    /**
     * 加入购物车
     */
    private void addToCar() {
        CarModel carModel = CarModel.getInstance();
        //1.获取商品颜色,尺寸,这里根据选择的来调整
        int[] productPros = {1, 2};  //{颜色,尺寸}

        //2.获取登录用户名
        if(!UserLoginUtil.isLogin()) {
            Toast.makeText(ResUtil.getContext(), "您还没有登录~", Toast.LENGTH_SHORT).show();

            //TODO:跳转登录页面,activity->fragment


        }else{
            String userName = UserLoginUtil.getLoginUser();
            carModel.addToCar(userName, mPoductBean.id, productPros);
            Toast.makeText(ResUtil.getContext(), "已加入购物车", Toast.LENGTH_SHORT).show();
        }

        dismiss();
    }

    /**
     * 添加商品数量
     */
    private void addNum() {
        String productNum = mProductDialogNumEdit.getText().toString();
        if (TextUtils.isEmpty(productNum)) {
            productNum = "1";
        }
        int num = Integer.parseInt(productNum);
        if (num >= mPoductBean.buyLimit) {
            //达到限购数
            Toast.makeText(ResUtil.getContext(), "单品您只能买" + mPoductBean.buyLimit + "件哟~", Toast.LENGTH_SHORT).show();
            num = mPoductBean.buyLimit;
        } else if (num <= 0) {
            num = 1;
        } else {
            num++;
        }
        mProductDialogNumEdit.setText(num + "");
    }

    /**
     * 减少商品数量
     */
    private void reduceNum() {
        String productNum = mProductDialogNumEdit.getText().toString();
        if (TextUtils.isEmpty(productNum)) {
            productNum = "1";
        }
        int num = Integer.parseInt(productNum);
        if (num > 10) {
            num = 10;
        } else if (num <= 1) {
            num = 1;
        } else {
            num--;
        }
        mProductDialogNumEdit.setText(num + "");
    }

}
