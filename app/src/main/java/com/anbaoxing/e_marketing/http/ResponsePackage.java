package com.anbaoxing.e_marketing.http;

/**
 * author：kang
 * time:  2016-12-30
 *
 * 响应数据包接口
 */
public interface ResponsePackage extends DataPackage{

    /** 错误信息 */
    String errmsg = "errmsg";

    String getErrmsg();
    void setErrmsg(String errmsg);
}
