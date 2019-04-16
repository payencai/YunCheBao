package com.tool;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/15 15:24
 * 邮箱：771548229@qq..com
 */
public class StringUtils {
    // 方法二：
    public static String listToString2(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }

}
