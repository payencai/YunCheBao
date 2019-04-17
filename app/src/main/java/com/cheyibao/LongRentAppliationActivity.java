package com.cheyibao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.view.RentCarAddressView;
import com.cheyibao.view.RentCarTimeView;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LongRentAppliationActivity extends AppCompatActivity {

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
    @BindView(R.id.rent_Car_address_view)
    RentCarAddressView rentCarAddressView;
    @BindView(R.id.rent_car_time_View)
    RentCarTimeView rentCarTimeView;
    @BindView(R.id.select_car_model_view)
    TextView selectCarModelView;
    @BindView(R.id.select_shop_count)
    TextView selectShopCount;
    @BindView(R.id.contact_person_name_et)
    EditText contactPersonNameEt;
    @BindView(R.id.contact_person_phone_et)
    EditText contactPersonPhoneEt;
    @BindView(R.id.identify_card_et)
    EditText identifyCardEt;
    @BindView(R.id.remarks_et)
    EditText remarksEt;
    @BindView(R.id.mergency_contact_person_name_et)
    EditText mergencyContactPersonNameEt;
    @BindView(R.id.emergency_contact_phone_et)
    EditText emergencyContactPhoneEt;
    @BindView(R.id.submit_application_tv)
    TextView submitApplicationTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_rent_appliation);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.select_car_model_view)
    public void onSelectCarModelViewClicked() {
    }

    @OnClick(R.id.select_shop_count)
    public void onSelectShopCountClicked() {
    }

    @OnClick(R.id.submit_application_tv)
    public void onSubmitApplicationTvClicked() {
    }
}
