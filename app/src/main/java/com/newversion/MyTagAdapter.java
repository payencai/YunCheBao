package com.newversion;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：凌涛 on 2019/1/29 14:30
 * 邮箱：771548229@qq..com
 */
public class MyTagAdapter extends BaseQuickAdapter<NewTag,BaseViewHolder> {
    public MyTagAdapter(int layoutResId, @Nullable List<NewTag> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewTag item) {
        helper.addOnClickListener(R.id.btnDelete);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_user=helper.getView(R.id.tv_user);
        tv_name.setText(item.getName()+"("+item.getList().size()+")");
        String value="";
        for (int i = 0; i <item.getList().size() ; i++) {
            value=item.getList().get(i).getName()+","+value;
        }
        tv_user.setText(value);
    }

}
