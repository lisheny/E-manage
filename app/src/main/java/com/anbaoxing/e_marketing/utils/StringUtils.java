package com.anbaoxing.e_marketing.utils;

import android.text.TextUtils;

import com.anbaoxing.e_marketing.http.RequestPackage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author：kang
 * time:  2016-11-21
 *
 * 字符串操作工具类
 */
public class StringUtils {

    /**
     * 判断是否为手机号码
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            return phoneNumber.matches(Constant.PHONE);
        }else {
            return false;
        }
    }

    /**
     * 身份证
     */
    public static boolean isID(String id) {
        if (id != null) {
            return id.matches(Constant.ID);
        }else {
            return false;
        }
    }

    /** 将字节数组转换成 JSONObject 对象 */
    public static JSONObject byteToJSONObject(byte[] bytes) throws JSONException {
        if (bytes != null && bytes.length > 0) {
            String msg = new String(bytes);
            msg = msg.replace(Constant.REQUEST_HEAD, "");
            if (!StringUtils.textIsEmpty(msg)) {
                return new JSONObject(msg);
            }else {
                return getErrorJSONObject();
            }
        }else {
            return getErrorJSONObject();
        }
    }

    /** 获取错误的 JSONObject */
    public static JSONObject getErrorJSONObject() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put(RequestPackage.packmd5,"");
        obj.put(RequestPackage.ver, VersionUtils.getVersionName());
        obj.put(RequestPackage.command,"000");
        obj.put(RequestPackage.errmsg,"网络繁忙，请稍后重试。");
        obj.put(RequestPackage.errcode,"001");
        obj.put(RequestPackage.timestamp,""+System.currentTimeMillis());
        return obj;
    }

    /** 银行卡号 */
    public static boolean isCardNo(String cardNo){
        if (cardNo != null) {
            return cardNo.matches(Constant.CARD_NO);
        }else {
            return false;
        }
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return Constant.EMAILER.matcher(email).matches();
    }

    /**
     * 六位验证码
     */
    public static boolean isFourCode(String fourCode) {
        if (fourCode != null){
            return fourCode.matches(Constant.FOUR_CODE);
        }else {
            return false;
        }
    }


    /** 将字符串转换成 int 类型 默认值返回 0 */
    public static int stringToInt(String intString){
        if (TextUtils.isEmpty(intString)){
            return 0;
        }else {
            intString = intString.trim();
            if (intString.matches("^\\d+$")){
                return Integer.parseInt(intString);
            }else {
                return 0;
            }
        }
    }

    /** 将字符串转换成  类型 默认值返回 0 */
    public static long stringToLong(String longString){
        if (TextUtils.isEmpty(longString)){
            return 0;
        }else {
            longString = longString.trim();
            if (longString.matches("^\\d+$")){
                return Long.parseLong(longString);
            }else {
                return 0;
            }
        }
    }

    /** 判断 字符串是否为空 */
    public static boolean textIsEmpty(String text){
        if (TextUtils.isEmpty(text) || "null".equals(text)
                || "(null)".equals(text)){
            return true;
        }else {
            return false;
        }
    }

    /** 在小于 10 的数字前面加 0 */
    public static String getValue(int date){
        return date < 10 ? "0" + date : String.valueOf(date);
    }
}
