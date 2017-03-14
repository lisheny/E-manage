package com.anbaoxing.e_marketing.beens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 2017/2/17.
 */

public class UseBeen {
    @SerializedName("ver")
    @Expose
    private Object ver;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("userrole")
    @Expose
    private String userrole;
    @SerializedName("alive_time")
    @Expose
    private String aliveTime;
    @SerializedName("alive_number")
    @Expose
    private String aliveNumber;
    @SerializedName("balance")
    @Expose
    private Object balance;
    @SerializedName("pid")
    @Expose
    private Object pid;
    @SerializedName("pidcode")
    @Expose
    private Object pidcode;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("isblack")
    @Expose
    private String isblack;

    public Object getVer() {
        return ver;
    }

    public void setVer(Object ver) {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getAliveTime() {
        return aliveTime;
    }

    public void setAliveTime(String aliveTime) {
        this.aliveTime = aliveTime;
    }

    public String getAliveNumber() {
        return aliveNumber;
    }

    public void setAliveNumber(String aliveNumber) {
        this.aliveNumber = aliveNumber;
    }

    public Object getBalance() {
        return balance;
    }

    public void setBalance(Object balance) {
        this.balance = balance;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public Object getPidcode() {
        return pidcode;
    }

    public void setPidcode(Object pidcode) {
        this.pidcode = pidcode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsblack() {
        return isblack;
    }

    public void setIsblack(String isblack) {
        this.isblack = isblack;
    }
}
