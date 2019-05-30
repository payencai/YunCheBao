package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.BottomMenuDialog;
import com.tool.GlideImageLoader;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.PhotoAdapter;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

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
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderReturnApplyActivity extends NoHttpBaseActivity {
    private Context ctx;
    PhoneOrderEntity.ItemListBean mItemListBean;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.tv_goodsname)
    TextView tv_goodsname;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_reason)
    TextView tv_reason;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.gv_photo)
    GridView gv_photo;
    @BindView(R.id.tv_price1)
    TextView tv_price1;
    @BindView(R.id.tv_price2)
    TextView tv_price2;
    @BindView(R.id.et_said)
    EditText et_said;
    private String TAG = "---Yancy---";
    PhotoAdapter mPhotoAdapter;
    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    String imgs = "";
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_return_apply_layout);
        mItemListBean= (PhoneOrderEntity.ItemListBean) getIntent().getSerializableExtra("data");
        type=getIntent().getExtras().getInt("type");
        initView();
    }
    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                path.clear();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    path.add(s);
                    upImage(PlatformContans.Commom.uploadImg, new File(s));
                }
                mPhotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };
        mPhotoAdapter = new PhotoAdapter(this, path);
        gv_photo.setAdapter(mPhotoAdapter);
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.yancy.gallerypickdemo.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, 6)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(6)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(OrderReturnApplyActivity.this);
            }
        });

    }

    private void addRefund() {
        String cause=tv_reason.getText().toString();
        String said=et_said.getEditableText().toString();
        if(TextUtils.isEmpty(cause)){
            ToastUtil.showToast(OrderReturnApplyActivity.this, "理由不能为空");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        if(!TextUtils.isEmpty(imgs)){
            if(imgs.contains(","))
              params.put("refuseImg", imgs.substring(1));
        }
        params.put("orderItemId", mItemListBean.getId());
        params.put("refuseCause",reason);
        params.put("refusePrice", mItemListBean.getPrice());
        params.put("refuseReason", said);
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.applyRefund, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(OrderReturnApplyActivity.this, "提交成功");
                        finish();
                    }else{
                        String msg=jsonObject.getString("message");
                        ToastUtil.showToast(OrderReturnApplyActivity.this, msg);
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
                Log.e("upload", "onResponse: " + imgs);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    imgs = imgs + "," + data;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "申请退款");
        ButterKnife.bind(this);
        ctx = this;
        initGallery();
        setUI();
    }
    private void setUI()
    {
        tv_num.setText("x"+mItemListBean.getNumber());
        tv_price.setText("￥"+mItemListBean.getPrice());
        tv_goodsname.setText(mItemListBean.getCommodityName());
        tv_reason.setText("");
        String images=mItemListBean.getCommodityImage();
        if (!TextUtils.isEmpty(images)) {
            if(images.contains(",")){
                images=images.split(",")[0];
            }
        }
        tv_price1.setText("￥"+mItemListBean.getPrice());
        tv_price2.setText("最多退"+mItemListBean.getPrice()+",含发货邮费¥0.00");
        Glide.with(this).load(images).into(iv_goods);
        if(type==1){
            tv_reason.setText("未收到货");
        }
    }
    private BottomMenuDialog bottomDialog;

    private void alert1Panel(Context ctx) {
        String[] items = new String[]{"未收到货", "已收到货"};
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.setTitle("货物状态");
        builder.addMenu(items[0], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //本地选择
//                picSelect();
                bottomDialog.dismiss();
            }
        });
        builder.addMenu(items[1], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
//                picCamera();
                bottomDialog.dismiss();
            }
        });
        bottomDialog = builder.create();
        bottomDialog.show();
    }
    String reason;
    private void alert2Panel(Context ctx) {

        String[] items = new String[]{"未收到货", "大小/重量与商品描述不符", "生产日期/保质期与商品描述不符", "标签/批次/包装/成分等与商品描述不符",
                "商品变质/发霉/有异物", "质量问题", "包装/商品破损"};
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.setTitle("退款原因");
        for (int i = 0; i < items.length; i++) {
            int finalI = i;
            builder.addMenu(items[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reason=items[finalI];
                    tv_reason.setText(reason);
                    bottomDialog.dismiss();
                }
            });

        }

        bottomDialog = builder.create();

        bottomDialog.show();
    }

    @OnClick({R.id.back, R.id.lay1, R.id.lay2,R.id.tv_submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
                alert1Panel(ctx);
                break;
            case R.id.lay2:
                alert2Panel(ctx);
                break;
            case R.id.tv_submit:

                addRefund();
                break;
        }
    }
}
