package com.newversion;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.mytag.CreateTagsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/29 14:30
 * 邮箱：771548229@qq..com
 */
public class MyTagAdapter extends BaseQuickAdapter<NewTag,BaseViewHolder> {
    private boolean fromTagOrCircle;

    public MyTagAdapter(int layoutResId, @Nullable List<NewTag> data,boolean fromTagOrCircle ) {
        super(layoutResId, data);
        this.fromTagOrCircle = fromTagOrCircle;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewTag item) {
        helper.addOnClickListener(R.id.btnDelete).addOnClickListener(R.id.ll_all_look);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_user=helper.getView(R.id.tv_user);
        tv_name.setText(item.getName()+"("+item.getList().size()+")");
        String value="";

        ArrayList<String> values = new ArrayList();

        for (int i = 0; i <item.getList().size() ; i++) {
            values.add(item.getList().get(i).getName());
        }

        if(values.size()>0){
            value = listToString(values);
        }

        tv_user.setText(value);

        ImageView iv_select_all_lock=helper.getView(R.id.iv_select_all_lock);
        Button btnDelete=helper.getView(R.id.btnDelete);


        if(fromTagOrCircle){
            btnDelete.setVisibility(View.VISIBLE);

            iv_select_all_lock.setVisibility(View.GONE);
        }else {
            btnDelete.setVisibility(View.GONE);

            if(item.isChecked()){
                iv_select_all_lock.setVisibility(View.VISIBLE);
            }else {
                iv_select_all_lock.setVisibility(View.INVISIBLE);
            }

        }

        LinearLayout ll_all_look = helper.getView(R.id.ll_all_look);

        ll_all_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.isBack()){
                    Intent intent=new Intent(mContext, CreateTagsActivity.class);
                    intent.putExtra("data",item);
                    MyTagsActivity myTagsActivity= (MyTagsActivity) mContext;
                    myTagsActivity.startActivityForResult(intent,2);
                    return;
                }
                item.setChecked(!item.isChecked());
                notifyDataSetChanged();
            }
        });
    }

    /**List转String逗号分隔*/
    private String listToString(ArrayList<String> list) {

        StringBuilder stringBuilder = new StringBuilder();
        for(String str : list){
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        String strs = stringBuilder.toString();
        strs = strs.substring(0, strs.length() - 1);

        return strs;
    }

}
