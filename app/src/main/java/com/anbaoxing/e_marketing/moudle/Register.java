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
 * time:  2016-11-22
 *
 * 注册
 */
public class Register extends RequestPackageImpl{

    private static final String TAG = Register.class.getSimpleName() + "  -->  ";

    /** 注册请求包的字段 */
    public static final String nickname = "nickname";
    public static final String gender = "gender";
    public static final String age = "age";
    public static final String birthday = "birthday";
    public static final String registertype = "registertype";

    /**
     * 注册方式：
     * 1--手机号码
     * 2--邮箱
     * 3--身份证号码
     * 4--第三方授权注册
     */
    public static final String REGISTER_TYPE_PHONE_NUMBER = "1";
    public static final String REGISTER_TYPE_EMAIL = "2";
    public static final String REGISTER_TYPE_ID_CARD_NO = "3";
    public static final String REGISTER_TYPE_ACCREDIT = "4";

    public Register() {
        this(RequestPackage.REGISTER_COMMAND);
    }

    public Register(String command) {
        super(command);
    }

    /**
     * 发送 注册 请求
     * @param etUserName 用户名 输入框
     * @param etPassWord 密码 输入框
     * @param registerType 注册方式，Register.REGISTER_TYPE_PHONE_NUMBER or
     *                      Register.REGISTER_TYPE_EMAIL ....
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param registerListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void register(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String registerType, @NonNull String url,
                         Object tag, OnRegisterListener registerListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        try {
            if (this.checkEnter(etUserName,etPassWord,registerType)){
                // 验证成功
                this.verifySucceed(registerListener,etUserName,etPassWord);
            }else {
                // 验证失败
                this.verifyFailure(registerListener,etUserName,etPassWord);
                Logger.d(TAG,"register()  -->  传入的参数不正确,或输入的内容不正确");

                return;
            }
            // 隐藏键盘
            MainUtils.concealKeyboard(etPassWord);
            requestPackage.put(username,MainUtils.getText(etUserName)); // 用户名
            String passWord = MD5Utils.getMd5(MainUtils.getText(etPassWord));
            requestPackage.put(password,passWord); // 密码 - MD5加密

            requestPackage.put(registertype, registerType);
            if (this.isSupplementComplete()) {
                // 补全请求包
                this.supplementComplete();
            }
            if (registerListener != null){
                // 调用接口
                registerListener.onRegisterBefore(etUserName, etPassWord, requestPackage);
            }
            // 发送请求
            this.sendPostRequest(url,tag,requestPackage,okHttpResponseHandler);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * 发送 注册 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param registerType 注册方式
     * @param tag 请求 tag，用于取消请求
     * @param registerListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void register(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String registerType,
                         Object tag, OnRegisterListener registerListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.register(etUserName,etPassWord,registerType, URL.HOST_URL,tag,registerListener,okHttpResponseHandler);
    }

    /**
     * 发送 注册 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param registerType 注册方式
     * @param registerListener  注册监听器接口
     * @param okHttpResponseHandler 请求回调
     */
    public void register(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String registerType,
                         OnRegisterListener registerListener, @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.register(etUserName,etPassWord,registerType, URL.HOST_URL,null,registerListener,okHttpResponseHandler);
    }

    /**
     * 发送 注册 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param registerType 注册方式
     * @param okHttpResponseHandler 请求回调
     */
    public void register(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String registerType,
                         @NonNull OkHttpResponseHandler okHttpResponseHandler) {
        this.register(etUserName,etPassWord,registerType,URL.HOST_URL,null,null,okHttpResponseHandler);
    }

    /**
     * 发送 注册 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param registerType 注册方式
     * @param tag 请求 tag，用于取消请求
     * @param registerListener  注册监听器接口
     * @param okHttpResponseHandlerImpl 请求回调
     */
    public void register(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String registerType,
                         Object tag, OnRegisterListener registerListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        this.register(etUserName,etPassWord,registerType,URL.HOST_URL,tag,registerListener, okHttpResponseHandlerImpl);
    }

