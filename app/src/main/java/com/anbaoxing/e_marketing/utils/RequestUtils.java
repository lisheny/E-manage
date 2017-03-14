package com.anbaoxing.e_marketing.utils;

import android.text.TextUtils;

import com.anbaoxing.e_marketing.http.RequestPackage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * author：kang
 * time:  2016-12-1
 *
 * 获取请求包
 */
public class RequestUtils {

    /**
     * 获取请求数据包，语言默认为中文,命令为空会返回空的请求包
     * @param command 请求命令
     * @return 请求数据包 JSONObject
     */
    public static JSONObject getJsonRequestPackage(String command){
        try {
            if (TextUtils.isEmpty(command)) {
                return new JSONObject();
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(RequestPackage.packmd5,"");
            jsonObject.put(RequestPackage.ver,VersionUtils.getVersionName());
            jsonObject.put(RequestPackage.command,command);
            jsonObject.put(RequestPackage.errcode,Constant.ERRCODE);
            jsonObject.put(RequestPackage.timestamp, System.currentTimeMillis());
            jsonObject.put(RequestPackage.apptype,Constant.APP_TYPE_ANDROID); // Android
            jsonObject.put(RequestPackage.language,Constant.LANGUAGE_CHINESE); // 语言：1--中文（默认）
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * 获取请求数据包，语言默认为中文,命令为空会返回空的请求包
     * @param command 请求命令
     * @return 请求数据包 TreeMap
     */
    public static TreeMap<String,Object> getTreeMapRequestPackage(String command){
        if (TextUtils.isEmpty(command)) {
            return new TreeMap<>();
        }
        TreeMap<String,Object> map = new TreeMap<>();
        map.put(RequestPackage.packmd5,"");
        map.put(RequestPackage.ver,VersionUtils.getVersionName());
        map.put(RequestPackage.command,command);
        map.put(RequestPackage.errcode,Constant.ERRCODE);
        map.put(RequestPackage.timestamp, System.currentTimeMillis());
        map.put(RequestPackage.apptype,Constant.APP_TYPE_ANDROID); // Android
        map.put(RequestPackage.language,Constant.LANGUAGE_CHINESE); // 语言：1--中文（默认）
        return map;
    }

    /**
     * 将 Map 集合转换成 JSONObject
     * @param map 要转换的 Map 集合
     * @return JSONObject
     */
    public static JSONObject mapToJsonObject(Map<String, Object> map){
        JSONObject jsonObject = new JSONObject();
        if (map == null){
            return jsonObject;
        }
        try{
            Set<String> keys = map.keySet();
            for (String key : keys) {
                jsonObject.put(key, map.get(key));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    /** 将 字符串转换成 JSONObject */
    public static JSONObject stringToJsonObject(String response){
        try {
            if (TextUtils.isEmpty(response)){
                return getErrorJSONObject();
            }else {
                return new JSONObject(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /** 从 JSONObject 中获取字符串 */
    public static String getString(JSONObject response, String key){
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
    public static JSONObject getErrorJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(RequestPackage.packmd5,"");
        obj.put(RequestPackage.ver, VersionUtils.getVersionName());
        obj.put(RequestPackage.command,Constant.ERRCODE);
        obj.put(RequestPackage.errmsg, "网络繁忙！");
        obj.put(RequestPackage.errcode,"001");
        obj.put(RequestPackage.timestamp,""+ System.currentTimeMillis());
        return obj;
    }
}
