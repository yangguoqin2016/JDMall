package com.onlyone.jdmall.fragment.category;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.HomeCategoryBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.SuperBaseFragment;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: yangguoqin
 * @创建时间: 2016/3/5 18:31
 * @描述: ${TODO}
 */
public class HomeCategoryFragment extends SuperBaseFragment<HomeCategoryBean> implements View.OnClickListener {

    @Bind(R.id.category_first_pager_first_img)
    ImageView mCategoryFirstPagerFirstImg;
    @Bind(R.id.category_first_pager_first_text)
    TextView  mCategoryFirstPagerFirstText;
    @Bind(R.id.category_first_pager_second_img)
    ImageView mCategoryFirstPagerSecondImg;
    @Bind(R.id.category_first_pager_second_text)
    TextView  mCategoryFirstPagerSecondText;
    @Bind(R.id.category_first_pager_third_img)
    ImageView mCategoryFirstPagerThirdImg;
    @Bind(R.id.category_first_pager_thrid_text)
    TextView  mCategoryFirstPagerThridText;
    @Bind(R.id.category_first_pager_four_img)
    ImageView mCategoryFirstPagerFourImg;
    @Bind(R.id.category_first_pager_four_text)
    TextView  mCategoryFirstPagerFourText;
    @Bind(R.id.category_first_pager_five_img)
    ImageView mCategoryFirstPagerFiveImg;
    @Bind(R.id.category_first_pager_five_text)
    TextView  mCategoryFirstPagerFiveText;
    @Bind(R.id.category_first_pager_six_img)
    ImageView mCategoryFirstPagerSixImg;
    @Bind(R.id.category_first_pager_six_text)
    TextView  mCategoryFirstPagerSixText;

    //访问的地址
    String baseUrl = "http://188.188.5.72:8080/market";
    @Bind(R.id.category_first_pager_title_text)
    TextView     mCategoryFirstPagerTitleText;
    @Bind(R.id.category_first_pager_first_container)
    LinearLayout mCategoryFirstPagerFirstContainer;
    @Bind(R.id.category_first_pager_second_container)
    LinearLayout mCategoryFirstPagerSecondContainer;
    @Bind(R.id.category_first_pager_third_container)
    LinearLayout mCategoryFirstPagerThirdContainer;
    @Bind(R.id.category_first_pager_four_container)
    LinearLayout mCategoryFirstPagerFourContainer;
    @Bind(R.id.category_first_pager_five_container)
    LinearLayout mCategoryFirstPagerFiveContainer;
    @Bind(R.id.category_first_pager_six_container)
    LinearLayout mCategoryFirstPagerSixContainer;

    private List<HomeCategoryBean.HomeCategoryInfoBean>                                                           mDatasList;
    private Map<Integer, Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>> mSuperDatas;

    public static Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>               mFirstDatas;
    public Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>               mSecondDatas;
    public Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>               mThirdDatas;
    public Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>               mFourDatas;
    public Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>               mFiveDatas;
    public Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>               mSixDatas;
    public List<Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>>         mListData;
    public MainActivity mMainActivity;
    private View                             mTopBarView;
    private MainActivity                     mActivity;

    @Override
    protected void refreshSuccessView(HomeCategoryBean data) {
        //拿多一份数据去进行分类处理
        this.mDatasList = data.category;

        //子线程中去计算
        new Thread() {
            @Override
            public void run() {
                classifyDatas();
                distributeDatas();
            }
        }.start();

        //数据的前六条就是商品分类的根分类节点
        System.out.println("获取第一个分类节点的访问地址:" + baseUrl + data.category.get(0).pic);

        //注意最大id为59,但对应的父ID超过153,则只能根据已有的id去设置到界面
        //妈妈专区
        Picasso.with(getContext()).load(baseUrl + data.category.get(1).pic).centerCrop().fit().into(mCategoryFirstPagerFirstImg);
        //时尚女装
        Picasso.with(getContext()).load(baseUrl + data.category.get(9).pic).centerCrop().fit().into(mCategoryFirstPagerSecondImg);
        //宝宝用品
        Picasso.with(getContext()).load(baseUrl + data.category.get(23).pic).centerCrop().fit().into(mCategoryFirstPagerThirdImg);
        //日常用品
        Picasso.with(getContext()).load(baseUrl + data.category.get(33).pic).centerCrop().fit().into(mCategoryFirstPagerFourImg);
        //儿童服饰
        Picasso.with(getContext()).load(baseUrl + data.category.get(55).pic).centerCrop().fit().into(mCategoryFirstPagerFiveImg);
        //儿童玩具
        Picasso.with(getContext()).load(baseUrl + data.category.get(26).pic).centerCrop().fit().into(mCategoryFirstPagerSixImg);


    }

