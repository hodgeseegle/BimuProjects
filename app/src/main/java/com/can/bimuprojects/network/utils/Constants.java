package com.can.bimuprojects.network.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.can.bimuprojects.utils.AppUtils;

/**
 * 网络请求常量类
 */
public class Constants {

	/**
	 * 设备的sn码
	 * */
	private static String sn = "";
	/**
	 * 网络请求的url
	 */
	// 这里根据比目的具体数据修改
	public static String URL = "http://30.bimuwang.com/version3/ports/api.php";
	public static String APPKEY = "888";
	public static final int RTIMES = 1;
	public static String SECRET = "567745674567544";


	/**
	 * 获取设备的sn码
	 * */
	public static String getSN(Context context) {
		if (sn.equals("")) {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			sn = tm.getSimSerialNumber()==null ? "" : tm.getSimSerialNumber();
			if(sn==null||sn.equals("")){
				sn = tm.getDeviceId();
			}
		}
		return sn;
	}


}
