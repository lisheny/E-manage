package com.anbaoxing.e_marketing.http;



import com.blankj.utilcode.utils.ToastUtils;

import java.io.File;

/**
 * author：kangjia
 * time:  2016-11-10
 *
 * 文件下载成功，发送的消息
 */
public class OkHttpFileSucceedMessageRunnable implements Runnable {

    private File file;
    /** 请求 id */
    private int id;

    /** 请求回调 */
    private OkHttpFileResponseHandler fileResponseHandler;

    public OkHttpFileSucceedMessageRunnable(File file, int id, OkHttpFileResponseHandler fileResponseHandler) {
        this.file = file;
        this.id = id;
        this.fileResponseHandler = fileResponseHandler;
    }

    @Override
    public void run() {
        if (fileResponseHandler != null) {
            // 回调接口，此方法运行在 UI 线程
            fileResponseHandler.onSucceed(file.toString(),id);
            fileResponseHandler.onFileSucceed(file,id);
        }else {
            ToastUtils.showShortToast("网络繁忙！");
        }
    }
}
