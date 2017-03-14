package com.anbaoxing.e_marketing.moudle;


import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.blankj.utilcode.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import okhttp3.Call;

/**
 * author：kang
 * time:  2016-
 */
public class MyOkHttpResponseHandler implements OkHttpResponseHandler {

    private static final String TAG = MyOkHttpResponseHandler.class.getSimpleName() + "  -->  ";

    @Override
    public void onSucceed(String response, int id) {
        String info = TAG + "onSucceed()  -->  " + "  response：" + response + "  id：" + id;
        Logger.i(TAG,info);
    }

    @Override
    public void onFailure(Call call, Exception e, int id) {
        ToastUtils.showShortToast("网络请求失败");
        String info = TAG + "onFailure()  -->  call：" + call.request().toString() + "  id：" + id;
        Logger.i(TAG,info);
    }
}
