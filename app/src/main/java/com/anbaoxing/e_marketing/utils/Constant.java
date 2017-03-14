package com.anbaoxing.e_marketing.utils;

import java.util.regex.Pattern;

/**
 * author：kangjia
 * time:  2016-5-23
 *
 * 常量
 */
public interface Constant {

    String PHONE = "^1[3,4,5,8,7]\\d{9}$";

    Pattern EMAILER = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    // 身份证
    String ID = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    //银行卡号
    String CARD_NO= "^(\\d{16}|\\d{19})$";

    //六位验证码
    String FOUR_CODE = "^\\d{6}$";
    // 数字
    String NUMBER = "^\\d+$";

    /** 字母 a-z A-Z */
    String LETTER = "^[a-z]|[A-Z]$";


    /** 请求数据包 头部 */
    String REQUEST_HEAD = "mobile";
    /** 响应数据包 头部 */
    String RESPOND_HEAD = "mobile=";

    String SUCCESS_CODE = "1";
    String ERRCODE = "000";

    String VERSIONCODE = "1";

    /** 用于启动界面时传递数据的 Key */
    String KEY_DATA = "key_data";

    // APP类型:1--Android，2--IOS
    String APP_TYPE_ANDROID = "Android"; // Android

    // 语言：1--中文（默认），2--英文
    String LANGUAGE_CHINESE = "1";
    String LANGUAGE_ENGLISH = "2";

    //----------------------------------command-------------------------------------
    //获取签名
    String CMD_GET_SIGN = "1017";

    //同步验签
    String CMD_SYNC_SIGN = "1018";

    //------------------------------------------------------------------------------


    //协议版本号1.0
    String CMD_VERSION_1 = "1.0";

    //-----------------------------------错误码--------------------------------------
    //正常
    String ERRCODE_NORMAL = "000";

    //------------------------------------------------------------------------------


    //Service url
    String SERVICE_ALIPAY_URL = "http://192.168.1.139:8080/mycircle/doAction?";

    //列表详情展示URL
    String SHOW_URL = "SHOW_URL";
}












