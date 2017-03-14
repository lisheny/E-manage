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
 * time:  2016-11-21
 *
 * 登录
 */
public class Login extends RequestPackageImpl {

    private static final String TAG = Login.class.getSimpleName() + "  -->  ";

    /** 登录请求包的字段 */
    public static final String logintype = "logintype";

    /** 登录类型：
     *  1--手机号码
     * 2--邮箱
     * 3--验证码(该方式登录不需要密码)
     * 4--身份证号码
     * 5--第三方授权登录
     */
    public static final String LOGIN_TYPE_PHONE_NUMBER = "1";
    public static final String LOGIN_TYPE_EMAIL = "2";
    public static final String LOGIN_TYPE_AUTH_CODE = "3";
    public static final String LOGIN_TYPE_ID_CARD_NO = "4";
    public static final String LOGIN_TYPE_ACCREDIT = "5";

    public Login() {
        this(RequestPackage.LOGIN_COMMAND);
    }

    public Login(String command) {
        super(command);
    }

    /**
     * 发送 登录 请求
     * @param etUserName 用户名 输入框
     * @param etPassWord 密码 输入框
     * @param loginType 登录方式，Login.LOGIN_TYPE_PHONE_NUMBER or
     *                  Login.LOGIN_TYPE_EMAIL ....
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param loginListener  登录监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void login(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String loginType, @NonNull String url,
                      Object tag, OnLoginListener loginListener, @NonNull OkHttpResponseHandler okHttpResponseHandler){
        try {
            if (this.checkEnter(etUserName,etPassWord,loginType)){
                // 验证成功
                this.verifySucceed(loginListener,etUserName,etPassWord);
            }else {
                // 验证失败
                this.verifyFailure(loginListener, etUserName, etPassWord);
                Logger.d(TAG,"login()  -->  传入的参数不正确,或输入的内容不正确");
                return;
            }
            // 隐藏键盘
            MainUtils.concealKeyboard(etPassWord);
            requestPackage.put(username, MainUtils.getText(etUserName));
            String passWord = MD5Utils.getMd5(MainUtils.getText(etPassWord));
            if (LOGIN_TYPE_AUTH_CODE.equals(loginType)){
                // 验证码登录(该方式登录不需要密码)
                requestPackage.put(authcode,passWord);
                requestPackage.put(password,defaultValue);
            }else {
                requestPackage.put(password, passWord);
                requestPackage.put(authcode, defaultValue);
            }
            requestPackage.put(logintype,loginType);
            if (loginListener != null){
                // 调用接口
                loginListener.onLoginBefore(etUserName,etPassWord,requestPackage);
            }
            // 发送请求
            this.sendPostRequest(url,tag,requestPackage,okHttpResponseHandler);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * 发送 登录 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param loginType 登录方式
     * @param okHttpResponseHandler 请求回调
     */
    public void login(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String loginType,
                      @NonNull OkHttpResponseHandler okHttpResponseHandler){
        this.login(etUserName,etPassWord,loginType, URL.HOST_URL,null,null,okHttpResponseHandler);
    }

    /**
     * 发送 登录 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param loginType 登录方式
     * @param loginListener  登录监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void login(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String loginType,
                      @NonNull OnLoginListener loginListener, @NonNull OkHttpResponseHandler okHttpResponseHandler){
        this.login(etUserName,etPassWord,loginType,URL.HOST_URL,null,loginListener,okHttpResponseHandler);
    }

    /**
     * 发送 登录 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param loginType 登录方式
     * @param loginListener  登录监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void login(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String loginType, @NonNull String url,
                      @NonNull OnLoginListener loginListener, @NonNull OkHttpResponseHandler okHttpResponseHandler){
        this.login(etUserName,etPassWord,loginType,url,null,loginListener,okHttpResponseHandler);
    }

    /**
     * 发送 登录 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param loginType 登录方式
     */
    public void login(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String loginType,
                      @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl){
        this.login(etUserName,etPassWord,loginType,URL.HOST_URL,null,null, okHttpResponseHandlerImpl);
    }

    /**
     * 发送 登录 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param loginType 登录方式
     * @param loginListener  登录监听器接口
     */
    public void login(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String loginType,
                      @NonNull OnLoginListener loginListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl){
        this.login(etUserName,etPassWord,loginType,URL.HOST_URL,null,loginListener, okHttpResponseHandlerImpl);
    }

    /**
     * 发送 登录 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param loginType 登录方式
     * @param loginListener  登录监听器接口
     */
    public void login(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String loginType, @NonNull String url,
                      @NonNull OnLoginListener loginListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl){
        this.login(etUserName,etPassWord,loginType,url,null,loginListener, okHttpResponseHandlerImpl);
    }

    /**
     * 判断输入格式和登录类型,false - 输入错误，true - 正确
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param loginType 登录方式
     */
    private boolean checkEnter(EditText etUserName, EditText etPassWord, String loginType){
        if (this.userNamePwdIsEmpty(etUserName, etPassWord,loginType)) {
            //用户名或密码为空
            return false;
        }

        String userName = MainUtils.getText(etUserName); // 用户名
        if (LOGIN_TYPE_PHONE_NUMBER.equals(loginType)){  // 手机号码
            // 判断手机号码格式
            return isPhoneNumber(userName);
        }else if (LOGIN_TYPE_EMAIL.equals(loginType)){  // 邮箱
            // 判断邮箱格式
            return isEmail(userName);
        }else if (LOGIN_TYPE_ID_CARD_NO.equals(loginType)){ // 身份证号码
            // 判断身份证号码格式
            return isIdCardNo(userName);
        }else if (LOGIN_TYPE_ACCREDIT.equals(loginType)){  // 第三方授权登录
            return true;
        }else if (LOGIN_TYPE_AUTH_CODE.equals(loginType)){  // 验证码(该方式登录不需要密码)
            return true;
        }else {
            Logger.d(TAG,"checkEnter()  -->  没有这样的登录类型：" + loginType);

            errorMsg = "没有这样的登录类型：" + loginType + "。" +
                    "(登录类型有：Login.LOGIN_TYPE_PHONE_NUMBER or Login.LOGIN_TYPE_EMAIL ....)";
            return false;
        }
    }

    /** 登录监听器 */
    public interface OnLoginListener extends OnVerifyListener{

        /**
         * 登录之前调用,获取请求包
         * @param etUserName 用户名
         * @param etPassWord 密码
         * @param requestPackage 请求包
         */
        void onLoginBefore(EditText etUserName, EditText etPassWord, JSONObject requestPackage);
    }
}
