package com.vipcenter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bbcircle.adapter.KindAdapter;
import com.bbcircle.fragment.KindFragment;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.vipcenter.fragment.GiftKindFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by sdhcjhss on 2018/1/8.
 */

public class GiftAllKindActivity extends NoHttpFragmentBaseActivity implements
        OnItemClickListener {

   // private String[] strs = {"电子导航", "精品装饰", "美容保养", "大牌配件", "改装装潢"};
    private ListView listView;
   // private KindAdapter adapter;
   // private GiftKindFragment myFragment;
    public static int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.all_kind_layout);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
     //   findViewById(R.id.back).setVisibility(View.VISIBLE);
        //findViewById(R.id.user_center_icon).setVisibility(View.GONE);
       // listView = (ListView) findViewById(R.id.listview);
      //  adapter = new KindAdapter(this, strs);
     //   listView.setAdapter(adapter);
      //  listView.setOnItemClickListener(this);
        //创建MyFragment对象
//        myFragment = new GiftKindFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//       // fragmentTransaction.replace(R.id.fragment_container, myFragment);
//        //通过bundle传值给MyFragment
//        Bundle bundle = new Bundle();
//        bundle.putString(KindFragment.TAG, strs[mPosition]);
//        myFragment.setArguments(bundle);
//        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        //拿到当前位置
//        mPosition = position;
//        //即使刷新adapter
//        adapter.notifyDataSetChanged();
//        for (int i = 0; i < strs.length; i++) {
//            myFragment = new GiftKindFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, myFragment);
//            Bundle bundle = new Bundle();
//            bundle.putString(KindFragment.TAG, strs[position]);
//            myFragment.setArguments(bundle);
//            fragmentTransaction.commit();
//        }
    }


//    @OnClick({R.id.back})
//    public void OnClick(View v) {
//        switch (v.getId()) {
//            case R.id.back:
//                onBackPressed();
//                break;
//        }
//    }
}
