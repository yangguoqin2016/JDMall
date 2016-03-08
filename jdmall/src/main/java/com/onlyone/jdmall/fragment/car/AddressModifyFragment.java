package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.car
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 12:24
 * @描述: 修改地址的Fragment
 */
public class AddressModifyFragment extends BaseFragment<Object> implements View.OnClickListener {
    @Bind(R.id.addressmodify_et_contacts)
    EditText  mAddressmodifyEtContacts;
    @Bind(R.id.addressmodify_et_phone)
    EditText  mAddressmodifyEtPhone;
    @Bind(R.id.addressmodify_et_province)
    TextView  mAddressmodifyEtProvince;
    @Bind(R.id.addressmodify_et_detailaddress)
    EditText  mAddressmodifyEtDetailaddress;
    @Bind(R.id.addressmodify_iv_default)
    ImageView mAddressmodifyIvDefault;
    @Bind(R.id.addressmodify_iv_delete)
    ImageView mAddressmodifyIvDelete;
    private MainActivity mMainActivity;

    @Override
    public void onAttach(Context context) {
        mMainActivity = (MainActivity) context;
        super.onAttach(context);
    }

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    public void onResume() {
        //设置TopBar
        View topBar = View.inflate(mMainActivity, R.layout.inflate_topbar_addressmodify, null);
        mMainActivity.setTopBarView(topBar);
        TextView tvBack = (TextView) topBar.findViewById(R.id.topbar_tv_back);
        TextView tvSave = (TextView) topBar.findViewById(R.id.topbar_tv_save);
        tvBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        super.onResume();
    }

    @Override
    protected View loadSuccessView() {
        //设置中心内容
        View contentView = View.inflate(mMainActivity, R.layout.inflate_addressmodify, null);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_tv_back://返回

                break;
            case R.id.topbar_tv_save://保存

                break;

            default:
                break;
        }
    }
}
