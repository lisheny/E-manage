package com.anbaoxing.e_marketing.http;

import java.io.File;

import okhttp3.Request;

/**
 * author：kangjia
 * time:  2016-10-20
 *
 * 下载文件，get 请求回调
 */
public abstract class OkHttpFileResponseHandler implements OkHttpResponseHandler{

    /**
     * UI Thread,发送请求之前调用
     * @param request 请求包
     * @param id 请求 id
     */
    public void onBefore(Request request, int id) {}

    /**
     * UI Thread，返回响应之后调用
     * @param id 请求 id
     */
    public void onAfter(int id) {}

    /**
     * UI Thread，文件下载成功以后调用
     * @param response 响应
     * @param id 请求 id
     */
    @Override
    public void onSucceed(String response, int id) {}

    /**
     * 文件下载成功，回调
     * @param response  下载下来的 File
     * @param id 请求 id
     */
    public abstract void onFileSucceed(File response, int id);

    /**
     * 更新进度， UI 线程
     * @param progress 下载文件的进度
     * @param total 文件大小
     * @param id id
     */
    public void onUpdateProgress(float progress, long total, int id){}
}
