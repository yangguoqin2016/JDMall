package com.onlyone.jdmall.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.activity.ProductDetailActivity;
import com.onlyone.jdmall.bean.ProductDetailBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.UserLoginUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    @Bind(R.id.product_dialog_color_gridview)
    GridView  mProductDialogColorGridview;
    @Bind(R.id.product_dialog_size_gridview)
    GridView  mProductDialogSizeGridview;

    public static TextView mSelectColorSize;   //请选择
    public static TextView mSelectColor;       //颜色
    public static TextView mSelectSize;        //尺寸

    private ProductDetailBean.ProductEntity mPoductBean;

    private List<ProductDetailBean.ProductEntity.ProductPropertyBean> mColorPropertyBeans;
    private List<ProductDetailBean.ProductEntity.ProductPropertyBean> mSizePropertyBeans;

    public static ProductDetailBean.ProductEntity.ProductPropertyBean mColorPropertyBean;
    public static ProductDetailBean.ProductEntity.ProductPropertyBean mSizePropertyBean;


    private PropertyAdapter mColorAdapter;
    private PropertyAdapter mSizeAdapter;

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
        //宽度用屏幕宽度
        attributes.width = ResUtil.getContext().getResources().getDisplayMetrics().widthPixels;
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

        mSelectColorSize = (TextView) findViewById(R.id.product_dialog_please_select_color_size);
        mSelectColor = (TextView) findViewById(R.id.product_dialog_select_color);
        mSelectSize = (TextView) findViewById(R.id.product_dialog_select_size);

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

        //4.颜色尺寸数据设置
        initGridView();

    }

    /**
     * 颜色,尺寸初始化
     */
    private void initGridView() {

        List<ProductDetailBean.ProductEntity.ProductPropertyBean> propertyBeans = mPoductBean.productProperty;


        mColorPropertyBeans = new ArrayList<>();
        mSizePropertyBeans = new ArrayList<>();
        mColorPropertyBean = new ProductDetailBean.ProductEntity.ProductPropertyBean();
        mSizePropertyBean = new ProductDetailBean.ProductEntity.ProductPropertyBean();

        for (ProductDetailBean.ProductEntity.ProductPropertyBean propertyBean : propertyBeans) {
            if ("颜色".equals(propertyBean.k)) {
                mColorPropertyBeans.add(propertyBean);
            } else {
                mSizePropertyBeans.add(propertyBean);
            }
        }


        mColorAdapter = new PropertyAdapter(mColorPropertyBeans);
        mSizeAdapter = new PropertyAdapter(mSizePropertyBeans);

        mProductDialogColorGridview.setAdapter(mColorAdapter);
        mProductDialogSizeGridview.setAdapter(mSizeAdapter);

        mProductDialogColorGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mProductDialogSizeGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //设置两个GriView的监听事件
        setGridItemClickListener();
    }


    @OnClick({R.id.product_dialog_dismiss, R.id.product_dialog_addcar, R.id.product_dialog_buy, R.id.product_dialog_num_reduce, R.id.product_dialog_num_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_dialog_dismiss:
                dismiss();
                //Depricated:  mDismissListener.onDismiss();
                break;
            case R.id.product_dialog_addcar:
                addToCar();
                break;
            case R.id.product_dialog_buy:
                buyNow();
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
     * 立即购买
     */
    private void buyNow() {

    }

    /**
     * 加入购物车
     */
    private void addToCar() {
        //如果有颜色或尺寸没有选择不能加入购物车
        boolean isColorSelected = ProductDialog.mColorPropertyBean.isSelected;
        boolean isSizeSlected = ProductDialog.mSizePropertyBean.isSelected;

        if (!isColorSelected && isSizeSlected) {
            Toast.makeText(ResUtil.getContext(), "请选择颜色", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isColorSelected && !isSizeSlected) {
            Toast.makeText(ResUtil.getContext(), "请选择尺寸", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isColorSelected && !isSizeSlected) {
            Toast.makeText(ResUtil.getContext(), "请选择颜色和尺寸", Toast.LENGTH_SHORT).show();
            return;
        }

        CarModel carModel = CarModel.getInstance();
        //1.获取商品颜色,尺寸,这里根据选择的来调整
        int[] productPros = {mColorPropertyBean.id, mSizePropertyBean.id};  //{颜色id,尺寸id}

        //2.获取登录用户名
        if (!UserLoginUtil.isLogin()) {
            Toast.makeText(ResUtil.getContext(), "您还没有登录~", Toast.LENGTH_SHORT).show();

            //TODO:跳转登录页面,activity->fragment
            //先跳转到MainActivity
            Intent intent = new Intent(ResUtil.getContext(), MainActivity.class);
            intent.putExtra("productDialog",1);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       //     ResUtil.getContext().startActivity(intent);

//            HolderFragment holderFragment = new HolderFragment() {
//                @Override
//                protected Fragment getChildFragment() {
//                    return FragmentFactory.getFragment(4);
//                }
//            };
//            LoginFragment loginFragment = new LoginFragment();
//            holderFragment.goForward(loginFragment);



        } else {
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

    /**
     * 点击选择商品颜色 尺寸
     */
    private void setGridItemClickListener() {
        //颜色adapter条目点击
        mColorAdapter.setOnItemClickListener(new PropertyAdapter.OnItemClickListener() {
            @Override
            public void itemClick(List<ProductDetailBean.ProductEntity.ProductPropertyBean> datas, int position) {

                for (int i = 0; i < datas.size(); i++) {

                    if (datas.get(i).isSelected && i == position) {
                        datas.get(i).isSelected = false;    //选中后再次点击不选中
                    } else {

                        datas.get(i).isSelected = (i == position);
                    }


                    mColorPropertyBean = datas.get(position);
                    mColorAdapter.notifyDataSetChanged();
                }

                //点击后将选择颜色放入数组中
                ProductDetailActivity.mPropertyBeanArr[0] = mColorPropertyBean;
                //更新ProductDeatilActivity中颜色尺寸选择显示
                ProductDetailActivity.showSelectedColorAndSize();


            }
        });

        //尺寸adapter条目点击
        mSizeAdapter.setOnItemClickListener(new PropertyAdapter.OnItemClickListener() {
            @Override
            public void itemClick(List<ProductDetailBean.ProductEntity.ProductPropertyBean> datas, int position) {
                for (int i = 0; i < datas.size(); i++) {

                    if (datas.get(i).isSelected && i == position) {
                        datas.get(i).isSelected = false;
                    } else {

                        datas.get(i).isSelected = (i == position);
                    }

                    mSizePropertyBean = datas.get(position);
                    mSizeAdapter.notifyDataSetChanged();
                }

                //点击后将选择尺寸放入数组中
                ProductDetailActivity.mPropertyBeanArr[1] = mSizePropertyBean;
                //更新ProductDeatilActivity中颜色尺寸选择显示
                ProductDetailActivity.showSelectedColorAndSize();

            }
        });
    }

    //    /**
    //     * 接口回调监听窗口关闭
    //     *
    //     * @param listener
    //     */
    //    @Override
    //    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
    //        super.setOnDismissListener(listener);
    //        mDismissListener.onDismiss();
    //        System.out.println("窗口关闭了.......");
    //
    //    }
    //
    //    private OnDismissListener mDismissListener;
    //
    //    public interface OnDismissListener {
    //        void onDismiss();
    //    }
    //
    //    public void setOnDialogDismissListener(OnDismissListener listener) {
    //        mDismissListener = listener;
    //    }


}

/**
 * 颜色与尺寸Adapter接收外界数据注入与对外提供回调接口
 */
class PropertyAdapter extends BaseAdapter {

    List<ProductDetailBean.ProductEntity.ProductPropertyBean> mDatas;

    /*--------------------接口回调-----------------*/
    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }
    /*--------------------接口回调-----------------*/

    public PropertyAdapter(List<ProductDetailBean.ProductEntity.ProductPropertyBean> propertyBeans) {
        mDatas = propertyBeans;
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        if (mDatas == null) {

            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(ResUtil.getContext(), R.layout.item_color_size_category, null);
            holder.tvProperty = (TextView) convertView.findViewById(R.id.color_size_item);
            holder.llContainer = (LinearLayout) convertView.findViewById(R.id.color_size_container);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProductDetailBean.ProductEntity.ProductPropertyBean bean = mDatas.get(position);

        if (bean.isSelected) {
            //当前item选中
            holder.llContainer.setBackgroundResource(R.drawable.product_prop_selected_shape);
            holder.tvProperty.setTextColor(Color.WHITE);
        } else {
            //未选中
            holder.llContainer.setBackgroundResource(R.drawable.product_prop_normal_shape);
            holder.tvProperty.setTextColor(Color.BLACK);
        }

        holder.tvProperty.setText(bean.v);


        //TextView容器设置点击监听
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(mDatas, position);
            }
        });

        //已选择 颜色 尺寸 ,根据选择动态改变
        //如果是多次点击,已经选中的条目再次点击,将未选中状态,但这是后color或size还是有值的
        //所有要根据是否选中来作为判断条件

        boolean isColorSelected = ProductDialog.mColorPropertyBean.isSelected;
        boolean isSizeSlected = ProductDialog.mSizePropertyBean.isSelected;

        String color = ProductDialog.mColorPropertyBean.v;
        String size = ProductDialog.mSizePropertyBean.v;

        if (isColorSelected && isSizeSlected) {
            ProductDialog.mSelectColorSize.setText("已选择");
            ProductDialog.mSelectColor.setText(color);
            ProductDialog.mSelectSize.setText(size);
        }

        if (isColorSelected && !isSizeSlected) {
            ProductDialog.mSelectColorSize.setText("请选择");
            ProductDialog.mSelectColor.setText("");
            ProductDialog.mSelectSize.setText("尺寸");
        }

        if (!isColorSelected && isSizeSlected) {
            ProductDialog.mSelectColorSize.setText("请选择");
            ProductDialog.mSelectColor.setText("颜色");
            ProductDialog.mSelectSize.setText("");
        }

        if (!isColorSelected && !isSizeSlected) {
            ProductDialog.mSelectColorSize.setText("请选择");
            ProductDialog.mSelectColor.setText("颜色");
            ProductDialog.mSelectSize.setText("尺寸");
        }

        return convertView;
    }

    private class ViewHolder {
        TextView     tvProperty;
        LinearLayout llContainer;
    }

    public interface OnItemClickListener {
        void itemClick(List<ProductDetailBean.ProductEntity.ProductPropertyBean> datas, int position);
    }
}
