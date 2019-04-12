package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import com.cheyibao.adapter.CarCommomAdapter;
import com.cheyibao.adapter.CarTypeAdapter;
import com.cheyibao.adapter.ColorAdapter;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.GridViewForScrollView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class OldCarSelectActivity extends NoHttpBaseActivity {
    @BindView(R.id.textBtn)
    TextView rightBtn;

    private Context ctx;
    @BindView(R.id.sg_from)
    GridViewForScrollView sg_from;
    @BindView(R.id.sg_paifang)
    GridViewForScrollView sg_paifang;
    @BindView(R.id.sg_ranyou)
    GridViewForScrollView sg_ranyou;
    @BindView(R.id.sg_variableBox)
    GridViewForScrollView sg_variableBox;
    @BindView(R.id.sg_seat)
    GridViewForScrollView sg_seat;
    @BindView(R.id.sg_country)
    GridViewForScrollView sg_country;
    @BindView(R.id.sg_color)
    GridViewForScrollView sg_color;
    @BindView(R.id.sg_type)
    GridViewForScrollView sg_type;

    List<String> colors;
    List<String> carType;
    ColorAdapter mColorAdapter;
    CarTypeAdapter mCarTypeAdapter;
    CarCommomAdapter mPaifangAdapter;
    CarCommomAdapter mRanyouAdapter;
    CarCommomAdapter mBoxAdapter;
    CarCommomAdapter mSeatAdapter;
    CarCommomAdapter mCountryAdapter;
    CarCommomAdapter mFromAdapter;
    String displacement;
    String fuel;
    String color;
    String variableBox;
    String seat;
    String country;
    String models;
    int type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_car_select_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "筛选");
        ButterKnife.bind(this);
        ctx = this;
        rightBtn.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
        rightBtn.setText("重置");
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 oldTypePos = 5;
                 oldPaiPos = 3;
                 oldYouPos = 4;
                 oldBoxPos = 2;
                 oldSeatPos = 5;
                 oldCountryPos = 6;
                 oldFromPos=2;
                mColorAdapter.setPos(10);
                mCarTypeAdapter.setPos(5);
                mPaifangAdapter.setPos(3);
                mRanyouAdapter.setPos(4);
                mBoxAdapter.setPos(2);
                mSeatAdapter.setPos(5);
                mCountryAdapter.setPos(6);
                mFromAdapter.setPos(2);
                mFromAdapter.notifyDataSetChanged();
                mColorAdapter.notifyDataSetChanged();
                mCarTypeAdapter.notifyDataSetChanged();
                mPaifangAdapter.notifyDataSetChanged();
                mRanyouAdapter.notifyDataSetChanged();
                mBoxAdapter.notifyDataSetChanged();
                mSeatAdapter.notifyDataSetChanged();
                mCountryAdapter.notifyDataSetChanged();
                displacement = "";
                fuel = "";
                color = "";
                variableBox = "";
                seat = "";
                country = "";
                models = "";
                type=0;
            }
        });
        initColor();
        initType();
        initView1();
        initView2();
        initView3();
        initView4();
        initView5();
        initView6();
    }

    int oldTypePos = 5;
    int oldPaiPos = 3;
    int oldYouPos = 4;
    int oldBoxPos = 2;
    int oldSeatPos = 5;
    int oldCountryPos = 6;
    int oldFromPos = 2;
    private void initColor() {
        colors = new ArrayList<>();
        colors.add("黑色");
        colors.add("深灰色");
        colors.add("白色");
        colors.add("绿色");
        colors.add("红色");
        colors.add("蓝色");
        colors.add("香槟色");
        colors.add("橙色");
        colors.add("咖啡色");
        colors.add("彩色");
        colors.add("不限");
        mColorAdapter = new ColorAdapter(this, colors);
        mColorAdapter.setPos(10);
        sg_color.setAdapter(mColorAdapter);
        sg_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mColorAdapter.setPos(position);
                color = colors.get(position);
                mColorAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView2() {

        List<String> list = new ArrayList<>();
        list.add("国二以上");
        list.add("国三以上");
        list.add("国四以上");
        list.add("不限");
        mPaifangAdapter = new CarCommomAdapter(this, list);
        mPaifangAdapter.setPos(oldPaiPos);
        sg_paifang.setAdapter(mPaifangAdapter);
        sg_paifang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldPaiPos != position) {
                    View oldTypeSelect = sg_paifang.getChildAt(oldPaiPos);
                    view.setBackgroundResource(R.drawable.selected_bg_yellow_small);
                    oldTypeSelect.setBackgroundResource(R.drawable.gray_stroke_r4);
                    oldPaiPos = position;
                }
                displacement = list.get(position);
            }
        });
    }

    private void initType() {
        carType = new ArrayList<>();
        carType.add("两厢轿车");
        carType.add("三厢轿车");
        carType.add("皮卡");
        carType.add("跑车");
        carType.add("面包车");
        carType.add("不限");
        mCarTypeAdapter = new CarTypeAdapter(this, carType);
        mCarTypeAdapter.setPos(oldTypePos);
        sg_type.setAdapter(mCarTypeAdapter);
        sg_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldTypePos != position) {
                    View oldTypeSelect = sg_type.getChildAt(oldTypePos);
                    view.setBackgroundResource(R.drawable.selected_bg_yellow_small);
                    oldTypeSelect.setBackgroundResource(R.drawable.gray_stroke_r4);
                    oldTypePos = position;
                }
                models = carType.get(position);
            }
        });
    }

    private void initView1() {

        List<String> list = new ArrayList<>();
        list.add("个人");
        list.add("商家");
        list.add("不限");
        mFromAdapter = new CarCommomAdapter(this, list);
        mFromAdapter.setPos(oldFromPos);
        sg_from.setAdapter(mFromAdapter);
        sg_from.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldFromPos != position) {
                    View oldTypeSelect = sg_from.getChildAt(oldFromPos);
                    view.setBackgroundResource(R.drawable.selected_bg_yellow_small);
                    oldTypeSelect.setBackgroundResource(R.drawable.gray_stroke_r4);
                    oldFromPos = position;
                }
                if(list.get(position).equals("个人")){
                    type=2;
                }else if(list.get(position).equals("商家")){
                    type=1;
                }else{
                    type=0;
                }
            }
        });

    }


    private void initView3() {

        List<String> list = new ArrayList<>();
        list.add("汽油");
        list.add("柴油");
        list.add("电动");
        list.add("油电混合");
        list.add("不限");
        mRanyouAdapter = new CarCommomAdapter(this, list);
        mRanyouAdapter.setPos(oldYouPos);
        sg_ranyou.setAdapter(mRanyouAdapter);
        sg_ranyou.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldYouPos != position) {
                    View oldTypeSelect = sg_ranyou.getChildAt(oldYouPos);
                    view.setBackgroundResource(R.drawable.selected_bg_yellow_small);
                    oldTypeSelect.setBackgroundResource(R.drawable.gray_stroke_r4);
                    oldYouPos = position;
                }
                fuel = list.get(position);
            }
        });
    }

    private void initView4() {

        List<String> list = new ArrayList<>();
        list.add("自动");
        list.add("手动");
        list.add("不限");
        mBoxAdapter = new CarCommomAdapter(this, list);
        mBoxAdapter.setPos(oldBoxPos);
        sg_variableBox.setAdapter(mBoxAdapter);
        sg_variableBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldBoxPos != position) {
                    View oldTypeSelect = sg_variableBox.getChildAt(oldBoxPos);
                    view.setBackgroundResource(R.drawable.selected_bg_yellow_small);
                    oldTypeSelect.setBackgroundResource(R.drawable.gray_stroke_r4);
                    oldBoxPos = position;
                }
                variableBox = list.get(position);
            }
        });
    }

    private void initView5() {


        List<String> list = new ArrayList<>();
        list.add("2座");
        list.add("4座");
        list.add("5座");
        list.add("6座");
        list.add("7座以上");
        list.add("不限");
        mSeatAdapter = new CarCommomAdapter(this, list);
        mSeatAdapter.setPos(oldSeatPos);
        sg_seat.setAdapter(mSeatAdapter);
        sg_seat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldSeatPos != position) {
                    View oldTypeSelect = sg_seat.getChildAt(oldSeatPos);
                    view.setBackgroundResource(R.drawable.selected_bg_yellow_small);
                    oldTypeSelect.setBackgroundResource(R.drawable.gray_stroke_r4);
                    oldSeatPos = position;
                }
                seat = list.get(position);
            }
        });
    }

    private void initView6() {

        List<String> list = new ArrayList<>();
        list.add("德系");
        list.add("日系");
        list.add("美系");
        list.add("法系");
        list.add("韩系");
        list.add("国产");
        list.add("不限");
        mCountryAdapter = new CarCommomAdapter(this, list);
        mCountryAdapter.setPos(oldCountryPos);
        sg_country.setAdapter(mCountryAdapter);
        sg_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldCountryPos != position) {
                    View oldTypeSelect = sg_country.getChildAt(oldCountryPos);
                    view.setBackgroundResource(R.drawable.selected_bg_yellow_small);
                    oldTypeSelect.setBackgroundResource(R.drawable.gray_stroke_r4);
                    oldCountryPos = position;
                }
                country = list.get(position);
            }
        });
    }

    @OnClick({R.id.back, R.id.submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                Intent intent=new Intent();
                if("不限".equals(country)){
                    country="";
                }
                if("不限".equals(models)){
                    models="";
                }
                if("不限".equals(displacement)){
                    displacement="";
                }
                if("不限".equals(seat)){
                    seat="";
                }
                if("不限".equals(variableBox)){
                    variableBox="";
                }
                if("不限".equals(color)){
                    color="";
                }
                if("不限".equals(fuel)){
                    fuel="";
                }

                intent.putExtra("country",country);
                intent.putExtra("models",models);
                intent.putExtra("displacement",displacement);
                intent.putExtra("seat",seat);
                intent.putExtra("variableBox",variableBox);
                intent.putExtra("color",color);
                intent.putExtra("variableBox",variableBox);
                intent.putExtra("fuel",fuel);
                intent.putExtra("type",type);
                setResult(1,intent);
                finish();
                //ActivityAnimationUtils.commonTransition(OldCarSelectActivity.this, OldCarListActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
