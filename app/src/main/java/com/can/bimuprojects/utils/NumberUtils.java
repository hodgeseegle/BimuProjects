package com.can.bimuprojects.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.can.bimuprojects.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by can on 2017/4/15.
 * 判断是否为数字
 */

public class NumberUtils {

    private static boolean isMatch(String regex, String orginal){
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    //是否为正整数
    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    //是否为负整数
    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    //是否为整数
    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    //是否为正十进制
    public static boolean isPositiveDecimal(String orginal){
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }
    //是否为负十进制
    public static boolean isNegativeDecimal(String orginal){
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    //是否为十进制
    public static boolean isDecimal(String orginal){
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }
    //是否为数字
    public static boolean isRealNumber(String orginal){
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    //获取子字符串的下标
    public static int getIndexOf(String s,String s1,int index){
        s = s.toUpperCase();
        s1 = s1.toUpperCase();
        String string = s.substring(index);
        return string.indexOf(s1)+index;
    }

    //是否包含子字符串
    public static boolean isAbout(String s,String s1,int index){
        s = s.toUpperCase();
        s1 = s1.toUpperCase();
        String string = s.substring(index);
        if(string.contains(s1)){
            return true;
        }else
            return false;
    }

    //获取设置子字符串颜色后的style
    public static SpannableStringBuilder getSpannableString(Context context, String content, String tag){
        SpannableStringBuilder style=new SpannableStringBuilder(content);
        int index;
        for (int j =0;j<content.length();j++){
            if(isAbout(content,tag,j)){
                index = getIndexOf(content,tag,j);
                if(index!=-1){
                    style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_app_text_yes)), index, index+tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                    j = index+tag.length();
                }
            }
        }
        return  style;
    }

    //匹配冠锋这个煞笔
    public static List<String> matherGF(String string){
        String regex = "bid=\"([^\"]*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string);
        List<String> lists = new ArrayList<>();
        while (m.find()) {
            for(int i = 0;i<m.groupCount();i++){
                int index1 = m.group(i).indexOf("\"")+1;
                int index2 = m.group(i).indexOf('-');
                if(index1>=0){
                    String idString = m.group(i).substring(index1,index2);
                    lists.add(idString);
                }
            }
        }
        return lists;
    }
    /**
     * 将集合转为字符串
     */
    public static String list2String(List<String> list){
        String string = "";
        for(int i =0;i<list.size();i++){
            if(i!=list.size()-1){
                string += list.get(i)+",";
            }else{
                string+=list.get(i);
            }
        }
        return string;
    }

}
