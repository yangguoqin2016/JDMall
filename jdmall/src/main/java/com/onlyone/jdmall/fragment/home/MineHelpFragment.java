package com.onlyone.jdmall.fragment.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.MineFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.home
 * @创建者: Ashin
 * @创建时间: 2016/3/6 10:51
 * @描述: ${TODO}
 */
public class MineHelpFragment extends BaseFragment<Object> implements View.OnClickListener {
    @Bind(R.id.help_buy_guide)
    LinearLayout mHelpBuyGuide;
    @Bind(R.id.help_buy_serve)
    LinearLayout mHelpBuyServe;
    @Bind(R.id.help_send_way)
    LinearLayout mHelpSendWay;
    @Bind(R.id.mine_btn_bye)
    ImageView    mMineBtnBye;
    @Bind(R.id.mine_btn_look_order)
    ImageView    mMineBtnLookOrder;


    private MainActivity mMainActivity;
    private View         mTopBarView;
    private TextView     mHelpBack;

    @Override
    public void onResume() {
        Log.d("aaa", "MineAboutFragment----onResume");
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_help, null);
        mHelpBack = (TextView) mTopBarView.findViewById(R.id.mine_tv_help_back);
        mHelpBack.setOnClickListener(this);
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(mTopBarView);
        mMainActivity.setHideTopBar(false);
        super.onResume();
    }

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(ResUtil.getContext(), R.layout.mine_help_centre, null);
        ButterKnife.bind(this, rootView);
        mHelpBuyGuide.setOnClickListener(this);
        mHelpBuyServe.setOnClickListener(this);
        mHelpSendWay.setOnClickListener(this);
        mMineBtnBye.setOnClickListener(this);
        mMineBtnLookOrder.setOnClickListener(this);
        return rootView;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_tv_help_back://返回
                FragmentManager manager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = manager.findFragmentByTag(MineFragment.TAG_MINEHELP_FRAGMENT);
                transaction.remove(fragment);
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
                transaction.commit();
                break;
            case R.id.help_buy_guide://购物指南
                Toast.makeText(ResUtil.getContext(),"购物指南",Toast.LENGTH_SHORT).show();
                break;
            case R.id.help_buy_serve://售后服务
                break;
            case R.id.help_send_way://配送方式

                break;
            case R.id.mine_btn_bye://继续购物
                //TODO：点击继续购物按钮，跳转到主界面
                break;
            case R.id.mine_btn_look_order://查看订单
                //TODO：点击查看订单按钮，跳转到订单详情界面
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMainActivity.setHideTopBar(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
