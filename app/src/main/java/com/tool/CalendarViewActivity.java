package com.tool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;

import com.example.yunchebao.R;


import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/10/18.
 */

public class CalendarViewActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {
    @BindView(R.id.calendarView)
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view_layout);
        ButterKnife.bind(this);
        calendarView.setOnDateChangeListener(this);
        calendarView.setMinDate( new Date().getTime());
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Intent mIntent = new Intent();
        mIntent.putExtra("entity", year +"-"+month+"-"+dayOfMonth);
        CalendarViewActivity.this.setResult(1,mIntent);
        CalendarViewActivity.this.finish();
    }

    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                break;
        }
    }
}
