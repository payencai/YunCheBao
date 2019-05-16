package com.xihubao;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entity.PhoneCommBaseType;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.example.yunchebao.rongcloud.adapter.WeekDateHorizontalAdatper;
import com.example.yunchebao.rongcloud.model.WeekDate;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.CalendarViewActivity;
import com.tool.UIControlUtils;
import com.xihubao.fragment.Shop4SListFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/12.
 */

public class Shop4SListActivity extends NoHttpFragmentBaseActivity {


    private ArrayList<PhoneCommBaseType> menuList = new ArrayList<PhoneCommBaseType>();
    private WeekDateHorizontalAdatper horizontalAdatper;
    private List<WeekDate> mWeekDates = new ArrayList<>();
    @BindView(R.id.tv_dis)
    TextView tv_dis;
    @BindView(R.id.carname)
    TextView carname;
    @BindView(R.id.grid)
    GridView weekDateGridView;
    String buytime;
    String city;
    String car;
    String dis;
    public String selectTime;
    Shop4SListFragment mSListFragment;
    /**
     * fragmentLit集合
     */
    // private HashMap<String, Fragment> fragmentList = new HashMap<String, Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_4s_list_layout);
        Bundle bundle = getIntent().getExtras();
        car = bundle.getString("car");
        buytime = bundle.getString("time");
        dis = bundle.getString("dis");
        city = bundle.getString("city");
        initView();
    }
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);

        return result;
    }
    public static String getBeforeAfterDate(String datestr, int day) {
        Log.e("date",datestr);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date starDate=null;
        try {
             starDate=df.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(starDate);
        int Year = cal.get(Calendar.YEAR);
        int Month = cal.get(Calendar.MONTH);
        int Day = cal.get(Calendar.DAY_OF_MONTH);

        int NewDay = Day + day;

        cal.set(Calendar.YEAR, Year);
        cal.set(Calendar.MONTH, Month);
        cal.set(Calendar.DAY_OF_MONTH, NewDay);
        Date d=new Date(cal.getTimeInMillis());
        String result = df.format(d);
        return result;
    }


    public  String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    private void initGrid() {
        for (int i = 0; i < 7; i++) {
            WeekDate weekDate = new WeekDate();
            weekDate.setDate(getFetureDate(i));
            weekDate.setWeek(dateToWeek(getFetureDate(i)));
            if(i==0){
                selectTime=weekDate.getDate();
                mSListFragment.setTime(selectTime);
                weekDate.setSelect(true);
            }else{
                weekDate.setSelect(false);
            }
            mWeekDates.add(weekDate);
        }
        horizontalAdatper = new WeekDateHorizontalAdatper(this, mWeekDates);

//调用控制水平滚动的方法
        setHorizontalGridView(mWeekDates.size(), weekDateGridView);
        weekDateGridView.setAdapter(horizontalAdatper);
        weekDateGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0;i<mWeekDates.size();i++){
                    if(position == i){
                        selectTime=mWeekDates.get(i).getDate();
                        mSListFragment.setTime(selectTime);
                        mWeekDates.get(i).setSelect(true);
                    }else{
                        mWeekDates.get(i).setSelect(false);
                    }
                }
                horizontalAdatper.notifyDataSetChanged();

            }
        });
//绑定适配器


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String date=data.getStringExtra("entity");
            mWeekDates.clear();
            updateWeekDate(date);
        }
    }

    private void updateWeekDate(String date){
        for (int i = 0; i <7 ; i++) {
            String time=getBeforeAfterDate(date,i);
            String week=dateToWeek(time);
            WeekDate weekDate=new WeekDate();
            weekDate.setDate(time);
            weekDate.setWeek(week);
            if(i==0){
                selectTime=weekDate.getDate();
                mSListFragment.setTime(selectTime);
                weekDate.setSelect(true);
            }
            mWeekDates.add(weekDate);
        }
        horizontalAdatper.notifyDataSetChanged();
    }
    /**
     * 水平滚动的GridView的控制
     */
    private void setHorizontalGridView(int siz, GridView gridView) {
        int size = siz;
//      int length = (int) getActivity().getResources().getDimension(
//              R.dimen.coreCourseWidth);
        int length = 60;
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length) * density);
        int itemWidth = (int) ((length) * density);

        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(0); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

    }

    public String getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "4S保养预约");
        ButterKnife.bind(this);
        mSListFragment= (Shop4SListFragment) getSupportFragmentManager().findFragmentById(R.id.shop4list);
        mSListFragment.setCar(car);
        mSListFragment.setCity(city);
        mSListFragment.setDis(dis);
        mSListFragment.setBuyTime(buytime);
        mSListFragment.initListData();
        carname.setText(car);
        tv_dis.setText(dis);
        initGrid();
    }


    @OnClick({R.id.back, R.id.calendarBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.calendarBtn:
                // startActivity(new Intent(Shop4SListActivity.this, CalendarViewActivity.class));
                ActivityAnimationUtils.commonTransition(Shop4SListActivity.this, CalendarViewActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
