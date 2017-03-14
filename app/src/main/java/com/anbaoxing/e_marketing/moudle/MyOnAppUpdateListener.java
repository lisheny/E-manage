package com.anbaoxing.e_marketing.moudle;

import android.app.ProgressDialog;

import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.utils.MD5Utils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

/**
 * author：kang
 * time:  2016-12-2
 */
public class MyOnAppUpdateListener implements AppUpdateBiz.OnAppUpdateListener{

    private static final String TAG = MyOnAppUpdateListener.class.getSimpleName() + "  -->  ";

    /**
     * 发送请求之前调用
     * @param username 用户名
     * @param requestPackage 请求包
     */
    @Override
    public void onUpdateBefore(String username, JSONObject requestPackage) {
        String info = TAG + "onUpdateBefore() --> username：" + username + "  requestPackage：" + requestPackage;
        Logger.d(info);
        //发送出去的请求包数据都用MD5加密一遍
        try {
            requestPackage.remove(RequestPackage.packmd5);
            String mMD5 = MD5Utils.getPackmd5(requestPackage);
            requestPackage.put(RequestPackage.packmd5,mMD5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求发送成功以后调用
     * @param response 响应数据包
     * @param id 请求 id
     */
    @Override
    public void onRequestSucceed(String response, int id) {
        String info = TAG + "onRequestSucceed() --> response：" + response + "  id：" + id;
       Logger.d(info);
    }

    @Override
    public void onRequestFailure(Call call, Exception e, int id) {
        String info = TAG + "onRequestFailure() --> call：" + call.request().url() + "  Exception：" + e.getMessage() + "  id：" + id;
       Logger.d(info);
    }

    /**
     * 服务器上有新的版本，是否需要弹出对话框提示用户升级
     * @param serverVersionCode 服务器上的版本号
     * @param downloadUrl Apk 文件 Url
     * @return 是否需要提示用户升级，true - 提示用户，false - 不提示用户
     */
    @Override
    public boolean onIsHintUserUpdate(int serverVersionCode, String downloadUrl) {
        return true;
    }

    /**
     * 更新 ProgressDialog 进度,调用多次
     * @param progress 下载进度(字节)
     * @param total 文件大小(字节)
     * @param id 请求 id
     * @return 是否已经更新过进度，true - 已经更新过进度，false - 没有更新过进度
     */
    @Override
    public boolean onUpdateProgress(ProgressDialog dialog, float progress, long total, int id) {
        return false;
    }

    /**
     * 下载文件成功以后调用
     * @param response 下载的文件路径 (/storage/emulated/0/Download/Application.apk)
     * @param apkFile Apk 文件 (/storage/emulated/0/Download/Application.apk)
     * @param id 请求 id
     * @return  是否要安装 Apk 文件，true - 安装，false - 不安装
     */
    @Override
    public boolean onDownloadSucceed(File response, String apkFile, int id) {
        String info = TAG + "onDownloadSucceed() --> response：" + response + "  apkFile：" + apkFile + "  id：" + id;
       Logger.d(info);
        return true;
    }

    @Override
    public void onDownloadFailure(Call call, Exception e, int id) {
        String info = TAG + "onDownloadFailure() --> call：" + call.request().url() + "  Exception：" + e.getMessage() + "  id：" + id;
       Logger.d(info);
    }
}
