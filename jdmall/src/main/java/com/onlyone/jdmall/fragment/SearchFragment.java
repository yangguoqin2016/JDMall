package com.onlyone.jdmall.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 10:48
 * @描述: RadioGroup里面的搜索
 */
public class SearchFragment extends  BaseFragment<Object> implements View.OnClickListener {

    private MainActivity mMainActivity;
    public static final String TAG_SEARCHRESULT_FRAGMENT = "tag_searchresult_fragment";

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        //1.得到TopBar.
        View topBar = View.inflate(ResUtil.getContext(),R.layout.inflate_topbar_search,null);

        //得到TopBar的孩子,设置监听事件
        TextView tvBack = (TextView) topBar.findViewById(R.id.topbar_tv_back);
        TextView tvSearch = (TextView) topBar.findViewById(R.id.topbar_tv_search);
        tvBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        //2.首先先拿到Fragment关联的Activity
        mMainActivity = (MainActivity) getActivity();

        //3.得到MainActivity,再设置TopBar的Ui
        mMainActivity.setTopBarView(topBar);

        //搜索页面
        View contentView = View.inflate(ResUtil.getContext(), R.layout.inflate_search, null);
        return contentView;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }

    /**
     * 返回键的点击事件
     * 返回到首页
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        	case R.id.topbar_tv_back://返回
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_home);
                break;
            case R.id.topbar_tv_search://搜索
                FragmentManager manager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fl_content_container,new SearchResultFragment(),TAG_SEARCHRESULT_FRAGMENT);
                transaction.commit();
                break;

            default:
                break;
        }
    }
}
