package com.anbaoxing.e_marketing.moudle;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.http.URL;
import com.anbaoxing.e_marketing.utils.MD5Utils;
import com.anbaoxing.e_marketing.utils.MainUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author：kang
 * time:  2016-11-23
 *
 * 忘记密码(验证验证码)，重置密码
 */
public class ForgetPassword extends RequestPackageImpl{

    private static final String TAG = ForgetPassword.class.getSimpleName() + "  -->  ";

    /** 忘记密码请求包的字段 */
    public static final String newpassword = "newpwd";

    /**
     * 请求类型：
     * 1--忘记密码，可能需要验证验证码
     * 2--重置密码
     */
    public static final String REQUEST_TYPE_FORGET_PASSWORD = "1";
    public static final String REQUEST_TYPE_RESET_PASSWORD = "2";

    public ForgetPassword() {
        this(RequestPackage.FORGET_PASSWORD_COMMAND);
    }

    public ForgetPassword(String command) {
        super(command);
    }

    /**
     * 发送 忘记密码(验证验证码)，重置密码，请求，
     * 如果有 用户名(authcode) 和 验证码(authcode)，需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etNewPassWord 密码
     * @param requestType 请求类型，ForgetPassword.REQUEST_TYPE_FORGET_PASSWORD or
     * ForgetPassword.REQUEST_TYPE_RESET_PASSWORD ....
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param forgetPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void forgetPassword(@NonNull EditText etNewPassWord, @NonNull String requestType, @NonNull String url, Object tag,
                               OnForgetPasswordListener forgetPasswordListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        try {
            if (this.passWordIsEmpty(etNewPassWord,requestType) ||
                    (!this.checkRequestType(requestType))){ // 检查请求类型
                // 密码为空
                this.verifyFailure(forgetPasswordListener,etNewPassWord);
                return;
            }else {
                // 密码输入完成
                this.verifySucceed(forgetPasswordListener,etNewPassWord);
            }
            // 隐藏键盘
            MainUtils.concealKeyboard(etNewPassWord);
            // 密码
            String passWord = MD5Utils.getMd5(MainUtils.getText(etNewPassWord));
            requestPackage.put(newpassword, passWord);
            requestPackage.put(requesttype,requestType);

            if (this.isSupplementComplete()) {
                // 补全请求包
                this.supplementComplete();
            }
            if (forgetPasswordListener != null){
                // 调用接口
                forgetPasswordListener.onForgetPasswordBefore(etNewPassWord, requestPackage);
            }
            // 发送请求
            this.sendPostRequest(url,tag,requestPackage,okHttpResponseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送 忘记密码(验证验证码)，重置密码，请求，
     * 如果有 用户名(authcode) 和 验证码(authcode)，需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etNewPassWord 密码
     * @param requestType 注册方式
     * @param tag 请求 tag，用于取消请求
     * @param forgetPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     * @see ForgetPassword
     * @see # ForgetPassword.REQUEST_TYPE_FORGET_PASSWORD or ForgetPassword.REQUEST_TYPE_RESET_PASSWORD ....
     */
    public void forgetPassword(@NonNull EditText etNewPassWord, @NonNull String requestType, Object tag,
                               OnForgetPasswordListener forgetPasswordListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.forgetPassword(etNewPassWord, requestType, URL.HOST_URL, tag, forgetPasswordListener, okHttpResponseHandler);
    }

    /**
     * 发送 忘记密码(验证验证码)，重置密码，请求，
     * 如果有 用户名(authcode) 和 验证码(authcode)，需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etNewPassWord 密码
     * @param requestType 注册方式
     * @param forgetPasswordListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     * @see  ForgetPassword
     * @see # ForgetPassword.REQUEST_TYPE_FORGET_PASSWORD or ForgetPassword.REQUEST_TYPE_RESET_PASSWORD ....
     */
    public void forgetPassword(@NonNull EditText etNewPassWord, @NonNull String requestType,
                               OnForgetPasswordListener forgetPasswordListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.forgetPassword(etNewPassWord, requestType, URL.HOST_URL, null, forgetPasswordListener, okHttpResponseHandler);
    }

