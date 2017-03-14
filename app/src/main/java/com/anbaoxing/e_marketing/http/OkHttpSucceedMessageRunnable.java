package com.anbaoxing.e_marketing.http;


import com.blankj.utilcode.utils.ToastUtils;

/**
 * author：kangjia
 * time:  2016-11-10
 *
 * 请求成功，发送的消息
 */
public class OkHttpSucceedMessageRunnable implements Runnable {

    /** 响应数据包 */
    private String response;
    /** 请求 id */
    private int id;

    /** 请求回调 */
    private OkHttpResponseHandler okHttpResponseHandler;

    public OkHttpSucceedMessageRunnable(String response, int id, OkHttpResponseHandler okHttpResponseHandler) {
        this.response = response;
        this.id = id;
        this.okHttpResponseHandler = okHttpResponseHandler;
    }

    @Override
    public void run() {
        if (okHttpResponseHandler != null) {
            // 回调接口，此方法运行在 UI 线程
            okHttpResponseHandler.onSucceed(response, id);
        }else {
            ToastUtils.showShortToast("网络繁忙！");
        }
    }

    public String getResponse() {
        return response;
    }

    public int getId() {
        return id;
    }

    public OkHttpResponseHandler getOkHttpResponseHandler() {
        return okHttpResponseHandler;
    }

    public void setOkHttpResponseHandler(OkHttpResponseHandler okHttpResponseHandler) {
        this.okHttpResponseHandler = okHttpResponseHandler;
    }
}
