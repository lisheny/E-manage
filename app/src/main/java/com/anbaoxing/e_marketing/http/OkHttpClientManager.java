package com.anbaoxing.e_marketing.http;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.anbaoxing.e_marketing.utils.Constant;
import com.blankj.utilcode.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author：kangjia
 * time:  2016-10-19
 *
 * 发送 OkHttp 请求
 */
public class OkHttpClientManager {

    /** 所有超时时间 N s */
    private final static int TIMEOUT = 15 * 1000;

    private static OkHttpClientManager okHttpClientManager;
    private Handler handler;

    private OkHttpClientManager(){
        // 创建关联 主线程的 Handler 对象
        handler = new Handler(Looper.getMainLooper());
    }

    /** 单例模式 */
    public static OkHttpClientManager getInstance(){
        if (okHttpClientManager == null){
            synchronized (OkHttpClientManager.class){
                if (okHttpClientManager == null){
                    // 创建 okHttpClientManager 对象
                    okHttpClientManager = new OkHttpClientManager();
                }
            }
        }
        return okHttpClientManager;
    }

    /**
     * OkHttp post 请求
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param jsonObject  请求参数
     * @param okHttpResponseHandler 请求回调
     */
    private void _okHttpPost(String url, Object tag, JSONObject jsonObject, final OkHttpResponseHandler okHttpResponseHandler){
        try {
            // 请求参数 或 URL 为空
            if (parameterIsEmpty(url, jsonObject)) return;

            // 异步发送 post 请求
            getPostRequestCall(url, tag, jsonObject).execute(new StringCallback() {
                /** 此方法运行在工作线程 */
                @Override
                public void onError(Call call, Exception e, int id) {
                    // 请求失败
                    _onFailure(call, e, id, okHttpResponseHandler);
                }

                /** 此方法运行在工作线程 */
                @Override
                public void onResponse(String response, int id) {
                    // 请求成功
                    _onSucceed(response, id, okHttpResponseHandler);
                }
            });
        } catch (Exception e) {
            ToastUtils.showShortToast("网络繁忙！");
            e.printStackTrace();
        }
    }

    /**
     * OkHttp post 请求
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param jsonObject  请求参数
     * @param okHttpResponseHandlerImpl 请求回调
     */
    private void _okHttpPost(String url, Object tag, JSONObject jsonObject, final OkHttpResponseHandlerImpl okHttpResponseHandlerImpl){
        try {
            // 请求参数 或 URL 为空
            if (parameterIsEmpty(url, jsonObject)) return;

            // 异步发送 post 请求
            getPostRequestCall(url, tag, jsonObject).execute(new StringCallback() {

                /**
                 * UI Thread,发送请求之前调用
                 * @param request 请求包
                 * @param id 请求 id
                 */
                @Override
                public void onBefore(Request request, int id) {
                    if (okHttpResponseHandlerImpl != null) {
                        okHttpResponseHandlerImpl.onBefore(request, id);
                    }
                }

                /** 此方法运行在工作线程 */
                @Override
                public void onError(Call call, Exception e, int id) {
                    // 请求失败
                    _onFailure(call, e, id, okHttpResponseHandlerImpl);
                }

                /** 此方法运行在工作线程 */
                @Override
                public void onResponse(String response, int id) {
                    // 请求成功
                    _onSucceed(response, id, okHttpResponseHandlerImpl);
                }

                /**
                 * UI Thread，返回响应之后调用
                 * @param id 请求 id
                 */
                @Override
                public void onAfter(int id) {
                    if (okHttpResponseHandlerImpl != null) {
                        okHttpResponseHandlerImpl.onAfter(id);
                    }
                }
            });
        } catch (Exception e) {
            ToastUtils.showShortToast("网络繁忙！");
            e.printStackTrace();
        }
    }

    /**
     * OkHttp get 请求，下载文件
     * @param url 请求 url
     * @param destFileDir  目标文件存储的文件夹路径
     * @param destFileName  目标文件存储的文件名
     * @param tag 请求标签，用于取消请求
     * @param fileResponseHandler 请求回调
     */
    private void _okHttpGetDownloadFile(String url, Object tag, String destFileDir, String destFileName,
                                        final OkHttpFileResponseHandler fileResponseHandler){
        try {
            // 判断 参数 是否为空
            if (fileParameterIsEmpty(url, destFileDir, destFileName)) return;

            // 异步发送 get 请求
            getGetRequestCall(url, tag).execute(new FileCallBack(destFileDir,destFileName) {
                /** UI Thread */
                @Override
                public void onBefore(Request request, int id) {
                    fileResponseHandler.onBefore(request,id);
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    // 请求失败
                    _onFailure(call, e, id, fileResponseHandler);
                }

                /** UI Thread */
                @Override
                public void inProgress(float progress, long total, int id) {
                    // UI Thread
                    fileResponseHandler.onUpdateProgress(progress, total, id);
                }

                @Override
                public void onResponse(File response, int id) {
                    // 请求成功
                    _onFileSucceed(response, id, fileResponseHandler);
                }
            });
        } catch (Exception e) {
            ToastUtils.showShortToast("网络繁忙！");
            e.printStackTrace();
        }
    }

