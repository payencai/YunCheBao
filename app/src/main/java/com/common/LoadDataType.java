package com.common;

import java.util.Map;

public abstract class  LoadDataType {
    public abstract Map<String,Object> initParam();
    public void initData(){}
    public void loadMoreData(){}
    public void refreshData(){}

    public void submitData(){}
}
