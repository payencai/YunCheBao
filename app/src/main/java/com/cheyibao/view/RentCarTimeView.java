package com.cheyibao.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.util.RentCarUtils;
import com.common.DateUtils;
import com.example.yunchebao.R;
import com.payencai.library.util.ToastUtil;
import com.system.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentCarTimeView extends LinearLayout {

    @BindView(R.id.start_time_view)
    TextView startTimeView;
    @BindView(R.id.time_duration_view)
    TextView timeDurationView;
    @BindView(R.id.end_time_view)
    TextView endTimeView;

    private boolean isEnabled = true;

    private long startTime;
    private long endTime;
    private long duration;

    public RentCarTimeView(Context context) {
        this(context, null);
    }

    public RentCarTimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RentCarTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RentCarTimeView);
         isEnabled = ta.getBoolean(R.styleable.RentCarTimeView_enabled,true);
        ta.recycle();  //注意回收
        LayoutInflater.from(context).inflate(R.layout.view_rent_car_time_view,this);
        startTimeView = findViewById(R.id.start_time_view);
        timeDurationView = findViewById(R.id.time_duration_view);
        endTimeView = findViewById(R.id.end_time_view);
        startTimeView.setEnabled(isEnabled);
        endTimeView.setEnabled(isEnabled);

        startTime = System.currentTimeMillis();
        endTime = startTime + RentCarUtils.DAY;
        duration = RentCarUtils.DAY;
        startTimeView.setText(getSpannableString(startTime));
        endTimeView.setText(getSpannableString(endTime));
        timeDurationView.setText(String.format("%s天",RentCarUtils.day(duration)));

        final FragmentActivity fragmentActivity = (context instanceof FragmentActivity) ? (FragmentActivity) context :null;

        startTimeView.setOnClickListener(v -> DateUtils.initTimePickerDialog(getContext(),(timePickerView, millseconds) -> {
            startTime = millseconds;
            duration = endTime - startTime;
            if (duration < 0){
                duration = 0;
                ToastUtil.showToast(getContext(),"开始时间必须小于结束时间");
                return;
            }
            if (endTime>0){
                timeDurationView.setText(String.format("%s天",RentCarUtils.day(duration)));
            }
            startTimeView.setText(getSpannableString(millseconds));
            if (onDayChangedListener!=null){
                onDayChangedListener.onDayChanger(RentCarUtils.day(duration));
            }
        },"开始时间",startTime<=0?System.currentTimeMillis():startTime).show(Objects.requireNonNull(fragmentActivity).getSupportFragmentManager(),"all"));


        endTimeView.setOnClickListener(v -> DateUtils.initTimePickerDialog(getContext(),(timePickerView, millseconds) -> {
            endTime = millseconds;
            duration = endTime - startTime;
            if (duration<0){
                duration = 0;
                ToastUtil.showToast(getContext(),"开始时间必须小于结束时间");
                return;
            }
            if (startTime>0){
                timeDurationView.setText(String.format("%s天",RentCarUtils.day(duration)));
            }

            endTimeView.setText(getSpannableString(millseconds));

            if (onDayChangedListener!=null){
                onDayChangedListener.onDayChanger(RentCarUtils.day(duration));
            }

        },"结束时间",endTime<=0? System.currentTimeMillis():endTime).show(Objects.requireNonNull(fragmentActivity).getSupportFragmentManager(),"all"));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        isEnabled = enabled;
        startTimeView.setEnabled(isEnabled);
        endTimeView.setEnabled(isEnabled);
    }

    public void initTime(long startTime, long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        duration = endTime - startTime;
        startTimeView.setText(getSpannableString(startTime));
        endTimeView.setText(getSpannableString(endTime));
        timeDurationView.setText(String.format("%s天",RentCarUtils.day(duration)));
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return duration;
    }

    public int getDay(){
        return RentCarUtils.day(duration);
    }

    private SpannableString getSpannableString(long millseconds){
        String time = DateUtils.formatDateTime(millseconds,"M月dd日 EHH:mm");
        SpannableString spannableString = new SpannableString(time.replace(" ","\n"));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#999999"));
        spannableString.setSpan(colorSpan, time.indexOf(" "), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public abstract static class OnDayChangedListener{
        public void onDayChanger(int day){}
    }
    private OnDayChangedListener onDayChangedListener;

    public void setOnDayChangedListener(OnDayChangedListener onDayChangedListener) {
        this.onDayChangedListener = onDayChangedListener;
    }
}
