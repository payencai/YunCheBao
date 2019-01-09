package com.payencai.library.citypicker.model;

import com.payencai.library.citypicker.model.City;

public class LocatedCity extends City {

    public LocatedCity(String name, String province, String code) {
        super(name, province, "定位城市", code);
    }
}
