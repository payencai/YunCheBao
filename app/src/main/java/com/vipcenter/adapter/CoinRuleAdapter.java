package com.vipcenter.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.CoinRlue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：凌涛 on 2019/3/5 15:34
 * 邮箱：771548229@qq..com
 */
public class CoinRuleAdapter extends BaseQuickAdapter<CoinRlue,BaseViewHolder> {
    public CoinRuleAdapter(int layoutResId, @Nullable List<CoinRlue> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinRlue item) {
        TextView tv_content=helper.getView(R.id.tv_content);
        int coin=item.getCoin();
        int pos=helper.getAdapterPosition();
        pos++;
        String condition=item.getConditions();
        if(isNumber(condition)){
            int cost=Integer.parseInt(condition);
            if(cost>10)
               tv_content.setText(pos+"."+"每消费"+cost+"元 , 奖励"+coin+"宝币");
            else{
                tv_content.setText(pos+"."+"每成功交易"+cost+"单 , 奖励"+coin+"宝币");
            }
        }else{
            tv_content.setText(pos+"."+condition+" , 奖励"+coin+"宝币");
        }

    }
    private boolean isNumber(String str){
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();


    }
}
