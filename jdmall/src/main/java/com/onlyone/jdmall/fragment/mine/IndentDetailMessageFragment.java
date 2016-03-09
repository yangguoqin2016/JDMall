package com.onlyone.jdmall.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.IndentDetailMessageBean;
import com.onlyone.jdmall.bean.IndentMesListBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.fragment.SuperBaseFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.mine
 * @创建者: Administrator
 * @创建时间: 2016/3/9 10:57
 * @描述: ${TODO}
 */
public class IndentDetailMessageFragment extends SuperBaseFragment<IndentMesListBean> {

    private static final String TAG = "IndentDetailMessageFragment";
    @Bind(R.id.indentdetail_indentnum)
    TextView mIndentdetailIndentnum;
    @Bind(R.id.indentdetail_state)
    TextView mIndentdetailState;
    @Bind(R.id.indentdetail_name)
    TextView mIndentdetailName;
    @Bind(R.id.indentdetail_address)
    TextView mIndentdetailAddress;
    @Bind(R.id.indentdetail_listview)
    ListView mIndentdetailListview;
    @Bind(R.id.indentdetail_payment)
    TextView mIndentdetailPayment;
    @Bind(R.id.indentdetail_delivery)
    TextView mIndentdetailDelivery;
    @Bind(R.id.indentdetail_invoiceInfo)
    TextView mIndentdetailInvoiceInfo;
    @Bind(R.id.indentdetail_totalPrice)
    TextView mIndentdetailTotalPrice;
    @Bind(R.id.indentdetail_totalPoint)
    TextView mIndentdetailTotalPoint;
    @Bind(R.id.indentdetail_freight)
    TextView mIndentdetailFreight;
    @Bind(R.id.indentdetail_acturePay)
    TextView mIndentdetailActurePay;
    @Bind(R.id.indentdetail_indentTime)
    TextView mIndentdetailIndentTime;
    @Bind(R.id.indentdetail_cancleIndent)
    Button   mIndentdetailCancleIndent;

