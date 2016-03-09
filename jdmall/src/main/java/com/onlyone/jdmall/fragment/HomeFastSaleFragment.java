package com.onlyone.jdmall.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.activity.ProductDetailActivity;
import com.onlyone.jdmall.bean.HomeFastSaleBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.view.ListViewForScrollView;
import com.onlyone.jdmall.view.RatioLayout;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: yangguoqin
 * @创建时间: 2016/3/5 11:36
 * @描述: 这是主页点击促销快报后跳转的, 促销快报页面
 */
public class HomeFastSaleFragment extends SuperBaseFragment<HomeFastSaleBean> implements View.OnClickListener {
    @Bind(R.id.fastsale_gird_r1_left)
    ImageView      mFastsaleGridR1Left;
    @Bind(R.id.fastsale_gird_r1_right)
    ImageView      mFastsaleGridR1Right;
    @Bind(R.id.fastsale_gird_r1)
    RelativeLayout mFastsaleGridR1;
    @Bind(R.id.fastsale_gird_r2_left)
    ImageView      mFastsaleGridR2Left;
    @Bind(R.id.fastsale_gird_r2_right)
    ImageView      mFastsaleGridR2Right;
    @Bind(R.id.fastsale_gird_r2)
    RelativeLayout mFastsaleGridR2;
    @Bind(R.id.fastsale_gird_r3_left)
    ImageView      mFastsaleGridR3Left;
    @Bind(R.id.fastsale_gird_r3_right)
    ImageView      mFastsaleGridR3Right;
    @Bind(R.id.fastsale_gird_r3)
    RelativeLayout mFastsaleGridR3;
    @Bind(R.id.fastsale_gird_r4_left)
    ImageView      mFastsaleGridR4Left;
    @Bind(R.id.fastsale_gird_r4_right)
    ImageView      mFastsaleGridR4Right;
    @Bind(R.id.fastsale_gird_r4)
    RelativeLayout mFastsaleGridR4;
    @Bind(R.id.fastsale_scrollview)
    ScrollView     mFastsaleScrollview;

    @Bind(R.id.fastsale_gird_r1_left_container)
    RelativeLayout mFastsaleGridR1LeftContainer;
    @Bind(R.id.fastsale_gird_r1_right_container)
    RelativeLayout mFastsaleGridR1RightContainer;
    @Bind(R.id.fastsale_gird_r2_left_container)
    RelativeLayout mFastsaleGridR2LeftContainer;
    @Bind(R.id.fastsale_gird_r2_right_container)
    RelativeLayout mFastsaleGridR2RightContainer;
    @Bind(R.id.fastsale_gird_r3_left_container)
    RelativeLayout mFastsaleGridR3LeftContainer;
    @Bind(R.id.fastsale_gird_r3_right_container)
    RelativeLayout mFastsaleGridR3RightContainer;
    @Bind(R.id.fastsale_gird_r4_left_container)
    RelativeLayout mFastsaleGridR4LeftContainer;
    @Bind(R.id.fastsale_gird_r4_right_container)
    RelativeLayout mFastsaleGridR4RightContainer;


    private View                                         mTopBarView;
    private MainActivity                                 mActivity;
    private ImageView                                    mTypeIv;
    private ListViewForScrollView                        mFastsale_listView;
    private List<HomeFastSaleBean.HomeFastSaleTopicBean> mDataList;
    private LinearLayout mFastsale_gridView;
    private boolean isShowList = false;//标记当前显示的状态

