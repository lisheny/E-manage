package com.anbaoxing.e_marketing.alipay;



/**
 * 签名结果bean
 *
 * @author JustRush
 * @Changed at 2017/2/24
 * @see [class or method]
 * @since 1
 */

public class SignResultBean extends BaseBean {

    public String errmsg;
    public String sign;


    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
