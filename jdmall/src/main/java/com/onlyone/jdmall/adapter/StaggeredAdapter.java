package com.onlyone.jdmall.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.ProductDetailActivity;
import com.onlyone.jdmall.bean.LimitBuyBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.adapter
 * @创建者: wt
 * @创建时间: 2016/3/8 21:44
 * @描述: ${TODO}
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.MyViewHolder> {

    private List<LimitBuyBean.LimitBuyItemBean> mProductList;
    private RecyclerView mRecyclerView;

    public StaggeredAdapter(List<LimitBuyBean.LimitBuyItemBean> datas ,RecyclerView recyclerView) {
        mProductList = datas;
        mRecyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(parent.getContext(), R.layout.item_staggered, null);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setDataAndRefresh(mProductList.get(position));
    }

    /**
     * 获取条目数
     * @return
     */
    @Override
    public int getItemCount() {
        if(mProductList != null){
           return  mProductList.size();
        }
        return 0;
    }

    /**
     * holder:1、持有视图
     *      2、持有数据
     *      3、视图和数据绑定
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPic;
        private TextView mTvName;
        private LimitBuyBean.LimitBuyItemBean mData;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIvPic = (ImageView) itemView.findViewById(R.id.item_straggered_iv);
            mTvName = (TextView) itemView.findViewById(R.id.item_straggered_tv);
        }

        public void setDataAndRefresh(LimitBuyBean.LimitBuyItemBean productBean) {
            mData = productBean;
            refreshView(productBean);
        }

        private void refreshView(LimitBuyBean.LimitBuyItemBean productBean) {

            String url = Url.ADDRESS_SERVER+productBean.pic;
            LogUtil.d("vovo" , "url = "+url);
            Picasso.with(ResUtil.getContext()).load(url).into(mIvPic, new Callback() {
                @Override
                public void onSuccess() {
                    mRecyclerView.requestLayout();
                    LogUtil.d("vovo", "ssssssss");
                }

                @Override
                public void onError() {
                    mIvPic.requestLayout();
                    LogUtil.d("vovo", "eeeeeeeee");
                }
            });
            mIvPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( ResUtil.getContext() , ProductDetailActivity.class);
                    intent.putExtra("id", mData.id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ResUtil.getContext().startActivity(intent);
                }
            });
            mTvName.setText(productBean.name);
        }
    }
}
