package com.anbaoxing.e_marketing.utils;

import android.text.TextUtils;

import com.anbaoxing.e_marketing.http.RequestPackage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Description : <md5加密处理类>
 * author: lushan
 * date  : 2015/9/29 11:07
 */
public class MD5Utils {

    private static final String MD5_END = "abx23579436";

    public static String getMd5(String inStr) {
        if(TextUtils.isEmpty(inStr)){
            return "";
        }
        MessageDigest md5Digest=null;
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = inStr.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPackmd5(TreeMap<String,Object> map){
        if (map == null) return "";
        // 值连接加密
        StringBuilder builder = new StringBuilder();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            // 生成 MD5 时，不包括 packmd5 字段
            if (!key.equals(RequestPackage.packmd5)) {
                builder.append(map.get(key));
            }
        }
        builder.append(MD5_END);
        return MD5Utils.getMd5(builder.toString());
    }

    /***
     * 获取请求包的 MD5
     * @param jsonObject 请求包
     * @return
     */
    public static String getPackmd5(JSONObject jsonObject){
        if (jsonObject == null) return "";
        String packmd5 = "";
        try {
            // 值连接加密
            StringBuilder builder = new StringBuilder();
            Iterator<String> iterator = jsonObject.keys();
            Map<String,String> map= jsonToMap(jsonObject);
            Map<String, String> sortMap = new TreeMap<String, String>(
                    new MapKeyComparator());
            sortMap.putAll(map);
      /*      while (iterator.hasNext()){
                String key = iterator.next();
                // 不加入 MD5 运算的字段值
                if (!key.equals(field)) {
                    builder.append(jsonObject.get(key));
                }

            }*/
            for (Map.Entry<String, String> entry : sortMap.entrySet()) {
                if(!entry.getKey().equals("packmd5")){
                    builder.append(entry.getValue()+"");
                    System.out.println("要加密的字符串顺序:"+entry.getKey());
                }
                //         	   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            }
            builder.append("abx23579436");
            packmd5 = MD5Utils.getMd5(builder.toString());
            return packmd5;
        } catch (Exception e){
            e.printStackTrace();
            return packmd5;
        }
    }

//    public static String getPackmd5(JSONObject jsonObject){
//        if (jsonObject == null) return "";
//        String packmd5 = "";
//        // 将 JSONObject 对象进行排序
//        jsonObject = ResponseUtils.sortJSONObject(jsonObject);
//        try {
//            // 值连接加密
//            StringBuilder builder = new StringBuilder();
//            Iterator<String> iterator = jsonObject.keys();
//            while (iterator.hasNext()){
//                String key = iterator.next();
//                // 生成 MD5 时，不包括 packmd5 字段
//                if (!key.equals(RequestPackage.packmd5)) {
//                    builder.append(jsonObject.get(key));
//                }
//            }
//            builder.append(MD5_END);
//            packmd5 = MD5Utils.getMd5(builder.toString());
//            return packmd5.toLowerCase();
//        } catch (Exception e){
//            e.printStackTrace();
//            return packmd5;
//        }
//    }

    /***
     * 获取请求包的 MD5
     * @param jsonObject 请求包
     * @param field 不加入 MD5 运算的字段值
     * @return
     */
    public static String getPackmd5(JSONObject jsonObject,String field){
        if (jsonObject == null) return "";
        String packmd5 = "";
        // 将 JSONObject 对象进行排序
        jsonObject = ResponseUtils.sortJSONObject(jsonObject);
        try {
            // 值连接加密
            StringBuilder builder = new StringBuilder();
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()){
                String key = iterator.next();
                // 不加入 MD5 运算的字段值
                if (!key.equals(field)) {
                    builder.append(jsonObject.get(key));
                }
            }
            builder.append(MD5_END);
            packmd5 = MD5Utils.getMd5(builder.toString());
            return packmd5.toLowerCase();
        } catch (Exception e){
            e.printStackTrace();
            return packmd5;
        }
    }

    public static final Gson gson = new Gson();
    // json 转 map
    private static Map<String,String> jsonToMap(JSONObject jsonObject) {
        // 得到json
        JSONObject json = jsonObject;
        // 使用谷歌的gson将json转换为map类型
        // TypeToken<Map<String, String>>()  此格式可以以自己的需求进行调整
        Map<String, String> mapData = gson.fromJson(json.toString(), new TypeToken<Map<String, String>>(){}.getType());
        // 循环map
        for (Map.Entry<String, String> entry : mapData.entrySet()) {
            System.out.print(entry.getKey() + ":" + entry.getValue() + "\n");
        }
        return mapData;
    }
}












