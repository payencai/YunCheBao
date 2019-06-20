package com.example.yunchebao.yuedan.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.SelectCarTypeActivity;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;
import com.tool.WheelView;
import com.vipcenter.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/22.
 */

public class BookNewCarFragment extends BaseFragment {
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_brand)
    EditText et_brand;
    @BindView(R.id.et_color)
    EditText et_color;
    @BindView(R.id.et_note)
    EditText et_note;
    @BindView(R.id.et_type)
    TextView et_type;
    @BindView(R.id.ll_cartype)
    LinearLayout ll_cartype;
    @BindView(R.id.et_des)
    EditText et_des;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_pub)
    SuperTextView tv_pub;
    @BindView(R.id.rl_num)
    RelativeLayout rl_num;
    private List<String> nums = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.book_new_car_layout, container, false);
        commHiddenKeyboard(rootView);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    double price = 0;
    String carCategory;
    String brand;
    String color;
    String detail;
    String cartype;
    int num;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&data!=null){
            cartype=data.getStringExtra("name");
            et_type.setText(cartype);
        }
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
                tv_num.setText(wv.getSeletedItem());
                dialog.dismiss();
                num= Integer.parseInt(wv.getSeletedItem());

            }
        });
        wv.setOffset(1);
        wv.setItems(nums);
        wv.setSeletion(0);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }
    private void initView() {
        rl_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCount();
            }
        });
        if(MyApplication.getUserInfo().getCarList().size()>0){
            et_type.setText(MyApplication.getUserInfo().getCarList().get(0).getModels());
        }
        ll_cartype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), SelectCarTypeActivity.class),1);
            }
        });
        tv_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price2 = et_price.getEditableText().toString();
                if (TextUtils.isEmpty(price2)) {
                    ToastUtil.showToast(getContext(), "请输入价格");
                    return;
                }
                price = Double.parseDouble(price2);
                carCategory = et_type.getText().toString();
                color = et_color.getEditableText().toString();
                detail = et_des.getEditableText().toString();
                if (TextUtils.isEmpty(carCategory)) {
                    ToastUtil.showToast(getContext(), "输入不能为空！");
                    return;
                }

                if (TextUtils.isEmpty(color)) {
                    ToastUtil.showToast(getContext(), "输入不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(detail)) {
                    ToastUtil.showToast(getContext(), "输入不能为空！");
                    return;
                }
                if(MyApplication.isLogin)
                addOrder();
                else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
    }

    private void addOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("price", price);
        params.put("carCategory",carCategory);
        params.put("color",color);
        params.put("detail",detail);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude",MyApplication.getaMapLocation().getLatitude());
        params.put("shopNumber",tv_num.getText().toString());
        params.put("range",Integer.parseInt(et_note.getEditableText().toString()));
        Log.e("parmas",params.toString());
        HttpProxy.obtain().post(PlatformContans.Appointment.addNewCarAppointment, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    String msg=jsonObject.getString("message");
                    if(code==0){
                        ToastUtil.showToast(getContext(),"发布成功！");
                        getActivity().finish();
                    }else{
                        ToastUtil.showToast(getContext(),msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(getContext(),"发布成功",Toast.LENGTH_LONG).show();
                Log.e("result",result);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
