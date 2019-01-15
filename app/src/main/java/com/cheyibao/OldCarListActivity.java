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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baike.adapter.MagzineListAdapter;
import com.cheyibao.adapter.CarRecommendListAdapter;
import com.cheyibao.adapter.PriceGridAdapter;
import com.cheyibao.model.NewCarMenu;
import com.cheyibao.model.OldCar;
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
import com.tool.UIControlUtils;
import com.tool.view.TopMiddlePopup;
import com.warkiz.widget.IndicatorSeekBar;
import com.xihubao.BrandGoodsListActivity;
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

public class OldCarListActivity extends NoHttpBaseActivity {
    private List<OldCar> list;
    private CarRecommendListAdapter adapter;
    private Context ctx;
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;

    @BindView(R.id.rule_line_tv)
    TextView topLineTv;

    private ArrayList<String> items = new ArrayList<String>();
    private boolean isClose = false, isOpen3 = false, isOpen = false;
    public static int screenW, screenH;

    private TopMiddlePopup middlePopup;

    int page = 1;
    @BindView(R.id.menu1)
    TextView menuText1;
    @BindView(R.id.menu2)
    TextView menuText2;
    @BindView(R.id.menu3)
    TextView menuText3;

    List<String> tagStrList;
    private TagContainerLayout mTagContainerLayout;
    String price1 = "5万以下";
    String price2 = "5-10万";
    String price3 = "10-15万";
    String price4 = "15-30万";
    String price5 = "30万以上";
    String brand = "品牌";
    String shop = "商家";
    String one = "个人";
    int flag;
    NewCarMenu mNewCarMenu;
    String firstId="";//品牌Id;
    String startprice="";
    String endprice="";
    int type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_car_list_layout);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            flag = bundle.getInt("price");
            mNewCarMenu = (NewCarMenu) bundle.getSerializable("menu");
            if(mNewCarMenu!=null){
                firstId=mNewCarMenu.getFirstId();
            }
        }

        initView();
        getData();
    }

    private void getData() {
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
        if(type>0){
            params.put("type", type);
        }
        Log.e("params",params.toString());
        // params.put("orderByClause",3);
        HttpProxy.obtain().get(PlatformContans.OldCar.getOldCarMerchantCarByApp, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        OldCar baikeItem = new Gson().fromJson(item.toString(), OldCar.class);
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
                tagStrList.add(brand);
                break;
            case 2:
                type=1;
                tagStrList.add(shop);
                break;
            case 3:
                type=2;
                tagStrList.add(one);
                break;
            case -1:
                startprice="0";
                endprice="5";
                tagStrList.add(price1);
                break;
            case 5:
                startprice="5";
                endprice="10";
                tagStrList.add(price2);
                break;
            case 10:
                startprice="10";
                endprice="15";
                tagStrList.add(price3);
                break;
            case 15:
                startprice="15";
                endprice="30";
                tagStrList.add(price4);
                break;
            case 30:
                startprice="30";
                tagStrList.add(price5);
                break;
        }
        //tagStrList.add("10-20万");
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
        adapter = new CarRecommendListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", list.get(position));
                ActivityAnimationUtils.commonTransition(OldCarListActivity.this, OldCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);

            }
        });
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


    /**
     * 设置弹窗
     *
     * @param type
     */
    private void setPopup(int type) {
        middlePopup = new TopMiddlePopup(OldCarListActivity.this, screenW, screenH,
                onItemClickListener, items, type);
        middlePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isOpen = false;
                menuText1.setTextColor(getResources().getColor(R.color.black_33));
                Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
                drawable.setBounds(0, 0, 20, 0);
                menuText1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        });
        if (isOpen) {
            menuText1.setTextColor(getResources().getColor(R.color.colorPrimary));
            Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
            drawable.setBounds(0, 0, 20, 0);
            menuText1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            menuText1.setTextColor(getResources().getColor(R.color.black_33));
            Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
            drawable.setBounds(0, 0, 20, 0);
            menuText1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }


    /**
     * 设置弹窗内容
     *
     * @return
     */
    private void getItemsName() {
        items.add("智能排序");
        items.add("价格最低");
        items.add("价格最高");
        items.add("最新发布");
        items.add("车龄最短");
        items.add("里程最少");
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
            phases.add("3万以下");
            phases.add("3-5万");
            phases.add("5-7万");
            phases.add("7-9万");
            phases.add("9-12万");
            phases.add("12-16万");
            phases.add("16-20万");
            phases.add("20万以上");
            pw = new PopupWindow(ctx);
            View view = LayoutInflater.from(ctx).inflate(R.layout.merge_filter_price_grid, null);
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
            SuperTextView bt_confirm = (SuperTextView) view.findViewById(R.id.bt_confirm);
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pw.dismiss();
                    isOpen3 = false;
                }
            });
            priceText = (TextView) view.findViewById(R.id.priceText);
            IndicatorSeekBar indicatorSeekBar = (IndicatorSeekBar) view.findViewById(R.id.indicatorSeekBar);
            indicatorSeekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

                }

                @Override
                public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {
//                priceText.setText(textBelowTick+"及以下");
                }

                @Override
                public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

                }

                @Override
                public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

                }
            });
            pw.setContentView(view);
            pw.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            pw.setOutsideTouchable(true);
            pw.showAsDropDown(topLineTv, 0, 0);
        }

    }

    @OnClick({R.id.back, R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.rechargeBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.menu1:
                isOpen = isOpen ? false : true;
                setPopup(0);
                middlePopup.show(topLineTv);
                break;
            case R.id.menu2:
                startActivityForResult(new Intent(OldCarListActivity.this, CarBrandSelectActivity.class), 1);
                break;
            case R.id.menu3:
                isOpen3 = isOpen3 ? false : true;
                popPriceView();
                break;
            case R.id.menu4:
                ActivityAnimationUtils.commonTransition(OldCarListActivity.this, OldCarSelectActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rechargeBtn:
                mTagContainerLayout.removeAllTags();
                break;
        }
    }


}
