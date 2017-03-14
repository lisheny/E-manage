package com.anbaoxing.e_marketing.alipay;


/**
 * 获取签名bean
 *
 * @author JustRush
 * @Changed at 2017/2/24
 * @see [class or method]
 * @since 1
 */

public class GetSignBean extends BaseBean {

    public String uid;
    /**
     * 1-支付宝支付
     * 2-微信支付
     */
    public String paytype;

    public String orderno;

    public String syncRequest; //同步支付数据

    public String getSyncRequest() {
        return syncRequest;
    }

    public void setSyncRequest(String syncRequest) {
        this.syncRequest = syncRequest;
    }




    public String getOrdermoney() {
        return ordermoney;
    }

    public void setOrdermoney(String ordermoney) {
        this.ordermoney = ordermoney;
    }

    public String ordermoney;

    public String getOrder_no() {
        return orderno;
    }

    public void setOrder_no(String order_no) {
        this.orderno = order_no;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
}
