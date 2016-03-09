package com.onlyone.jdmall.fragment.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
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
    public static final  String HELP_USER_GUIDE_FRAGMENT     = "help_user_guide_fragment";
    private static final String HELP_SEND_WAY_FRAGMENT       = "help_send_way_fragment";
    private static final String HELP_SELLED_SERVICE_FRAGMENT = "help_selled_service_fragment";
    private static final String MY_INDENT_FRAGMENT           = "my_indent_fragment";
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
    @Bind(R.id.help_center_tv_call)
    LinearLayout mHelpCenterTvCall;


    private MainActivity            mMainActivity;
    private View                    mTopBarView;
    private TextView                mHelpBack;
    private HelpUserGuideFragment   mHelpUserGuideFragment;
    private HelpSendWayFragment     mHelpSendWayFragment;
    private HelpUserServiceFragment mHelpUserServiceFragment;
    private MyIndentFragment        mMyIndentFragment;
    //private FragmentManager         mManager;

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
        mHelpCenterTvCall.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.mine_tv_help_back://返回
                /*mManager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = mManager.beginTransaction();
                Fragment fragment = mManager.findFragmentByTag(MineFragment.TAG_MINEHELP_FRAGMENT);
                transaction.remove(fragment);
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
                transaction.commit();*/
                ((HolderFragment) getParentFragment()).goBack();
                break;
            case R.id.help_buy_guide://购物指南
                Toast.makeText(ResUtil.getContext(), "购物指南", Toast.LENGTH_SHORT).show();
                if (mHelpUserGuideFragment == null) {
                    mHelpUserGuideFragment = new HelpUserGuideFragment();
                }
                changeFragment(mHelpUserGuideFragment, HELP_USER_GUIDE_FRAGMENT);
                break;
            case R.id.help_buy_serve://售后服务
                if (mHelpUserServiceFragment == null) {
                    mHelpUserServiceFragment = new HelpUserServiceFragment();
                }
                changeFragment(mHelpUserServiceFragment, HELP_SELLED_SERVICE_FRAGMENT);
                break;
            case R.id.help_send_way://配送方式
                if (mHelpSendWayFragment == null) {
                    mHelpSendWayFragment = new HelpSendWayFragment();
                }
                changeFragment(mHelpSendWayFragment, HELP_SEND_WAY_FRAGMENT);
                break;
            case R.id.mine_btn_bye://继续购物
                //TODO：点击继续购物按钮，跳转到主界面
                //MainActivity activity = (MainActivity) getActivity();
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_home);
                break;
            case R.id.mine_btn_look_order://查看订单
                //TODO：点击查看订单按钮，跳转到我的订单界面
                if (mMyIndentFragment == null) {
                    mMyIndentFragment = new MyIndentFragment();
                }
                changeFragment(mMyIndentFragment, MY_INDENT_FRAGMENT);
                break;
            case R.id.help_center_tv_call:
                //打客服电话
                AlertDialog.Builder builder = new AlertDialog.Builder(mMainActivity);
                builder.setTitle("系统提示")//设置对话框标题
                        .setMessage("您有什么疑问？您需要联系客服处理吗？")//设置显示的内容
                                //添加确定按钮
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //当点击确定时就拨打电话
                                //意图：想干什么事
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addCategory("android.intent.category.DEFAULT");
                                //url:统一资源定位符
                                //uri:统一资源标示符（更广）
                                intent.setData(Uri.parse("tel://400-6666-6666"));
                                //开启系统拨号器
                                mMainActivity.startActivity(intent);
                            }
                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    //添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //响应事件返回更多设置的主界面

                    }
                }).show();
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
     *
     * @param fragment
     * @param tag
     */
    private void changeFragment(Fragment fragment, String tag) {
        /*FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_content_container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();*/
        HolderFragment parentFragmrnt = (HolderFragment) getParentFragment();
        parentFragmrnt.goForward(fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