    /**
     * 发送 注册 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param registerType 注册方式
     * @param registerListener  注册监听器接口
     * @param okHttpResponseHandlerImpl 请求回调
     */
    public void register(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String registerType,
                         OnRegisterListener registerListener, @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        this.register(etUserName,etPassWord,registerType,URL.HOST_URL,null,registerListener, okHttpResponseHandlerImpl);
    }

    /**
     * 发送 注册 请求
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param registerType 注册方式
     * @param okHttpResponseHandlerImpl 请求回调
     */
    public void register(@NonNull EditText etUserName, @NonNull EditText etPassWord, @NonNull String registerType,
                         @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        this.register(etUserName,etPassWord,registerType,URL.HOST_URL,null,null, okHttpResponseHandlerImpl);
    }

    /**
     * 判断输入格式和注册类型,false - 输入错误，true - 正确
     * @param etUserName 用户名
     * @param etPassWord 密码
     * @param registerType 注册方式
     */
    private boolean checkEnter(EditText etUserName, EditText etPassWord, String registerType) {
        if (this.userNamePwdIsEmpty(etUserName, etPassWord, registerType)) {
            //用户名或密码为空
            return false;
        }

        String userName = MainUtils.getText(etUserName); // 用户名
        if (REGISTER_TYPE_PHONE_NUMBER.equals(registerType)){  // 手机号码
            // 判断手机号码格式
            return isPhoneNumber(userName);
        }else if (REGISTER_TYPE_EMAIL.equals(registerType)){  // 邮箱
            // 判断邮箱格式
            return isEmail(userName);
        }else if (REGISTER_TYPE_ID_CARD_NO.equals(registerType)){ // 身份证号码
            // 判断身份证号码格式
            return isIdCardNo(userName);
        }else if (REGISTER_TYPE_ACCREDIT.equals(registerType)){
            return true;
        }else {
            Logger.d(TAG,"checkEnter()  -->  没有这样的注册类型：" + registerType);

            errorMsg = "没有这样的注册类型：" + registerType + "。" +
                    "(注册类型有：Register.REGISTER_TYPE_PHONE_NUMBER or Register.REGISTER_TYPE_EMAIL ....)";
            return false;
        }
    }

    /** 判断请求包字段是否完整，如果不完整，则传默认值 */
    private void supplementComplete(){
        try {

            if (!requestPackage.has(authcode)){
                requestPackage.put(authcode,defaultValue);
            }
            if (!requestPackage.has(nickname)){
                requestPackage.put(nickname,defaultValue);
            }

            if (!requestPackage.has(gender)){
                requestPackage.put(gender,defaultValue);
            }
            if (!requestPackage.has(age)){
                requestPackage.put(age,defaultValue);
            }

            if (!requestPackage.has(birthday)){
                requestPackage.put(birthday,defaultValue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return this.getField(Register.nickname);
    }

    public void setNickname(String nickname) {
        this.setField(Register.nickname,nickname);
    }

    public String getGender() {
        return this.getField(Register.gender);
    }

    public void setGender(String gender) {
        this.setField(Register.gender,gender);
    }

    public String getAge() {
        return this.getField(Register.age);
    }

    public void setAge(String age) {
        this.setField(Register.age,age);
    }

    public String getBirthday() {
        return this.getField(Register.birthday);
    }

    public void setBirthday(String birthday) {
        this.setField(Register.birthday,birthday);
    }

    public String getRegistertype() {
        return this.getField(Register.registertype);
    }

    public void setRegistertype(String registertype) {
        this.setField(Register.registertype,registertype);
    }

    /** 注册监听器 */
    public interface OnRegisterListener extends OnVerifyListener{
        /**
         * 注册之前调用,获取请求包
         * @param etUserName 用户名
         * @param etPassWord 密码
         * @param requestPackage 请求包
         */
        void onRegisterBefore(EditText etUserName, EditText etPassWord, JSONObject requestPackage);
    }
}
