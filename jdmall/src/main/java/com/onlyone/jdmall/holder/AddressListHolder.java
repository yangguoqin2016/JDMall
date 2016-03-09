package com.onlyone.jdmall.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.AddressBean;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.holder
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 14:20
 * @描述: ${TODO}
 */
public class AddressListHolder extends BaseHolder<AddressBean.AddressList> {
	@Bind(R.id.item_addresslist_tv_name)
	public TextView  mItemAddresslistTvName;
	@Bind(R.id.item_addresslist_tv_num)
	public TextView  mItemAddresslistTvNum;
	@Bind(R.id.item_addresslist_tv_position)
	public TextView  mItemAddresslistTvPosition;
	@Bind(R.id.item_addresslist_iv_selected)
	public ImageView mItemAddresslistIvSelected;

	@Override
	public View initHolderView() {
		View rootView = View.inflate(ResUtil.getContext(), R.layout.item_addresslist, null);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void setDataAndRefreshUI(AddressBean.AddressList data) {
		mItemAddresslistTvName.setText(data.name);
		mItemAddresslistTvNum.setText(data.phoneNumber);
		mItemAddresslistIvSelected.setSelected(data.isDefault == 1 ? true : false);
		mItemAddresslistTvPosition.setText(data.city + data.addressArea + data.addressDetail);
	}
}
