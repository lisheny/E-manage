package com.anbaoxing.e_marketing.http;

import okhttp3.Request;

/**
 * author：kang
 * time:  2016-11-22
 *
 * 需要在请求之前，或请求之后做相应的处理时的请求回调
 */
public interface OkHttpResponseHandlerImpl extends OkHttpResponseHandler{

    /**
     * UI Thread,发送请求之前调用
     * @param request 请求包
     * @param id 请求 id
     */
    void onBefore(Request request, int id);

    /**
     * UI Thread，返回响应之后调用，onSucceed 之后调用
     * @param id 请求 id
     */
    void onAfter(int id);
}
