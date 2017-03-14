package com.anbaoxing.e_marketing.beens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 2017/2/17.
 */

public class RegisterBeen {

    @SerializedName("ver")
    @Expose
    private String ver;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("pwd")
    @Expose
    private String pwd;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
