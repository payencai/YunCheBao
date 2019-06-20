package com.example.yunchebao.cheyibao.oldcar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.CarPayActivity;
import com.cheyibao.NewCarParamsActivity;
import com.cheyibao.OldCarCommentActivity;
import com.cheyibao.OldCarShopActivity;
import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.adapter.NewCarParamsAdapter;
import com.cheyibao.adapter.OldCarImageAdapter;
import com.cheyibao.model.Merchant;
import com.cheyibao.model.NewCarParams;
import com.cheyibao.model.OldCar;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.view.CircleImageView;
import com.tool.ActivityConstans;
import com.tool.LogUtil;
import com.tool.UIControlUtils;
import com.tool.listview.PersonalListView;
import com.vipcenter.RegisterActivity;
import com.xihubao.ShopInfoActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.imkit.RongIM;

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class OldCarDetailActivity extends NoHttpBaseActivity {
    OldCar mOldCar;
    @BindView(R.id.slideshowView)
    Banner banner;
    @BindView(R.id.collectBtn)
    ImageView collectIcon;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_oldprice)
    TextView tv_oldprice;
    @BindView(R.id.tv_newprice)
    TextView tv_newprice;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_color)
    TextView tv_color;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_nexttime)
    TextView tv_nexttime;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_priod)
    TextView tv_priod;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_regtime)
    TextView tv_regtime;
    @BindView(R.id.tv_dis)
    TextView tv_dis;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.ll_person)
    LinearLayout ll_person;
    @BindView(R.id.ll_shop)
    LinearLayout ll_shop;
    @BindView(R.id.lv_params)
    PersonalListView lv_params;
    @BindView(R.id.lv_photo)
    PersonalListView lv_photo;
    @BindView(R.id.iv_head)
    CircleImageView iv_head;
    @BindView(R.id.tv_seecomment)
    TextView tv_seecomment;

    List<String> images = new ArrayList<>();
    NewCarParamsAdapter mNewCarParamsAdapter;
    NewCarParams mNewCarParams;
    OldCarImageAdapter mOldCarImageAdapter;
    List<String> mList = new ArrayList<>();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        id=getIntent().getStringExtra("id");
        mOldCar = (OldCar) bundle.getSerializable("data");
        setContentView(R.layout.oldcar_detail_layout);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();

    }

    private void getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("carCategoryDetailId", mOldCar.getCarCategoryDetailId());
        HttpProxy.obtain().get(PlatformContans.NewCar.getDetailParams, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("params", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray param = data.getJSONArray("param");
                    if (param.length() > 0) {
                        for (int i = 0; i < param.length(); i++) {
                            JSONObject item = param.getJSONObject(i);
                            if (item.getString("paramValue").equals("基本信息")) {
                                mNewCarParams = new Gson().fromJson(item.toString(), NewCarParams.class);
                                if (mNewCarParams.getParam().size() > 0) {
                                    NewCarParams.ParamBean paramBean = mNewCarParams.getParam().get(0);
                                    paramBean.setParent(mNewCarParams.getParamValue());
                                    List<NewCarParams.ParamBean> paramBeans = mNewCarParams.getParam();
                                    paramBeans.remove(0);
                                    paramBeans.add(0, paramBean);
                                    mNewCarParamsAdapter = new NewCarParamsAdapter(OldCarDetailActivity.this, paramBeans);
                                    lv_params.setAdapter(mNewCarParamsAdapter);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    Merchant merchant;

    private void getShop() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", mOldCar.getMerchantId());

        HttpProxy.obtain().get(PlatformContans.Shop.getMerchantById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    merchant = new Gson().fromJson(data.toString(), Merchant.class);
                    tv_address.setText(merchant.getProvince() + merchant.getCity() + merchant.getDistrict() + merchant.getAddress());
                    tv_grade.setText(merchant.getGrade() + "");
                    tv_shopname.setText(merchant.getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void setData( ) {
        String banner1=mOldCar.getCarCategoryDetail().getBanner1();
        String banner2=mOldCar.getCarCategoryDetail().getBanner2();
        String banner3=mOldCar.getCarCategoryDetail().getBanner3();
        if(!TextUtils.isEmpty(banner1)){
            if(banner1.contains(",")){
                String []pics=banner1.split(",");
                for (int i = 0; i <pics.length; i++) {
                    if(!TextUtils.isEmpty(pics[i]))
                    images.add(pics[i]);
                }
            }else{
                images.add(banner1);
            }
        }
        images.add(banner2);
        images.add(banner3);
        initBanner();
        tv_oldprice.setText("新车含税:￥" + mOldCar.getNewPrice());
        tv_newprice.setText("￥" + mOldCar.getOldPrice());
        tv_oldprice.getPaint().setFlags( Paint.STRIKE_THRU_TEXT_FLAG );
        String name = mOldCar.getFirstName();
        if (!TextUtils.isEmpty(mOldCar.getSecondName()) && !"null".equals(mOldCar.getSecondName())) {
            name = name + mOldCar.getSecondName();
        }
        if (!TextUtils.isEmpty(mOldCar.getThirdName()) && !"null".equals(mOldCar.getThirdName())) {
            name = name + mOldCar.getThirdName();
        }
        tv_name.setText(name);
        tv_nickname.setText(mOldCar.getLinkman());
        tv_content.setText("车辆描述: " + mOldCar.getCarDescribe());
        tv_color.setText(mOldCar.getColor());
        tv_nexttime.setText(mOldCar.getLastValidateCar().substring(0, 10));
        tv_regtime.setText(mOldCar.getRegistrationTime().substring(0, 10));
        tv_num.setText(mOldCar.getChange() + "次");
        tv_priod.setText(mOldCar.getInsuranceValidTime() );
        tv_city.setText(mOldCar.getRegistrationAddress());
        tv_dis.setText(mOldCar.getDistance() + "公里");
        Glide.with(this).load(mOldCar.getHeadPortrait()).into(iv_head);
        if (mOldCar.getType() == 1) {
            ll_shop.setVisibility(View.VISIBLE);
        } else {
            ll_person.setVisibility(View.VISIBLE);
        }

        if (mOldCar.getCarImage().contains(",")) {
            String[] images = mOldCar.getCarImage().split(",");
            for (int i = 0; i < images.length; i++) {
                mList.add(images[i]);
            }
        } else {
            mList.add(mOldCar.getCarImage());
        }
        // mList.add("http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011520425817");
        mOldCarImageAdapter = new OldCarImageAdapter(OldCarDetailActivity.this, mList);
        lv_photo.setAdapter(mOldCarImageAdapter);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();
        isCollect();
        getParams();
        if(!TextUtils.isEmpty(mOldCar.getMerchantId()))
            getShop();
    }

    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().get(PlatformContans.OldCar.getOldCarMerchantCarById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mOldCar = new Gson().fromJson(data.toString(), OldCar.class);
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "车辆详情");
        ButterKnife.bind(this);
        //网络地址获取轮播图
        tv_seecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OldCarDetailActivity.this, OldCarCommentActivity.class);
                intent.putExtra("id", mOldCar.getMerchantId());
                startActivity(intent);
            }
        });
        tv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OldCarDetailActivity.this, OldCarShopActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("id", mOldCar.getMerchantId());
                startActivity(intent);
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OldCarDetailActivity.this, OldCarShopActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("id", mOldCar.getMerchantId());
                startActivity(intent);
            }
        });
        // tv_shopname.setText(mOldCar.get);
        if(mOldCar!=null){
            id=mOldCar.getId();
            getDetail();
        }else{
            getDetail();
        }


    }

    public void callToPhoneSweetAlert(final String phone) {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确定要电话联系商家吗？")
                .setConfirmText("拨打")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        callToPhone(phone);
                    }
                })
                .setCancelText("算了")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(OldCarDetailActivity.this).load((String) path).into(imageView);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }

    @OnClick({R.id.back, R.id.collectBtn, R.id.toDetailConfig, R.id.rl_chat, R.id.callForMoreBtn,R.id.ll_head})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                Intent intent2 = new Intent(OldCarDetailActivity.this, ShopInfoActivity.class);
                intent2.putExtra("id", merchant.getId());
                startActivity(intent2);
                break;
            case R.id.collectBtn:
                if(!MyApplication.isLogin){
                    startActivity(new Intent(OldCarDetailActivity.this, RegisterActivity.class));
                    return;
                }
                if (isCollect == 0) {
                    isCollect = 1;
                    collectIcon.setImageResource(R.mipmap.collect_yellow);
                } else if (isCollect == 1) {
                    isCollect = 0;
                    collectIcon.setImageResource(R.mipmap.collect_gray_hole);
                }
                collect();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.toDetailConfig:
                Intent intent = new Intent(OldCarDetailActivity.this, NewCarParamsActivity.class);
                intent.putExtra("param", mOldCar.getCarCategoryDetailId());
                startActivity(intent);
                //ActivityAnimationUtils.commonTransition(OldCarDetailActivity.this, OldCarConfigDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
