package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.InvoiceBean;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.car
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 20:41
 * @描述: 发票类型的Fragment
 */
public class ReceiptFragment extends BaseFragment<InvoiceBean> implements View.OnClickListener,
																		  RadioGroup
																				  .OnCheckedChangeListener
{
	@Bind(R.id.rb_receipt_person)
	RadioButton mRbReceiptPerson;
	@Bind(R.id.rb_receipt_company)
	RadioButton mRbReceiptCompany;
	@Bind(R.id.rg_receipt_head)
	RadioGroup  mRgReceiptHead;
	@Bind(R.id.et_receipt_title)
	EditText    mEtReceiptTitle;
	@Bind(R.id.rb_receipt_book)
	RadioButton mRbReceiptBook;
	@Bind(R.id.rb_receipt_sound)
	RadioButton mRbReceiptSound;
	@Bind(R.id.rb_receipt_game)
	RadioButton mRbReceiptGame;
	@Bind(R.id.rb_receipt_software)
	RadioButton mRbReceiptSoftware;
	@Bind(R.id.rb_receipt_ziliao)
	RadioButton mRbReceiptZiliao;
	@Bind(R.id.rg_receipt_content)
	RadioGroup  mRgReceiptContent;
	private MainActivity mMainActivity;

	private int    mHeaderType = 1; //发票头类型
	private int    mTicketType = 1; //发票类型
	private String mHeader     = "二狗子"; //发票抬头
	private BalanceFragment.OnResultBack mListener;

	@Override
	public void onAttach(Context context) {
		mMainActivity = (MainActivity) context;
		super.onAttach(context);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		Bundle arg = getArguments();
		//读取调用处传递过来的参数信息
		if (arg != null) {
			mTicketType = arg.getInt("ticketType", 1);
			mHeaderType = arg.getInt("headerType", 1);
			mHeader = arg.getString("header");
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		View topBar = View.inflate(mMainActivity, R.layout.inflate_topbar_receipt, null);
		mMainActivity.setTopBarView(topBar);
		super.onResume();

		//进行数据回显
		mEtReceiptTitle.setText(mHeader);

		topBar.findViewById(R.id.topbar_tv_balance_center).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						goBack();
					}
				});
	}

	@Override
	protected void refreshSuccessView(InvoiceBean data) {

	}

	@Override
	protected View loadSuccessView() {
		View contentView = View.inflate(mMainActivity, R.layout.inflate_receipt, null);

		ButterKnife.bind(this, contentView);

		updateCheckState();

		mRgReceiptHead.setOnCheckedChangeListener(this);
		mRgReceiptContent.setOnCheckedChangeListener(this);

		return contentView;
	}

	@Override
	protected void loadData(LoadListener<InvoiceBean> listener) {
		listener.onSuccess(null);
	}

	@Override
	protected void handleError(Exception e) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);

		mHeader = TextUtils.isEmpty(mHeader) ? "二狗子" : mHeader;

		if (mListener != null) {
			mListener.onResult(new BalanceFragment.TicketInfo(mHeaderType,
					mHeader, mTicketType));
		}
	}

	/**
	 * 更新两组单选按钮选中状态
	 */
	public void updateCheckState() {

		switch (mHeaderType) {
			case 1:
				mRgReceiptHead.check(R.id.rb_receipt_person);
				break;
			case 2:
				mRgReceiptHead.check(R.id.rb_receipt_company);
				break;
		}

		switch (mTicketType) {
			case 1:
				mRgReceiptContent.check(R.id.rb_receipt_book);
				break;
			case 2:
				mRgReceiptContent.check(R.id.rb_receipt_sound);
				break;
			case 3:
				mRgReceiptContent.check(R.id.rb_receipt_game);
				break;
			case 4:
				mRgReceiptContent.check(R.id.rb_receipt_software);
				break;
			case 5:
				mRgReceiptContent.check(R.id.rb_receipt_ziliao);
				break;
		}

	}

	@Override
	public void onClick(View v) {
		updateCheckState();
	}

	public void setOnResultBackListener(BalanceFragment.OnResultBack listener) {
		mListener = listener;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group == mRgReceiptHead) {
			//发票头类型
			switch (checkedId) {
				case R.id.rb_receipt_person:
					mHeaderType = 1;
					break;
				case R.id.rb_receipt_company:
					mHeaderType = 2;
					break;
			}

		} else if (group == mRgReceiptContent) {
			//发票类型
			switch (checkedId) {
				case R.id.rb_receipt_book:
					mTicketType = 1;
					break;
				case R.id.rb_receipt_sound:
					mTicketType = 2;
					break;
				case R.id.rb_receipt_game:
					mTicketType = 3;
					break;
				case R.id.rb_receipt_software:
					mTicketType = 4;
					break;
				case R.id.rb_receipt_ziliao:
					mTicketType = 5;
					break;
			}
		}
	}

	@Override
	public boolean preGoBack() {
		String header = mEtReceiptTitle.getText().toString();
		boolean isEmpty = TextUtils.isEmpty(header);
		if (isEmpty) {
			Toast.makeText(ResUtil.getContext(), "请输入发票抬头", Toast.LENGTH_SHORT).show();
		}
		return !isEmpty;
	}
}
