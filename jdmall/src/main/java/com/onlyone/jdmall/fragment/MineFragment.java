package com.onlyone.jdmall.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.MineUserInfoBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.mine.AddressManagerFragment;
import com.onlyone.jdmall.fragment.mine.MineAboutFragment;
import com.onlyone.jdmall.fragment.mine.MineHelpFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/5 16:09
 * @描述: ${TODO}
 */

public class MineFragment extends BaseFragment<MineUserInfoBean> implements View.OnClickListener {

    public static final String TAG_MINEABOUT_FRAGMENT = "tag_mineabout_fragment";

    public static final String TAG_MINEHELP_FRAGMENT = "tag_minehelp_fragment";

    public static final String TAG_MINEFAVORITE_FRAGMENT = "tag_minefavorite_fragment";

    public static final  String TAG_MINEADDRESSMANAGER_FRAGMENT = "tag_mineaddressmanager_fragment";
    public static final  String TAG_USERFEEDBACK_FRAGMENT       = "tag_userfeedback_fragment";
    private static final String    TAG_LOGIN_FRAGMENT              = "tag_login_fragment";

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
    @Bind(R.id.mine_tv_username)
    TextView     mMineTvUsername;
    @Bind(R.id.mine_tv_user_level)
    TextView     mMineTvUserLevel;
    @Bind(R.id.mine_tv_user_bonus)
    TextView     mMineTvUserBonus;

    private MineHelpFragment         mHelpFragment;
    private MainActivity             mMainActivity;
    private Fragment                 mFavorableGiftFragment;
    private MineUserFeedbackFragment mUserFeedbackFragment;
    private Fragment                 mAboutFragment;
    private Fragment                 mFavoriteFrament;
    private Fragment                 mAddressManagerFragment;
    private LoginFragment            mLoginFragment;
    SPUtil spUtil = new SPUtil(ResUtil.getContext());

    @Override
    protected void refreshSuccessView(MineUserInfoBean data) {
        if (data == null || data.response.equals("error")) {
            /*没获取到userid,或者登陆不成功*/
            FrameLayout framelayout = mLoadPager.getRootView();
            framelayout.removeAllViews();
            /*获取空视图*/
            View emptyView = View.inflate(ResUtil.getContext(), R.layout.mine_userinfo_empty, null);
            Button btnRelogin = (Button) emptyView.findViewById(R.id.mine_userinfo_btn_relogin);
            btnRelogin.setOnClickListener(this);
            /*添加空视图*/
            framelayout.addView(emptyView);
            return;
        }

        mMineTvUsername.setText(spUtil.getString(SP.USERNAME,""));
        mMineTvUserLevel.setText(data.userInfo.level);
        mMineTvUserBonus.setText(data.userInfo.bonus+"积分");
    }

    @Override
    protected View loadSuccessView() {
        View successView = View.inflate(ResUtil.getContext(), R.layout.fragment_mine, null);
        ButterKnife.bind(this, successView);
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
        return successView;
    }


    @Override
    protected void loadData(final LoadListener<MineUserInfoBean> listener) {
        RequestQueue queue = NetUtil.getRequestQueue();

        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                MineUserInfoBean userInfoBean = gson.fromJson(s, MineUserInfoBean.class);
                //Log.v("userInfoBean","userInfoBean--"+userInfoBean);
                //Log.v("userInfoBean","sssss--"+s);
                listener.onSuccess(userInfoBean);
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        };

        String url = Url.ADDRESS_USERINFO;
        StringRequest request = new StringRequest(Request.Method.GET, url, success, error) {
            //添加请求头
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                String userid = spUtil.getLong(SP.USERID, 0)+"";
                //Log.v("userInfoBean","userid--"+userid);
                map.put("userid", userid);
                return map;
            }
        };

        queue.add(request);
        //listener.onSuccess(null);

    }

    @Override
    protected void handleError(Exception e) {
        Toast.makeText(ResUtil.getContext(), "unknown Exception has occured:" + e.toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setHideTopBar(true);

        super.onResume();
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back_btn:
                spUtil.putBoolean(SP.ISLOGINSUCCESS,false);
                spUtil.putLong(SP.USERID, 0);

                LogUtil.d("userid-1====" + spUtil.getLong(SP.USERID, 0));
                LogUtil.d("ISLOGINSUCCESS-1====" + spUtil.getBoolean(SP.ISLOGINSUCCESS, false));
                if (mLoginFragment == null) {
                    mLoginFragment = new LoginFragment();
                }
                changeFragment(mLoginFragment, TAG_LOGIN_FRAGMENT);
                //退出登录
                break;
            case R.id.fragment_ll_mine_order://我的订单
                break;
            case R.id.fragment_ll_mine_address://地址管理
                if (mAddressManagerFragment==null) {
                    mAddressManagerFragment = new AddressManagerFragment();
                }
                changeFragment(mAddressManagerFragment, TAG_MINEADDRESSMANAGER_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_gift://优惠券/礼品卡
                if (mFavorableGiftFragment == null) {
                    mFavorableGiftFragment = new MineFavorableGiftFragment();
                }
                changeFragment(mFavorableGiftFragment, TAG_MINEFAVORITE_FRAGMENT);

                break;
            case R.id.fragment_ll_mine_favorite://收藏夹
                if(mFavoriteFrament==null) {
                    mFavoriteFrament = new MineFavoriteFragment();
                }
                changeFragment(mFavoriteFrament, TAG_MINEFAVORITE_FRAGMENT);
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
                if (mUserFeedbackFragment == null) {
                    mUserFeedbackFragment = new MineUserFeedbackFragment();
                }
                changeFragment(mUserFeedbackFragment, TAG_USERFEEDBACK_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_about://关于
                //Toast.makeText(ResUtil.getContext(), "点我了", Toast.LENGTH_SHORT).show();
                if (mAboutFragment == null) {
                    mAboutFragment = new MineAboutFragment();
                }
                changeFragment(mAboutFragment, TAG_MINEABOUT_FRAGMENT);
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
            case R.id.mine_userinfo_btn_relogin://跳转到登陆界面
                /*spUtil.putBoolean(SP.ISLOGINSUCCESS,false);
                spUtil.putLong(SP.USERID, 0);

                LogUtil.d("userid-1====" + spUtil.getLong(SP.USERID, 0));
                LogUtil.d("ISLOGINSUCCESS-1===="+ spUtil.getBoolean(SP.ISLOGINSUCCESS,false));*/
                if (mLoginFragment == null) {
                    mLoginFragment = new LoginFragment();
                }
                changeFragment(mLoginFragment, TAG_LOGIN_FRAGMENT);
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
        /*FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_content_container, fragment, tag);
        transaction.addToBackStack("aaa");

        transaction.commit();*/
        HolderFragment parentFragmrnt = (HolderFragment) getParentFragment();
        parentFragmrnt.goForward(fragment);
    }
}
