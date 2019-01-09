package com.zaaach.citypicker.adapter;


import com.payencai.library.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
}
