package com.anbaoxing.e_marketing.moudle;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.anbaoxing.e_marketing.MyApplication;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.http.URL;
import com.anbaoxing.e_marketing.utils.MainUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author：kang
 * time:  2016-11-23
 *
 * 修改密码
 */
public class AmendPassword extends RequestPackageImpl{

    /** 修改密码请求包的字段 */
    public static final String oldpwd = "pwd";
    public static final String newpwd = "newpwd";

    public AmendPassword() {
        this(RequestPackage.AMEND_PASSWORD_COMMAND);
    }

    public AmendPassword(String command) {
        super(command);
    }

    /**
     * 发送 修改密码 请求，如果有 用户名(authcode) 和 验证码(authcode)，
     * 需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etOldPassWord 原密码 输入框
     * @param etNewPassWord 新密码 输入框
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param amendPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void amendPassword(@NonNull EditText etOldPassWord, @NonNull EditText etNewPassWord,
                              @NonNull String url, Object tag, OnAmendPasswordListener amendPasswordListener,
                              @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        try {
            if (this.enterIsEmpty(etOldPassWord) || this.enterIsEmpty(etNewPassWord)){
                // 输入为空
                this.verifyFailure(amendPasswordListener,etOldPassWord,etNewPassWord);
                return;
            }else {
                // 验证成功
                this.verifySucceed(amendPasswordListener,etOldPassWord,etNewPassWord);
            }
            // 隐藏键盘
            MainUtils.concealKeyboard(etNewPassWord);
            // 原密码
            String oldPassWord = MainUtils.getMd5(etOldPassWord);
            // 新密码
            String newPassWord = MainUtils.getMd5(etNewPassWord);
            requestPackage.put(username, MyApplication.getUseBeen().getUsername());
            requestPackage.put(oldpwd, oldPassWord);
            requestPackage.put(newpwd,newPassWord);
            if (this.isSupplementComplete()) {
                // 补全请求包
                this.supplementComplete();
            }
            if (amendPasswordListener != null){
                // 调用接口
                amendPasswordListener.onAmendPasswordBefore(etOldPassWord,etNewPassWord,requestPackage);
            }
            // 发送请求
            this.sendPostRequest(url, tag, requestPackage, okHttpResponseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送 修改密码 请求，如果有 用户名(authcode) 和 验证码(authcode)，
     * 需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etOldPassWord 原密码 输入框
     * @param etNewPassWord 新密码 输入框
     * @param tag 请求 tag，用于取消请求
     * @param amendPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void amendPassword(@NonNull EditText etOldPassWord, @NonNull EditText etNewPassWord, Object tag,
                              OnAmendPasswordListener amendPasswordListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.amendPassword(etOldPassWord,etNewPassWord, URL.HOST_URL,tag,amendPasswordListener,okHttpResponseHandler);
    }

    /**
     * 发送 修改密码 请求，如果有 用户名(authcode) 和 验证码(authcode)，
     * 需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etOldPassWord 原密码 输入框
     * @param etNewPassWord 新密码 输入框
     * @param amendPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void amendPassword(@NonNull EditText etOldPassWord, @NonNull EditText etNewPassWord,
                              OnAmendPasswordListener amendPasswordListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.amendPassword(etOldPassWord,etNewPassWord, URL.HOST_URL,null,amendPasswordListener,okHttpResponseHandler);
    }

    /**
     * 发送 修改密码 请求，如果有 用户名(authcode) 和 验证码(authcode)，
     * 需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etOldPassWord 原密码 输入框
     * @param etNewPassWord 新密码 输入框
     * @param okHttpResponseHandler 请求回调
     */
    public void amendPassword(@NonNull EditText etOldPassWord, @NonNull EditText etNewPassWord,
                              @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.amendPassword(etOldPassWord,etNewPassWord, URL.HOST_URL,null,null,okHttpResponseHandler);
    }

    /**
     * 发送 修改密码 请求，如果有 用户名(authcode) 和 验证码(authcode)，
     * 需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etOldPassWord 原密码 输入框
     * @param etNewPassWord 新密码 输入框
     * @param tag 请求 tag，用于取消请求
     * @param amendPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void amendPassword(@NonNull EditText etOldPassWord, @NonNull EditText etNewPassWord, Object tag,
                              OnAmendPasswordListener amendPasswordListener,
                              @NonNull OkHttpResponseHandlerImpl okHttpResponseHandler) {
        this.amendPassword(etOldPassWord,etNewPassWord,URL.HOST_URL,tag,amendPasswordListener,okHttpResponseHandler);
    }

    /**
     * 发送 修改密码 请求，如果有 用户名(authcode) 和 验证码(authcode)，
     * 需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etOldPassWord 原密码 输入框
     * @param etNewPassWord 新密码 输入框
     * @param amendPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void amendPassword(@NonNull EditText etOldPassWord, @NonNull EditText etNewPassWord,
                              OnAmendPasswordListener amendPasswordListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandler) {
        this.amendPassword(etOldPassWord,etNewPassWord,URL.HOST_URL,null,amendPasswordListener,okHttpResponseHandler);
    }

    /**
     * 发送 修改密码 请求，如果有 用户名(authcode) 和 验证码(authcode)，
     * 需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etOldPassWord 原密码 输入框
     * @param etNewPassWord 新密码 输入框
     * @param okHttpResponseHandler 请求回调
     */
    public void amendPassword(@NonNull EditText etOldPassWord, @NonNull EditText etNewPassWord,
                              @NonNull OkHttpResponseHandlerImpl okHttpResponseHandler) {
        this.amendPassword(etOldPassWord,etNewPassWord,URL.HOST_URL,null,null,okHttpResponseHandler);
    }

    /** 判断请求包字段是否完整，如果不完整，则传默认值 */
    private void supplementComplete(){
        try {
            if (!requestPackage.has(username)){
                requestPackage.put(username,defaultValue);
            }
            if (!requestPackage.has(oldpwd)){
                requestPackage.put(oldpwd,defaultValue);
            }
            if (!requestPackage.has(newpwd)){
                requestPackage.put(newpwd,defaultValue);
            }
            if (!requestPackage.has(authcode)){
                requestPackage.put(authcode,defaultValue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** 修改密码监听接口 */
    public interface OnAmendPasswordListener extends OnVerifyListener{
        /**
         * 发送请求之前调用,获取请求包
         * @param etOldPassWord 原密码
         * @param etNewPassWord 新密码
         * @param requestPackage 请求包
         */
        void onAmendPasswordBefore(EditText etOldPassWord, EditText etNewPassWord, JSONObject requestPackage);
    }
}
