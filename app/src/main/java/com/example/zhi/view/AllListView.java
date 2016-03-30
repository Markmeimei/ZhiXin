package com.example.zhi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * 禁止ListView滑动，全部显示
 *
 * Author: Eron
 * Date: 2016/3/30
 * Time: 22:26
 */
public class AllListView extends ListView {
    public AllListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int mExpandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
