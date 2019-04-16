package com.cheyibao.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cheyibao.ShopDetailActivity;
import com.cheyibao.ShopListNoAreaActivity;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.RentCarUtils;
import com.cheyibao.view.RentCarAddressView;
import com.cheyibao.view.RentCarTimeView;
import com.common.ResourceUtils;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;
import com.system.model.AddressBean;
import com.tool.slideshowview.SlideShowView;

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
    ListView recommendedVehicleTypeListView;
    @BindView(R.id.long_rent_parentView)
    LinearLayout longRentParentView;
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;
    //    @BindView(R.id.send_car_city_text_view)
//    TextView sendCarCityTextView;
//    @BindView(R.id.return_the_car_city_text_view)
//    TextView returnTheCarCityTextView;
    @BindView(R.id.rent_car_time_View)
    RentCarTimeView rentCarTimeView;
    @BindView(R.id.rent_Car_address_view)
    RentCarAddressView rentCarAddressView;


    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rent_home_layout, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        getBaner();
        initView();
        return rootView;
    }

    private void initView() {
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
                    break;
            }
        });

    }

    private ColorStateList colors() {
        int[][] status = new int[2][];
        status[0] = new int[]{android.R.attr.state_checked};
        status[1] = new int[]{};
        int[] colors = new int[]{ResourceUtils.getColorByResource(getContext(), R.color.black_33), ResourceUtils.getColorByResource(getContext(), R.color.black_5D)};
        return new ColorStateList(status, colors);
    }


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
                        String url = item.getString("picture");
                        Map<String, String> image_uri = new HashMap<String, String>();
                        image_uri.put("imageUrls", url);
                        imageList.add(image_uri);
                    }
                    slideShowView.setImageUrls(imageList);
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
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_AREA_1, rentCarAddressView.getTakeCarAddress());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_AREA_2, rentCarAddressView.getReturnCarAddress());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_SHOP, rentCarAddressView.getRentShop());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_IS_TO_HOME_SERVICE, rentCarAddressView.isToHomeService());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_START_TIME, rentCarTimeView.getStartTime());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_END_TIME, rentCarTimeView.getEndTime());
        if(rentCarAddressView.isToHomeService()){
            if (rentCarAddressView.getTakeCarAddress()==null){
                ToastUtil.showToast(getContext(),"请选择取车地址！");
                return;
            }
            Intent intent = new Intent(getContext(),ShopListNoAreaActivity.class);
            startActivity(intent);
        }else {
            if (rentCarAddressView.getRentShop()==null){
                ToastUtil.showToast(getContext(),"请选择取车还车店铺！");
                return;
            }
            Intent intent = new Intent(getContext(),ShopDetailActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.self_driver_order_click_view)
    public void onSelfDriverOrderClickViewClicked() {
    }

    @OnClick(R.id.high_end_self_driving_click_view)
    public void onHighEndSelfDrivingClickViewClicked() {
    }

    @OnClick(R.id.store_query_click_view)
    public void onStoreQueryClickViewClicked() {
    }

    @OnClick(R.id.car_type_query_click)
    public void onCarTypeQueryClickClicked() {
    }

    @OnClick(R.id.long_rent_btn)
    public void onLongRentBtnClicked() {
    }
}
