package com.cheyibao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.fragment.RentCarModelsFragment;
import com.cheyibao.fragment.RentShopComentFragment;
import com.cheyibao.fragment.SelfDriverOrderFragment;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.nohttp.sample.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelfDriverOrderActivity extends AppCompatActivity {

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
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_driver_order);
        ButterKnife.bind(this);
        title.setText("自驾订单");
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(SelfDriverOrderFragment.newInstance(-1));
        fragmentArrayList.add(SelfDriverOrderFragment.newInstance(2));
        fragmentArrayList.add(SelfDriverOrderFragment.newInstance(3));
        tabLayout.setViewPager(viewpager, new String[]{"全部","服务中","待评论"}, this, fragmentArrayList);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }
}
