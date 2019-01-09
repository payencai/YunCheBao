package com.payencai.library.citypicker.adapter;


import com.payencai.library.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
