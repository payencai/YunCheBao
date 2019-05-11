package com.tool;

import java.text.DecimalFormat;

/**
 * 作者：凌涛 on 2019/2/22 11:44
 * 邮箱：771548229@qq..com
 */
public class MathUtil {
    /**
     * DecimalFormat转换最简便
     */
    public static  String getDoubleTwo(double data) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(data);
    }
    public static  String getOne(double data) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(data);
    }
}
