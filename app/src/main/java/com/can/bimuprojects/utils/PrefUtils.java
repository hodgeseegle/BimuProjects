package com.can.bimuprojects.utils;

import android.content.SharedPreferences;

import com.can.bimuprojects.application.BimuApplication;

import java.util.Set;

/**
 * Created by can on 2017/4/14.
 * 这个SP自动以程序包名作为SP名
 */
public class PrefUtils {

    public static SharedPreferences getDefaultPref(){
        SharedPreferences sp= BimuApplication.getPreference();
        return sp;
    }

    public static void put(String key , String value){
        getDefaultPref().edit().putString(key,value).commit();
    }


    public static String get(String key , String defaultValue){
        return getDefaultPref().getString(key,defaultValue);
    }

    public static void put(String key,Set<String> histories){
        getDefaultPref().edit().putStringSet(key,histories).commit();
    }

    public static Set<String> get(String key){
        return getDefaultPref().getStringSet(key,null);
    }
    public static void clear(){
        getDefaultPref().edit().clear().commit();
    }

    public static void putBoolean(String key ,boolean value){
        getDefaultPref().edit().putBoolean(key,value).commit();

    }
    public static boolean getBoolean(String key , boolean defValue){
        return getDefaultPref().getBoolean(key,defValue);
    }

    public static void clearAll(){
        getDefaultPref().edit().clear().commit();
    }

}
