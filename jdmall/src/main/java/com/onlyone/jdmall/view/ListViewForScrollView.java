package com.onlyone.jdmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.view
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 19:25
 * @描述: 解决了ScrollView嵌套ListView不能滚动的问题的问题
 */
public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        this(context, null);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