    private TextView                                      mBack;
    private long                                          mOrderId;
    private IndentDetailMessageBean                       mMessageBean;
    private List<IndentDetailMessageBean.ProductListBean> mProductList;
    private float mTotlePrice;
    public IndentDetailMessageFragment(long orderId) {
        this.mOrderId = orderId;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View topBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_indentdetail, null);
        mBack = (TextView) topBarView.findViewById(R.id.indentdetail_back);
        MainActivity activity = (MainActivity) getActivity();
        activity.setTopBarView(topBarView);
        activity.setHideTopBar(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadData(LoadListener<IndentMesListBean> listener) {
        listener.onSuccess(null);
        netLoadData();
//        Log.d(TAG, "2222");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HolderFragment) getParentFragment()).goBack();
            }
        });

        mIndentdetailCancleIndent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Url.ADDRESS_SERVER+"/ordercancel?orderId="+mOrderId;
                RequestQueue queue = Volley.newRequestQueue(ResUtil.getContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(ResUtil.getContext(),"已成功取消订单",Toast.LENGTH_SHORT).show();
                        ((HolderFragment)getParentFragment()).goBack();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        SPUtil sp = new SPUtil(ResUtil.getContext());
                        long userid = sp.getLong(SP.USERID, 0);
                        map.put("userid", "" + userid);
                        return map;
                    }
                };

                queue.add(request);
            }
        });
    }

    private void netLoadData() {
        String url = getUrl();
        RequestQueue queue = Volley.newRequestQueue(ResUtil.getContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String strJson) {
                //                Log.d(TAG, strJson);
                Toast.makeText(ResUtil.getContext(), "请求成功", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                mMessageBean = gson.fromJson(strJson, IndentDetailMessageBean.class);
                //                Log.d(TAG, messageBean.response);

                showData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                SPUtil sp = new SPUtil(ResUtil.getContext());
                long userid = sp.getLong(SP.USERID, 0);
                map.put("userid", "" + userid);
                return map;
            }
        };

        queue.add(request);
    }

    @Override
    protected String getUrl() {
        String url = Url.ADDRESS_SERVER + "/orderdetail?orderId=" + mOrderId;
        return url;
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    protected IndentMesListBean parseJson(String jsonStr) {
        return null;
    }

    @Override
    protected View loadSuccessView() {
        View indentDetailView = View.inflate(ResUtil.getContext(), R.layout.inflate_orderdetail, null);
        ButterKnife.bind(this, indentDetailView);

        return indentDetailView;
    }

    @Override
    protected void refreshSuccessView(IndentMesListBean data) {
//        Log.d(TAG, "1111");

    }

    private void showData() {
        if (mMessageBean != null) {
            Log.d(TAG, mMessageBean.response);
            //地址信息
            IndentDetailMessageBean.AddressInfoBean addressInfo = mMessageBean.addressInfo;
            // 商品数量及总价
            IndentDetailMessageBean.CheckoutAddupBean checkoutAddup = mMessageBean.checkoutAddup;
            //配送的时间
            IndentDetailMessageBean.DeliveryInfoBean deliveryInfo = mMessageBean.deliveryInfo;
            //发票信息
            IndentDetailMessageBean.InvoiceInfoBean invoiceInfo = mMessageBean.invoiceInfo;
            //状态及处理,orderid
            IndentDetailMessageBean.OrderInfoBean orderInfo = mMessageBean.orderInfo;
            //付款方式
            IndentDetailMessageBean.PaymentInfoBean paymentInfo = mMessageBean.paymentInfo;
            //商品详情
            mProductList = mMessageBean.productList;
            if(orderInfo==null){
                return;
            }
            mIndentdetailIndentnum.setText("4001018" + orderInfo.orderId);//订单号
            mIndentdetailState.setText(orderInfo.status);//处理的状态
            String date = new SimpleDateFormat().format(new Date(orderInfo.time));//订单提交的日期
            mIndentdetailIndentTime.setText(date);

            mIndentdetailName.setText(addressInfo.name);
            mIndentdetailAddress.setText(addressInfo.addressArea + addressInfo.addressDetail);
            int type = paymentInfo.type;
            if (type == 1) {
                mIndentdetailPayment.setText("货到付款");
            } else if (type == 2) {
                mIndentdetailPayment.setText("货到POS机");
            } else if (type == 3) {
                mIndentdetailPayment.setText("支付宝付款");
            } else {
                mIndentdetailPayment.setText("其它类型支付");
            }

            int payType = deliveryInfo.type;
            if (payType == 1) {
                mIndentdetailDelivery.setText("周一至周五送货");
            } else if (payType == 2) {
                mIndentdetailDelivery.setText("双休日及公众假期送货");
            } else if (payType == 3) {
                mIndentdetailDelivery.setText("时间不限，工作日双休日及公众假期均可送货");
            }
            //发票信息
            mIndentdetailInvoiceInfo.setText(invoiceInfo.invoiceTitle + "," + invoiceInfo.invoiceContent);

            //商品总额
            mIndentdetailTotalPrice.setText(checkoutAddup.totalPrice + "");
            mIndentdetailTotalPoint.setText(checkoutAddup.totalPoint + "");
            mIndentdetailFreight.setText(checkoutAddup.freight + "");


            mIndentdetailListview.setAdapter(new IndentDetailAdapter());
            float totlePrice = checkoutAddup.freight + checkoutAddup.totalPrice;
            mIndentdetailActurePay.setText(""+totlePrice);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class IndentDetailAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mProductList != null) {
                Log.d(TAG,""+mProductList.size());
                return mProductList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ResUtil.getContext(), R.layout.item_indentdetail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            IndentDetailMessageBean.ProductListBean productListBean = mProductList.get(position);
            IndentDetailMessageBean.ProductListBean.ProductBean product = productListBean.product;
            String picUrl =Url.ADDRESS_SERVER+product.pic;

            if(!TextUtils.isEmpty(picUrl)){
                Picasso.with(ResUtil.getContext()).load(picUrl).into(holder.mItemIndentdetailPic);
            }
            List<IndentDetailMessageBean.ProductListBean.ProductBean.ProductPropertyBean> productProperty = product.productProperty;
            if(productProperty.size()==2){
                holder.mItemIndentdetailProductColor.setText(productProperty.get(0).v);
                holder.mItemIndentdetailProductSize.setText(product.productProperty.get(1).v);
            }else if(productProperty.size()==1){
                holder.mItemIndentdetailProductColor.setText(productProperty.get(0).v);
            }
            int count = productListBean.prodNum;
            holder.mItemIndentdetailTotalCount.setText(""+count);
            holder.mItemIndentdetailPrice.setText(""+product.price);
            holder.mItemIndentdetailName.setText(product.name);

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_indentdetail_pic)
            ImageView mItemIndentdetailPic;
            @Bind(R.id.item_indentdetail_name)
            TextView  mItemIndentdetailName;
            @Bind(R.id.item_indentdetail_productColor)
            TextView  mItemIndentdetailProductColor;
            @Bind(R.id.item_indentdetail_productSize)
            TextView  mItemIndentdetailProductSize;
            @Bind(R.id.item_indentdetail_totalCount)
            TextView  mItemIndentdetailTotalCount;
            @Bind(R.id.item_indentdetail_price)
            TextView  mItemIndentdetailPrice;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
