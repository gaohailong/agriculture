package com.sxau.agriculture.utils;

import android.util.Log;

import java.lang.reflect.Array;
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
        for (int i = 0; i < strList.size(); i++) {
            str1 = strList.get(i).substring(0, strList.get(i).length());
            str2 = ConstantUtil.DOMAIN + str1 + ConstantUtil.UPLOAD_PIC_SUFFIX;
            wholeList.add(str2);
        }

        return wholeList;
    }

    public static String changeToWholeUrl(String str) {
        String str1 = new String();
        String str2 = new String();
        if (str != null) {
            str1 = str.substring(0, str.length());
            str2 = ConstantUtil.DOMAIN + str1 + ConstantUtil.UPLOAD_PIC_SUFFIX;
        } else {
            str2 = null;
        }
        return str2;
    }


//    http://sxnk110.workerhub.cn/#/article/100",

    /**
     * 获取图片id
     *
     * @param string
     * @return
     */
    public static int rotatePictureCut(String string) {
        String str = new String();
        if (string != null) {
            str = string.substring(ConstantUtil.ARTICLE_BASE_URL.length(), string.length());
            return Integer.parseInt(str);
        } else {
            return 0;

        }
    }

    public static String subStringToNoMark(String string) {
        String str = new String();
        if (string != null) {
            str = string.substring(1, string.length() - 1);
            str = str.replace("\\n","\n");
            return str;
        } else {
            return null;
        }
    }
}
