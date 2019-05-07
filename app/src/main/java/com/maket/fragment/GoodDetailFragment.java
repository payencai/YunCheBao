package com.maket.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.maket.GoodDetailActivity;
import com.maket.SinglePayActivity;
import com.maket.adapter.AttenAddressListAdapter;
import com.maket.adapter.ConfigAdapter;
import com.maket.adapter.ConfigSizeAdapter;
import com.maket.adapter.GoodsCommentImageAdapter;
import com.maket.model.GoodDetail;
import com.maket.model.GoodParam;
import com.maket.model.GoodsComment;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.view.GridViewForScrollView;
import com.tool.view.HorizontalListView;
import com.vipcenter.AddressAddActivity;
import com.maket.OrderConfirmActivity;
import com.vipcenter.RegisterActivity;
import com.vipcenter.ShopMainListActivity;
import com.vipcenter.model.PersonAddress;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import go.error;
import io.rong.imkit.RongIM;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodDetailFragment extends BaseFragment {
    private Context ctx;
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    Banner banner;
    @BindView(R.id.my_scrollview)
    PullToRefreshScrollView pullToRefreshScrollView;
    @BindView(R.id.priceText)
    TextView priceText;
    @BindView(R.id.originalPriceText)
    TextView originalPriceText;
    @BindView(R.id.iv_heart)
    ImageView iv_heart;
    @BindView(R.id.tv_selectParams)
    TextView tv_selectParams;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_commentNum)
    TextView tv_commentNum;
    @BindView(R.id.sr_score)
    SimpleRatingBar sr_score;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.sd_head)
    SimpleDraweeView sd_head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.item_time)
    TextView item_time;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.srb_score)
    SimpleRatingBar srb_score;
    @BindView(R.id.imgList)
    GridViewForScrollView hL_img;
    GoodsCommentImageAdapter mGoodsCommentImageAdapter;
    OnTabChangeListener listener;
    GoodDetail mGoodDetail;
    List<String> images = new ArrayList<>();
    PersonAddress mPersonAddress;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.maket_good_detail_layout, container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getContext()).load((String) path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }


    public void setUIData() {
        String[] urls = mGoodDetail.getCommodityImage().split(",");
        for (int i = 0; i < urls.length; i++) {
            images.add(urls[i]);
        }
        initBanner();
        priceText.setText("￥" + mGoodDetail.getDiscountPrice());
        originalPriceText.setText("专柜价: ￥" + mGoodDetail.getOriginalPrice());
        originalPriceText.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        tv_commentNum.setText("用户评价(" + mGoodDetail.getCommentData().getNumber() + ")");
        tv_name.setText(mGoodDetail.getName());
        sr_score.setRating((float) mGoodDetail.getCommentData().getScore());
        tv_score.setText(mGoodDetail.getCommentData().getScore() + "分");
    }

    public void addToShopCar() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        GoodDetailActivity goodDetailActivity = (GoodDetailActivity) getActivity();
        String firstId = mGoodParam.getId();
        String secondId = goodSize.getId();
        String commodityId = goodDetailActivity.getGoodList().getId();
        Map<String, Object> params = new HashMap<>();
        params.put("commodityId", commodityId);
        params.put("number", count);
        params.put("firstSpecificationId", firstId);
        params.put("secondSpecificationId", secondId);
        Log.e("params", params.toString());
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addShoppingCar,
                token, params, new ICallBack() {
                    @Override
                    public void OnSuccess(String result) {
                        Log.e("data", result);
                        ToastUtil.showToast(getContext(), "添加成功");
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
    }

    private void getComment() {
        GoodDetailActivity goodDetailActivity = (GoodDetailActivity) getActivity();
        Map<String, Object> params = new HashMap<>();
        params.put("commodityId", goodDetailActivity.getGoodList().getId());
        params.put("page", 1);
        params.put("type", 1);
        HttpProxy.obtain().get(PlatformContans.GoodsOrder.getGoodsComment, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodsComment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<String> imgList = new ArrayList<>();

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodsComment goodsComment = new Gson().fromJson(item.toString(), GoodsComment.class);
                        if (i == 0) {
                            sd_head.setImageURI(Uri.parse(goodsComment.getHeadPortrait()));
                            content.setText(goodsComment.getContent());
                            name.setText(goodsComment.getNickName());
                            item_time.setText(goodsComment.getCreateTime());
                            srb_score.setRating(goodsComment.getScore());
                            if (!TextUtils.isEmpty(goodsComment.getImgs())) {
                                String[] list = goodsComment.getImgs().split(",");
                                for (int j = 0; j < list.length; j++) {
                                    imgList.add(list[j]);
                                }
                            }
                            mGoodsCommentImageAdapter = new GoodsCommentImageAdapter(getContext(), imgList);
                            hL_img.setAdapter(mGoodsCommentImageAdapter);
                        }
                    }
                    if (data.length() > 0) {
                        ll_comment.setVisibility(View.VISIBLE);
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

    private void init() {
        ctx = getActivity();

        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (listener != null) {
                    listener.changeTab(1);
                }
            }
        });
        ll_comment.setVisibility(View.GONE);


        isCollect();
        getDetail();
        getAddress(1);
        getSizeAndColor();
        getComment();

    }

    /**
     * 自定义Toast信息显示面板,我要报名
     *
     * @Title: commonToastDefined
     * @Description: TODO
     * @param @param text
     * @return void
     * @throws
     */
    Dialog dialog;
    List<GoodParam> listColor = new ArrayList<>();
    List<GoodParam.SecondSpecificationsBean> listSize = new ArrayList<>();
    ConfigAdapter adapterColor;
    ConfigSizeAdapter adapterSize;
    TextView tv_price;
    TextView tv_num;
    int count = 1;
    GoodParam.SecondSpecificationsBean goodSize;
    GoodParam mGoodParam;
    TextView tv_value;
    int originCount = 0;

    public void attenConfigToast() {
        GoodDetailActivity goodDetailActivity = (GoodDetailActivity) getActivity();
        View view = getActivity().getLayoutInflater().inflate(R.layout.atten_good_config_select, null);
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.img);
        ImageView cancelBtn = (ImageView) view.findViewById(R.id.cancelBtn);
        tv_price = (TextView) view.findViewById(R.id.priceText);
        tv_num = (TextView) view.findViewById(R.id.tv_num);
        TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        TextView tv_add = (TextView) view.findViewById(R.id.tv_add);
        tv_value = (TextView) view.findViewById(R.id.tv_count);

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(tv_value.getText().toString());
                if (count > 1) {
                    count--;
                    originCount++;
                    tv_value.setText(count + "");
                    tv_selectParams.setText(mGoodParam.getSpecificationsValue() + "," + goodSize.getSpecificationsValue() + "," + count + "件");
                    tv_num.setText("库存: " + originCount);
                }
            }
        });


        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(tv_value.getText().toString());
                if (count < originCount) {
                    count++;
                    originCount--;
                    tv_value.setText(count + "");
                    tv_selectParams.setText(mGoodParam.getSpecificationsValue() + "," + goodSize.getSpecificationsValue() + "," + count + "件");
                    tv_num.setText("库存: " + originCount);
                }

            }
        });
        simpleDraweeView.setImageURI(Uri.parse(goodDetailActivity.getGoodList().getCommodityImage()));
        ll.getBackground().setAlpha(20);
        dialog = new Dialog(getActivity(), R.style.dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);

        dialog.setContentView(view);
        dialog.show();
        HorizontalListView gv_color = (HorizontalListView) view.findViewById(R.id.gv_color);
        HorizontalListView gv_size = (HorizontalListView) view.findViewById(R.id.gv_size);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        adapterColor = new ConfigAdapter(ctx, listColor);
        adapterSize = new ConfigSizeAdapter(ctx, listSize);
        gv_color.setAdapter(adapterColor);
        gv_size.setAdapter(adapterSize);
        gv_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGoodParam = listColor.get(position);
                adapterColor.setPos(position);
                adapterColor.notifyDataSetChanged();
                listSize.clear();
                listSize.addAll(listColor.get(position).getSecondSpecifications());
                adapterSize.notifyDataSetChanged();
            }
        });
        gv_size.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goodSize = listSize.get(position);
                adapterSize.setPos(position);
                adapterSize.notifyDataSetChanged();
                if (listSize.size() > 0) {
                    int num = listSize.get(position).getComInventory() - 1;
                    tv_num.setText("库存: " + num);
                    priceText.setText("￥" + listSize.get(position).getPrice());
                    tv_price.setText("￥" + listSize.get(position).getPrice());
                    tv_selectParams.setText(mGoodParam.getSpecificationsValue() + "," + goodSize.getSpecificationsValue() + "," + count + "件");
                }

            }
        });

        view.findViewById(R.id.toShopCartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    if (MyApplication.isLogin)
                        addToShopCar();
                }
            }
        });
        view.findViewById(R.id.buyNowBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("param", mGoodParam);
                    bundle.putSerializable("child", goodSize);
                    bundle.putSerializable("detail", mGoodDetail);
                    bundle.putSerializable("count", count);
                    ActivityAnimationUtils.commonTransition(getActivity(), SinglePayActivity.class, ActivityConstans.Animation.FADE, bundle);
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        if (listSize.size() > 0) {
            tv_num.setText("库存: " + listSize.get(0).getComInventory());
            tv_price.setText("￥" + listSize.get(0).getPrice());
        }

    }

    public void getDetail() {

        GoodDetailActivity goodDetailActivity = (GoodDetailActivity) getActivity();
        Map<String, Object> params = new HashMap<>();
        params.put("babyMerchantCommodityId", goodDetailActivity.getGoodList().getId());
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getGoodDetail, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mGoodDetail = new Gson().fromJson(data.toString(), GoodDetail.class);
                    setUIData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private AttenAddressListAdapter mAddressListAdapter;
    private List<PersonAddress> mPersonAddresses;
    int page = 1;

    public void getSizeAndColor() {
        listColor.clear();
        listSize.clear();
        GoodDetailActivity goodDetailActivity = (GoodDetailActivity) getActivity();
        Map<String, Object> params = new HashMap<>();
        params.put("babyMerchantCommodityId", goodDetailActivity.getGoodList().getId());
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getBabyMerComFirstSpecifications, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodParam goodParam = new Gson().fromJson(item.toString(), GoodParam.class);
                        listColor.add(goodParam);
                        if (i == 0) {
                            mGoodParam = listColor.get(0);
                            listSize.addAll(goodParam.getSecondSpecifications());
                            if (listSize.size() > 0) {
                                goodSize = listSize.get(0);
                                originCount = goodSize.getComInventory();
                            }
                        }
                    }
                    if (mGoodParam != null){
                        priceText.setText("￥"+mGoodParam.getSecondSpecifications().get(0).getPrice());
                        tv_selectParams.setText(mGoodParam.getSpecificationsValue() + "," + goodSize.getSpecificationsValue() + "," + "1件");}


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getAddress(int type) {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        } else {
            tv_address.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("isDefault", type);
        HttpProxy.obtain().get(PlatformContans.AddressManage.getUserAddress, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("getAddress", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        if (type == 0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject item = data.getJSONObject(i);
                                PersonAddress baikeItem = new Gson().fromJson(item.toString(), PersonAddress.class);
                                mPersonAddresses.add(baikeItem);
                            }
                            mAddressListAdapter.notifyDataSetChanged();
                        } else {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject item = data.getJSONObject(i);
                                mPersonAddress = new Gson().fromJson(item.toString(), PersonAddress.class);
                            }
                            tv_address.setText(mPersonAddress.getProvince() + mPersonAddress.getCity() + mPersonAddress.getDistrict() + mPersonAddress.getAddress());
                        }
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

    public void attenAddressToast() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.atten_good_address_select, null);
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        ll.getBackground().setAlpha(20);
        dialog = new Dialog(getActivity(), R.style.dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);

        dialog.setContentView(view);
        dialog.show();
        ListView listView = (ListView) view.findViewById(R.id.listView);
        mPersonAddresses = new ArrayList<>();
        mAddressListAdapter = new AttenAddressListAdapter(ctx, mPersonAddresses);
        listView.setAdapter(mAddressListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                mPersonAddress = mPersonAddresses.get(position);
                tv_address.setText(mPersonAddress.getProvince() + mPersonAddress.getCity() + mPersonAddress.getDistrict() + mPersonAddress.getAddress());
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
        getAddress(0);
    }


    public interface OnTabChangeListener {
        void changeTab(int position);
    }

    public void setListener(OnTabChangeListener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.commentLay, R.id.iv_heart,
            R.id.configSelectLay, R.id.addressLay, R.id.toCustomServiceBtn, R.id.toShopDetailBtn, R.id.submitBtn, R.id.ll_carshop})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.commentLay:
                if (listener != null) {
                    listener.changeTab(2);
                }
                break;
            case R.id.configSelectLay:
                attenConfigToast();
                break;
            case R.id.iv_heart:
                if (MyApplication.isLogin) {
                    if (isCollect == 0) {
                        isCollect = 1;
                        collect();
                    } else if (isCollect == 1) {
                        isCollect = 0;
                        uncollect();
                    }
                }else{
                    startActivity(new Intent(getContext(),RegisterActivity.class));
                }
                break;
            case R.id.addressLay:
                attenAddressToast();
                break;
            case R.id.toCustomServiceBtn:
                if(MyApplication.isLogin){
                    RongIM.getInstance().startPrivateChat(getContext(),mGoodDetail.getBabyMerchantId(),mGoodDetail.getBabyMerchantName());
                }else{
                    startActivity(new Intent(getContext(),RegisterActivity.class));
                }

                break;
            case R.id.toShopDetailBtn:
                if(MyApplication.isLogin){
                    Bundle bundle2=new Bundle();
                    bundle2.putString("id",mGoodDetail.getBabyMerchantId());
                    ActivityAnimationUtils.commonTransition(getActivity(), ShopMainListActivity.class, ActivityConstans.Animation.FADE,bundle2);
                }else{
                    startActivity(new Intent(getContext(),RegisterActivity.class));
                }

                break;
            case R.id.submitBtn:
                if (MyApplication.isLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("param", mGoodParam);
                    bundle.putSerializable("child", goodSize);
                    bundle.putSerializable("detail", mGoodDetail);
                    bundle.putSerializable("count", count);
                    ActivityAnimationUtils.commonTransition(getActivity(), SinglePayActivity.class, ActivityConstans.Animation.FADE, bundle);
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                break;
            case R.id.ll_carshop:
                if (MyApplication.isLogin)
                    addToShopCar();
                else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                break;
        }
    }

    int isCollect = -1;

    public void isCollect() {
        Map<String, Object> params = new HashMap<>();
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        GoodDetailActivity activity = (GoodDetailActivity) getActivity();
        params.put("commodityId", activity.getGoodList().getId());
        HttpProxy.obtain().get(PlatformContans.Collect.isCollectionByCommodityId, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    if ("0".equals(data)) {
                        isCollect = 0;
                        iv_heart.setImageResource(R.mipmap.white_heart_icon);
                    } else {
                        isCollect = 1;
                        iv_heart.setImageResource(R.mipmap.orange_heart_icon);
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


    public void uncollect() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        Map<String, Object> params = new HashMap<>();
        GoodDetailActivity activity = (GoodDetailActivity) getActivity();
        params.put("commodityId", activity.getGoodList().getId());
        HttpProxy.obtain().post(PlatformContans.Collect.deleteCommodityCollection, token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        iv_heart.setImageResource(R.mipmap.white_heart_icon);
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

    public void collect() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        Map<String, Object> params = new HashMap<>();
        GoodDetailActivity activity = (GoodDetailActivity) getActivity();
        params.put("commodityId", activity.getGoodList().getId());
        HttpProxy.obtain().post(PlatformContans.Collect.addCommodityCollection, token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        iv_heart.setImageResource(R.mipmap.orange_heart_icon);
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
