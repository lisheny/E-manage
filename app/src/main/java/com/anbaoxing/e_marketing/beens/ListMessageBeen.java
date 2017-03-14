package com.anbaoxing.e_marketing.beens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lisheny on 2017/2/22.
 */

public class ListMessageBeen {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;
    @SerializedName("cilist")
    @Expose
    private List<Cilist> cilist = null;

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

    public List<Cilist> getCilist() {
        return cilist;
    }

    public void setCilist(List<Cilist> cilist) {
        this.cilist = cilist;
    }
}
