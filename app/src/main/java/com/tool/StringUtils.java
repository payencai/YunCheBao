package com.tool;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 作者：凌涛 on 2019/4/15 15:24
 * 邮箱：771548229@qq..com
 */
public class StringUtils {
    // 方法二：
    public static String listToString2(List<String> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                if (!TextUtils.isEmpty(list.get(i)))
                    sb.append(list.get(i));
            } else {
                if (!TextUtils.isEmpty(list.get(i))) {
                    sb.append(list.get(i));
                    sb.append(separator);
                }
            }
        }
        return sb.toString();
    }

    public static ArrayList<String> StringToArrayList(String str, String separator) {
        ArrayList<String> arr = new ArrayList<String>();
        if ((str == null) || (separator == null)) {
            return arr;
        }
        StringTokenizer st = new StringTokenizer(str, separator);
        while (st.hasMoreTokens()) {
            arr.add(st.nextToken());
        }
        return arr;
    }

}
