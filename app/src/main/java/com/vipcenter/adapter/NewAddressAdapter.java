package com.vipcenter.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.PersonAddress;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/26 18:26
 * 邮箱：771548229@qq..com
 */
public class NewAddressAdapter extends BaseQuickAdapter<PersonAddress,BaseViewHolder> {
    public NewAddressAdapter(int layoutResId, @Nullable List<PersonAddress> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonAddress item) {

        helper.addOnClickListener(R.id.ll_edit).addOnClickListener(R.id.ll_delete).addOnClickListener(R.id.ll_default);
        TextView name = (TextView) helper.getView(R.id.name);
        TextView   phone = (TextView) helper.getView(R.id.phone);
        TextView   address = (TextView) helper.getView(R.id.address);
        CheckBox    deAddress = (CheckBox) helper.getView(R.id.de_box);
        name.setText(item.getNickname());
        phone.setText(item.getTelephone());
        address.setText(item.getProvince()+item.getCity()+item.getDistrict()+item.getAddress());
        if(item.getIsDefault()==1){
            deAddress.setChecked(true);
        }else{
            deAddress.setChecked(false);
        }
        deAddress.setClickable(false);
    }
}
