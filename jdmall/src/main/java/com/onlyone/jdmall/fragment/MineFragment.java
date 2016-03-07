package com.onlyone.jdmall.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.mine.AddressManagerFragment;
import com.onlyone.jdmall.fragment.mine.MineAboutFragment;
import com.onlyone.jdmall.fragment.mine.MineHelpFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/5 16:09
 * @描述: ${TODO}
 */

public class MineFragment extends BaseFragment<Object> implements View.OnClickListener {

    public static final String TAG_MINEABOUT_FRAGMENT = "tag_mineabout_fragment";

    public static final String TAG_MINEHELP_FRAGMENT = "tag_minehelp_fragment";

    public static final String TAG_MINEFAVORITE_FRAGMENT = "tag_minefavorite_fragment";

    public static final String TAG_MINEADDRESSMANAGER_FRAGMENT = "tag_mineaddressmanager_fragment";
    @Bind(R.id.fragment_ll_mine_order)
    LinearLayout mFragmentLlMineOrder;
    @Bind(R.id.fragment_ll_mine_address)
    LinearLayout mFragmentLlMineAddress;
    @Bind(R.id.fragment_ll_mine_gift)
    LinearLayout mFragmentLlMineGift;
    @Bind(R.id.fragment_ll_mine_favorite)
    LinearLayout mFragmentLlMineFavorite;
    @Bind(R.id.fragment_ll_mine_record)
    LinearLayout mFragmentLlMineRecord;
    @Bind(R.id.fragment_ll_mine_help)
    LinearLayout mFragmentLlMineHelp;
    @Bind(R.id.fragment_ll_mine_feedback)
    LinearLayout mFragmentLlMineFeedback;
    @Bind(R.id.fragment_ll_mine_about)
    LinearLayout mFragmentLlMineAbout;
    @Bind(R.id.mine_back_btn)
    Button       mMineBackBtn;
    @Bind(R.id.mine_tv_call)
    TextView     mMineTvCall;

<<<<<<< Updated upstream
    private MainActivity     mMainActivity;
    private MineHelpFragment mHelpFragment;
=======
    private MainActivity mMainActivity;
    private Fragment mFavorableGiftFragment;
>>>>>>> Stashed changes


    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View successView = View.inflate(ResUtil.getContext(), R.layout.fragment_mine, null);
        ButterKnife.bind(this, successView);
        return successView;
    }


    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }



    @Override
    public void onResume() {
        Log.d("aaa", "onResume");
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setHideTopBar(true);

        //设置点击事件监听
        mFragmentLlMineOrder.setOnClickListener(this);
        mFragmentLlMineAddress.setOnClickListener(this);
        mFragmentLlMineGift.setOnClickListener(this);
        mFragmentLlMineFavorite.setOnClickListener(this);
        mFragmentLlMineRecord.setOnClickListener(this);
        mFragmentLlMineHelp.setOnClickListener(this);
        mFragmentLlMineFeedback.setOnClickListener(this);
        mFragmentLlMineAbout.setOnClickListener(this);
        mMineBackBtn.setOnClickListener(this);
        mMineTvCall.setOnClickListener(this);

        super.onResume();
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
        Log.d("aaa", "onDestroyView");
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back_btn:
                //退出登录
                break;
            case R.id.fragment_ll_mine_order://我的订单
                break;
            case R.id.fragment_ll_mine_address://地址管理
                Fragment addressManagerFragment = new AddressManagerFragment();
                changeFragment(addressManagerFragment, TAG_MINEADDRESSMANAGER_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_gift://优惠券/礼品卡
                if(mFavorableGiftFragment == null){
                    mFavorableGiftFragment = new MineFavorableGiftFragment();
                }
                changeFragment(mFavorableGiftFragment, TAG_MINEFAVORITE_FRAGMENT);

                break;
            case R.id.fragment_ll_mine_favorite://收藏夹
                Fragment favoriteFrament = new MineFavoriteFragment();
                changeFragment(favoriteFrament, TAG_MINEFAVORITE_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_record://浏览记录
                break;
            case R.id.fragment_ll_mine_help://帮助中心
                if (mHelpFragment == null) {
                    mHelpFragment = new MineHelpFragment();
                }
                changeFragment(mHelpFragment, TAG_MINEHELP_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_feedback://用户反馈
                break;
            case R.id.fragment_ll_mine_about://关于
                Toast.makeText(ResUtil.getContext(), "点我了", Toast.LENGTH_SHORT).show();
                Fragment aboutFragment = new MineAboutFragment();
                changeFragment(aboutFragment, TAG_MINEABOUT_FRAGMENT);
                break;
            case R.id.mine_tv_call:
                //客服电话
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
            default:
                break;
        }
    }

    /**
     * 实现fragment跳转
     *
     * @param fragment
     * @param tag
     */
    private void changeFragment(Fragment fragment, String tag) {
        FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_content_container, fragment, tag);
        transaction.addToBackStack("aaa");

        transaction.commit();
    }
}
