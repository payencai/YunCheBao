package com.cheyibao.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.CarMeal;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.common.ResourceUtils;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.mediapicker.utils.ScreenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tool.DensityUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ListPopupWindow.MATCH_PARENT;

public class CarMealPopupWindow {
    private Context context;
    private PopupWindow popupWindow;
    private View view;
    private SmartRefreshLayout refreshLayout ;
    private MultipleStatusView multipleStatusView;
    private ImageView closePopupWindowIv;

    private String carId;

    private RecyclerView carMealRecyclerView;

    private Adapter adapter;

    public CarMealPopupWindow(View view,String carId) {
        this(view.getContext(),carId);
        this.view = view;
    }

    public CarMealPopupWindow(Context context,String carId) {
        this.context = context;
        this.carId = carId;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_window_for_car_meal, null);
        int height = ScreenUtils.getScreenHeight(context);
        popupWindow = new PopupWindow(view, MATCH_PARENT, height*2/3, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ResourceUtils.getColorByResource(context, R.color.white)));

        carMealRecyclerView = view.findViewById(R.id.car_meal_list);
        carMealRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        refreshLayout = view.findViewById(R.id.refresh_layout);
        multipleStatusView = view.findViewById(R.id.multiple_status_view);

        closePopupWindowIv = view.findViewById(R.id.close_popup_window_iv);

        closePopupWindowIv.setOnClickListener(v -> popupWindow.dismiss());

        adapter = new Adapter(new ArrayList<>());
        adapter.bindToRecyclerView(carMealRecyclerView);

        refreshLayout.setOnRefreshListener(refreshLayout -> loadDataType.refreshData());

        popupWindow.setOnDismissListener(() -> {
            if (this.view != null) {
                setBackgroundAlpha(0);
            } else {
                setBackgroundAlpha(1.0f);
            }
        });
        if (!TextUtils.isEmpty(carId)){
            loadDataType.initData();
        }
    }

    public void setCarMealOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener){
        adapter.setOnItemClickListener(listener);
    }

    public CarMealPopupWindow(Context context) {
        this(context,"");
    }

    public void setCarId(String carId) {
        this.carId = carId;
        loadDataType.initData();
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            map.put("id",carId);
            return map;
        }

        @Override
        public void initData() {
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(PlatformContans.CarRent.getCarMealByCarId, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<CarMeal>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<CarMeal>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<CarMeal> carMealList) {
                            if (carMealList==null || carMealList.size()<=0){
                                multipleStatusView.showEmpty();
                            }else {
                                multipleStatusView.showContent();
                                adapter.setNewData(carMealList);
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    multipleStatusView.showError();
                }
            });
        }

        @Override
        public void refreshData() {
            HttpProxy.obtain().get(PlatformContans.CarRent.getCarMealByCarId, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<CarMeal>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<CarMeal>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                            refreshLayout.finishRefresh(false);
                        }

                        @Override
                        public void onSuccess(List<CarMeal> carMealList) {
                            if (carMealList==null || carMealList.size()<=0){
                                multipleStatusView.showEmpty();
                            }else {
                                multipleStatusView.showContent();
                                adapter.setNewData(carMealList);
                            }
                            refreshLayout.finishRefresh(true);
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishRefresh(false);
                }
            });
        }
    };

    public void showAsDropDown(View view) {
        popupWindow.showAsDropDown(view);
        if (this.view != null) {
            setBackgroundAlpha(126);
        } else {
            setBackgroundAlpha(0.5f);
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        popupWindow.showAsDropDown(anchor, xoff, yoff);
        if (this.view != null) {
            setBackgroundAlpha(126);
        } else {
            setBackgroundAlpha(0.5f);
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        popupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
        if (this.view != null) {
            setBackgroundAlpha(126);
        } else {
            setBackgroundAlpha(0.5f);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0-255 255表示完全不透明
     */
    private void setBackgroundAlpha(int bgAlpha) {
        Drawable drawable = this.view.getBackground();
        if (drawable == null) {
            drawable = new ColorDrawable(ResourceUtils.getColorByResource(context, R.color.black));
            this.view.setBackground(drawable);
        }
        this.view.getBackground().setAlpha(bgAlpha);
    }


    public void dismiss() {
        popupWindow.dismiss();
    }


     static class Adapter extends BaseQuickAdapter<CarMeal, Adapter.ViewHolder> {
        Adapter(List<CarMeal> carMealList) {
            super(R.layout.item_car_meal_list_view, carMealList);
        }

        @Override
        protected void convert(ViewHolder helper, CarMeal item) {
            helper.rentDurationTv.setText(String.format("%s天",item.getRentDay()));
            helper.dayPriceView.setText(String.format("%s元",item.getDayPrice()));
            helper.originalPriceView.setText(String.format("原价%s元",item.getOriginalPrice()));
            helper.originalPriceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            helper.totalPriceView.setText(String.format("￥%s",item.getAllPrice()));
            helper.cutDownView.setText(String.format("省%s",item.getDiscount()));

            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.OVAL);
            drawable.setStroke(2,0xFFF5A623);
            helper.bookingView.setBackground(drawable);
        }

        static class ViewHolder extends BaseViewHolder {
            @BindView(R.id.rent_duration_tv)
            TextView rentDurationTv;
            @BindView(R.id.day_price_view)
            TextView dayPriceView;
            @BindView(R.id.original_price_view)
            TextView originalPriceView;
            @BindView(R.id.cut_down_view)
            TextView cutDownView;
            @BindView(R.id.total_price_view)
            TextView totalPriceView;
            @BindView(R.id.booking_view)
            FrameLayout bookingView;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
