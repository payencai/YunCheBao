package com.cheyibao.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cheyibao.StopListActivity;
import com.costans.PlatformContans;
import com.entity.Banner;

import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.system.X5WebviewActivity;
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

public class RentCarFragment extends BaseFragment   {

    private static final int REQUEST_CODE_ADDRESS_FOR_MAP_SEND = 1;
    private static final int REQUEST_CODE_ADDRESS_FOR_MAP_TAKE = 2;
    private static final int REQUEST_CODE_ADDRESS_FOR_STORE_SEND = 3;
    private static final int REQUEST_CODE_ADDRESS_FOR_STORE_TAKE = 4;


    @BindView(R.id.rent_type_parent_view)
    RadioGroup rentTypeParentView;
    @BindView(R.id.self_driving_radio_button)
    RadioButton selfDrivingRadioButton;
    @BindView(R.id.long_rent_radio_button)
    RadioButton longRentRadioButton;
    @BindView(R.id.send_car_address_text_view)
    TextView sendCarAddressTextView;
    @BindView(R.id.is_send_the_car_to_home_checked_view)
    CheckBox isSendTheCarToHomeCheckedView;
    @BindView(R.id.return_the_car_address_text_view)
    TextView returnTheCarAddressTextView;
    @BindView(R.id.is_go_home_to_take_the_car_checked_view)
    CheckBox isGoHomeToTakeTheCarCheckedView;
    @BindView(R.id.rent_the_car_start_time_text_view)
    TextView rentTheCarStartTimeTextView;
    @BindView(R.id.rent_the_car_time_text_view)
    TextView rentTheCarTimeTextView;
    @BindView(R.id.rent_the_car_end_time_text_view)
    TextView rentTheCarEndTimeTextView;
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


    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();

    private Unbinder unbinder;

    private AddressBean addressBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rent_home_layout, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        getBaner();
        initView();
        return rootView;
    }

    private void initView(){
        selfDrivingRadioButton.setTextColor(colors());
        longRentRadioButton.setTextColor(colors());
        selfDrivingRadioButton.setChecked(true);

        selfDrivingRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        longRentRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        rentTypeParentView.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.self_driving_radio_button:
                    longRentParentView.setVisibility(View.GONE);
                    selfDrivingParentView.setVisibility(View.VISIBLE);
                    selfDrivingRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                    longRentRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                    break;
                case R.id.long_rent_radio_button:
                    longRentParentView.setVisibility(View.VISIBLE);
                    selfDrivingParentView.setVisibility(View.GONE);
                    selfDrivingRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                    longRentRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                    break;
            }
        });

        isGoHomeToTakeTheCarCheckedView.setButtonDrawable(drawables());
        isSendTheCarToHomeCheckedView.setButtonDrawable(drawables());

    }

    private ColorStateList colors(){
        int[][] status = new int[2][];
        status[0] = new int[]{android.R.attr.state_checked};
        status[1] = new int[]{};
        int[] colors = new int[]{getColorByResource(R.color.black_33),getColorByResource(R.color.black_5D)};
        return new ColorStateList(status,colors);
    }

    private StateListDrawable drawables(){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked},getDrawableByResource(R.mipmap.carrental_btn_checkthe_selected));
        stateListDrawable.addState(new int[]{},getDrawableByResource(R.mipmap.carrental_btn_checkthe_normal));
        return stateListDrawable;
    }

    private Drawable getDrawableByResource(int resource){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getDrawable(resource,null);
        }else {
            return getResources().getDrawable(resource);
        }
    }

    private int getColorByResource(int resource){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(resource,null);
        }else {
            return getResources().getColor(resource);
        }
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
//        //网络地址获取轮播图
//        imageList.clear();
//        for (int i = 0; i < 3; i++) {
//            Map<String, String> image_uri = new HashMap<String, String>();
//            image_uri.put("imageUrls", "https://you.autoimg.cn/_autohomecar__zhouyouji/657C2F909017074F9C59CA0B88DA0F0BDDC9.jpg?imageMogr2/format/jpg/thumbnail/790|watermark/2/text/5rG96L2m5LmL5a62/font/5b6u6L2v6ZuF6buR/fontsize/270/fill/I0ZGRkZGRg==");
//            imageList.add(image_uri);
//        }

    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 选择取车地点
     */
    @OnClick(R.id.send_car_address_text_view)
    public void onSendCarAddressTextViewClicked() {
        if (isSendTheCarToHomeCheckedView.isChecked()){
            Intent intent = new Intent(getContext(),X5WebviewActivity.class);
            startActivityForResult(intent,REQUEST_CODE_ADDRESS_FOR_MAP_SEND);
        }else {
           Intent intent = new Intent(getContext(),StopListActivity.class);
           startActivityForResult(intent, REQUEST_CODE_ADDRESS_FOR_STORE_SEND);
        }
    }

    /**
     * 选择还车地址
     */
    @OnClick(R.id.return_the_car_address_text_view)
    public void onReturnTheCarAddressTextViewClicked() {
        if (isGoHomeToTakeTheCarCheckedView.isChecked()){
            Intent intent = new Intent(getContext(),X5WebviewActivity.class);
            startActivityForResult(intent,REQUEST_CODE_ADDRESS_FOR_MAP_TAKE);
        }else {
            Intent intent = new Intent(getContext(),StopListActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADDRESS_FOR_STORE_TAKE);
        }
    }

    @OnClick(R.id.rent_the_car_start_time_text_view)
    public void onRentTheCarStartTimeTextViewClicked() {
    }

    @OnClick(R.id.rent_the_car_end_time_text_view)
    public void onRentTheCarEndTimeTextViewClicked() {
    }

    @OnClick(R.id.pick_the_car_view)
    public void onPickTheCarViewClicked() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADDRESS_FOR_MAP_SEND:
                if (data!=null){
                    addressBean= (AddressBean) data.getSerializableExtra("address");
                    String address=String.format("%s    %s",addressBean.getCityname(),addressBean.getPoiaddress()) ;
                    sendCarAddressTextView.setText(address);
                }
                break;
            case REQUEST_CODE_ADDRESS_FOR_MAP_TAKE:
                if (data!=null){
                    addressBean= (AddressBean) data.getSerializableExtra("address");
                    String address=String.format("%s    %s",addressBean.getCityname(),addressBean.getPoiaddress()) ;
                    returnTheCarAddressTextView.setText(address);
                }
                break;
            case REQUEST_CODE_ADDRESS_FOR_STORE_SEND:
                break;
            case REQUEST_CODE_ADDRESS_FOR_STORE_TAKE:
                break;
        }
    }
}
