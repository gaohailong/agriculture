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
        str = str.substring(0, str.length() - 1);
        return str;
    }

    public static List<String> changeToWholeUrlList(List<String> strList) {
        String str1 = new String();
        String str2 = new String();
        List<String> wholeList = new ArrayList<String>();
        for (int i = 0 ; i<strList.size();i++){
            str1 = strList.get(i).substring(0, strList.get(i).length() - 4);
            str2 = ConstantUtil.DOMAIN + str1 + "?imageView2/0/w/0/format/jpg";
            wholeList.add(str2);
        }

        return wholeList;
    }
}
