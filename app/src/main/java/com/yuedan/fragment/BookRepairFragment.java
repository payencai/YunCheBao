package com.yuedan.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.adapter.AttenAddressListAdapter;
import com.nohttp.sample.BaseFragment;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.WheelView;
import com.vipcenter.AddressAddActivity;
import com.vipcenter.RegisterActivity;
import com.vipcenter.model.PersonAddress;
import com.xihubao.CarBrandSelectActivity;
import com.yuedan.WashCarType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookRepairFragment extends BaseFragment {

    private static final String[] PLANETS = new String[]{"普通洗车", "特殊洗车"};
    private List<String> cartypes = new ArrayList<>();
    private Context ctx;
    @BindView(R.id.rl_cartype)
    RelativeLayout rl_cartype;
    @BindView(R.id.rl_num)
    RelativeLayout rl_num;
    @BindView(R.id.rl_washtype)
    RelativeLayout rl_washtype;
    @BindView(R.id.rl_time)
    RelativeLayout rl_time;
    @BindView(R.id.addressLay)
    RelativeLayout addressLay;
    WashCarType mWashCarType;
    @BindView(R.id.washtype)
    TextView washtype;
    @BindView(R.id.tv_cartype)
    TextView tv_cartype;
    @BindView(R.id.item1)
    SuperTextView tv_item1;
    @BindView(R.id.item2)
    SuperTextView tv_item2;
    @BindView(R.id.item3)
    SuperTextView tv_item3;
    @BindView(R.id.item4)
    SuperTextView tv_item4;
    @BindView(R.id.item5)
    SuperTextView tv_item5;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_honMoney)
    TextView tv_honMoney;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_public)
    TextView tv_public;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_note)
    EditText et_note;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.et_address)
    TextView et_address;

    @BindView(R.id.et_detail)
    EditText et_detail;
    List<WashCarType> mWashCarTypes;
    int cartype=1;
    int position=0;
    String imgs;
    String video;
    double honmoney;
    String carCategory;
    String address;
    private List<String> nums = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_book_repair, container, false);
        commHiddenKeyboard(rootView);
        ButterKnife.bind(this, rootView);
        ctx = getActivity();
        cartype = getArguments().getInt("type");
        mWashCarTypes = new ArrayList<>();
        initView();
        return rootView;
    }
    AddressBean mAddressBean;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&data!=null){
            mAddressBean= (AddressBean) data.getSerializableExtra("address");
            address=mAddressBean.getPoiaddress();
            et_address.setText(address);
            Log.e("mAddressBean",mAddressBean.toString());
        }
        if(requestCode==3&&data!=null){
            carCategory=data.getStringExtra("name");
            tv_cartype.setText(carCategory);
        }
    }

    public static BookRepairFragment newInstance(int type) {
        BookRepairFragment bookWashCarFragment = new BookRepairFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bookWashCarFragment.setArguments(bundle);
        return bookWashCarFragment;
    }

    private void setUI() {

        tv_item1.setText(mWashCarType.getEarnestMoneyOne()+"元");
        tv_item2.setText(mWashCarType.getEarnestMoneyTwo()+"元");
        tv_item3.setText(mWashCarType.getEarnestMoneyThree()+"元");
        tv_item4.setText(mWashCarType.getEarnestMoneyFour()+"元");
        tv_item5.setText(mWashCarType.getEarnestMoneyFive()+"元");
        washtype.setText(mWashCarType.getName());
        tv_price.setText(mWashCarType.getPrice()+"/次");
        if(mWashCarType.getEarnestMoneyOne()<=0){
            tv_item1.setText("无");
        }
        honmoney=mWashCarType.getEarnestMoneyOne();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", cartype);
        HttpProxy.obtain().get(PlatformContans.Commom.getAppointmentCategoryListByApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        WashCarType washCarType = new Gson().fromJson(item.toString(), WashCarType.class);
                        cartypes.add(washCarType.getName());
                        mWashCarTypes.add(washCarType);
                    }
                    if (mWashCarTypes.size() > 0) {
                        mWashCarType = mWashCarTypes.get(0);
                    }
                    setUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void showSelectCount() {
        for (int i = 1; i <=10 ; i++) {
            nums.add(i+"");
        }
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_washcar_type, null);

        final Dialog dialog = new Dialog(getContext(), R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        WheelView wv = (WheelView) view.findViewById(R.id.wheelview);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tv_num.setText(wv.getSeletedItem());
            }
        });
        wv.setOffset(1);
        wv.setItems(nums);
        wv.setSeletion(0);