    /**
     * 子线程中当执行完数据分类后,再次对数据进行分到多个子类中
     */
    private void distributeDatas() {

        initSonDataMap();

        Set<Integer> keySet = mSuperDatas.keySet();
       for(Integer key:keySet){
           Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>
                   map = mSuperDatas.get(key);
           //根据存储了要分配给子类数据的集合,当前遍历到的数据给了子类
           Map sonDataMap = mListData.get(key-1);
           sonDataMap = map;
       }

    }

    private void initSonDataMap() {
        mFirstDatas = new HashMap<>();
        mSecondDatas = new HashMap<>();
        mThirdDatas = new HashMap<>();
        mFourDatas = new HashMap<>();
        mFiveDatas = new HashMap<>();
        mSixDatas = new HashMap<>();

        //分配六个大类数据,存储到集合中去
        mListData = new ArrayList();

        mListData.add(mFirstDatas);
        mListData.add(mSecondDatas);
        mListData.add(mThirdDatas);
        mListData.add(mFourDatas);
        mListData.add(mFiveDatas);
        mListData.add(mSixDatas);
    }

    /**
     * 对分类数据进行处理
     * 按照此计算方法计算后,会出现其中一个第33,即元素的属性中的id=129,少了这个,因为其中的isLeafNode属性和别人不同
     */
    private void classifyDatas() {

        Map<Integer,List<HomeCategoryBean.HomeCategoryInfoBean>> mapDatas = new HashMap<>();
        int id = 0;
        //遍历大数据集合
        for(int i = 0; i < mDatasList.size(); i++){
            //存储根节点的id
            id = mDatasList.get(i).id;
            //如果元素的父id是0则是根节点
            if( mDatasList.get(i).parentId == 0){
                //创建一个集合先存储第二层的元素在集合中的位置
                List<HomeCategoryBean.HomeCategoryInfoBean> list = new ArrayList<>();

                //遍历大数据集合
                for(int j = 0; j < mDatasList.size(); j++){
                    //如果isLeafNode为false,父id不为0,表示不是最终节点,且不是根节点
                    if(mDatasList.get(j).isLeafNode == false && mDatasList.get(j).parentId!=0){
                        //当上一层的id为本层元素的父id
                        if(id == mDatasList.get(j).parentId){
                            //得到此元素的属性中的id值
                            HomeCategoryBean.HomeCategoryInfoBean homeCategoryInfoBean = mDatasList.get(j);
                            if(!list.contains(homeCategoryInfoBean)){
                                //如果不包含这个下标则添加进去
                                list.add(homeCategoryInfoBean);
                            }
                        }
                    }
                }
                //当在根id知道的情况下,过滤出第二层的id,添加到list后,
                //存储了第一层根节点的id,和 这个id对应的第二层的子节点形成的集合
                mapDatas.put(id,list);
            }
        }
        //经过两个循环后得到 map<每个根节点的id,这个id对应的子id形成的集合>
        Set<Map.Entry<Integer, List<HomeCategoryBean.HomeCategoryInfoBean>>> entrySet = mapDatas.entrySet();
        Iterator<Map.Entry<Integer, List<HomeCategoryBean.HomeCategoryInfoBean>>> iterator = entrySet.iterator();

        //设置一个最终存储所有关系数据的map,除了根节点外其他都存储实体类
        mSuperDatas = new HashMap<>();

        while(iterator.hasNext()){
            Map.Entry<Integer, List<HomeCategoryBean.HomeCategoryInfoBean>> next = iterator.next();
            //得到每个根节点的属性中的id值 和 对应的第二层的节点的属性中的id值
            Integer key = next.getKey();
            //第二层节点的分类之一,这个集合包含父id为key的元素的id值
            List<HomeCategoryBean.HomeCategoryInfoBean> value = next.getValue();

            List<HomeCategoryBean.HomeCategoryInfoBean> list = null;

            Map<HomeCategoryBean.HomeCategoryInfoBean,List<HomeCategoryBean.HomeCategoryInfoBean>> map = new HashMap<>();
            //遍历存储第二层节点的集合
            for(HomeCategoryBean.HomeCategoryInfoBean info:value){
               list = new ArrayList();
                //遍历大数据集合
                for(int i=0;i<mDatasList.size();i++){
                    int parentId = mDatasList.get(i).parentId;
                    //如果元素的父id被这个第二层节点包含,则表明这个元素是这个第二层节点的子节点
                    if(info.id == parentId){
                        //将元素的属性中的id添加到集合
                        list.add(mDatasList.get(i));
                    }
                }
                //存储了第二层每个节点的id,和这个id对应的第三层的子节点形成的集合
                map.put(info,list);
            }
            //最终数据,存的都是元素实体类
            mSuperDatas.put(key, map);
        }

    }

    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(getContext(), R.layout.category_fragment, null);
        //获取到mainActivity,方便后面的点击跳转
        mMainActivity = (MainActivity) getActivity();
        //小刀绑定
        ButterKnife.bind(this, rootView);
        //开启对条目的点击监听
        initListener();

