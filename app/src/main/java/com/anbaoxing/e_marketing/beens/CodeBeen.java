package com.anbaoxing.e_marketing.beens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 2017/2/17.
 */

public class CodeBeen {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;

    @SerializedName("errcode")
    @Expose
    private String errcode;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
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
}
