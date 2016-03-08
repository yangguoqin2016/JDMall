package com.onlyone.jdmall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.fragment.FragmentFactory;
import com.onlyone.jdmall.fragment.MineFragment;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.onlyone.jdmall.view.NoScrollLazyViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

	/**
	 * 导航布局
	 */
	@Bind(R.id.fl_daohang)
	public FrameLayout mFlDaohang;
	/**
	 * 主要显示界面的懒加载ViewPager
	 */

	/*-------------------- 底部导航单选按钮组 - begin --------------------*/
	@Bind(R.id.rb_bottom_home)
	RadioButton mRbBottomHome;
	@Bind(R.id.rb_bottom_search)
	RadioButton mRbBottomSearch;
	@Bind(R.id.rb_bottom_band)
	RadioButton mRbBottomBand;
	@Bind(R.id.rb_bottom_car)
	RadioButton mRbBottomCar;
	@Bind(R.id.rb_bottom_mine)
	RadioButton mRbBottomMine;
	@Bind(R.id.rg_bottom_nav)
	public RadioGroup mRgBottomNav;
	/*-------------------- 底部导航单选按钮组 - end --------------------*/

	@Bind(R.id.vp_main)
	public NoScrollLazyViewPager mVpMain;

	private FragmentManager mManager;
	private NavAdapter      mNavAdapter;
	private SPUtil mSPUtil;
	private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSPUtil = new SPUtil(this);
		initView();
		initEvent();
		intData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		ButterKnife.unbind(this);
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		mRgBottomNav.setOnCheckedChangeListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void intData() {
		mManager = getSupportFragmentManager();

//		mManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//			@Override
//			public void onBackStackChanged() {
//
//			}
//		});
		mNavAdapter = new NavAdapter(mManager);
		mVpMain.setAdapter(mNavAdapter);
	}

	/**
	 * 设置顶部Bar的视图,只需要添加模块相对应的View即可
	 * 自动移除之前的视图
	 *
	 * @param view Bar视图
	 */
	public void setTopBarView(View view) {
		if (view == null) {
			return;
		}

		ViewParent parent = view.getParent();
		if (parent != null && parent instanceof ViewGroup){
			((ViewGroup) parent).removeView(view);
		}

		mFlDaohang.removeAllViews();


		mFlDaohang.addView(view);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		//底部导航按钮选中状态切换的时候就切换ViewPager显示的页面
		switch (checkedId) {
			case R.id.rb_bottom_home:
				mVpMain.setCurrentItem(0);
				break;
			case R.id.rb_bottom_search:
				mVpMain.setCurrentItem(1);
				break;
			case R.id.rb_bottom_band:
				mVpMain.setCurrentItem(2);
				break;
			case R.id.rb_bottom_car:
				mVpMain.setCurrentItem(3);
				break;
			case R.id.rb_bottom_mine:
				mVpMain.setCurrentItem(4);
				break;
		}
	}


	class NavAdapter extends FragmentStatePagerAdapter {
		public NavAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			boolean isLoginSuccess = mSPUtil.getBoolean(SP.ISLOGINSUCCESS, false);
			LogUtil.d(TAG,"isLoginSuccess  =  "+isLoginSuccess );
			if(position==4&& isLoginSuccess){
				LogUtil.d(TAG,"进来newMineFragment了-------");
				return new MineFragment();
			}
			return FragmentFactory.getFragment(position);
		}

		@Override
		public int getCount() {
			return FragmentFactory.MAX_FRAGMENT_COUNT;
		}
	}

	/**
	 * 设置是否显示TopBar
	 *
	 * @param isHide true隐藏，false显示
	 */
	public void setHideTopBar(boolean isHide) {
		mFlDaohang.setVisibility(isHide ? View.GONE : View.VISIBLE);
	}

	/**
	 * 监听返回键
	 */
	@Override
	public void onBackPressed() {
		if (mListener != null) {
			mListener.onPressed();
		}
		super.onBackPressed();
	}

	private OnBackPressedListener mListener;

	/**
	 * 设置实体返回键监听
	 *
	 * @param listener 返回键监听
	 */
	public void setOnBackPreseedListener(OnBackPressedListener listener) {
		mListener = listener;
	}

	/**
	 * 如果需要监听返回键被按下的事件，可以实现这个接口
	 */
	public interface OnBackPressedListener {
		void onPressed();
	}
}
