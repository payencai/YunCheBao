package com.cheyibao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.Const;
import com.example.yunchebao.R;

/**
 * Created by sdhcjhss on 2017/12/24.
 */

public class ShopDetailActivity extends AppCompatActivity {

    private RentShop rentShop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_shop_detail);
        rentShop = (RentShop) Const.rentCarInfo.get("shop");
    }
}
