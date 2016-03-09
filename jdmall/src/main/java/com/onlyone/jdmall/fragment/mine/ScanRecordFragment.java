package com.onlyone.jdmall.fragment.mine;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.ProductDetailBean;
import com.onlyone.jdmall.constance.Serialize;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SerializeUtil;
import com.onlyone.jdmall.utils.UserLoginUtil;

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
    private List<ProductDetailBean.ProductEntity> mDataSet;

    @Override
    protected void refreshSuccessView(List<ProductDetailBean.ProductEntity> data) {
        SystemClock.sleep(1000);
        mScanRecordPb.setVisibility(View.GONE);
        mScanRecordLv.setAdapter(new ScanRecordAdapter());
    }

    /**
     * 成功的回调
     *
     * @return
     */
    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(ResUtil.getContext(), R.layout.scan_record_fragment, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 加载数据
     *
     * @param listener 监听
     */
    @Override
    protected void loadData(LoadListener<List<ProductDetailBean.ProductEntity>> listener) {
        //2.获取当前登录用户名
        String userName = UserLoginUtil.getLoginUser();

        //3.序列化取出保存的集合
        String keyTag = userName + "_" + Serialize.TAG_HISTORY;
        mDataSet = SerializeUtil.serializeObject(keyTag);
        if (mDataSet == null) {
            String detailMessage = "您还没有浏览过任何物品";
            listener.onError(new Exception(detailMessage));
        } else {
            listener.onSuccess(mDataSet);
        }
    }

    @Override
    protected void handleError(Exception e) {
        SystemClock.sleep(300);
        mScanRecordPb.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class ScanRecordAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(mDataSet != null){
                mDataSet.size();
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
            ViewHolder<ProductDetailBean.ProductEntity> holder = null;
            if(convertView == null){
                 holder = new ViewHolder<>();
                convertView = holder.mRootView;
            }else{
                holder = (ViewHolder<ProductDetailBean.ProductEntity>) convertView.getTag();
            }
            ProductDetailBean.ProductEntity entity = mDataSet.get(position);
            holder.refreshView(entity);
            return convertView;
        }

        public class ViewHolder<T> {

            public  View mRootView;
            public TextView mTest;

            public ViewHolder(){
                mRootView = View.inflate(ResUtil.getContext(), R.layout.scan_record_item, null);
               TextView mTest = (TextView) mRootView.findViewById(R.id.scan_record_tv_test);
                mRootView.setTag(this);
            }
            public void refreshView(T t){
                mTest.setText(t.toString());
            }
        }
    }
}