    /**
     * 发送 忘记密码(验证验证码)，重置密码，请求，
     * 如果有 用户名(authcode) 和 验证码(authcode)，需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etNewPassWord 密码
     * @param requestType 注册方式
     * @param okHttpResponseHandler 请求回调
     */
    public void forgetPassword(@NonNull EditText etNewPassWord, @NonNull String requestType,
                               @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.forgetPassword(etNewPassWord, requestType, URL.HOST_URL, null, null, okHttpResponseHandler);
    }

    /**
     * 发送 忘记密码(验证验证码)，重置密码，请求，
     * 如果有 用户名(authcode) 和 验证码(authcode)，需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etNewPassWord 密码
     * @param requestType 注册方式
     * @param tag 请求 tag，用于取消请求
     * @param forgetPasswordListener  注册监听器接口
     * @param okHttpResponseHandlerImpl 请求回调
     */
    public void forgetPassword(@NonNull EditText etNewPassWord, @NonNull String requestType, Object tag,
                               OnForgetPasswordListener forgetPasswordListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        this.forgetPassword(etNewPassWord, requestType, URL.HOST_URL, tag, forgetPasswordListener, okHttpResponseHandlerImpl);
    }

    /**
     * 发送 忘记密码(验证验证码)，重置密码，请求，
     * 如果有 用户名(authcode) 和 验证码(authcode)，需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etNewPassWord 密码
     * @param requestType 注册方式
     * @param forgetPasswordListener  注册监听器接口
     * @param okHttpResponseHandlerImpl 请求回调
     */
    public void forgetPassword(@NonNull EditText etNewPassWord, @NonNull String requestType,
                               OnForgetPasswordListener forgetPasswordListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        this.forgetPassword(etNewPassWord, requestType, URL.HOST_URL, null, forgetPasswordListener, okHttpResponseHandlerImpl);
    }

    /**
     * 发送 忘记密码(验证验证码)，重置密码，请求，
     * 如果有 用户名(authcode) 和 验证码(authcode)，需要自己设置，否则传空字符串
     * this.setAuthcode(String authcode),设置 验证码
     * this.setUsername(String username),设置 用户名
     * @param etNewPassWord 密码
     * @param requestType 注册方式
     * @param okHttpResponseHandlerImpl 请求回调
     */
    public void forgetPassword(@NonNull EditText etNewPassWord, @NonNull String requestType,
                               @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        this.forgetPassword(etNewPassWord, requestType, URL.HOST_URL, null, null, okHttpResponseHandlerImpl);
    }

    /** 检查请求类型 true - 成功 false - 失败 */
    private boolean checkRequestType(String requestType){
        if (REQUEST_TYPE_FORGET_PASSWORD.equals(requestType)){
            return true;
        }else if(REQUEST_TYPE_RESET_PASSWORD.equals(requestType)){
            return true;
        }else {
            Logger.d(TAG,"checkEnter()  -->  没有这样的请求类型：" + requestType);

            errorMsg = "没有这样的请求类型：" + requestType + "。" +
                    "(请求类型有：ForgetPassword.REQUEST_TYPE_FORGET_PASSWORD or ForgetPassword.REQUEST_TYPE_RESET_PASSWORD)";
            return false;
        }
    }

    /** 判断请求包字段是否完整，如果不完整，则传默认值 */
    private void supplementComplete(){
        try {
            if (!requestPackage.has(username)){
                requestPackage.put(username,defaultValue);
            }
            if (!requestPackage.has(authcode)){
                requestPackage.put(authcode,defaultValue);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /** 忘记密码监听接口 */
    public interface OnForgetPasswordListener extends OnPassWordVerifyListener{
        /**
         * 发送请求之前调用,获取请求包
         * @param etPassWord 密码
         * @param requestPackage 请求包
         */
        void onForgetPasswordBefore(EditText etPassWord, JSONObject requestPackage);
    }
}





































