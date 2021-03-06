package com.onlyone.jdmall.fragment.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import com.onlyone.jdmall.adapter.SuperBaseAdapter;
import com.onlyone.jdmall.bean.IndentMesListBean;
import com.onlyone.jdmall.bean.MyIndentBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.fragment.SuperBaseFragment;
import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.holder.MyIndentHolder;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.mine
 * @创建者: Administrator
 * @创建时间: 2016/3/8 14:15
 * @描述: ${TODO}
 */
public class MyIndentFragment extends SuperBaseFragment<MyIndentBean> implements View.OnClickListener {


    private static final String TAG = "MyIndentFragment";
    @Bind(R.id.myindent_nowonemonthindent)
    TextView mNowonemonthindent;
    @Bind(R.id.myindent_beforeonemonthindent)
    TextView mBeforeonemonthindent;
    @Bind(R.id.myindent_cancleindent)
    TextView mCancleindent;
    @Bind(R.id.myindent_listview)
    ListView mListview;
    private MainActivity            mActivity;
    private TextView                mMyIndentBack;
    private View                    mMyIndentView;
    private MyIndentBean            mMyIndentBean;
    private MyIndentAdapter         mMyIndentAdapter;
    private List<IndentMesListBean> mListBeans;

    private int mType;
    private int mPage;
    private int mPageNum;
    private int mCurrState = 1;

    //国钦修改的:添加了无参构造和有参构造的注解
    public MyIndentFragment(){}

    @SuppressLint("ValidFragment")
    public MyIndentFragment(int state){
        this.mCurrState = state;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View topBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_myindent, null);
        mMyIndentBack = (TextView) topBarView.findViewById(R.id.myindent_back);
        mActivity = (MainActivity) getActivity();
        mActivity.setTopBarView(topBarView);
        mActivity.setHideTopBar(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public String getUrl(int type, int page, int pageNum) {
        String url = Url.ADDRESS_SERVER + "/orderlist?type=" + type + "&page=" + page + "&pageNum=" + pageNum;
        return url;
}

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    protected void loadData(LoadListener<MyIndentBean> listener) {
        listener.onSuccess(null);
    }

    private void netLoadData(String url) {
        RequestQueue queue = Volley.newRequestQueue(ResUtil.getContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                Toast.makeText(ResUtil.getContext(), "服务器请求成功", Toast.LENGTH_SHORT).show();
                mMyIndentBean = parseJson(json);
                mListBeans = mMyIndentBean.orderList;
                mMyIndentAdapter = new MyIndentAdapter(mListview, mListBeans);
                mListview.setAdapter(mMyIndentAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ResUtil.getContext(), "服务器请求失败", Toast.LENGTH_SHORT).show();
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
    protected MyIndentBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        MyIndentBean myIndentBean = gson.fromJson(jsonStr, MyIndentBean.class);
        return myIndentBean;
    }

    @Override
    protected View loadSuccessView() {
        mMyIndentView = View.inflate(ResUtil.getContext(), R.layout.mine_myindent_fragment, null);
        ButterKnife.bind(this, mMyIndentView);
        return mMyIndentView;
    }

    @Override
    protected void refreshSuccessView(MyIndentBean data) {
        if (mCurrState == 1) {
            mNowonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_selected1);
            netLoadData(getUrl(1, 0, 10));
        } else if (mCurrState == 2) {
            mBeforeonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_selected2);
            netLoadData(getUrl(2, 0, 10));
        } else if (mCurrState == 3) {
            mCancleindent.setBackgroundResource(R.drawable.shape_myindent_tv_selected3);
            netLoadData(getUrl(3, 0, 10));
        }


        mNowonemonthindent.setOnClickListener(this);
        mBeforeonemonthindent.setOnClickListener(this);
        mCancleindent.setOnClickListener(this);
        mMyIndentBack.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myindent_nowonemonthindent:
                mCurrState = 1;
                mBeforeonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_normal2);
                mCancleindent.setBackgroundResource(R.drawable.shape_myindent_tv_normal3);
                mNowonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_selected1);
                //清空集合
                mListBeans.removeAll(mListBeans);
                mMyIndentAdapter.notifyDataSetChanged();//gq修改的
                //请求数据添加到集合中
                netLoadData(getUrl(1, 0, 10));
                break;
            case R.id.myindent_beforeonemonthindent:
                mCurrState = 2;
                mNowonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_normal1);
                mCancleindent.setBackgroundResource(R.drawable.shape_myindent_tv_normal3);
                mBeforeonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_selected2);
                mListBeans.removeAll(mListBeans);
                mMyIndentAdapter.notifyDataSetChanged();//gq修改的
                mType = 2;
                mPage = 0;
                netLoadData(getUrl(2, 0, 10));
                break;
            case R.id.myindent_cancleindent:
                mCurrState = 3;
                mNowonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_normal1);
                mBeforeonemonthindent.setBackgroundResource(R.drawable.shape_myindent_tv_normal2);
                mCancleindent.setBackgroundResource(R.drawable.shape_myindent_tv_selected3);

                mListBeans.removeAll(mListBeans);
                mMyIndentAdapter.notifyDataSetChanged();//gq修改的
                netLoadData(getUrl(3, 0, 10));
                break;
            case R.id.myindent_back:
                mCurrState = 1;
                ((HolderFragment) getParentFragment()).goBack();
                break;
        }
    }

    private class MyIndentAdapter extends SuperBaseAdapter<IndentMesListBean> {
        public MyIndentAdapter(AbsListView listView, List<IndentMesListBean> datas) {
            super(listView, datas);
        }

        @Override
        public BaseHolder initSpecialHolder() {
            return new MyIndentHolder();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IndentMesListBean indentMesListBean = mListBeans.get(position);
            long orderId = indentMesListBean.orderId;
            ((HolderFragment) getParentFragment()).goForward(new IndentDetailMessageFragment(orderId));
        }

        @Override
        protected List<IndentMesListBean> doLoadMore() throws Exception {
          /*  mPage = mPage+1;
            String url = getUrl(mType, mPage, mPageNum);
            netLoadData(url);
            if(mListBeans == null){
                mPage--;
            }*/
            return super.doLoadMore();
        }
    }


}
