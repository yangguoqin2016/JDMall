package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.AddressBean;
import com.onlyone.jdmall.bean.ProvinceBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.fragment.mine.AddressManagerFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private static final String TAG_ADDRESSMODIFY_FRAGMENT = "tag_addressmodify_fragment";
    private AddressBean.AddressList mAddressList;
    @Bind(R.id.addressmodify_et_contacts)
    EditText  mAddressmodifyEtContacts;
    @Bind(R.id.addressmodify_et_phone)
    EditText  mAddressmodifyEtPhone;
    @Bind(R.id.addressmodify_et_detailaddress)
    EditText  mAddressmodifyEtDetailaddress;
    @Bind(R.id.addressmodify_iv_default)
    ImageView mAddressmodifyIvDefault;
    @Bind(R.id.addressmodify_iv_delete)
    ImageView mAddressmodifyIvDelete;
    @Bind(R.id.addressmodify_et_province)
    TextView  mAddressmodifyTvProvince;

    private ArrayList<ProvinceBean>                 options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>>            options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private MainActivity mMainActivity;
    private TextView     tvTime, tvOptions;
    TimePickerView    pvTime;
    OptionsPickerView pvOptions;
    View              vMasker;


    public AddressModifyFragment(AddressBean.AddressList addressList) {
        mAddressList = addressList;
    }

    @Override
    public void onAttach(Context context) {
        mMainActivity = (MainActivity) context;
        super.onAttach(context);
    }

    @Override
    protected void refreshSuccessView(Object data) {
        Log.d("AddressModifyFragment", "2");
        mAddressmodifyEtContacts.setText(mAddressList.name);
        mAddressmodifyEtPhone.setText(mAddressList.phoneNumber);
        mAddressmodifyEtDetailaddress.setText(mAddressList.addressDetail);
        mAddressmodifyTvProvince.setText(mAddressList.province + mAddressList.city + mAddressList.addressArea);
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

        /*点击监听*/
        mAddressmodifyIvDefault.setOnClickListener(this);
        mAddressmodifyIvDelete.setOnClickListener(this);
        initProvinceSelect(contentView);
        Log.d("AddressModifyFragment", "1");
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
                goBack();
                break;
            case R.id.topbar_tv_save://保存
                saveModify();
                break;
            case R.id.addressmodify_iv_default://设为默认地址
                mAddressList.isDefault = 1;
                setDefault();
                break;
            case R.id.addressmodify_iv_delete://删除地址
                // TODO: 2016/3/9 弹出一个对话框再次确认 
                deleteAddress();
                goBack();

                break;
            default:
                break;
        }
    }

    private void setDefault() {
        RequestQueue queue = NetUtil.getRequestQueue();


        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(ResUtil.getContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ResUtil.getContext(),"设置失败",Toast.LENGTH_SHORT).show();
            }
        };
        String url = Url.ADDRESS_ADDRESSDEFAULT + "?id=" + mAddressList.id;
        StringRequest request = new StringRequest(Request.Method.GET, url, success, error);
        queue.add(request);
    }

    private void deleteAddress() {
        RequestQueue queue = NetUtil.getRequestQueue();


        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(ResUtil.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ResUtil.getContext(),"删除失败",Toast.LENGTH_SHORT).show();
            }
        };
        String url = Url.ADDRESS_ADDRESSDELETE + "?id=" + mAddressList.id;
        StringRequest request = new StringRequest(Request.Method.GET, url, success, error);
        queue.add(request);
    }

    private void saveModify() {

        final String name = mAddressmodifyEtContacts.getText().toString().trim();
        final String phone = mAddressmodifyEtPhone.getText().toString().trim();
        final String province = mAddressmodifyTvProvince.getText().toString().trim();
        final String detailAddr = mAddressmodifyEtDetailaddress.getText().toString().trim();
        /*用户输入校验*/
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(ResUtil.getContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(ResUtil.getContext(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(province)) {
            Toast.makeText(ResUtil.getContext(), "请选择省市", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(detailAddr)) {
            Toast.makeText(ResUtil.getContext(), "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = NetUtil.getRequestQueue();

        Response.Listener success = new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonString) {
                Gson gson = new Gson();
                //                mAddressAddBean = gson.fromJson(jsonString, AddressAddBean.class);
                Log.d("AddressAddFragment", "jsonString==" + jsonString);
                Toast.makeText(ResUtil.getContext(), "保存修改成功", Toast.LENGTH_SHORT).show();

            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ResUtil.getContext(), "修改失败", Toast.LENGTH_SHORT).show();
            }
        };

        //http://localhost:8080/market/addresssave
        String url = Url.ADDRESS_SERVER + "/addresssave";

        StringRequest request = new StringRequest(Request.Method.POST, url, success, error) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id", mAddressList.id + "");
                map.put("name", name);
                map.put("phoneNumber", phone);
                map.put("province", province.substring(0, 2));
                map.put("city", province.substring(2, 4));
                map.put("addressArea", province.substring(4, 6));
                //万家丽路960号
                map.put("addressDetail", detailAddr);
                map.put("zipCode", 231343 + "");
                map.put("isDefault", 1 + "");

                Log.d("AddressAddFragment", "name=" + map.get("name") + "phone=" + map.get("phoneNumber") + "detailAddr=" + map.get("addressDetail"));
                return map;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                SPUtil spUtil = new SPUtil(ResUtil.getContext());
                String userid = spUtil.getLong(SP.USERID, 0) + "";
                headers.put("userid", userid);
                Log.d("MineFavoriteFragment", "---------------" + userid);
                return headers;
            }
        };

        queue.add(request);

        /*跳转到上级界面*/
        SystemClock.sleep(1500);
        AddressManagerFragment fragment = new AddressManagerFragment();
        changeFragment(fragment, TAG_ADDRESSMODIFY_FRAGMENT);
    }

    private void changeFragment(BaseFragment fragment, String tag) {
        HolderFragment parentFragmrnt = (HolderFragment) getParentFragment();
        parentFragmrnt.goForward(fragment);
    }

    private void initProvinceSelect(View view) {

        vMasker = view.findViewById(R.id.vMasker);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvOptions = (TextView) view.findViewById(R.id.addressmodify_et_province);

        //时间选择器
        //        pvTime = new TimePickerView(mMainActivity, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        //        pvTime = new TimePickerView(ResUtil.getContext(), TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        //        Calendar calendar = Calendar.getInstance();
        //        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvTime.setText(getTime(date));
            }
        });
        //弹出时间选择器
        tvTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });

        //选项选择器
        pvOptions = new OptionsPickerView(getActivity());

        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "广东省，以岭南东道、广南东路得名", "其他数据"));
        options1Items.add(new ProvinceBean(1, "湖南", "湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南", "芒果TV"));
        options1Items.add(new ProvinceBean(3, "广西", "嗯～～", ""));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<String>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<String>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        ArrayList<String> options2Items_03 = new ArrayList<String>();
        options2Items_03.add("桂林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_02 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_03 = new ArrayList<ArrayList<String>>();
        ArrayList<String> options3Items_01_01 = new ArrayList<String>();
        options3Items_01_01.add("白云");
        options3Items_01_01.add("天河");
        options3Items_01_01.add("海珠");
        options3Items_01_01.add("越秀");
        options3Items_01.add(options3Items_01_01);
        ArrayList<String> options3Items_01_02 = new ArrayList<String>();
        options3Items_01_02.add("南海");
        options3Items_01_02.add("高明");
        options3Items_01_02.add("顺德");
        options3Items_01_02.add("禅城");
        options3Items_01.add(options3Items_01_02);
        ArrayList<String> options3Items_01_03 = new ArrayList<String>();
        options3Items_01_03.add("其他");
        options3Items_01_03.add("常平");
        options3Items_01_03.add("虎门");
        options3Items_01.add(options3Items_01_03);
        ArrayList<String> options3Items_01_04 = new ArrayList<String>();
        options3Items_01_04.add("其他1");
        options3Items_01_04.add("其他2");
        options3Items_01_04.add("其他3");
        options3Items_01.add(options3Items_01_04);
        ArrayList<String> options3Items_01_05 = new ArrayList<String>();
        options3Items_01_05.add("其他1");
        options3Items_01_05.add("其他2");
        options3Items_01_05.add("其他3");
        options3Items_01.add(options3Items_01_05);

        ArrayList<String> options3Items_02_01 = new ArrayList<String>();
        options3Items_02_01.add("雨花");
        options3Items_02_01.add("芙蓉");
        options3Items_02_01.add("天心");
        options3Items_02_01.add("开福");
        options3Items_02_01.add("岳麓");
        options3Items_02_01.add("长沙6");
        options3Items_02_01.add("长沙7");
        options3Items_02_01.add("长沙8");
        options3Items_02.add(options3Items_02_01);
        ArrayList<String> options3Items_02_02 = new ArrayList<String>();
        options3Items_02_02.add("岳1");
        options3Items_02_02.add("岳2");
        options3Items_02_02.add("岳3");
        options3Items_02_02.add("岳4");
        options3Items_02_02.add("岳5");
        options3Items_02_02.add("岳6");
        options3Items_02_02.add("岳7");
        options3Items_02_02.add("岳8");
        options3Items_02_02.add("岳9");
        options3Items_02.add(options3Items_02_02);
        ArrayList<String> options3Items_03_01 = new ArrayList<String>();
        options3Items_03_01.add("好山水");
        options3Items_03.add(options3Items_03_01);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        //        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tvOptions.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });
        //点击弹出选项选择器
        tvOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
