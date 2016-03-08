package com.onlyone.jdmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.view
 * @创建者: wt
 * @创建时间: 2016/3/8 19:02
 * @描述: ${TODO}
 */
public class ExpandGridView extends GridView {
    public ExpandGridView(Context context) {
        super(context);
    }

    public ExpandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
