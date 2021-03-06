package com.onlyone.jdmall.fragment.category;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.activity.ProductDetailActivity;
import com.onlyone.jdmall.adapter.MyBaseAdapter;
import com.onlyone.jdmall.bean.HomeCategoryBean;
import com.onlyone.jdmall.bean.ItemBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.category
 * @创建者: yangguoqin
 * @创建时间: 2016/3/7 10:09
 * @描述: ${TODO}
 */
public class HomeCategoryPagerSecondFragment extends BaseFragment<ItemBean> implements AdapterView.OnItemClickListener {
    @Bind(R.id.category_common_pager_tv)
    TextView mCategoryCommonPagerTv;
    @Bind(R.id.category_common_pager_gv)
    GridView mCategoryCommonPagerGv;
    private List<HomeCategoryBean.HomeCategoryInfoBean> mListDatas;
    private MainActivity                                mActivity;
    private View                                        mTopBarView;
    private MainActivity                                mMainactivity;

    @Override
    protected void refreshSuccessView(ItemBean data) {
        mCategoryCommonPagerTv.setText("时尚女装");

        //从分类的根页面获取到数据
        mListDatas = HomeCategoryFragment.mSecondList;
        mCategoryCommonPagerGv.setAdapter(new CategoryFirstAdapter(mListDatas));
        mCategoryCommonPagerGv.setOnItemClickListener(this);
        System.out.println("数据有多少个:" + mListDatas.size());

    }

    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(getContext(), R.layout.category_fragment_pager_first, null);
        ButterKnife.bind(this, rootView);
        mMainactivity = (MainActivity) getActivity();
        return rootView;
    }

    @Override
    protected void loadData(LoadListener<ItemBean> listener) {
        listener.onSuccess(null);
    }


    @Override
    protected void handleError(Exception e) {
        Toast.makeText(getContext(), "分类第二个页面出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity = (MainActivity) getActivity();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_category_pager, null);
        mActivity.setTopBarView(mTopBarView);
        //点击返回图片返回首页
        mTopBarView.findViewById(R.id.hot_product_topbar_back).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						((HolderFragment)getParentFragment()).goBack();
					}
				});
    }

    /**
     * back键回退到首页,恢复首页的TopBar
     */
    private void restoreHomeTopBar() {
        View titlBar = View.inflate(ResUtil.getContext(), R.layout.home_title, null);
        mActivity.setTopBarView(titlBar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }



    //创建GridView的适配器
    class CategoryFirstAdapter extends MyBaseAdapter<HomeCategoryBean.HomeCategoryInfoBean> {

        public CategoryFirstAdapter(List<HomeCategoryBean.HomeCategoryInfoBean> datas) {
            super(datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           /* ImageView imageView;
            System.out.println("正在设置界面");
            if(convertView==null){
                imageView = new ImageView(getContext());
                imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
                //imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10,10,10,10);
            }else{
                imageView = (ImageView) convertView;
            }

            Picasso.with(getContext()).load(Url.ADDRESS_SERVER + mListDatas.get(position).pic)
                    .fit().into(imageView);*/

            Holder holder = null;
            if(convertView == null){
                holder = new Holder();
                convertView = View.inflate(getContext(), R.layout.category_item, null);

                holder.iv = (ImageView) convertView.findViewById(R.id.category_item_img);
                holder.tv = (TextView) convertView.findViewById(R.id.category_item_tv);

                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }

            holder.tv.setText(mListDatas.get(position).name);
            Picasso.with(getContext()).load(Url.ADDRESS_SERVER + mListDatas.get(position).pic)
                    .fit().centerInside().into(holder.iv);
            return convertView;
        }
    }

    class Holder{
        TextView tv;
        ImageView iv;
    }


    private void getwindowsData(){

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;

    }

    /**
     * 监听点击条目去跳转详情页面
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //TODO:跳转详情页面

        Intent intent = new Intent(ResUtil.getContext(), ProductDetailActivity.class);

        intent.putExtra("id",2);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ResUtil.getContext().startActivity(intent);

    }

}
