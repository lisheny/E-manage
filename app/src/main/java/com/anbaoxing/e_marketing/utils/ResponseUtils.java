package com.anbaoxing.e_marketing.utils;

import android.text.TextUtils;

import com.anbaoxing.e_marketing.http.RequestPackage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * author：kang
 * time:  2017/1/3
 *
 * 处理响应数据包的工具类
 */
public class ResponseUtils {

    /** 响应数据包超时时间 2 分钟（120000 毫秒） 5 分钟 （300000 毫秒） */
    private static final int RESPONSE_TIME_OUT = 1000 * 60 * 2;

    /** 将 字符串转换成 JSONObject */
    public static JSONObject stringToJsonObject(String response){
        try {
            if (TextUtils.isEmpty(response)){
                return new JSONObject();
            }else {
                response = response.replace(Constant.RESPOND_HEAD,"");
                return new JSONObject(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /** 从 JSONObject 中获取字符串 */
    public static String getString(JSONObject response,String key){
        try {
            if (response != null && key != null){
                if (response.has(key)){
                    return response.getString(key);
                }else {
                    return "";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 获取错误的 JSONObject */
    public static JSONObject getErrorJSONObject() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put(RequestPackage.packmd5,"");
        obj.put(RequestPackage.ver, VersionUtils.getVersionName());
        obj.put(RequestPackage.command,Constant.ERRCODE);
        obj.put(RequestPackage.errmsg,"网络繁忙");
        obj.put(RequestPackage.errcode,"001");
        obj.put(RequestPackage.timestamp,""+System.currentTimeMillis());
        return obj;
    }

    /**
     * 判断 packmd5 值是否合法
     * @param response 响应数据包
     * @return true - 合法，false - 不合法
     */
    public static boolean verifyPackMd5(JSONObject response){
        if (response == null) return false;
        String obj = response.toString();
        if (TextUtils.isEmpty(obj)) return false;

        // 响应数据包的 Md5 - 原有的
        String packmd5 = ResponseUtils.getString(response,RequestPackage.packmd5);
        // 响应数据包的 Md5 - 重新生成的
        String responseMd5 = MD5Utils.getPackmd5(response);
        if (packmd5.equals(responseMd5)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断 timestamp 字段是否合法
     * @param response 响应数据包
     * @return true - 超时，false - 没超时
     */
    public static boolean verifyTimeStamp(JSONObject response){
        if (response == null) return false;
        String obj = response.toString();
        if (TextUtils.isEmpty(obj)) return false;
        // 响应时间
        String responseTimestamp = ResponseUtils.getString(response,RequestPackage.timestamp);
        long responseTime = StringUtils.stringToLong(responseTimestamp);
        // 接收响应时的时间
        long currentTime = System.currentTimeMillis();

        if ((currentTime - responseTime) > RESPONSE_TIME_OUT){
            // 判断响应数据包的时间是否超时
            return true;
        }else {
            return false;
        }
    }

    /** 将 JSONObject 对象进行排序 */
    public static JSONObject sortJSONObject(JSONObject jsonObject){
        if (jsonObject == null || TextUtils.isEmpty(jsonObject.toString())) return null;

        TreeMap<String,Object> map = new TreeMap<>();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()){
            String key = iterator.next();
            map.put(key,ResponseUtils.getString(jsonObject,key));
        }
        JSONObject sortObj = new JSONObject(map);
        return sortObj;
    }
}
