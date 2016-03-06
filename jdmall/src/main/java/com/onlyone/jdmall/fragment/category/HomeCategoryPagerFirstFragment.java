package com.onlyone.jdmall.fragment.category;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.ItemBean;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.category
 * @创建者: yangguoqin
 * @创建时间: 2016/3/6 14:52
 * @描述: 这是从分类主页跳转到的第一个条目的页面
 */
public class HomeCategoryPagerFirstFragment extends BaseFragment<ItemBean> {
    @Bind(R.id.category_common_pager_tv)
    TextView mCategoryCommonPagerTv;
    @Bind(R.id.category_common_pager_gv)
    GridView mCategoryCommonPagerGv;

    @Override
    protected void refreshSuccessView(ItemBean data) {

        mCategoryCommonPagerTv.setText("跳转到了妈妈专区的页面");

    }

    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(getContext(), R.layout.category_fragment_pager_first, null);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void loadData(LoadListener<ItemBean> listener) {

        listener.onSuccess(null);

    }

    @Override
    protected void handleError(Exception e) {
        Toast.makeText(getContext(),"分类第一个页面出错",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