    /**
     * OkHttp get 请求
     * @param url 请求 url
     * @param tag 请求标签，用于取消请求
     * @param okHttpResponseHandler 请求回调
     */
    private void _okHttpGet(String url, Object tag, final OkHttpResponseHandler okHttpResponseHandler){
        getGetRequestCall(url,tag).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                _onFailure(call,e,id,okHttpResponseHandler);
            }

            @Override
            public void onResponse(String response, int id) {
                _onSucceed(response,id,okHttpResponseHandler);
            }
        });
    }

    /**
     * 请求成功 回调
     * @param response 响应数据包
     * @param okHttpResponseHandler  请求回调
     */
    private void _onSucceed(final String response, final int id, final OkHttpResponseHandler okHttpResponseHandler){
        OkHttpSucceedMessageRunnable succeedMessageRunnable =
                new OkHttpSucceedMessageRunnable(response,id,okHttpResponseHandler);
        handler.post(succeedMessageRunnable);
    }

    /**
     * 请求成功 回调
     * @param file 下载下来的 File
     * @param fileResponseHandler  请求回调
     */
    private void _onFileSucceed(final File file, final int id, final OkHttpFileResponseHandler fileResponseHandler){
        OkHttpFileSucceedMessageRunnable fileSucceedMessageRunnable =
                new OkHttpFileSucceedMessageRunnable(file,id,fileResponseHandler);
        handler.post(fileSucceedMessageRunnable);
    }

    /**
     * 请求失败 回调
     * @param call Call
     * @param e IoException
     * @param okHttpResponseHandler  请求回调
     */
    private void _onFailure(final Call call, final Exception e, final int id, final OkHttpResponseHandler okHttpResponseHandler){
        OkHttpFailureMessageRunnable failureMessageRunnable =
                new OkHttpFailureMessageRunnable(call,e,id,okHttpResponseHandler);
        handler.post(failureMessageRunnable);
    }



    /** 判断请求参数是否为空 */
    private boolean parameterIsEmpty(String url, JSONObject parameter){
        if (TextUtils.isEmpty(url) || parameter == null || TextUtils.isEmpty(parameter.toString())){
            ToastUtils.showShortToast("请求参数 或 URL 为空");
            return true;
        }else {
            return false;
        }
    }

    /** 下载文件时的请求参数是否为空 */
    private boolean fileParameterIsEmpty(String url, String destFileDir, String destFileName){
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(destFileDir) || TextUtils.isEmpty(destFileName)){
            ToastUtils.showShortToast("请求参数 或 URL 为空");
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取 post 请求的 RequestCall
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param jsonObject  请求参数
     */
    public static RequestCall getPostRequestCall(String url, Object tag, JSONObject jsonObject){
        RequestCall requestCall = OkHttpUtils.post().url(url).tag(tag)
                .addParams(Constant.REQUEST_HEAD, jsonObject.toString())
                .build();
        OkHttpClientManager.setTimeOut(requestCall);
        return requestCall;
    }

    /**
     * 获取 get 请求的 RequestCall
     * @param url 请求 url
     */
    public static RequestCall getGetRequestCall(String url, Object tag){
        RequestCall requestCall = OkHttpUtils.get().url(url).tag(tag).build();
        OkHttpClientManager.setTimeOut(requestCall);
        return requestCall;
    }

    /** 设置 所有连接超时时间 */
    private static void setTimeOut(RequestCall requestCall){
        if (requestCall != null) {
            requestCall.readTimeOut(TIMEOUT).writeTimeOut(TIMEOUT).connTimeOut(TIMEOUT);
        }
    }

    /** 根据 tag,取消请求 */
    public static void cancelRequest(Object tag){
        OkHttpUtils.getInstance().cancelTag(tag);
    }

    /**
     * OkHttp post 请求
     * @param parameter  请求参数
     * @param okHttpResponseHandler 请求回调
     */
    public static void okHttpPost(@NonNull JSONObject parameter, @NonNull final OkHttpResponseHandler okHttpResponseHandler){
        getInstance()._okHttpPost(com.anbaoxing.e_marketing.http.URL.HOST_URL, null, parameter, okHttpResponseHandler);
    }

    /**
     * OkHttp post 请求
     * @param url 请求 url
     * @param parameter  请求参数
     * @param okHttpResponseHandler 请求回调
     */
    public static void okHttpPost(@NonNull String url, @NonNull JSONObject parameter, @NonNull final OkHttpResponseHandler okHttpResponseHandler) {
        getInstance()._okHttpPost(url,null,parameter,okHttpResponseHandler);
    }

    /**
     * OkHttp post 请求
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param parameter  请求参数
     * @param okHttpResponseHandler 请求回调
     */
    public static void okHttpPost(@NonNull String url, Object tag, @NonNull JSONObject parameter,
                                  @NonNull final OkHttpResponseHandler okHttpResponseHandler) {
        getInstance()._okHttpPost(url,tag,parameter,okHttpResponseHandler);
    }

    /**
     * OkHttp post 请求
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     *@param parameter  请求参数
     * @param okHttpResponseHandlerImpl 需要在请求之前，或请求之后做相应的处理时的请求回调
     */
    public static void okHttpPost(@NonNull String url, Object tag, @NonNull JSONObject parameter,
                                  @NonNull final OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        getInstance()._okHttpPost(url,tag,parameter, okHttpResponseHandlerImpl);
    }

    /**
     * OkHttp post 请求
     * @param url 请求 url
     *@param parameter  请求参数
     * @param okHttpResponseHandlerImpl 需要在请求之前，或请求之后做相应的处理时的请求回调
     */
    public static void okHttpPost(@NonNull String url, @NonNull JSONObject parameter,
                                  @NonNull final OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        getInstance()._okHttpPost(url,null,parameter, okHttpResponseHandlerImpl);
    }

    /**
     * OkHttp get 请求，下载文件
     * @param url 请求 url
     * @param destFileDir  目标文件存储的文件夹路径
     * @param destFileName  目标文件存储的文件名
     * @param fileResponseHandler 请求回调
     */
    public static void okHttpGetDownloadFile(@NonNull String url, @NonNull String destFileDir,
                                             @NonNull String destFileName, @NonNull final OkHttpFileResponseHandler fileResponseHandler){
        getInstance()._okHttpGetDownloadFile(url, null, destFileDir, destFileName, fileResponseHandler);
    }

    /**
     * OkHttp get 请求，下载文件
     * @param destFileDir  目标文件存储的文件夹路径
     * @param destFileName  目标文件存储的文件名
     * @param fileResponseHandler 请求回调
     */
    public static void okHttpGetDownloadFile(@NonNull String destFileDir, @NonNull String destFileName,
                                             @NonNull final OkHttpFileResponseHandler fileResponseHandler){
        getInstance()._okHttpGetDownloadFile(com.anbaoxing.e_marketing.http.URL.HOST_URL, null, destFileDir, destFileName, fileResponseHandler);
    }

    /**
     * OkHttp get 请求，下载文件
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param destFileDir  目标文件存储的文件夹路径
     * @param destFileName  目标文件存储的文件名
     * @param fileResponseHandler 请求回调
     */
    public static void okHttpGetDownloadFile(@NonNull String url, @NonNull Object tag , @NonNull String destFileDir,
                                             @NonNull String destFileName, @NonNull final OkHttpFileResponseHandler fileResponseHandler){
        getInstance()._okHttpGetDownloadFile(url,tag,destFileDir,destFileName,fileResponseHandler);
    }

    /**
     * OkHttp get 请求
     * @param url 请求 url
     * @param tag 请求标签，用于取消请求
     * @param okHttpResponseHandler 请求回调
     */
    public static void okHttpGet(@NonNull String url, @NonNull Object tag, @NonNull final OkHttpResponseHandler okHttpResponseHandler){
        getInstance()._okHttpGet(url, tag, okHttpResponseHandler);
    }

    /**
     * OkHttp get 请求
     * @param url 请求 url
     * @param okHttpResponseHandler 请求回调
     */
    public static void okHttpGet(@NonNull String url, @NonNull final OkHttpResponseHandler okHttpResponseHandler){
        getInstance()._okHttpGet(url,null,okHttpResponseHandler);
    }
}


