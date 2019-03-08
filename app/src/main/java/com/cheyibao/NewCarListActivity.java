package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.baike.adapter.CarListAdapter;
import com.caryibao.NewCar;
import com.cheyibao.adapter.CarRecommendListAdapter;
import com.cheyibao.adapter.PriceGridAdapter;
import com.cheyibao.model.NewCarMenu;
import com.cheyibao.model.OldCar;
import com.cheyibao.model.TestPopupWindow;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.view.TopMiddlePopup;
import com.vipcenter.model.UserInfo;
import com.warkiz.widget.IndicatorSeekBar;
import com.xihubao.CarBrandSelectActivity;
import com.xihubao.model.CarBrand;

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
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class NewCarListActivity extends NoHttpBaseActivity {
    private List<NewCar> list;
    private CarListAdapter adapter;
    private Context ctx;
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;
    int page = 1;
    @BindView(R.id.rule_line_tv)
    TextView topLineTv;

    private ArrayList<String> items = new ArrayList<String>();
    private boolean isClose = false, isOpen3 = false, isOpen = false;
    public static int screenW, screenH;

    private TopMiddlePopup middlePopup;

    List<String> tagStrList;
    private TagContainerLayout mTagContainerLayout;

    @BindView(R.id.menu1)
    TextView menuText1;
    @BindView(R.id.ll_select)
    LinearLayout ll_select;
    int flag;
    CarBrand mCarBrand;
    String firstId="";//品牌Id;
    String startprice="";
    String endprice="";
    int type=0;//个人商家

    NewCarMenu mNewCarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_car_list_layout);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            flag = bundle.getInt("flag");
            mNewCarMenu= (NewCarMenu) bundle.getSerializable("menu");
            mCarBrand= (CarBrand) bundle.getSerializable("brand");
        }
        initView();
    }

    private void getData() {
        //UserInfo userInfo= MyApplication.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        if(!TextUtils.isEmpty(firstId)){
            params.put("firstId", firstId);
        }
        if(!TextUtils.isEmpty(startprice)){
            params.put("startprice", startprice);
        }
        if(!TextUtils.isEmpty(endprice)){
            params.put("endprice", endprice);
        }
        Log.e("params",params.toString());
        HttpProxy.obtain().get(PlatformContans.NewCar.getNewCarMerchantMessage, params,  new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getnewcar", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewCar baikeItem = new Gson().fromJson(item.toString(), NewCar.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initTag() {
        tagStrList = new ArrayList<>();

        switch (flag) {
            case 1:
                startprice="0";
                endprice="100000";
                tagStrList.add("10万以下");
                break;
            case 2:
                startprice="100000";
                endprice="250000";
                tagStrList.add("10-25万");
                break;
            case 3:
                startprice="250000";
                endprice="400000";
                tagStrList.add("25-40万");
                break;
            case 4:
                startprice="400000";
                endprice="550000";
                tagStrList.add("40-55万");
                break;
            case 5:
                startprice="550000";
                endprice="700000";
                tagStrList.add("55-70万");
                break;
            case 6:
                startprice="700000";
                endprice="850000";
                tagStrList.add("70-85万");
                break;
            case 7:
                startprice="850000";
                endprice="1000000";
                tagStrList.add("85-100万");
                break;
            case 8:
                startprice="1000000";
                endprice="100000000";
                tagStrList.add("100万以上");
                break;
            case 9:
                break;
            case 10:
                tagStrList.add(mCarBrand.getName());
                firstId=mCarBrand.getFistId();
                break;

        }
    }

    private void initView() {
        ButterKnife.bind(this);
        ctx = this;
        getItemsName();
        getScreenPixels();
        initTag();
        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        mTagContainerLayout.setTags(tagStrList);
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {
                mTagContainerLayout.removeTag(position);
            }

            @Override
            public void onTagLongClick(final int position, String text) {
                mTagContainerLayout.removeTag(position);
            }

            @Override
            public void onTagCrossClick(int position) {
                mTagContainerLayout.removeTag(position);
            }
        });

        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new CarListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",list.get(position));
                ActivityAnimationUtils.commonTransition(NewCarListActivity.this, NewCarDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        getData();
    }

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }

    PopupWindow popupWindow;

    private void showSortPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_select, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(ll_select);
        popupWindow.setOutsideTouchable(false);
    }



    /**
     * 设置弹窗内容
     *
     * @return
     */
    private void getItemsName() {
        items.add("智能排序");
        items.add("价格由低到高");
        items.add("价格由高到低");
        items.add("销量由低到高");
        items.add("销量由高到低");
    }


    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            middlePopup.dismiss();
            menuText1.setText(items.get(position));
            menuText1.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
            Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
            drawable.setBounds(0, 0, 20, 0);
            menuText1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    };
    private TextView priceText;
    private PopupWindow pw;

    private void popPriceView() {
        if (pw != null) {
            if (isOpen3) {
                pw.showAsDropDown(topLineTv, 0, 0);
            } else {
                pw.dismiss();
            }
        } else {
            List<String> phases = new ArrayList<>();
            phases.add("不限");
            phases.add("10万以下");
            phases.add("10-25万");
            phases.add("25-40万");
            phases.add("40-60万");
            phases.add("60-100万");
            phases.add("100万以上");
            pw = new PopupWindow(ctx);
            View view = LayoutInflater.from(ctx).inflate(R.layout.merge_only_price_grid, null);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
//                if (position == 0 || position == mTopGridData.size() + 1) {
//                    return 4;
//                }
                    return 1;
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(new PriceGridAdapter(ctx, phases, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pw.dismiss();
//                TextView textView = (TextView) v;
//                String text = (String) textView.getTag();
//                if (textView == menuText3) {
//                    menuText3 = null;
//                    textView.setSelected(false);
//                }else if (mTopGridData.contains(text)) {
//                    if (menuText3 != null) {
//                        menuText3.setSelected(false);
//                    }
//                    menuText3 = textView;
//                    textView.setSelected(true);
//                }
                }
            }));
            pw.setContentView(view);
            pw.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            pw.setOutsideTouchable(true);
            pw.showAsDropDown(topLineTv, 0, 0);
        }

    }

    @OnClick({R.id.back, R.id.menu1,R.id.menu2, R.id.menu3, R.id.menu4, R.id.rechargeBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.menu1:
                if (popupWindow == null)
                    showSortPopupWindow();
                else {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }else{
                        showSortPopupWindow();
                    }
                }
//                isOpen = isOpen?false:true;
//                setPopup(0);
//                middlePopup.show(ll_select);
                break;
            case R.id.menu2:
                startActivityForResult(new Intent(NewCarListActivity.this, CarBrandSelectActivity.class), 1);
                break;
            case R.id.menu3:
                isOpen3 = isOpen3 ? false : true;
                popPriceView();
                break;
            case R.id.menu4:
                ActivityAnimationUtils.commonTransition(NewCarListActivity.this, OldCarSelectActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rechargeBtn:
                mTagContainerLayout.removeAllTags();
                break;
        }
    }


}
