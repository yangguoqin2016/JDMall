package com.onlyone.jdmall.fragment.mine;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.ProductDetailBean;
import com.onlyone.jdmall.constance.Serialize;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SerializeUtil;
import com.onlyone.jdmall.utils.UserLoginUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.mine
 * @创建者: wt
 * @创建时间: 2016/3/9 11:24
 * @描述: ${TODO}
 */
public class ScanRecordFragment extends BaseFragment<List<ProductDetailBean.ProductEntity>> {
    @Bind(R.id.scan_record_lv)
    ListView mScanRecordLv;
    @Bind(R.id.scan_record_pb)
    ProgressBar mScanRecordPb;
    private ArrayList<ProductDetailBean.ProductEntity> mDataSet;
    private View mRootView;
    private ScanRecordAdapter mAdapter;
    private String mUserName;
    private String mKeyTag;

    @Override
    protected void refreshSuccessView(List<ProductDetailBean.ProductEntity> data) {
        mScanRecordPb.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 成功的回调
     *
     * @return
     */
    @Override
    protected View loadSuccessView() {
        LogUtil.d("vava", "loadSuccessView");
        mRootView = View.inflate(ResUtil.getContext(), R.layout.scan_record_fragment, null);
        ButterKnife.bind(this, mRootView);
        mAdapter = new ScanRecordAdapter();
        mScanRecordLv.setAdapter(mAdapter);
        return mRootView;
    }

    /**
     * 加载数据
     *
     * @param listener 监听
     */
    @Override
    protected void loadData(LoadListener<List<ProductDetailBean.ProductEntity>> listener) {
        //2.获取当前登录用户名
        mUserName = UserLoginUtil.getLoginUser();

        //3.序列化取出保存的集合
        mKeyTag = mUserName + "_" + Serialize.TAG_BROWSE;
        mDataSet = SerializeUtil.serializeObject(mKeyTag);
        if (mDataSet == null || mDataSet.size() ==0) {
            String detailMessage = "您还没有浏览过任何物品";
            LogUtil.d("vava", detailMessage);
            listener.onError(new Exception(detailMessage));
        } else {
            LogUtil.d("vava", "===mDataSet size ===" + mDataSet.size());
            listener.onSuccess(mDataSet);
        }
    }

    @Override
    protected void handleError(Exception e) {
        SystemClock.sleep(300);
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText(e.getMessage());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.BLACK);
        FrameLayout view = mLoadPager.getRootView();
        view.addView(tv);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class ScanRecordAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDataSet != null) {
                return mDataSet.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = holder.mRootView;
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.setCurrentPosition(position);
            ProductDetailBean.ProductEntity entity = mDataSet.get(position);
            holder.refreshView(entity);
            return convertView;
        }

        public class ViewHolder {

            public View mRootView;
            public TextView mTvName;
            public TextView mTvPriceLimit;
            public TextView mTvPriceMarket;
            public ImageView mIvPic;
            private ImageView mIvDel;
            private int mCurrentPos= 0;
            public ViewHolder() {
                mRootView = View.inflate(ResUtil.getContext(), R.layout.scan_record_item, null);
                mTvName = (TextView) mRootView.findViewById(R.id.scan_record_tv_name);
                mTvPriceLimit = (TextView) mRootView.findViewById(R.id.scan_record_tv_price_limit);
                mTvPriceMarket = (TextView) mRootView.findViewById(R.id.scan_record_tv_price_market);
                mIvPic = (ImageView) mRootView.findViewById(R.id.scan_record_iv);
                mIvDel = (ImageView) mRootView.findViewById(R.id.scan_record_iv_delete);

                mIvDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doDelAnimation();
                    }
                });
                mRootView.setTag(this);
            }

            private void doDelAnimation() {
                TranslateAnimation ta = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0,
                        Animation.RELATIVE_TO_PARENT, -1,
                        Animation.RELATIVE_TO_PARENT, 0,
                        Animation.RELATIVE_TO_PARENT, 0
                );
                ta.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //删除数据，更新UI
                        mDataSet.remove(mCurrentPos);
                        notifyDataSetChanged();
                        //同时删除本地文件保存的记录
                        SerializeUtil.deserializeObject(mKeyTag, mDataSet);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ta.setDuration(300);
                mRootView.startAnimation(ta);
            }

            public void refreshView(ProductDetailBean.ProductEntity entity) {
                if (entity.pics.size() > 0) {
                    String url = Url.ADDRESS_SERVER + entity.pics.get(0);
                    Picasso.with(ResUtil.getContext()).load(url).into(mIvPic);
                }
                mTvName.setText(entity.name);
                mTvPriceLimit.setText("￥" + entity.limitPrice);

                mTvPriceMarket.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                mTvPriceMarket.setText("￥" + entity.marketPrice);

            }

            public void setCurrentPosition(int position) {
                mCurrentPos =position;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        View title = View.inflate(ResUtil.getContext(), R.layout.scan_record_title, null);
        TextView tvBack = (TextView) title.findViewById(R.id.scan_record_title_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        MainActivity mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(title);
    }
}