//            case R.id.toSellerDetail:
//                ActivityAnimationUtils.commonTransition(OldCarDetailActivity.this, SellerDetailActivity.class, ActivityConstans.Animation.FADE);
//                break;
            case R.id.rl_chat:
                if(!MyApplication.isLogin){
                    startActivity(new Intent(OldCarDetailActivity.this,RegisterActivity.class));
                    return;
                }
                if(TextUtils.isEmpty(mOldCar.getMerchantId())){
                    RongIM.getInstance().startPrivateChat(OldCarDetailActivity.this, mOldCar.getUserId(), mOldCar.getLinkman());
                }else{
                    RongIM.getInstance().startPrivateChat(OldCarDetailActivity.this, merchant.getId(), merchant.getName());
                }

//                if (MyApplication.isLogin) {
//                   // RongIM.getInstance().startPrivateChat(OldCarDetailActivity.this, merchant.getId(), merchant.getName());
//                } else {
//                    startActivity(new Intent(OldCarDetailActivity.this, RegisterActivity.class));
//                }

                break;
            case R.id.callForMoreBtn:
                if(merchant!=null)
                   callToPhoneSweetAlert(merchant.getServiceTelephone());
                else{
                    callToPhoneSweetAlert(mOldCar.getLinkmanTelephone());
                }
                break;
        }
    }


    int isCollect = -1;

    public void isCollect() {
        Map<String, Object> params = new HashMap<>();
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getId();
        }
        params.put("userId", token);
        params.put("oldCarMerchantCarId", mOldCar.getId());
        HttpProxy.obtain().get(PlatformContans.Collect.getOldCarCollection, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int data = jsonObject.getInt("data");
                    if (data == 0) {
                        isCollect = 0;
                        collectIcon.setImageResource(R.mipmap.collect_gray_hole);
                    } else if (data == 1) {
                        isCollect = 1;
                        collectIcon.setImageResource(R.mipmap.collect_yellow);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public void collect() {
        Map<String, Object> params = new HashMap<>();
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        params.put("carImage", mOldCar.getCarImage());
        params.put("carPrice", mOldCar.getNewPrice());
        params.put("firstName", mOldCar.getFirstName());
        params.put("secondName", mOldCar.getSecondName());
        params.put("thirdName", mOldCar.getThirdName());
        params.put("oldCarMerchantCarId", mOldCar.getId());
        params.put("type", mOldCar.getType());
        String json = new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Collect.addOldCarCollection, token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    private void postOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("commodityId", mOldCar.getId());
        params.put("shopId", merchant.getId());
        params.put("shopName", merchant.getName());
        params.put("title", merchant.getName());
        params.put("image", mOldCar.getCarImage());
        params.put("number", 1);
        params.put("type", 2);
        params.put("telephone", mOldCar.getLinkmanTelephone());
        params.put("seat", mOldCar.getCarCategoryDetail().getSeat());
        params.put("carCategory", mOldCar.getCarCategoryDetail().getModels());
        HttpProxy.obtain().post(PlatformContans.CarOrder.addCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String orderId = jsonObject.getString("data");
                        Intent intent = new Intent(OldCarDetailActivity.this, CarPayActivity.class);
                        intent.putExtra("money", mOldCar.getNewPrice() + "");
                        intent.putExtra("orderid", orderId);
                        startActivity(intent);
                    } else if (code == 9999) {
                        ToastUtils.showLongToast(OldCarDetailActivity.this, "请先去实名认证");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
