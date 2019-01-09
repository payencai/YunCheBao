package com.payencai.library.list.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * 作者：凌涛 on 2019/1/2 10:56
 * 邮箱：771548229@qq..com
 */
public class PersonalListView extends ListView {

    public PersonalListView(Context context) {
        super(context);
    }

    public PersonalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonalListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
//
//作者：布吉岛原住民
//        链接：https://www.jianshu.com/p/e4e2e5870f00
//        來源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。