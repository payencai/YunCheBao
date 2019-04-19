package com.cheyibao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarModelsSelectedActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;
    @BindView(R.id.car_model_recycler_view)
    RecyclerView carModelRecyclerView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;

    private boolean isFirstLevel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_models_selected);
        ButterKnife.bind(this);
        title.setText("选择车型");
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            if (isFirstLevel){
                map.put("level",1);
            }else {
                map.put("categoryId","");
            }
            return map;
        }
    };

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }
}
