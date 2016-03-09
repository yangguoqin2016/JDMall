package com.onlyone.jdmall.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.onlyone.jdmall.R;


/**
 * Created by computer on 2016/3/1.
 */
public class RatioLayout extends FrameLayout {
    private float mPicRatio = 1.0f;
    public static final int RELATIVE_WIDTH = 0;//已知宽度,动态的计算高度
    public static final int RELATIVE_HEIGHT = 1;//已知高度,动态的计算宽度
    public int mCurState = RELATIVE_WIDTH;

    public void setCurState(int curState) {
        mCurState = curState;
    }

    public void setRatio(float ratio) {
        mPicRatio = ratio;
    }

    public float getPicRatio() {
        return mPicRatio;
    }

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //取出自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mCurState = typedArray.getInt(R.styleable.RatioLayout_rlRelative, RELATIVE_WIDTH);
        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_rlPicRatio, 1.0f);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度和高度的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && mCurState == RELATIVE_WIDTH) {
            //获取自身的高度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            //图片不想变形，就要 ：RatioLayout自身宽度/RatioLayout自身的高度=图片的宽高比 计算自身的应有的高度
            int selfHeight = (int) (selfWidth / mPicRatio + .5f);

            //使其强制贴合自己,避免有些事，子view使用wrap_content的属性或者100dp等具体值，导致失效
            measureChildrenInThisClass(selfWidth, selfHeight);

            //保存测量的结果
            setMeasuredDimension(selfWidth, selfHeight);
        } else if (heightMode == MeasureSpec.EXACTLY && mCurState == RELATIVE_WIDTH) {
            //得到自身的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
            //根据公式 RatioLayout自身宽度/RatioLayout自身的高度=图片的宽高比 计算自身的应有的宽度
            int selfWidth = (int) (selfHeight * mPicRatio + .5f);

            //使其强制贴合自己,避免有些事，子view使用wrap_content的属性或者100dp等具体值，导致失效
            measureChildrenInThisClass(selfWidth, selfHeight);

            //保存测绘的结果
            setMeasuredDimension(selfWidth, selfHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void measureChildrenInThisClass(int selfWidth, int selfHeight) {
        //得到孩子应有的宽度
        int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
        //得到孩子应有的高度
        int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        //测量孩子
        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
    }
}
