package com.cheyibao;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.yunchebao.R;
import com.warkiz.widget.IndicatorSeekBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: baiiu
 * time: 16/6/5 05 23:03
 * description:
 */
public class PriceGridView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.indicatorSeekBar)
    IndicatorSeekBar indicatorSeekBar;
    @BindView(R.id.priceText)
    TextView priceText;

    private List<String> mTopGridData;



    public PriceGridView(Context context) {
        this(context, null);
    }

    public PriceGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PriceGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PriceGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
        inflate(context, R.layout.merge_filter_price_grid, this);
        ButterKnife.bind(this, this);
        indicatorSeekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {
                priceText.setText(textBelowTick+"及以下");
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }


    public PriceGridView setmTopGridData(List<String> mTopGridData) {
        this.mTopGridData = mTopGridData;
        return this;
    }


    public PriceGridView build() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                if (position == 0 || position == mTopGridData.size() + 1) {
//                    return 4;
//                }
                return 1;
            }
        });



        return this;
    }

    private TextView mTopSelectedTextView;

    @Override
    public void onClick(View v) {

        TextView textView = (TextView) v;
        String text = (String) textView.getTag();
        if (textView == mTopSelectedTextView) {
            mTopSelectedTextView = null;
            textView.setSelected(false);
        }else if (mTopGridData.contains(text)) {
            if (mTopSelectedTextView != null) {
                mTopSelectedTextView.setSelected(false);
            }
            mTopSelectedTextView = textView;
            textView.setSelected(true);
        }
    }





}
