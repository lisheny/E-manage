package com.anbaoxing.e_marketing.moudle;

import okhttp3.Call;

/**
 * author：kang
 * time:  2016-12-2
 *
 * 请求失败接口
 */
public interface OnFailureListener {

    /**
     * 请求发送失败以后调用
     * @param call 请求内容
     * @param e 请求失败的异常
     * @param id 请求 id
     */
    void onRequestFailure(Call call, Exception e, int id);

    /**
     * 下载文件失败以后调用
     * @param call 请求内容
     * @param e 请求失败的异常
     * @param id 请求 id
     */
    void onDownloadFailure(Call call, Exception e, int id);
}
