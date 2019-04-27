package com.cheyibao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.util.RentCarUtils;
import com.cheyibao.view.RentCarAddressView;
import com.cheyibao.view.RentCarTimeView;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HighCarModelsDetailActivity extends AppCompatActivity {

    public static void startActivity(RentCarModel rentCarModel, Activity activity){
        Intent intent = new Intent(activity,HighCarModelsDetailActivity.class);
        intent.putExtra("rent_car_model",rentCarModel);
        activity.startActivity(intent);
    }

    public static void startActivity(RentCarModel rentCarModel, Fragment fragment){
        Intent intent = new Intent(fragment.getContext(),HighCarModelsDetailActivity.class);
        intent.putExtra("rent_car_model",rentCarModel);
        fragment.startActivity(intent);
    }

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;
    @BindView(R.id.car_model_banner_view)
    ImageView carModelBannerView;
    @BindView(R.id.car_model_name_view)
    TextView carModelNameView;
    @BindView(R.id.car_model_car_category_view)
    TextView carModelCarCategoryView;
    @BindView(R.id.car_model_is_auto_driver_view)
    TextView carModelIsAutoDriverView;
    @BindView(R.id.rent_car_time_View)
    RentCarTimeView rentCarTimeView;
    @BindView(R.id.rent_Car_address_view)
    RentCarAddressView rentCarAddressView;
    @BindView(R.id.confirm_button)
    CardView confirmButton;

    private RentCarModel rentCarModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_car_models_detail);
        ButterKnife.bind(this);
        rentCarModel = getIntent().getParcelableExtra("rent_car_model");
        title.setText(rentCarModel.getCarTategory());
        initView();
    }

    private void initView() {
        Glide.with(this).load(rentCarModel.getImage()).into(carModelBannerView);
        carModelNameView.setText(rentCarModel.getBrand());
        carModelCarCategoryView.setText(rentCarModel.getCarTategory());
        String seat = TextUtils.isEmpty(rentCarModel.getSeat())?"":rentCarModel.getSeat().replace("座", "");
        carModelIsAutoDriverView.setText(String.format("%s/%s座", rentCarModel.getVariableBox(), seat));
        rentCarAddressView.init(rentCarModel,false,false);
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmButtonClicked() {
        if (RentCarUtils.rentCarInfo==null){
            RentCarUtils.rentCarInfo = new HashMap<>();
        }
        RentCarUtils.rentCarInfo.clear();
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_CAR_MODEL,rentCarModel);
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_START_TIME,rentCarTimeView.getStartTime());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_END_TIME,rentCarTimeView.getEndTime());
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_IS_TO_HOME_SERVICE,false);
        RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_SHOP,rentCarAddressView.getRentShop());
        Intent intent = new Intent(this, RentCarOrderActivity.class);
        startActivity(intent);
    }
}
