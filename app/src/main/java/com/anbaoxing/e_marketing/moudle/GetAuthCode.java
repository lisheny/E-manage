package com.anbaoxing.e_marketing.moudle;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.http.URL;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author：kang
 * time:  2016-11-23
 *
 * 获取验证码
 */
public class GetAuthCode extends RequestPackageImpl{

    private static final String TAG = GetAuthCode.class.getSimpleName() + "  -->  ";

    /**
     * 请求类型
     * 是否需要判断用户是否存在:
     * 1--用户不存在，不要发短信(获取验证码)
     * 2--用户存在，不要发短信(注册)
     * 3--都要发短信(默认)
     */
    public static final String REQUEST_TYPE_USER_NOT_EXIST = "1";
    public static final String REQUEST_TYPE_USER_EXIST = "2";
    public static final String REQUEST_TYPE_ALL_DEFAULT  = "3";

    public GetAuthCode() {
        this(RequestPackage.GET_AUTH_CODE_COMMAND);
    }

    public GetAuthCode(String command) {
        super(command);
    }

    /**
     * 发送 获取验证码 请求
     * @param userName 用户名
     * @param loginType 登录类型
     * @param requestType 请求类型
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param authCodeListener  获取验证码监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void getAuthCode(@NonNull String userName, @NonNull String loginType, @NonNull String requestType, @NonNull String url,
                            Object tag, OnGetAuthCodeListener authCodeListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        try {
            if (!this.checkRequestType(requestType)){ // 判断请求类型
                requestType = GetAuthCode.REQUEST_TYPE_ALL_DEFAULT;
            }
            if (this.checkLoginType(userName,loginType)
                    && (!userNameisEmpty(userName))){ // 判断登录类型
                if (authCodeListener != null){
                    // 验证成功
                    authCodeListener.onVerifySucceed(userName,loginType,requestType);
                }
            }else {
                if (authCodeListener != null){
                    // 验证失败
                    authCodeListener.onVerifyFailure(userName, loginType, requestType,errorMsg);
                }
                return;
            }

            requestPackage.put(RequestPackageImpl.username,userName);
            requestPackage.put(Login.logintype,loginType);
            requestPackage.put(RequestPackageImpl.requesttype,requestType);
            if (authCodeListener != null){
                // 调用接口
                authCodeListener.onGetAuthCodeBefore(userName, loginType, requestType,requestPackage);
            }
            // 发送请求
            this.sendPostRequest(url, tag, requestPackage, okHttpResponseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送 获取验证码 请求
     * @param userName 用户名
     * @param loginType 登录类型
     * @param requestType 请求类型
     * @param tag 请求 tag，用于取消请求
     * @param authCodeListener  获取验证码监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void getAuthCode(@NonNull String userName, @NonNull String loginType, @NonNull String requestType, Object tag,
                            OnGetAuthCodeListener authCodeListener, @NonNull OkHttpResponseHandler okHttpResponseHandler){
        this.getAuthCode(userName,loginType,requestType, URL.HOST_URL,tag,authCodeListener,okHttpResponseHandler);
    }

    /**
     * 发送 获取验证码 请求
     * @param userName 用户名
     * @param loginType 登录类型
     * @param requestType 请求类型
     * @param authCodeListener  获取验证码监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void getAuthCode(@NonNull String userName, @NonNull String loginType, @NonNull String requestType,
                            OnGetAuthCodeListener authCodeListener, @NonNull OkHttpResponseHandler okHttpResponseHandler){
        this.getAuthCode(userName,loginType,requestType, URL.HOST_URL,null,authCodeListener,okHttpResponseHandler);
    }

    /**
     * 发送 获取验证码 请求
     * @param userName 用户名
     * @param loginType 登录类型
     * @param requestType 请求类型
     * @param okHttpResponseHandler 请求回调
     */
    public void getAuthCode(@NonNull String userName, @NonNull String loginType, @NonNull String requestType,
                            @NonNull OkHttpResponseHandler okHttpResponseHandler){
        this.getAuthCode(userName,loginType,requestType, URL.HOST_URL,null,null,okHttpResponseHandler);
    }

