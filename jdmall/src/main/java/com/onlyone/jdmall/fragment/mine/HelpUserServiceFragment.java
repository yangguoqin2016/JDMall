package com.onlyone.jdmall.fragment.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.MineFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.mine
 * @创建者: Ashin
 * @创建时间: 2016/3/7 14:18
 * @描述: ${TODO}
 */
public class HelpUserServiceFragment extends BaseFragment<Object> implements View.OnClickListener {

    private View            mSuccessView;
    private MainActivity    mMainActivity;
    private View            mTopBarView;
    private TextView        mHelpBack;
    private FragmentManager mManager;

    @Override
    public void onPause() {
        super.onPause();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_help, null);
        mHelpBack = (TextView) mTopBarView.findViewById(R.id.mine_tv_help_back);
        mHelpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mManager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = mManager.beginTransaction();
                Fragment fragment = mManager.findFragmentByTag(MineFragment.TAG_MINEHELP_FRAGMENT);
                transaction.remove(fragment);
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
                transaction.commit();
            }
        });
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(mTopBarView);
        mMainActivity.setHideTopBar(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        View topBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_sendway, null);
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(topBarView);

    }

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        mSuccessView = View.inflate(ResUtil.getContext(), R.layout.mine_help_selled_service, null);
        LinearLayout llCall = (LinearLayout) mSuccessView.findViewById(R.id.Mine_service_call);
        llCall.setOnClickListener(this);
        return mSuccessView;
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
        /*Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://400-6666-6666"));
        mMainActivity.startActivity(intent);*/
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
    }
}
