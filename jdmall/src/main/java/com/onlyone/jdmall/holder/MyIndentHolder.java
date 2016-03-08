package com.onlyone.jdmall.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.IndentMesListBean;
import com.onlyone.jdmall.utils.ResUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.holder
 * @创建者: Administrator
 * @创建时间: 2016/3/8 15:59
 * @描述: ${TODO}
 */
public class MyIndentHolder extends BaseHolder<IndentMesListBean> {
    @Bind(R.id.myindent_indentnum)
    TextView  mMyindentIndentnum;
    @Bind(R.id.myindent_price)
    TextView  mMyindentPrice;
    @Bind(R.id.myindent_state)
    TextView  mMyindentState;
    @Bind(R.id.myindent_message)
    ImageView mMyindentMessage;
    @Bind(R.id.myindent_time)
    TextView  mMyindentTime;

    @Override
    public View initHolderView() {
        View itemMyIndentView = View.inflate(ResUtil.getContext(), R.layout.item_myindent, null);
        ButterKnife.bind(this,itemMyIndentView);
        return itemMyIndentView;
    }

    @Override
    public void setDataAndRefreshUI(IndentMesListBean data) {

        mMyindentIndentnum.setText("4001018"+data.orderId);//订单编码
        mMyindentPrice.setText(""+data.price);//价格
        mMyindentState.setText(data.status);//订单处理的状态
        long time = data.time;//生成的订单的时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
        Date date = new Date(time);
        String curTime = simpleDateFormat.format(date);
        mMyindentTime.setText(curTime);
        //点击进入订单详情界面
        mMyindentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
