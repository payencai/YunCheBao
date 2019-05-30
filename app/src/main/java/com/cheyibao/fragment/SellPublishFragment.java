package com.cheyibao.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheyibao.SellCarInfoImproveActivity;
import com.cityselect.CityListActivity;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.SelectCarTypeActivity;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.CommonDateTools;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.xihubao.CarBrandSelectActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sdhcjhss on 2017/12/25.
 */

public class SellPublishFragment extends BaseFragment implements OnDateSetListener {
    private Context ctx;
    TimePickerDialog mDialogYearMonth;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.item1)
    TextView carInfoText;
    @BindView(R.id.item2)
    TextView cityText;
    @BindView(R.id.item3)
    TextView timeText;
    @BindView(R.id.remarkInfo)
    TextView remarkInfo;
    @BindView(R.id.item5)
    EditText et_price;
    @BindView(R.id.item4)
    EditText et_dis;
    String image;
    LoadingDialog mLoadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sell_car_publish_layout, container, false);
        ButterKnife.bind(this, rootView);
        ctx = getActivity();
        initView();
        return rootView;
    }


    /**
     * 裁剪图片
     */
    Uri photoOutputUri;

    private void startCategery() {
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mIntent.setType("image/*");
        startActivityForResult(mIntent, 3);
    }

    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output.jpg"));
        startActivityForResult(cropPhotoIntent, 4);
    }

    public void upImage(String url, File file) {
        mLoadingDialog.setLoadingText("上传中")
                .setSuccessText("上传成功")//显示加载成功时的文字
                .setFailedText("上传失败")
                .setInterceptBack(false)
                .setRepeatCount(3)
                .show();

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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.loadFailed();
                        Log.e("upload", "onResponse: " + e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    image = object.getString("data");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingDialog.loadSuccess();
                            Glide.with(getContext()).load(image).into(img);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initView() {
        mLoadingDialog=new LoadingDialog(getContext());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCategery();
            }
        });
        commHiddenKeyboard(rootView);
        String infoStr1 = "您必须保证信息的真实性，如信息与图片不符，我们会下架并通知您，提交即代表您同意";
        String infoStr2 = "《个人信息保护声明》";
        SpannableStringBuilder builder = new SpannableStringBuilder(infoStr1 + infoStr2);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.yellow_65)), infoStr1.length(), infoStr1.length() + infoStr2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        remarkInfo.setText(builder);
        timeText.setText(CommonDateTools.getCurrentDate("yyyy年MM月"));
        initTimePickerView();
    }

    private void initTimePickerView() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("上牌时间")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.YEAR_MONTH)
                .setWheelItemTextSize(12)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    String brand = data.getExtras().getString("name");
                    id = data.getExtras().getString("id");
                    id1 = data.getExtras().getString("id1");
                    id2 = data.getExtras().getString("id2");
                    id3 = data.getExtras().getString("id3");
                    Log.e("firstid",id+"----"+id1+"----"+id2+"----"+id3);
                    carInfoText.setText(brand);
                    break;
                case 2:
                    String city = data.getExtras().getString("city");
                    cityText.setText(city);
                    break;
                case 3:
                    cropPhoto(data.getData());
                    break;
                case 4:
                    File file = new File(photoOutputUri.getPath());
                    if (file.exists()) {
                        //Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                        //server_head.setImageBitmap(bitmap);
                        upImage(PlatformContans.Commom.uploadImg, file);
                    } else {
                        Toast.makeText(getContext(), "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    }

    private void alertTimePicker() {
        mDialogYearMonth.show(getChildFragmentManager(), "year_month");
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

    String id1, id2, id3, id = null;

    @OnClick({R.id.submitBtn, R.id.lay1, R.id.lay2, R.id.lay3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay1://品牌选择
                startActivityForResult(new Intent(getContext(), SelectCarTypeActivity.class), 1);

                break;
            case R.id.lay2://城市选择
                startActivityForResult(new Intent(getContext(), CityListActivity.class), 2);
                break;
            case R.id.lay3://时间选择
                if (mDialogYearMonth != null) {
                    alertTimePicker();
                }
                break;
            case R.id.submitBtn:
                Bundle bundle = new Bundle();
                if (TextUtils.isEmpty(id1)) {
                    ToastUtil.showToast(getContext(), "请选择车型");
                    return;
                }
                if (cityText.getText().toString().equals("请选择地点")) {
                    ToastUtil.showToast(getContext(), "上牌地点不能为空");
                    return;
                }
                if (TextUtils.isEmpty(et_dis.getEditableText().toString())) {
                    ToastUtil.showToast(getContext(), "请输入里程");
                    return;
                }
                if (TextUtils.isEmpty(et_price.getEditableText().toString())) {
                    ToastUtil.showToast(getContext(), "请输入价格");
                    return;
                }

                bundle.putString("id", id);
                bundle.putString("id1", id1);
                bundle.putString("id2", id2);
                bundle.putString("id3", id3);
                bundle.putString("city", cityText.getText().toString());
                bundle.putString("time", "2019-03");
                bundle.putString("dis", et_dis.getEditableText().toString());
                bundle.putString("price", et_price.getEditableText().toString());
                ActivityAnimationUtils.commonTransition(getActivity(), SellCarInfoImproveActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
        }
    }
}
