package com.common;

import java.util.Map;

public interface  LoadDataType {
    Map<String,Object> initParam();
    void initData();
     void loadMoreData();
     void refreshData();
}