    @Override
    protected void refreshSuccessView(HomeFastSaleBean data) {
        //拿加载成功返回的数据刷新页面

        //使用Picasso添加了两个地址,第一次使用的时候会访问网络,匹配后以后则不会访问网络
        //第一行两个
        System.out.println("地址:"+ Url.ADDRESS_SERVER +data.topic.get(0).pic);
        System.out.println("地址:" + Url.ADDRESS_SERVER + data.topic.get(1).pic);
        System.out.println("地址:" + Url.ADDRESS_SERVER + data.topic.get(2).pic);
        System.out.println("地址:" + Url.ADDRESS_SERVER + data.topic.get(3).pic);
        System.out.println("地址:" + Url.ADDRESS_SERVER + data.topic.get(4).pic);
        System.out.println("地址:" + Url.ADDRESS_SERVER + data.topic.get(5).pic);
        System.out.println("地址:" + Url.ADDRESS_SERVER + data.topic.get(6).pic);
        System.out.println("地址:" + Url.ADDRESS_SERVER + data.topic.get(7).pic);




        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(0).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR1Left);
        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(1).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR1Right);

        //第二行两个
        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(2).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR2Left);
        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(3).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR2Right);

        //第三行两个
        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(4).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR3Left);
        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(5).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR3Right);

        //第四行两个
        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(6).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR4Left);
        Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(7).pic)
                .transform(new CropCircleTransformation()).into(mFastsaleGridR4Right);

        mDataList = data.topic;
        mFastsale_listView.setAdapter(new PicAdapter());

    }

    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(getContext(), R.layout.fastsale_fragement, null);
        //获取到list显示风格的控件
        mFastsale_listView = (ListViewForScrollView) rootView.findViewById(R.id.fastsale_listview);
        mFastsale_gridView = (LinearLayout) rootView.findViewById(R.id.fastsale_gridview);

        ButterKnife.bind(this, rootView);
        initListener();
        return rootView;
    }

    /**
     * 对控件进行点击监听
     */
    private void initListener() {
        mFastsaleGridR1Left.setOnClickListener(this);
        mFastsaleGridR1Right.setOnClickListener(this);
        mFastsaleGridR2Left.setOnClickListener(this);
        mFastsaleGridR2Right.setOnClickListener(this);

        mFastsaleGridR3Left.setOnClickListener(this);
        mFastsaleGridR3Right.setOnClickListener(this);
        mFastsaleGridR4Left.setOnClickListener(this);
        mFastsaleGridR4Right.setOnClickListener(this);
    }

    @Override
    protected void handleError(Exception e) {
        //处理加载数据失败的异常
        Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected String getUrl() {
        String url = Url.ADDRESS_SERVER + "/topic?page=1&pageNum=10";
        return url;
    }

    @Override
    protected HomeFastSaleBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        HomeFastSaleBean bean = gson.fromJson(jsonStr, HomeFastSaleBean.class);
        return bean;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity = (MainActivity) getActivity();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_fastsale, null);
        //每次可见的时候就对列表形式进行监听
        //拿到图标的控件
        mTypeIv = (ImageView) mTopBarView.findViewById(R.id.fastsale_type_iv);
        mTypeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //当组图按钮点击时的回调
                Toast.makeText(ResUtil.getContext(), "点击了图标", Toast.LENGTH_SHORT)
                        .show();

                //如果当前显示的是listView，就显示gridview。否则相反
                mFastsale_listView.setVisibility(isShowList
                        ? View.GONE
                        : View.VISIBLE);
                mFastsale_gridView.setVisibility(isShowList
                        ? View.VISIBLE
                        : View.GONE);

                //改变图标
                mTypeIv.setImageResource(isShowList
                        ? R.drawable.icon_pic_grid_type
                        : R.drawable.icon_pic_list_type);

                //该变状态
                isShowList = !isShowList;
            }
        });



        mActivity.setTopBarView(mTopBarView);
        //点击返回图片返回首页
        mTopBarView.findViewById(R.id.hot_product_topbar_back).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((HolderFragment) getParentFragment()).goBack();
                    }
                });
    }


    private class PicAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if ( mDataList!= null) {
                return mDataList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mDataList != null) {
                return mDataList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ResUtil.getContext(), R.layout.fastsale_listview_item_pic, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.ivPic = (ImageView) convertView.findViewById(R.id.item_pic_iv_pic);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.item_pic_tv_title);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //设置数据
            HomeFastSaleBean.HomeFastSaleTopicBean bean = mDataList.get(position);

            String url = Url.ADDRESS_SERVER + bean.pic;

            Picasso.with(ResUtil.getContext())
                    .load(url)
                    .into(holder.ivPic);
            holder.tvTitle.setText(bean.name);

            //处理图片变形
            RatioLayout rl = new RatioLayout(ResUtil.getContext());
            rl.setCurState(RatioLayout.RELATIVE_WIDTH);
            float ratio = 224 / 340f;
            rl.setRatio(ratio);
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);


            return convertView;
        }
    }

    private class ViewHolder {
        ImageView ivPic;
        TextView  tvTitle;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {




        switch (v.getId()) {

            default:
                Random random = new Random();
                int id = random.nextInt(9);
                System.out.println("点击了");
                Intent intent = new Intent(ResUtil.getContext(), ProductDetailActivity.class);
                intent.putExtra("id", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ResUtil.getContext().startActivity(intent);
                break;
        }

    }


}