    /**
     * 发送 获取验证码 请求
     * @param userName 用户名
     * @param loginType 登录类型
     * @param requestType 请求类型
     * @param tag 请求 tag，用于取消请求
     * @param authCodeListener  获取验证码监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void getAuthCode(@NonNull String userName, @NonNull String loginType, @NonNull String requestType, Object tag,
                            OnGetAuthCodeListener authCodeListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandler){
        this.getAuthCode(userName,loginType,requestType,URL.HOST_URL,tag,authCodeListener,okHttpResponseHandler);
    }

    /**
     * 发送 获取验证码 请求
     * @param userName 用户名
     * @param loginType 登录类型
     * @param requestType 请求类型
     * @param authCodeListener  获取验证码监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void getAuthCode(@NonNull String userName, @NonNull String loginType, @NonNull String requestType,
                            OnGetAuthCodeListener authCodeListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandler){
        this.getAuthCode(userName,loginType,requestType,URL.HOST_URL,null,authCodeListener,okHttpResponseHandler);
    }

    /**
     * 发送 获取验证码 请求
     * @param userName 用户名
     * @param loginType 登录类型
     * @param requestType 请求类型
     * @param okHttpResponseHandler 请求回调
     */
    public void getAuthCode(@NonNull String userName, @NonNull String loginType, @NonNull String requestType,
                            @NonNull OkHttpResponseHandlerImpl okHttpResponseHandler){
        this.getAuthCode(userName,loginType,requestType,URL.HOST_URL,null,null,okHttpResponseHandler);
    }

    /**
     * 判断登录类型
     * @param userName 用户名
     * @param loginType 登录类型
     */
    private boolean checkLoginType(String userName, String loginType){
        if (Login.LOGIN_TYPE_PHONE_NUMBER.equals(loginType)){  // 手机号码
            return isPhoneNumber(userName);
        }else if (Login.LOGIN_TYPE_EMAIL.equals(loginType)){  // 邮箱
            return isEmail(userName);
        }else if (Login.LOGIN_TYPE_ID_CARD_NO.equals(loginType)){ // 身份证号码
            return isIdCardNo(userName);
        }else if (Login.LOGIN_TYPE_ACCREDIT.equals(loginType)){  // 第三方授权登录
            return true;
        }else if (Login.LOGIN_TYPE_AUTH_CODE.equals(loginType)){  // 验证码(该方式登录不需要密码)
            return true;
        }else {
            Logger.d(TAG,"checkLoginType()  -->  没有这样的登录类型：" + loginType);
            errorMsg = "没有这样的登录类型：" + loginType + "。" +
                    "(登录类型有：Login.LOGIN_TYPE_PHONE_NUMBER or Login.LOGIN_TYPE_EMAIL ....)";
            return false;
        }
    }

    /**
     * 判断请求类型
     * @param requestType 请求类型
     */
    private boolean checkRequestType(String requestType){
        if (REQUEST_TYPE_USER_NOT_EXIST.equals(requestType)){ // 用户不存在，不要发短信
            return true;
        }else if (REQUEST_TYPE_USER_EXIST.equals(requestType)){ // 用户存在，不要发短信
            return true;
        }else if (REQUEST_TYPE_ALL_DEFAULT.equals(requestType)){ // 都要发短信(默认)
            return true;
        }else {
            Logger.d(TAG,"checkRequestType()  -->  没有这样的请求类型：" + requestType);
             errorMsg = "没有这样的请求类型：" + requestType + "。" +
                    "(请求类型有：GetAuthCode.REQUEST_TYPE_USER_NOT_EXIST or GetAuthCode.REQUEST_TYPE_USER_EXIST...)";
            return false;
        }
    }

    /** 判断用户名是否为空 */
    private boolean userNameisEmpty(String userName){
        if (TextUtils.isEmpty(userName)){
            errorMsg = "用户名为空";
            return true;
        }else {
            return false;
        }
    }

    /** 获取验证码监听接口 */
    public interface OnGetAuthCodeListener{

        /**
         * 验证成功调用
         * @param userName 用户名
         * @param loginType 登录类型
         * @param requestType 请求类型
         */
        void onVerifySucceed(String userName, String loginType, String requestType);

        /**
         * 验证失败调用
         * @param userName 用户名
         * @param loginType 登录类型
         * @param requestType 请求类型
         * @param errorMsg 错误信息
         */
        void onVerifyFailure(String userName, String loginType, String requestType, String errorMsg);

        /**
         * 发送请求之前调用,获取请求包
         * @param userName 用户名
         * @param loginType 登录类型
         * @param requestType 请求类型
         * @param requestPackage 请求包
         */
        void onGetAuthCodeBefore(String userName, String loginType, String requestType, JSONObject requestPackage);
    }
}
