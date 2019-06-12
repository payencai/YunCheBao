package com.vipcenter;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.SelectCarTypeActivity;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tool.GlideImageEngine;
import com.vipcenter.model.MyCar;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.xihubao.CarBrandSelectActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MycarActivity extends AppCompatActivity {
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.rl_cartype)
    RelativeLayout rl_cartype;
    @BindView(R.id.tv_cartype)
    TextView tv_cartype;

    @BindView(R.id.iv_id1)
    ImageView iv_id1;
    @BindView(R.id.iv_id2)
    ImageView iv_id2;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.et_file)
    EditText et_file;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.back)
    ImageView back;
    String carNumber;
    String drivingBackImg;
    String drivingPositiveImg;
    String carLogo;
    String cartype;
    int flag=0;
    MyCar mMyCar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
        ButterKnife.bind(this);
        mMyCar= (MyCar) getIntent().getSerializableExtra("data");
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    String brand = data.getExtras().getString("name");
                    carLogo=data.getExtras().getString("logo");
                    tv_cartype.setText(brand);
                    break;
                case 201:
                case 202:
                    List<String> pathList = Matisse.obtainPathResult(data);

                    File file= null;
                    try {
                        file = new Compressor(this).setQuality(75).compressToFile(new File(pathList.get(0)));
                        upImage(PlatformContans.Commom.uploadImg,file);
                        Log.e("path",pathList.get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    break;

            }
        }

    }

    public void upImage(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("upload", "onResponse: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    if (flag == 1) {
                        drivingPositiveImg = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MycarActivity.this).load(drivingPositiveImg).into(iv_id1);
                            }
                        });

                    } else {
                        drivingBackImg = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MycarActivity.this).load(drivingBackImg).into(iv_id2);
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initView() {

        if(mMyCar!=null){
            drivingPositiveImg=mMyCar.getDrivingPositiveImg();
            drivingBackImg=mMyCar.getDrivingBackImg();
            carLogo=mMyCar.getCarLogo();
            cartype=mMyCar.getModels();
            tv_cartype.setText(cartype);
            tv_number.setText(mMyCar.getPlateNumber().substring(0,2));
            et_number.setText(mMyCar.getPlateNumber().substring(2));
            Glide.with(this).load(mMyCar.getDrivingPositiveImg()).into(iv_id1);
            Glide.with(this).load(mMyCar.getDrivingBackImg()).into(iv_id2);
        }
        tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_cartype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivityForResult(new Intent(MycarActivity.this, SelectCarTypeActivity.class), 1);
                else {
                    startActivity(new Intent(MycarActivity.this, RegisterActivity.class));
                }
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=MyApplication.getUserInfo().getName();
                cartype=tv_cartype.getText().toString();
                carNumber=tv_number.getText().toString()+et_number.getEditableText().toString();
                if(TextUtils.equals(cartype,"请选择车型")){
                    ToastUtil.showToast(MycarActivity.this,"请选择车型");
                    return;
                }
                if(TextUtils.isEmpty(et_number.getEditableText().toString())){
                    ToastUtil.showToast(MycarActivity.this,"请输入车牌号");
                    return;
                }
                if(TextUtils.isEmpty(drivingBackImg)){
                    ToastUtil.showToast(MycarActivity.this,"请上传身份证照片");
                    return;
                }
                if(TextUtils.isEmpty(drivingPositiveImg)){
                    ToastUtil.showToast(MycarActivity.this,"请上传身份证照片");
                    return;
                }
                if(mMyCar==null)
                    submit(cartype,carNumber,name);
                else{
                    update(cartype,carNumber,name);
                }
            }
        });
        iv_id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                selectPic(201);
            }
        });
        iv_id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                selectPic(202);
            }
        });
    }
    private void selectPic(int code){
        Matisse
                .from(this)
                //选择视频和图片
                //选择图片
                .choose(MimeType.ofImage())
                //选择视频

                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .capture(true)
                .captureStrategy(new CaptureStrategy(true,"com.yancy.gallerypickdemo.fileprovider"))
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                .maxSelectable(1)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
                //黑色主题
                .theme(R.style.Matisse_Dracula)
                //Glide加载方式
                //Picasso加载方式
                .imageEngine(new GlideImageEngine())
                //请求码
                .forResult(code);
    }
    private void update(String models,String plateNumber,String userName){
        Map<String,Object> params=new HashMap<>();
        params.put("models",models);
        params.put("id",mMyCar.getId());
        params.put("plateNumber",plateNumber);
        params.put("fileNumber",et_file.getEditableText().toString());
        params.put("userName",userName);
        params.put("drivingBackImg",drivingBackImg);
        params.put("drivingPositiveImg",drivingPositiveImg);
        params.put("carLogo",carLogo);

        HttpProxy.obtain().post(PlatformContans.DrivingLicense.editByUser,MyApplication.token, params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject res=new JSONObject(result);
                    int code=res.getInt("resultCode");
                    if(code==0){
                        ToastUtil.showToast(MycarActivity.this,"已成功提交申请");
                        finish();
                    }else{
                        String msg=res.getString("message");
                        ToastUtil.showToast(MycarActivity.this,msg);
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
    private void submit(String models,String plateNumber,String userName){
        Map<String,Object> params=new HashMap<>();
        params.put("models",models);
        params.put("plateNumber",plateNumber);
        params.put("userName",userName);
        params.put("drivingBackImg",drivingBackImg);
        params.put("drivingPositiveImg",drivingPositiveImg);
        params.put("carLogo",carLogo);
        String json=new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Commom.adddrivingLicense, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",json+result);
                try {
                    JSONObject res=new JSONObject(result);
                    int code=res.getInt("resultCode");
                    if(code==0){
                        ToastUtil.showToast(MycarActivity.this,"已成功提交申请");
                        finish();
                    }else{
                        String msg=res.getString("message");
                        ToastUtil.showToast(MycarActivity.this,msg);
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
    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_car_number, null);
        WheelView w1 = (WheelView) view.findViewById(R.id.main_wheelview);
        WheelView w2 = (WheelView) view.findViewById(R.id.sub_wheelview);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        w1.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {

            }
        });
        w2.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {

            }
        });
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        w1.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        w1.setSkin(WheelView.Skin.Holo); // common皮肤
        w1.setWheelData(getCity());  // 数据集合
        w2.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        w2.setSkin(WheelView.Skin.Holo); // common皮肤
        w2.setWheelData(getNumber());  // 数据集合
        Dialog bottomDialog = new Dialog(this, R.style.dialog);
        bottomDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = (String) w1.getSelectionItem();
                String number = (String) w2.getSelectionItem();
                tv_number.setText(city + number);
                bottomDialog.dismiss();
            }
        });
    }

    /*返回城市字母缩写*/
    public static ArrayList<String> getNumber() {
        ArrayList<String> c = new ArrayList();
        c.add("A");
        c.add("B");
        c.add("C");
        c.add("D");
        c.add("E");
        c.add("F");
        c.add("G");
        c.add("H");
        c.add("I");
        c.add("J");
        c.add("K");
        c.add("L");
        c.add("M");
        c.add("N");
        c.add("O");
        c.add("P");
        c.add("Q");
        c.add("R");
        c.add("S");
        c.add("T");
        c.add("U");
        c.add("V");
        c.add("W");
        c.add("X");
        c.add("Y");
        c.add("Z");
        return c;
    }

    /*返回省份缩写的集合*/
    public static ArrayList<String> getCity() {
        ArrayList<String> a = new ArrayList();
        a.add("赣");
        a.add("粤");
        a.add("京");
        a.add("沪");
        a.add("鄂");
        a.add("湘");
        a.add("川");
        a.add("渝");
        a.add("闽");
        a.add("晋");
        a.add("黑");
        a.add("津");
        a.add("浙");
        a.add("豫");
        a.add("贵");
        a.add("青");
        a.add("琼");
        a.add("宁");
        a.add("蒙");
        a.add("吉");
        a.add("冀");
        a.add("苏");
        a.add("皖");
        a.add("桂");
        a.add("云");
        a.add("陕");
        a.add("甘");
        a.add("藏");
        a.add("新");
        a.add("辽");
        a.add("鲁");
        return a;
    }

}
