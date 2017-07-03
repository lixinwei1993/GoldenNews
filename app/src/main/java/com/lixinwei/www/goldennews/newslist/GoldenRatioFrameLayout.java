package com.lixinwei.www.goldennews.newslist;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Miroslaw Stanek on 26.12.14.
 */
public class GoldenRatioFrameLayout extends FrameLayout {
    public GoldenRatioFrameLayout(Context context) {
        super(context);
    }

    public GoldenRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoldenRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GoldenRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, (int)0.618*widthMeasureSpec);
    }
}
