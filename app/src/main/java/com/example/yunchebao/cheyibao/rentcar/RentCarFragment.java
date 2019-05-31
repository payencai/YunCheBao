package com.example.yunchebao.cheyibao.rentcar;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.CarModelsDetailActivity;
import com.cheyibao.HighCarCategoryActivity;
import com.cheyibao.LongRentAppliationActivity;
import com.cheyibao.RentCarByCarModelActivity;
import com.cheyibao.RentCarByShopListActivity;
import com.cheyibao.SelfDriverOrderActivity;
import com.cheyibao.adapter.RentCarModelAdapter;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.util.RentCarUtils;
import com.cheyibao.view.RentCarAddressView;
import com.cheyibao.view.RentCarTimeView;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.common.ResourceUtils;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;
import com.system.WebviewActivity;
import com.vipcenter.RegisterActivity;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by sdhcjhss on 2017/12/9.
 */

public class RentCarFragment extends BaseFragment {
    @BindView(R.id.rent_type_parent_view)
    RadioGroup rentTypeParentView;
    @BindView(R.id.self_driving_radio_button)
    RadioButton selfDrivingRadioButton;
    @BindView(R.id.long_rent_radio_button)
    RadioButton longRentRadioButton;
    @BindView(R.id.pick_the_car_view)
    CardView pickTheCarView;
    @BindView(R.id.self_driver_order_click_view)
    TextView selfDriverOrderClickView;
    @BindView(R.id.high_end_self_driving_click_view)
    TextView highEndSelfDrivingClickView;
    @BindView(R.id.store_query_click_view)
    TextView storeQueryClickView;
    @BindView(R.id.car_type_query_click)
    TextView carTypeQueryClick;
    @BindView(R.id.self_driving_parent_view)
    LinearLayout selfDrivingParentView;
    @BindView(R.id.long_rent_btn)
    CardView longRentBtn;
    @BindView(R.id.recommended_vehicle_type_list_view)
    RecyclerView recommendedVehicleTypeListView;
    @BindView(R.id.long_rent_parentView)
    LinearLayout longRentParentView;
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.rent_car_time_View)
    RentCarTimeView rentCarTimeView;
    @BindView(R.id.rent_Car_address_view)
    RentCarAddressView rentCarAddressView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;


    //轮播图片
    private List<String> imageList = new ArrayList<>();

    private Unbinder unbinder;

    private RentCarModelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rent_home_layout, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        getBaner();
        initView();
        return rootView;
    }
    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getContext()).load((String) path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("url", bannerList.get(position).getPicture() + "-" + bannerList.get(position).getSkipUrl());
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                String url = bannerList.get(position).getSkipUrl();
                if (!url.contains("http") && !url.contains("https")) {
                    url = "http://" + url;
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(imageList);//设置图片源
        banner.start();
    }
    private void initView() {
        bannerList=new ArrayList<>();
        selfDrivingRadioButton.setTextColor(colors());
        longRentRadioButton.setTextColor(colors());
        selfDrivingRadioButton.setChecked(true);

        selfDrivingRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        longRentRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        rentTypeParentView.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.self_driving_radio_button:
                    longRentParentView.setVisibility(View.GONE);
                    selfDrivingParentView.setVisibility(View.VISIBLE);
                    selfDrivingRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    longRentRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    break;
                case R.id.long_rent_radio_button:
                    longRentParentView.setVisibility(View.VISIBLE);
                    selfDrivingParentView.setVisibility(View.GONE);
                    selfDrivingRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    longRentRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    loadDataType.initData();
                    break;
            }
        });

        recommendedVehicleTypeListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RentCarModelAdapter(new ArrayList<>());
        adapter.setDisplayPrice(false);
        adapter.bindToRecyclerView(recommendedVehicleTypeListView);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            RentCarModel carModel = (RentCarModel) adapter.getItem(position);
            Intent intent = new Intent(getContext(), CarModelsDetailActivity.class);
            if (carModel!=null){
                intent.putExtra("car_model",carModel);
            }
            startActivity(intent);
        });
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            return null;
        }

        @Override
        public void initData() {
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRecommendLongCarList, "", new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentCarModel>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                multipleStatusView.showContent();
                                adapter.setNewData(rentCarModels);
                            }else {
                                multipleStatusView.showEmpty();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    multipleStatusView.showError();
                }
            });

        }

        @Override
        public void loadMoreData() {

        }

        @Override
        public void refreshData() {

        }
    };


    private ColorStateList colors() {
        int[][] status = new int[2][];
        status[0] = new int[]{android.R.attr.state_checked};
        status[1] = new int[]{};
        int[] colors = new int[]{ResourceUtils.getColorByResource(getContext(), R.color.black_33), ResourceUtils.getColorByResource(getContext(), R.color.black_5D)};
        return new ColorStateList(status, colors);
    }

    List<Banner> bannerList;
    private void getBaner() {
        imageList.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("type", 4);
        HttpProxy.obtain().get(PlatformContans.Commom.getBannerList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Banner banner = new Gson().fromJson(item.toString(), Banner.class);
                        bannerList.add(banner);
                        String url = item.getString("picture");
                        Map<String, String> image_uri = new HashMap<String, String>();
                        image_uri.put("imageUrls", url);
                        imageList.add(banner.getPicture());
                    }
                    initBanner();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.pick_the_car_view)
    public void onPickTheCarViewClicked() {
        if (RentCarUtils.rentCarInfo == null) {
            RentCarUtils.rentCarInfo = new HashMap<>();
        }
        RentCarUtils.rentCarInfo.clear();
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_AREA_1, rentCarAddressView.getTakeCarAddress());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_AREA_2, rentCarAddressView.getReturnCarAddress());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_SHOP, rentCarAddressView.getRentShop());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_IS_TO_HOME_SERVICE, rentCarAddressView.isToHomeService());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_START_TIME, rentCarTimeView.getStartTime());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_END_TIME, rentCarTimeView.getEndTime());
        if (rentCarAddressView.isToHomeService()) {
            if (rentCarAddressView.getTakeCarAddress() == null) {
                ToastUtil.showToast(getContext(), "请选择取车地址！");
                return;
            }
            Intent intent = new Intent(getContext(), ShopListNoAreaActivity.class);
            startActivity(intent);
        } else {
            if (rentCarAddressView.getRentShop() == null) {
                ToastUtil.showToast(getContext(), "请选择取车还车店铺！");
                return;
            }
            Intent intent = new Intent(getContext(), ShopDetailActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.self_driver_order_click_view)
    public void onSelfDriverOrderClickViewClicked() {
        if (MyApplication.isLogin){
            startActivity(new Intent(getContext(), SelfDriverOrderActivity.class));
        }else {
            startActivity(new Intent(getContext(),RegisterActivity.class));
//            AvoidOnResult avoidOnResult = new AvoidOnResult(this);
//            avoidOnResult.startForResult(RegisterActivity.class, 1, (requestCode, resultCode, data) -> {
//                startActivity(new Intent(getContext(), SelfDriverOrderActivity.class));
//            });
        }
    }

    @OnClick(R.id.high_end_self_driving_click_view)
    public void onHighEndSelfDrivingClickViewClicked() {
        startActivity(new Intent(getContext(), HighCarCategoryActivity.class));
    }

    @OnClick(R.id.store_query_click_view)
    public void onStoreQueryClickViewClicked() {
        startActivity(new Intent(getContext(), RentCarByShopListActivity.class));
    }

    @OnClick(R.id.car_type_query_click)
    public void onCarTypeQueryClickClicked() {
        startActivity(new Intent(getContext(), RentCarByCarModelActivity.class));
    }

    @OnClick(R.id.long_rent_btn)
    public void onLongRentBtnClicked() {
        startActivity(new Intent(getContext(), LongRentAppliationActivity.class));
    }
}
