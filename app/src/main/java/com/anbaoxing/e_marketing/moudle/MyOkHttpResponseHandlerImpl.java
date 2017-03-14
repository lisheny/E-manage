package com.anbaoxing.e_marketing.moudle;

import com.anbaoxing.e_marketing.http.OkHttpResponseHandlerImpl;
import com.blankj.utilcode.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author：kang
 * time:  2016-
 */
public class MyOkHttpResponseHandlerImpl implements OkHttpResponseHandlerImpl {

    private static final String TAG = MyOkHttpResponseHandlerImpl.class.getSimpleName() + "  -->  ";

    /**
     * UI Thread,发送请求之前调用
     * @param request 请求包
     * @param id 请求 id
     */
    @Override
    public void onBefore(Request request, int id) {
        String info = TAG + "onBefore()  -->  request：" + request.toString() + "  id：" + id;
        Logger.i(TAG,info);
    }

    /**
     * UI Thread，返回响应之后调用， onSucceed 之后调用
     * @param id 请求 id
     */
    @Override
    public void onAfter(int id) {
        String info = TAG + "onAfter()  -->  id：" + id;
        Logger.d(TAG,info);
    }

    /**
     * 请求成功 -- 此方法在 Ui 线程中调用
     * @param response 响应数据包
     * @param id 请求 id
     */
    @Override
    public void onSucceed(String response, int id) {
        String info = TAG + "onSucceed()  -->  response：" + response + "  id：" + id;
        Logger.i(TAG,info);
    }

    /**
     * 请求失败 -- 此方法在 Ui 线程中调用
     * @param call 发请求
     * @param e 失败的异常
     * @param id 请求 id
     */
    @Override
    public void onFailure(Call call, Exception e, int id) {
        ToastUtils.showShortToast("网络请求失败");
        String info = TAG + "onFailure()  -->  call：" + call.request().toString() + "  id：" + id+" "+ e;
        Logger.e(TAG,info);
    }
}
