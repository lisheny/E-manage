package com.anbaoxing.e_marketing.alipay;

/**
 * $desc
 *
 * @author JustRush
 * @Changed at 2017/2/24
 * @see [class or method]
 * @since 1
 */

public class BaseBean {

    private  String id ;
    private  String username;
    //命令ID
    public String command;
    //协议版本
    public String ver;
    //防修改md5
    public String packmd5;
    //错误码
    public String errcode;
    //时间戳
    public String timestamp;
    //app类型
    public String apptype;
    //语言类型
    public String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getPackmd5() {
        return packmd5;
    }

    public void setPackmd5(String packmd5) {
        this.packmd5 = packmd5;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
