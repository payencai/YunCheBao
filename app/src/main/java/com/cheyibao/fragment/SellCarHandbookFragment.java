package com.cheyibao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/25.
 */

public class SellCarHandbookFragment extends BaseFragment {
    @BindView(R.id.answ1)
    TextView answ1;
    @BindView(R.id.answ2)
    TextView answ2;
    @BindView(R.id.answ3)
    TextView answ3;
    @BindView(R.id.answ4)
    TextView answ4;
    @BindView(R.id.answ5)
    TextView answ5;
    @BindView(R.id.arrow1)
    ImageView arrow1;
    @BindView(R.id.arrow2)
    ImageView arrow2;
    @BindView(R.id.arrow3)
    ImageView arrow3;
    @BindView(R.id.arrow4)
    ImageView arrow4;
    @BindView(R.id.arrow5)
    ImageView arrow5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sell_car_handbook, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @OnClick({R.id.ques1,R.id.ques2,R.id.ques3,R.id.ques4,R.id.ques5})
    public void Onclick(View v){
        switch (v.getId()){
            case R.id.ques1:
                if (answ1.isShown()){
                    answ1.setVisibility(View.GONE);
                    arrow1.setImageResource(R.mipmap.arrow_down);
                }else{
                    answ1.setVisibility(View.VISIBLE);
                    arrow1.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques2:
                if (answ2.isShown()){
                    answ2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.mipmap.arrow_down);
                }else{
                    answ2.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques3:
                if (answ3.isShown()){
                    answ3.setVisibility(View.GONE);
                    arrow3.setImageResource(R.mipmap.arrow_down);
                }else{
                    answ3.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques4:
                if (answ4.isShown()){
                    answ4.setVisibility(View.GONE);
                    arrow4.setImageResource(R.mipmap.arrow_down);
                }else{
                    answ4.setVisibility(View.VISIBLE);
                    arrow4.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques5:
                if (answ5.isShown()){
                    answ5.setVisibility(View.GONE);
                    arrow5.setImageResource(R.mipmap.arrow_down);
                }else{
                    answ5.setVisibility(View.VISIBLE);
                    arrow5.setImageResource(R.mipmap.arrow_up);
                }
                break;
        }
    }
}
