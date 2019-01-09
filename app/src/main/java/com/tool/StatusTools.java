package com.tool;

/**
 * Created by sdhcjhss on 2017/10/23.
 */

public class StatusTools {
    public static String getStatus(int type){
        String name = "";
        //单据状态1.等待,2.在途，3.完成，99.作废
        switch (type){
            case 1:
                name = "等待";
                break;
            case 2:
                name = "在途";
                break;
            case 3:
                name = "完成";
                break;
            case 99:
                name = "作废";
                break;
        }
        return name;
    }

    //
    public static String getOrderStatusName(String id){
        if (id.equals("0")){
            return "全部";
        }else if(id.equals("1")){
            return "待付款";
        }else if(id.equals("2")){
            return "待发货";
        }else if(id.equals("3")){
            return "待收货";
        }else if(id.equals("4")){
            return "待评价";
        }else{
            return "已评价";
        }
    }

}