        return rootView;
    }

    private void initListener() {
        //对六个根分类条目进行点击监听
        mCategoryFirstPagerFirstContainer.setOnClickListener(this);
        mCategoryFirstPagerSecondContainer.setOnClickListener(this);
        mCategoryFirstPagerThirdContainer.setOnClickListener(this);
        mCategoryFirstPagerFourContainer.setOnClickListener(this);
        mCategoryFirstPagerFiveContainer.setOnClickListener(this);
        mCategoryFirstPagerSixContainer.setOnClickListener(this);
    }

    @Override
    protected String getUrl() {
        String url = Url.ADDRESS_SERVER + "/category";
        return url;
    }

    @Override
    protected void handleError(Exception e) {
        Toast.makeText(getContext(), "加载分类数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected HomeCategoryBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        HomeCategoryBean bean = gson.fromJson(jsonStr, HomeCategoryBean.class);
        return bean;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity = (MainActivity) getActivity();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_category, null);
        mActivity.setTopBarView(mTopBarView);
        //点击返回图片返回首页
        mTopBarView.findViewById(R.id.hot_product_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = mActivity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(HomeCategoryFragment.this);
                transaction.commit();

                restoreHomeTopBar();
            }
        });

        //点击back按键回退首页
        mActivity.addOnBackPreseedListener(new MainActivity.OnBackPressedListener() {
            @Override
            public void onPressed() {
                restoreHomeTopBar();
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

    @Override
    public void onClick(View v) {
        FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.category_first_pager_first_container:
                //跳转到 妈妈专区
                transaction.add(R.id.fl_content_container,new HomeCategoryPagerFirstFragment());

                break;
            case R.id.category_first_pager_second_container:
                //跳转到 时尚女装

                break;
            case R.id.category_first_pager_third_container:
                //跳转到 宝宝用品

                break;
            case R.id.category_first_pager_four_container:
                //跳转到 日常用品

                break;
            case R.id.category_first_pager_five_container:
                //跳转到 儿童服饰

                break;
            case R.id.category_first_pager_six_container:
                //跳转到 儿童玩具

                break;
            default:
                break;
        }
        transaction.commit();
    }
}
