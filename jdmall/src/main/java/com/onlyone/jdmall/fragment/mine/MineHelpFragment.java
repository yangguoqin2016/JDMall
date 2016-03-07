package com.onlyone.jdmall.fragment.mine;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.onlyone.jdmall.utils.LogUtil;
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
    public static final  String HELP_USER_GUIDE_FRAGMENT = "help_user_guide_fragment";
    private static final String    HELP_SEND_WAY_FRAGMENT   = "help_send_way_fragment";
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


    private MainActivity          mMainActivity;
    private View                  mTopBarView;
    private TextView              mHelpBack;
    private HelpUserGuideFragment mHelpUserGuideFragment;
    private FragmentManager       mManager;
    private HelpSendWayFragment   mHelpSendWayFragment;

    @Override
    public void onResume() {
        LogUtil.i("ninini", "MineAboutFragment----onResume");
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
                mManager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = mManager.beginTransaction();
                Fragment fragment = mManager.findFragmentByTag(MineFragment.TAG_MINEHELP_FRAGMENT);
                transaction.remove(fragment);
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
                transaction.commit();
                break;
            case R.id.help_buy_guide://购物指南
                Toast.makeText(ResUtil.getContext(),"购物指南",Toast.LENGTH_SHORT).show();
                if (mHelpUserGuideFragment == null) {
                    mHelpUserGuideFragment = new HelpUserGuideFragment();
                }
                changeFragment(mHelpUserGuideFragment,HELP_USER_GUIDE_FRAGMENT);
                break;
            case R.id.help_buy_serve://售后服务
                break;
            case R.id.help_send_way://配送方式
                if (mHelpSendWayFragment == null) {
                    mHelpSendWayFragment = new HelpSendWayFragment();
                }
                changeFragment(mHelpSendWayFragment,HELP_SEND_WAY_FRAGMENT);
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
        LogUtil.i("ninini", "MineAboutFragment----onPause");
        mMainActivity.setHideTopBar(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 实现fragment跳转
     * @param fragment
     * @param tag
     */
    private void changeFragment(Fragment fragment,String tag) {
        FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_content_container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
