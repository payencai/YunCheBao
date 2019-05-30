package com.xihubao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.cityselect.CityListActivity;
import com.example.yunchebao.R;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.CommonDateTools;
import com.tool.UIControlUtils;
import com.vipcenter.RegisterActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/12.
 */

public class Shop4SInfoActivity extends FragmentActivity implements OnDateSetListener {
    TimePickerDialog mDialogYearMonth;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
    @BindView(R.id.item1)
    TextView carInfoText;
    @BindView(R.id.item2)
    TextView cityText;
    @BindView(R.id.item3)
    TextView timeText;
    @BindView(R.id.item4)
    EditText item4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_4s_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "4S保养");
        ButterKnife.bind(this);


        timeText.setText(CommonDateTools.getCurrentDate("yyyy年MM月"));
        initTimePickerView();
    }

    private void initTimePickerView() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("购车时间")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.YEAR_MONTH)
                .setWheelItemTextSize(12)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    String brand = data.getExtras().getString("name");
                    carInfoText.setText(brand);
                    break;
                case 2:
                    String city = data.getExtras().getString("city");
                    cityText.setText(city);
                    break;
            }
        }

    }

    private void alertTimePicker() {
        mDialogYearMonth.show(getSupportFragmentManager(), "year_month");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
        String text = getDateToString(millseconds);
        timeText.setText(text);
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @OnClick({R.id.back, R.id.submitBtn, R.id.lay1, R.id.lay2, R.id.lay3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1://品牌选择
                if (MyApplication.isLogin)
                    startActivityForResult(new Intent(Shop4SInfoActivity.this, CarBrandSelectActivity.class), 1);
                else {
                    startActivity(new Intent(Shop4SInfoActivity.this, RegisterActivity.class));
                }
                break;
            case R.id.lay2://城市选择
                startActivityForResult(new Intent(Shop4SInfoActivity.this, CityListActivity.class), 2);
                break;
            case R.id.lay3://时间选择
                if (mDialogYearMonth != null) {
                    alertTimePicker();
                }
                break;
            case R.id.submitBtn:
                Bundle bundle = new Bundle();
                bundle.putString("car", carInfoText.getText().toString());
                bundle.putString("city", cityText.getText().toString());
                bundle.putString("time", timeText.getText().toString());
                bundle.putString("dis", item4.getEditableText().toString());
                ActivityAnimationUtils.commonTransition(Shop4SInfoActivity.this, Shop4SListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
        }
    }


}
