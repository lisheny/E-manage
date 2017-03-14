package com.anbaoxing.e_marketing.http;



import com.blankj.utilcode.utils.ToastUtils;

import okhttp3.Call;

/**
 * author：kangjia
 * time:  2016-11-10
 *
 * 请求失败，发送的消息
 */
public class OkHttpFailureMessageRunnable implements Runnable {

    /** 请求数据包 */
    private Call call;
    /** 请求失败异常 */
    private Exception e;
    /** 请求 id */
    private int id;

    /** 请求回调 */
    private OkHttpResponseHandler okHttpResponseHandler;

    public OkHttpFailureMessageRunnable(Call call, Exception e, int id, OkHttpResponseHandler okHttpResponseHandler) {
        this.call = call;
        this.e = e;
        this.id = id;
        this.okHttpResponseHandler = okHttpResponseHandler;
    }

    @Override
    public void run() {
        if (okHttpResponseHandler != null) {
            // 回调接口，此方法运行在 UI 线程
            okHttpResponseHandler.onFailure(call,e,id);
        }else {
            ToastUtils.showShortToast("网络繁忙！");
        }
    }

    public Call getCall() {
        return call;
    }

    public Exception getException() {
        return e;
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
