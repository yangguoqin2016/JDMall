package com.onlyone.jdmall.holder;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.LimitBuyBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.holder
 * @创建者: wt
 * @创建时间: 2016/3/6 15:16
 * @描述: ${TODO}
 */
public class LimitBuyHolder extends BaseHolder<LimitBuyBean.LimitBuyItemBean> implements View.OnClickListener {
    @Bind(R.id.limit_item_iv)
    ImageView mLimitItemIv;
    @Bind(R.id.limit_item_tv_name)
    TextView mLimitItemTvName;
    @Bind(R.id.limit_item_tv_lp)
    TextView mLimitItemTvLp;
    @Bind(R.id.limit_item_tv_p)
    TextView mLimitItemTvP;
    @Bind(R.id.limit_item_pb)
    ProgressBar mLimitItemPb;
    @Bind(R.id.limit_item_btn)
    Button mLimitItemBtn;
    @Bind(R.id.limit_item_tv_saled)
    TextView mLimitItemTvSaled;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(ResUtil.getContext(), R.layout.limit_buy_item, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void setDataAndRefreshUI(LimitBuyBean.LimitBuyItemBean data) {
        //设置图片
        String url = Url.ADDRESS_SERVER + data.pic;
        Picasso.with(ResUtil.getContext()).load(url).into(mLimitItemIv);

        //物品名
        mLimitItemTvName.setText(data.name);
        //限时价格
        mLimitItemTvLp.setText("￥" + data.limitPrice);
        //会员价格
        mLimitItemTvP.setText("￥" + data.price);
        //画横线
        mLimitItemTvP.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //按键
        mLimitItemBtn.setOnClickListener(this);
        //TODO:自定progressBar，实现文字显示在进度条上
        Random random = new Random();
        int progress = random.nextInt(100);
        mLimitItemPb.setProgress(progress);
        mLimitItemTvSaled.setText("已抢购"+progress+"件");

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(ResUtil.getContext(), "点击了", Toast.LENGTH_SHORT).show();
    }
}
