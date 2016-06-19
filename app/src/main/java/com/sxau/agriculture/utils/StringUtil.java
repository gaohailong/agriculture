package com.sxau.agriculture.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Yawen_Li on 2016/6/18.
 */
public class StringUtil {
    public static List<String> changeStringToList(String str) {
        List<String> strList = new ArrayList<String>();
        strList = Arrays.asList(str.split(","));
        return strList;
    }

    public static String changeListToString(List<String> strList) {
        String str = new String();
        for (int i = 0; i < strList.size(); i++) {
            str += strList.get(i) + ",";
        }
        str = str.substring(0,str.length()-2);
        return str;
    }
}
