package com.example.yunchebao.yuedan.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.SelectCarTypeActivity;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.mediapicker.PickerActivity;
import com.payencai.library.mediapicker.PickerConfig;
import com.payencai.library.mediapicker.entity.Media;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.util.VideoUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;
import com.tool.WheelView;
import com.tool.view.GridViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.example.yunchebao.yuedan.adapter.ImageAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
public class BookRoadFragment extends Fragment {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_addr)
    EditText et_addr;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_item1)
    TextView tv_item1;
    @BindView(R.id.tv_item2)
    TextView tv_item2;
    @BindView(R.id.tv_item3)
    TextView tv_item3;
    @BindView(R.id.et_detail)
    EditText et_detail;
    @BindView(R.id.et_color)
    EditText et_color;
    @BindView(R.id.et_type)
    TextView et_type;
    @BindView(R.id.rl_cartype)
    RelativeLayout rl_cartype;
    @BindView(R.id.addressLay)
    RelativeLayout addressLay;
    @BindView(R.id.tv_public)
    TextView tv_public;
    @BindView(R.id.et_note)
    EditText et_note;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.rl_num)
    RelativeLayout rl_num;
    @BindView(R.id.rl_range)
    RelativeLayout rl_range;
    @BindView(R.id.iv_video)
    ImageView iv_video;

    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.gv_pic)
    GridViewForScrollView gv_pic;
    ImageAdapter mImageAdapter;

    public BookRoadFragment() {
        // Required empty public constructor
    }

    private List<String> nums = new ArrayList<>();
    int num;
    int range;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_road, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
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
                num = Integer.parseInt(wv.getSeletedItem());

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

    AddressBean mAddressBean;
    String address;
    String carCategory;
    String imgs;
    String video;
    String time;
    List<Uri> mSelected;
    List<String> images;
    ArrayList<Media> defaultSelect = new ArrayList<>();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && data != null) {
            mAddressBean = (AddressBean) data.getSerializableExtra("address");
            address = mAddressBean.getPoiaddress();
            tv_address.setText(address);
            Log.e("mAddressBean", mAddressBean.toString());
        }
        if (requestCode == 1 && data != null) {
            carCategory = data.getStringExtra("name");
            et_type.setText(carCategory);
        }
        if (requestCode == 3 && data != null) {
            defaultSelect = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            if (defaultSelect.size() > 0) {
                File filevideo = new File(defaultSelect.get(0).path);
                Bitmap bitmap = VideoUtil.voidToFirstBitmap(defaultSelect.get(0).path);
                iv_video.setImageBitmap(bitmap);
                iv_play.setVisibility(View.VISIBLE);
                upLoadVideo(PlatformContans.Commom.uploadVideo, filevideo);
            }
        }
    }


    private void chooseVideo() {
        Intent intent = new Intent(getContext(), PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//default image and video (Optional)
        long maxSize = 41943040L;//long long long long类型
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 10MB (Optional)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1);  //default 40 (Optional)
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //(Optional)默认选中的照片
        startActivityForResult(intent, 3);
    }

    public void upLoadVideo(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ToastUtil.showToast(getContext(),"视频太大超时");
                    }
                });
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

                try {
                    JSONObject object = new JSONObject(string);
                     String data = object.getString("data");
                    images.add(data);
                    Log.e("upload", "onResponse: " + string);
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


    private boolean checkInput() {
        boolean isOk = true;


        if (TextUtils.isEmpty(carCategory)) {
            isOk = false;
            ToastUtil.showToast(getContext(), "请选择车型");
            return  isOk;
        }
        if (TextUtils.isEmpty(et_detail.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(getContext(), "问题不能为空");
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
            ToastUtil.showToast(getContext(), "范围不能为空");
            return  isOk;
        }
        if (TextUtils.isEmpty(et_color.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(getContext(), "颜色不能为空");
            return  isOk;
        }
        if (TextUtils.isEmpty(et_addr.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(getContext(), "详细地址不能为空");
            return  isOk;
        }
        if (mAddressBean == null) {
            isOk = false;
            ToastUtil.showToast(getContext(), "车辆位置不能为空");
            return  isOk;
        }
        return isOk;
    }
    private void initView() {
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
                            .forResult(188);
                }
            }
        });
        tv_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_detail.setText(tv_item1.getText().toString());
            }
        });
        tv_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_detail.setText(tv_item2.getText().toString());
            }
        });
        tv_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_detail.setText(tv_item3.getText().toString());
            }
        });

        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });
        rl_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCount();
            }
        });

        rl_cartype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), SelectCarTypeActivity.class), 1);
            }
        });
        addressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), X5WebviewActivity.class), 2);
            }
        });
        tv_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    if(checkInput())
                    addService();
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
    }

    private void addService() {
        for (int i = 0; i < images.size(); i++) {
            imgs = imgs + "," + images.get(i);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", et_phone.getEditableText().toString());
        params.put("detail", et_detail.getEditableText().toString());
        params.put("carCategory", et_type.getText().toString());
        params.put("province", mAddressBean.getProvince() + "");
        params.put("color", et_color.getEditableText().toString());
        params.put("city", mAddressBean.getCityname() + "");
        params.put("area", mAddressBean.getDistrict() + "");
        params.put("address", mAddressBean.getPoiaddress());
        params.put("addressDetail", et_detail.getEditableText().toString());
        params.put("range", Integer.parseInt(et_note.getEditableText().toString()));
        params.put("shopNumber", Integer.parseInt(tv_num.getText().toString()));
        params.put("longitude", mAddressBean.getLatlng().getLng() + "");
        params.put("latitude", mAddressBean.getLatlng().getLat() + "");
        params.put("imgs", imgs);
        params.put("video", video);
        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.Appointment.addRoadRescueAppointment, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    String msg = jsonObject.getString("message");
                    if (code == 0) {
                        ToastUtil.showToast(getContext(), "发布成功！");
                        getActivity().finish();
                    } else {
                        ToastUtil.showToast(getContext(), msg);
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
}
