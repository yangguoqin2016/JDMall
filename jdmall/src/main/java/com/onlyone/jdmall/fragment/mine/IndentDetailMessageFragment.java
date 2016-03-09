package com.onlyone.jdmall.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
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

    private TextView mBack;
    private long     mOrderId;
    private IndentDetailMessageBean mMessageBean;

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
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HolderFragment) getParentFragment()).goBack();
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
        View indentDetailView = View.inflate(ResUtil.getContext(), R.layout.inflate_indentdetail, null);
        ButterKnife.bind(this, indentDetailView);

        return indentDetailView;
    }

    @Override
    protected void refreshSuccessView(IndentMesListBean data) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
