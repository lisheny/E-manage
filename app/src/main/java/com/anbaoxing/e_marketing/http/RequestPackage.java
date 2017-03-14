package com.anbaoxing.e_marketing.http;

/**
 * author：kang
 * time:  2016-11-21
 *
 * 请求包数据接口
 */
public interface RequestPackage {

    // ============ 请求包命令 ============

    /** 登录 */
    String LOGIN_COMMAND = "1001";
    /** 注册 */
    String REGISTER_COMMAND = "1002";
    /** 忘记密码(验证验证码)，重置密码 */
    String FORGET_PASSWORD_COMMAND = "1005";
    //忘记密码，获取验证码
    String FOEGET_PASSWORD_COMMAND = "1006";
    //检查验证码
    String CHECK_CODE = "1007";

    /** 修改密码 */
    String AMEND_PASSWORD_COMMAND = "1004";
    /** 获取验证码 */
    String GET_AUTH_CODE_COMMAND = "1003";
    /** 项目升级 */
    String APP_UPDATE_COMMAND = "1008";
    //问题反馈
    String ISSUE_COMMIT = "1009";
    //获取圈子经营信息
    String MANAGE_MESSAGE = "1010";
    //获取流量单价
    String GET_PRICE = "1018";
    //通过基站获取位置
    String GET_LOCATION = "1019";

    /** 获取网页链接地址 */
    String GET_WEB_PAGE_URL_COMMAND = "1013";

    // ============ 请求包字段 ============
    String packmd5 = "packmd5";
    String command = "command";

    String timestamp = "timestamp";
    String ver = "ver";

    String errcode = "error";
    String apptype = "apptype";

    String errmsg = "errmsg";
    String language = "language";

    String field1 = "field1";
    String field2 = "field2";
    String field3 = "field3";
    String field4 = "field4";
    String field5 = "field5";

    String getPackmd5();
    void setPackmd5(String packmd5);

    String getCommand();
    void setCommand(String command);

    String getTimestamp();
    void setTimestamp(String timestamp);

    String getVer();
    void setVer(String ver);

    String getErrcode();
    void setErrcode(String errcode);

    String getApptype();
    void setApptype(String apptype);

    String getLanguage();
    void setLanguage(String language);

    String getField1();
    void setField1(String field1);

    String getField2();
    void setField2(String field2);

    String getField3();
    void setField3(String field3);

    String getField4();
    void setField4(String field4);

    String getField5();
    void setField5(String field5);
}