//        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
//            @Override
//            public void onSelected(int selectedIndex, String item) {
//                position=selectedIndex-1;
//                Log.d("ddd", "[Dialog]selectedIndex: " + position + ", item: " + item);
//            }
//        });
        //wv.setSeletion(0);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }
    private void addService(){
        Map<String,Object> params=new HashMap<>();
        params.put("telephone",et_phone.getEditableText().toString());
        params.put("type",cartype);
        params.put("detail",et_detail.getEditableText().toString());
        params.put("address",mAddressBean.getPoiaddress());
        params.put("addressDetail",et_detail.getEditableText().toString());
        params.put("price",mWashCarType.getPrice());
        params.put("earnestMoney",honmoney);
        params.put("appointmentTime",tv_time.getText().toString());
        params.put("category", washtype.getText().toString());
        params.put("carCategory", carCategory);
        params.put("range", Integer.parseInt(et_note.getEditableText().toString()));
        params.put("shopNumber", Integer.parseInt(tv_num.getText().toString()));
        params.put("longitude",mAddressBean.getLatlng().getLng()+"");
        params.put("latitude",mAddressBean.getLatlng().getLat()+"");
        params.put("province",mAddressBean.getProvince()+"");
        params.put("city",mAddressBean.getCityname()+"");
        params.put("area",mAddressBean.getDistrict()+"");
        Log.e("result",params.toString());
        HttpProxy.obtain().post(PlatformContans.Commom.addWashRepairAppointment, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                //dialog.dismiss();
                Toast.makeText(getContext(),"发布成功",Toast.LENGTH_LONG).show();
                Log.e("result",result);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void showConfirmDialog(){
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_confirm_add, null);
        TextView tv_confirm= (TextView) view.findViewById(R.id.tv_confirm);
        final Dialog dialog = new Dialog(getContext(),R.style.alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // addService();
            }
        });
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        Display display= window.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (display.getWidth()*0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }
    private void initView() {
        //initDatePicker();
        tv_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    if(TextUtils.isEmpty(tv_time.getText().toString())){
                        return;
                    }
                    if(TextUtils.isEmpty(et_phone.getEditableText().toString())){
                        return;
                    }
                    if(TextUtils.isEmpty(et_address.getText().toString())){
                        return;
                    }
                    addService();
                    //showConfirmDialog();
                }else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        addressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),X5WebviewActivity.class),2);
            }
        });
        tv_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney=mWashCarType.getEarnestMoneyOne();
                tv_honMoney.setText("诚意金：¥"+honmoney);
                tv_item1.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney=mWashCarType.getEarnestMoneyTwo();
                tv_honMoney.setText("诚意金：¥"+honmoney);
                tv_item2.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney=mWashCarType.getEarnestMoneyThree();
                tv_honMoney.setText("诚意金：¥"+honmoney);
                tv_item3.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney=mWashCarType.getEarnestMoneyFour();
                tv_honMoney.setText("诚意金：¥"+honmoney);
                tv_item4.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney=mWashCarType.getEarnestMoneyFive();
                tv_honMoney.setText("诚意金：¥"+honmoney);
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.yellow_64));
            }
        });
        rl_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCount();
            }
        });
        rl_washtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWashType();
            }
        });
        rl_cartype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), CarBrandSelectActivity.class),3);
            }
        });
        rl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar startDate = Calendar.getInstance();
                startDate.set(1970, 1, 1);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2049, 12, 31);

                // customDatePicker.show(start);
                TimePickerView timePickerView = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        tv_time.setText( sd.format(date));
                    }
                }).setType(new boolean[]{true, true, true, true, true, false})//只显示年月日时分不显示秒
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("时间选择")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(false)//是否循环滚动
                        .setTitleColor(Color.WHITE)//标题文字颜色
                        .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                        .setCancelColor(Color.WHITE)//取消按钮文字颜色
                        .setTitleBgColor(Color.BLUE)//标题背景颜色
                        .setBgColor(Color.WHITE)//滚轮背景颜色
                        .setRangDate(startDate, endDate)//默认是1900-2100年
                        .setDate(Calendar.getInstance())// 打开默认选择系统时间
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();

                timePickerView.show();

            }
        });
        getData();
    }

    private Dialog dialog;

    public void attenAddressToast() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.atten_good_address_select, null);
        UIControlUtils.UITextControlsUtils.setUIText(view.findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "车辆位置");
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        ll.getBackground().setAlpha(20);
        dialog = new Dialog(getActivity(), R.style.DialogStyleNoTitle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<PersonAddress> list = new ArrayList<>();
        AttenAddressListAdapter adapter = new AttenAddressListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.addAddressBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                startActivityForResult(new Intent(getActivity(), AddressAddActivity.class), 1);
            }
        });


    }

    private void showWashType() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_washcar_type, null);

        final Dialog dialog = new Dialog(getContext(), R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        WheelView wv = (WheelView) view.findViewById(R.id.wheelview);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mWashCarType=mWashCarTypes.get(position);
                setUI();
            }
        });
        wv.setOffset(1);
        wv.setItems(cartypes);
        wv.setSeletion(position);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                //position=selectedIndex-1;
                // Log.d("ddd", "[Dialog]selectedIndex: " + position + ", item: " + item);
            }
        });
        //wv.setSeletion(0);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }

}
