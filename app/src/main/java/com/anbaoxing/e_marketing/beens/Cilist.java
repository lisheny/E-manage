package com.anbaoxing.e_marketing.beens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lisheny on 2017/2/22.
 */

public class Cilist {

    @SerializedName("circleid")
    @Expose
    private Integer circleid;
    @SerializedName("createtime")
    @Expose
    private String createtime;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("isdel")
    @Expose
    private Integer isdel;
    @SerializedName("issuetime")
    @Expose
    private String issuetime;
    @SerializedName("msgauthor")
    @Expose
    private String msgauthor;
    @SerializedName("msgcomefrom")
    @Expose
    private String msgcomefrom;
    @SerializedName("msgcontent")
    @Expose
    private String msgcontent;
    @SerializedName("msgtitle")
    @Expose
    private String msgtitle;
    @SerializedName("msgurl")
    @Expose
    private String msgurl;
    @SerializedName("remark")
    @Expose
    private String remark;

    public Integer getCircleid() {
        return circleid;
    }

    public void setCircleid(Integer circleid) {
        this.circleid = circleid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getIssuetime() {
        return issuetime;
    }

    public void setIssuetime(String issuetime) {
        this.issuetime = issuetime;
    }

    public String getMsgauthor() {
        return msgauthor;
    }

    public void setMsgauthor(String msgauthor) {
        this.msgauthor = msgauthor;
    }

    public String getMsgcomefrom() {
        return msgcomefrom;
    }

    public void setMsgcomefrom(String msgcomefrom) {
        this.msgcomefrom = msgcomefrom;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public String getMsgtitle() {
        return msgtitle;
    }

    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle;
    }

    public String getMsgurl() {
        return msgurl;
    }

    public void setMsgurl(String msgurl) {
        this.msgurl = msgurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}