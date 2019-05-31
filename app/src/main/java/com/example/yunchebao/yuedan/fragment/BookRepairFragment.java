package com.example.yunchebao.yuedan.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.SelectCarTypeActivity;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.mediapicker.PickerActivity;
import com.payencai.library.mediapicker.PickerConfig;
import com.payencai.library.mediapicker.entity.Media;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.util.VideoUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;
import com.tool.StringUtils;
import com.tool.WheelView;
import com.tool.view.GridViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.example.yunchebao.yuedan.model.WashCarType;
import com.example.yunchebao.yuedan.adapter.ImageAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookRepairFragment extends BaseFragment implements OnDateSetListener {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
    @BindView(R.id.item6)
    SuperTextView tv_item6;
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
    @BindView(R.id.iv_video)
    ImageView iv_video;

    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.gv_pic)
    GridViewForScrollView gv_pic;
    ImageAdapter mImageAdapter;
    List<WashCarType> mWashCarTypes;
    int cartype = 1;
    int position = 0;
    String imgs;
    String video;
    double honmoney;
    String carCategory;
    String address;
    List<Uri> mSelected;
    List<String> images;
    TimePickerDialog mTimePickerDialog;
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
    ArrayList<Media> defaultSelect = new ArrayList<>();

    private void initTimePickerView() {
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("预约时间")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.ALL)
                .setWheelItemTextSize(12)
                .build();

    }

    private void chooseVideo() {
        Intent intent = new Intent(getContext(), PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//default image and video (Optional)
        long maxSize = 10485760L;//long long long long类型
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 10MB (Optional)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1);  //default 40 (Optional)
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //(Optional)默认选中的照片
        startActivityForResult(intent, 4);
    }

    public void upLoadVideo(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file",
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
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
                    video = data;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_play.setVisibility(View.VISIBLE);
                        }
                    });
                    ///
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
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
                    images.add(data);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void setImages(Intent data) {

        mSelected = Matisse.obtainResult(data);
        for (int i = 0; i < mSelected.size(); i++) {
            Log.e("images", Matisse.obtainPathResult(data).get(i));
            File fileByUri = FileUtil.getFileByUri(Matisse.obtainResult(data).get(i), getContext());
            Luban.with(getContext())
                    .load(fileByUri)
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            //evaluationBeans.get(mTempPosition).getEvaluationImages().add(0,file);
                            upImage(PlatformContans.Commom.uploadImg, file);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && data != null) {
            mAddressBean = (AddressBean) data.getSerializableExtra("address");
            address = mAddressBean.getPoiaddress();
            et_address.setText(address);
            Log.e("mAddressBean", mAddressBean.toString());
        }
        if (requestCode == 3 && data != null) {
            carCategory = data.getStringExtra("name");
            tv_cartype.setText(carCategory);
        }
        if (requestCode == 4 && data != null) {
            defaultSelect = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            if (defaultSelect.size() > 0) {
                Bitmap bitmap = VideoUtil.voidToFirstBitmap(defaultSelect.get(0).path);
                iv_video.setImageBitmap(bitmap);
                File filevideo = new File(defaultSelect.get(0).path);
                upLoadVideo(PlatformContans.Commom.uploadVideo, filevideo);
            }
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

        tv_item1.setText((int) mWashCarType.getEarnestMoneyOne() + "元");
        tv_item2.setText((int) mWashCarType.getEarnestMoneyTwo() + "元");
        tv_item3.setText((int) mWashCarType.getEarnestMoneyThree() + "元");
        tv_item4.setText((int) mWashCarType.getEarnestMoneyFour() + "元");
        tv_item5.setText((int) mWashCarType.getEarnestMoneyFive() + "元");
        washtype.setText(mWashCarType.getName());
        tv_price.setText(mWashCarType.getPrice() + "");

        honmoney = mWashCarType.getEarnestMoneyOne();
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
        for (int i = 1; i <= 10; i++) {
            nums.add(i + "");
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

    private void addService() {

        imgs= StringUtils.listToString2(images,',');
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", et_phone.getEditableText().toString());
        params.put("type", cartype);
        params.put("detail", et_detail.getEditableText().toString());
        params.put("address", mAddressBean.getPoiaddress());
        params.put("addressDetail", et_detail.getEditableText().toString());
        params.put("price", mWashCarType.getPrice());
        params.put("earnestMoney", honmoney);
        params.put("appointmentTime", tv_time.getText().toString() + ":00");
        params.put("category", washtype.getText().toString());
        params.put("carCategory", carCategory);
        params.put("range", Integer.parseInt(et_note.getEditableText().toString()));
        params.put("shopNumber", Integer.parseInt(tv_num.getText().toString()));
        params.put("longitude", mAddressBean.getLatlng().getLng() + "");
        params.put("latitude", mAddressBean.getLatlng().getLat() + "");
        params.put("province", mAddressBean.getProvince() + "");
        params.put("city", mAddressBean.getCityname() + "");
        params.put("area", mAddressBean.getDistrict() + "");
        params.put("imgs", imgs);
        params.put("video", video);
        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.Appointment.addWashRepairAppointment, MyApplication.token, params, new ICallBack() {
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
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void showConfirmDialog() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_confirm_add, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        final Dialog dialog = new Dialog(getContext(), R.style.alert_dialog);
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
        Display display = window.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (display.getWidth() * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void initView() {
        //initDatePicker();
        initTimePickerView();
        images = new ArrayList<>();
        images.add("");
        mImageAdapter = new ImageAdapter(getContext(), images);
        gv_pic.setAdapter(mImageAdapter);
        gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    images.clear();
                    mImageAdapter.notifyDataSetChanged();
                    images.add("");
                    Matisse.from(getActivity())
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(4)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideImageEngine())
                            .forResult(189);
                }
            }
        });
        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });
        tv_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    if (checkInput()) {
                        addService();
                    }
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        addressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), X5WebviewActivity.class), 2);
            }
        });
        tv_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney = mWashCarType.getEarnestMoneyOne();
                tv_honMoney.setText("诚意金：¥" + honmoney);
                tv_item1.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
                tv_item6.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney = mWashCarType.getEarnestMoneyTwo();
                tv_honMoney.setText("诚意金：¥" + honmoney);
                tv_item2.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
                tv_item6.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney = mWashCarType.getEarnestMoneyThree();
                tv_honMoney.setText("诚意金：¥" + honmoney);
                tv_item3.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
                tv_item6.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney = mWashCarType.getEarnestMoneyFour();
                tv_honMoney.setText("诚意金：¥" + honmoney);
                tv_item4.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
                tv_item6.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney = mWashCarType.getEarnestMoneyFive();
                tv_honMoney.setText("诚意金：¥" + honmoney);
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.yellow_64));
                tv_item6.setTextColor(getResources().getColor(R.color.black_33));
            }
        });
        tv_item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                honmoney = 0;
                tv_honMoney.setText("诚意金：¥" + honmoney);
                tv_item1.setTextColor(getResources().getColor(R.color.black_33));
                tv_item2.setTextColor(getResources().getColor(R.color.black_33));
                tv_item3.setTextColor(getResources().getColor(R.color.black_33));
                tv_item4.setTextColor(getResources().getColor(R.color.black_33));
                tv_item5.setTextColor(getResources().getColor(R.color.black_33));
                tv_item6.setTextColor(getResources().getColor(R.color.yellow_64));
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
                startActivityForResult(new Intent(getContext(), SelectCarTypeActivity.class), 3);
            }
        });
        rl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.show(getFragmentManager(), "all");
            }
        });
        getData();
    }

    private boolean checkInput() {
        boolean isOk = true;
        if (TextUtils.equals("请选择时间", tv_time.getText().toString())) {
            isOk = false;
            ToastUtil.showToast(getContext(), "请选择时间");
            return  isOk;
        }
        if (TextUtils.isEmpty(et_detail.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(getContext(), "详情不能为空");

            return  isOk;
        }
        if (TextUtils.isEmpty(carCategory)) {
            isOk = false;
            ToastUtil.showToast(getContext(), "请选择车型");
            return  isOk;
        }
        if (TextUtils.isEmpty(et_phone.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(getContext(), "手机号不能为空");
            return  isOk;
        }
        if(!et_phone.getEditableText().toString().substring(0,1).equals("1")){
            isOk=false;
            ToastUtil.showToast(getContext(),"请手机号格式不正确");
            return  isOk;
        }
        if (TextUtils.isEmpty(et_note.getEditableText().toString())) {
            isOk = false;
            return  isOk;
        }
        if (mAddressBean == null) {
            isOk = false;
            ToastUtil.showToast(getContext(), "车辆位置不能为空");
            return  isOk;
        }
        return isOk;
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
                mWashCarType = mWashCarTypes.get(position);
                setUI();
            }
        });
        wv.setOffset(1);
        wv.setItems(cartypes);
        wv.setSeletion(position);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                position = selectedIndex - 1;
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

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        tv_time.setText(text);
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

}
