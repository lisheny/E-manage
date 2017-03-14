package com.anbaoxing.e_marketing.http;

/**
 * author：kang
 * time:  2016-12-30
 *
 * 数据接口，包括 请求数据包，和 响应数据包
 */
public interface DataPackage {

    // ============ 数据包字段 ============
    /** 响应数据包的 MD5 值 */
    String packmd5 = "packmd5";
    /** 项目版本号 */
    String ver = "ver";

    /** 响应命令 */
    String command = "command";
    /** 错误码 */
    String errcode = "errcode";

    /** 时间毫秒值 */
    String timestamp = "timestamp";

    /** 扩展字段 */
    String field1 = "field1";
    String field2 = "field2";
    String field3 = "field3";
    String field4 = "field4";
    String field5 = "field5";

    /** 响应数据包的 MD5 值 */
    String getPackmd5();
    void setPackmd5(String packmd5);

    /** 项目版本号 */
    String getVer();
    void setVer(String ver);

    /** 响应命令 */
    String getCommand();
    void setCommand(String command);

    /** 错误码 */
    String getErrcode();
    void setErrcode(String errcode);

    /** 时间毫秒值 */
    String getTimestamp();
    void setTimestamp(String timestamp);

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
