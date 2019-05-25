package com.example.yunchebao.rongcloud.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.xihubao.CarBrandSelectActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendActivity extends AppCompatActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;
    @BindView(R.id.tv_nosex)
    TextView tv_nosex;
    @BindView(R.id.tv_man)
    TextView tv_man;
    @BindView(R.id.tv_women)
    TextView tv_women;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_car)
    TextView tv_car;
    List<String> nums;
    CityPickerView mPicker = new CityPickerView();
    String province;
    String city;
    String area;
    String brand;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    @OnClick({R.id.rl_search, R.id.back, R.id.tv_nosex, R.id.tv_man, R.id.tv_women, R.id.rl_age, R.id.rl_city, R.id.rl_car,R.id.tv_search})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                if("不限".equals(minAge)){
                    minAge="";
                }
                if("不限".equals(maxAge)){
                    maxAge="";
                }
                Intent intent=new Intent(AddFriendActivity.this, SearchResultActivity.class);
                intent.putExtra("type","0");
                intent.putExtra("sex",sex);
                intent.putExtra("maxAge",maxAge);
                intent.putExtra("minAge",minAge);
                intent.putExtra("brand",brand);
                intent.putExtra("province",province);
                intent.putExtra("city",city);
                intent.putExtra("area",area);
                intent.putExtra("content","");
                startActivity(intent);
                break;
            case R.id.rl_search:
                startActivity(new Intent(AddFriendActivity.this, SearchAddActivity.class));
                break;
            case R.id.back:
                finish();
                break;
            case R.id.rl_age:
                showSelectCount();
                break;
            case R.id.rl_city:
                mPicker.showCityPicker();
                break;
            case R.id.rl_car:
                startActivityForResult(new Intent(AddFriendActivity.this, CarBrandSelectActivity.class), 3);
                break;
            case R.id.tv_nosex:
                sex="";
                tv_nosex.setSelected(true);
                tv_man.setSelected(false);
                tv_women.setSelected(false);
                tv_man.setTextColor(getResources().getColor(R.color.black_33));
                tv_women.setTextColor(getResources().getColor(R.color.black_33));
                tv_nosex.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_man:
                sex="男";
                tv_nosex.setSelected(false);
                tv_man.setSelected(true);
                tv_women.setSelected(false);
                tv_man.setTextColor(getResources().getColor(R.color.white));
                tv_women.setTextColor(getResources().getColor(R.color.black_33));
                tv_nosex.setTextColor(getResources().getColor(R.color.black_33));
                break;
            case R.id.tv_women:
                sex="女";
                tv_nosex.setSelected(false);
                tv_man.setSelected(false);
                tv_women.setSelected(true);
                tv_man.setTextColor(getResources().getColor(R.color.black_33));
                tv_women.setTextColor(getResources().getColor(R.color.white));
                tv_nosex.setTextColor(getResources().getColor(R.color.black_33));
                break;
        }
    }

    private void initCity() {
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择所在地")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#E9E9E9")//标题栏背景色
                .confirTextColor("#585858")//确认按钮文字颜色
                .confirmText("确认")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#585858")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(3)//显示item的数量
                .province("广东省")//默认显示的省份
                .city("广州市")//默认显示省份下面的城市
                .district("番禺区")
                .provinceCyclic(false)//省份滚轮是否可以循环滚动
                .cityCyclic(false)//城市滚轮是否可以循环滚动
                .drawShadows(true)//滚轮不显示模糊效果
                .setLineColor("#f3f3f3")//中间横线的颜色
                .setLineHeigh(2)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();
        mPicker.init(this);
        mPicker.setConfig(cityConfig);
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean provinceBean, CityBean cityBean, DistrictBean district) {
                province = provinceBean.getName();
                city = cityBean.getName();
                area = district.getName();
                tv_city.setText(provinceBean.getName() + cityBean.getName() + district.getName());
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });
    }

    private void initView() {
        tv_nosex.setSelected(true);
        tv_man.setSelected(false);
        tv_women.setSelected(false);
        nums = new ArrayList<>();
        initCity();
    }

    String minAge;
    String maxAge;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && data != null) {
            brand = data.getStringExtra("name");
            tv_car.setText(brand);
        }
    }

    private void showSelectCount() {
        nums.add("不限");
        for (int i = 1; i <= 99; i++) {
            nums.add(i + "");
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_age, null);

        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        WheelView wl_min = (WheelView) view.findViewById(R.id.wl_min);
        WheelView wl_max = (WheelView) view.findViewById(R.id.wl_max);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;

        wl_min.setStyle(style);
        wl_min.setWheelAdapter(new ArrayWheelAdapter(this));
        wl_min.setWheelData(nums);
        wl_min.setLoop(false);
        wl_min.setSkin(WheelView.Skin.Holo);
        wl_max.setStyle(style);
        wl_max.setWheelAdapter(new ArrayWheelAdapter(this));
        wl_max.setWheelData(nums);
        wl_max.setLoop(false);
        wl_max.setSkin(WheelView.Skin.Holo);
        wl_min.setSelection(0);
        wl_max.setSelection(0);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(wl_min.getCurrentPosition()==0){
                    minAge="不限";
                }
                if(wl_max.getCurrentPosition()==0){
                    maxAge="不限";
                }
                if(wl_min.getCurrentPosition()>0){
                    minAge = nums.get(wl_min.getCurrentPosition());
                }
                if(wl_max.getCurrentPosition()>0)
                     maxAge = nums.get(wl_max.getCurrentPosition());
                tv_age.setText(minAge + "-" + maxAge);

            }
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }
}
