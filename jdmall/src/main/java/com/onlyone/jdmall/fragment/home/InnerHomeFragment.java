package com.onlyone.jdmall.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名: JDMall
 * 包名: com.wt.myapplication
 * 创建者: wt
 * 创建时间: 2016/3/5 13:49
 * 描述: ${TODO}
 */
public class InnerHomeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.home_vp)
    ViewPager mHomeVp;

    private OnChangeFragmentListener mListener;

    public InnerHomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(ResUtil.getContext(), R.layout.inner_home_fragment, null);
        ButterKnife.bind(this, view);
        initView();
        setListener();
        return view;
    }

    private void initView() {
    }

    private void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
//        String str = "";
//        switch (v.getId()) {
//            case R.id.iv1:
//                str = "点击了第1个按钮";
//                if (mListener != null) {
//                    mListener.onChange(1);
//                }
//                break;
//            case R.id.iv2:
//                str = "点击了第2个按钮";
//                if (mListener != null) {
//                    mListener.onChange(2);
//                }
//                break;
//        }
//        Toast.makeText(ResUtil.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void setOnChangeFragmentListener(OnChangeFragmentListener listener) {
        mListener = listener;
    }

    public interface OnChangeFragmentListener {
        void onChange(int position);
    }

    public void refreshView(Object obj){

    }

    private class TopicAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }


    }
}
