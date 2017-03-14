package com.anbaoxing.e_marketing.moudle;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;

import com.anbaoxing.e_marketing.http.OkHttpClientManager;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.utils.Constant;
import com.anbaoxing.e_marketing.utils.MD5Utils;
import com.anbaoxing.e_marketing.utils.MainUtils;
import com.anbaoxing.e_marketing.utils.VersionUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author：kang
 * time:  2016-11-21
 *
 * 请求包实现类
 */
public abstract class RequestPackageImpl implements RequestPackage {

    private static final String TAG = RequestPackageImpl.class.getSimpleName() + "  -->  ";

    // ============== 请求包的字段(Key)，可根据 Key 获取值 ==============
    /** 用户名 */
    public static final String username = "username";
    /** 密码 */
    public static final String password = "pwd";
    /** 验证码 */
    public static final String authcode = "code";
    /** 请求类型 */
    public static final String requesttype = "requesttype";

    //中国移动 0  联通1   中国电信 2
    public static final String mnc = "mnc";
    //中国移动、联通取基站信息的两个参数
    public static final String lac = "lac";
    public static final String cell = "cell";
    //电信获取基站信息的三个参数
    public static final String sid = "sid";
    public static final String nid = "nid";
    public static final String bid = "bid";

    /** 默认值 */
    protected String defaultValue = "";
    /** 验证失败的信息 */
    protected String errorMsg = "";

    /** 是否补全请求包 */
    private boolean isSupplementComplete = true;

    /** 请求包,子类继承 */
    protected JSONObject requestPackage = new JSONObject();

    public RequestPackageImpl(String command) {
        this.setRequestPackage(command);
    }

    @Override
    public String getPackmd5() {
        return getField(RequestPackage.packmd5);
    }

    @Override
    public void setPackmd5(String packmd5) {
        this.setField(RequestPackage.packmd5, packmd5);
    }

    @Override
    public String getCommand() {
        return getField(RequestPackage.command);
    }

    @Override
    public void setCommand(String command) {
        this.setField(RequestPackage.command,command);
    }

    @Override
    public String getTimestamp() {
        return getField(RequestPackage.timestamp);
    }

    @Override
    public void setTimestamp(String timestamp) {
        this.setField(RequestPackage.timestamp, timestamp);
    }

    @Override
    public String getVer() {
        return getField(RequestPackage.ver);
    }

    @Override
    public void setVer(String ver) {
        this.setField(RequestPackage.ver,ver);
    }

    @Override
    public String getErrcode() {
        return getField(RequestPackage.errcode);
    }

    @Override
    public void setErrcode(String errcode) {
        this.setField(RequestPackage.errcode,errcode);
    }

    @Override
    public String getApptype() {
        return getField(RequestPackage.apptype);
    }

    @Override
    public void setApptype(String apptype) {
        this.setField(RequestPackage.apptype,apptype);
    }

    @Override
    public String getLanguage() {
        return getField(RequestPackage.language);
    }

    @Override
    public void setLanguage(String language) {
        this.setField(RequestPackage.language,language);
    }

    @Override
    public String getField1() {
        return getField(RequestPackage.field1);
    }

    @Override
    public void setField1(String field1) {
        this.setField(RequestPackage.field1,field1);
    }

    @Override
    public String getField2() {
        return getField(RequestPackage.field2);
    }

    @Override
    public void setField2(String field2) {
        this.setField(RequestPackage.field2,field2);
    }

    @Override
    public String getField3() {
        return getField(RequestPackage.field3);
    }

    @Override
    public void setField3(String field3) {
        this.setField(RequestPackage.field3,field3);
    }

    @Override
    public String getField4() {
        return getField(RequestPackage.field4);
    }

    @Override
    public void setField4(String field4) {
        this.setField(RequestPackage.field4,field4);
    }

    @Override
    public String getField5() {
        return getField(RequestPackage.field5);
    }

    @Override
    public void setField5(String field5) {
        this.setField(RequestPackage.field5,field5);
    }

    // ===================== 可能需要手动设置的字段 start  =====================
    /** 获取验证码 */
    public String getAuthcode() {
        return this.getField(Register.authcode);
    }

    /** 设置验证码 */
    public void setAuthcode(String authcode) {
        this.setField(Register.authcode, MD5Utils.getMd5(authcode));
    }

    /** 获取用户名 */
    public String getUsername(){
        return this.getField(ForgetPassword.username);
    }

    /** 设置用户名 */
    public void setUsername(String username){
        this.setField(ForgetPassword.username, username);
    }

    // ===================== 可能需要手动设置的字段 end  =====================

    /**
     * 从 JSONObject 中获取字段,没有找到返回空字符串
     * @param field TreeMap 中的 Key
     * @return  Key 对应的值
     */
    public String getField(String field){
        try {
            if (field == null){
                Logger.d("net_RequestPackgeImpl","getField()  -->  传入参数不能为空");
                return defaultValue;
            }else {
                if (requestPackage.has(field)) {
                    return requestPackage.getString(field);
                } else {
                    return defaultValue;
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 从 JSONObject 中获取字段,没有找到返回空字符串
     * @param field TreeMap 中的 Key
     * @return  Key 对应的值
     */
    public String getField(JSONObject jsonObject, String field){
        try {
            if (field == null || jsonObject == null){
                Logger.d("net_RequestPackgeImpl","getField()  -->  传入参数不能为空");
                return defaultValue;
            }else {
                if (jsonObject.has(field)) {
                    return jsonObject.getString(field);
                } else {
                    return defaultValue;
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 设置 jsonObject 中的内容
     * @param key Key
     * @param value Value
     */
    public void setField(String key, String value){
        try {
            if (value != null && key != null){
                requestPackage.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置 jsonObject 中的内容
     * @param key Key
     * @param value Value
     */
    public void setField(String key, Object value){
        try {
            if (value != null && key != null){
                requestPackage.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置 jsonObject 中的内容
     * @param key Key
     * @param value Value
     */
    public void setField(JSONObject jsonObject, String key, String value){
        try {
            if (jsonObject == null) return;
            if (value != null && key != null){
                jsonObject.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** 获取请求数据包 */
    public JSONObject getRequestPackage(){
        return this.requestPackage;
    }

    /** 设置请求数据包 */
    public void setRequestPackage(JSONObject jsonObject){
        if (jsonObject != null){
            this.requestPackage = jsonObject;
        }
    }

    /** 清空请求包 */
    public void clearRequestPackage(){
        this.requestPackage = new JSONObject();
    }

    /** 判断用户名，密码是否为空 */
    protected boolean userNamePwdIsEmpty(EditText etUserName, EditText etPassWord, String type){
        if (etUserName == null || etPassWord == null || type == null) {
            errorMsg = "用户名或密码输入框为 null，登录类型或请求类型为 null";
            Logger.d("net_RequestPackgeImpl","userNamePwdIsEmpty()  -->  传入参数不能为空");
            return true;
        }

        String userName = MainUtils.getText(etUserName); // 用户名
        String passWord = MainUtils.getText(etPassWord); // 密码
        if (TextUtils.isEmpty(userName)){
            // 用户名为空
            errorMsg = MainUtils.getHint(etUserName);
            ToastUtils.showShortToast("用户名为空");
            return true;
        }else if (TextUtils.isEmpty(passWord)){
            // 密码为空
            errorMsg = MainUtils.getHint(etPassWord);
            ToastUtils.showShortToast("密码为空");
            return true;
        }else {
            return false;
        }
    }

    /** 判断密码是否为空 */
    protected boolean passWordIsEmpty(EditText etPassWord, String type){
        if (etPassWord == null || type == null) {
            Logger.d("net_RequestPackagelmpl","userNamePwdIsEmpty()  -->  传入参数不能为空");

            return true;
        }

        String passWord = MainUtils.getText(etPassWord); // 密码
        if (TextUtils.isEmpty(passWord)){
            // 密码为空
            errorMsg = MainUtils.getHint(etPassWord);
            ToastUtils.showShortToast("密码为空");
            return true;
        }else {
            return false;
        }
    }

    /** 判断用户名，密码是否为空 */
    protected boolean userNameIsEmpty(EditText etUserName, String type){
        if (etUserName == null || type == null) {
            Logger.d("net_RequestPackagelmpl","userNamePwdIsEmpty()  -->  传入参数不能为空");
            return true;
        }

        String userName = MainUtils.getText(etUserName); // 用户名
        if (TextUtils.isEmpty(userName)){
            // 用户名为空
            errorMsg = MainUtils.getHint(etUserName);
            ToastUtils.showShortToast("用户名为空");
            return true;
        }else {
            return false;
        }
    }

    /** 判断输入是否为空 */
    public boolean enterIsEmpty(EditText editText){
        if (editText == null) {
            Logger.d("net_RequestPackagelmpl","enterIsEmpty()  -->  传入参数不能为空");

            return true;
        }
        String text = MainUtils.getText(editText);
        if (TextUtils.isEmpty(text)){
            // 输入为空
            errorMsg = MainUtils.getHint(editText);
            ToastUtils.showShortToast("输入为空");
            return true;
        }else {
            return false;
        }
    }

    /** 判断是否是 手机号码 */
    protected boolean isPhoneNumber(String userName){
        if (RegexUtils.isMobileExact(userName)){
            return true;
        }else {
            errorMsg = "手机号码格式不正确，请重新输入";
            // 手机号码格式不正确，请重新输入
            ToastUtils.showShortToast("手机号码格式不正确，请重新输入");
            return false;
        }
    }

    /** 判断是否是 邮箱 */
    protected boolean isEmail(String userName){
        if (RegexUtils.isEmail(userName)){
            return true;
        }else {
            errorMsg = "邮箱格式不正确，请重新输入";
            // 邮箱格式不正确，请重新输入
            ToastUtils.showShortToast("邮箱格式不正确，请重新输入");
            return false;
        }
    }

    /** 判断是否是 身份证号码 */
    protected boolean isIdCardNo(String userName){
        if (RegexUtils.isIDCard15(userName)||RegexUtils.isIDCard18(userName)){
            return true;
        }else {
            errorMsg = "身份证号码格式不正确，请重新输入";
            // 身份证号码格式不正确，请重新输入
            ToastUtils.showShortToast("身份证号码格式不正确，请重新输入");
            return false;
        }
    }

    /** 验证成功 */
    protected void verifySucceed(OnVerifyListener verifyListener, EditText etUserName, EditText etPassWord){
        if (verifyListener != null){
            verifyListener.onVerifySucceed(etUserName, etPassWord);
        }
    }

    /** 验证失败 */
    protected void verifyFailure(OnVerifyListener verifyListener, EditText etUserName, EditText etPassWord){
        if (verifyListener != null){
            verifyListener.onVerifyFailure(etUserName, etPassWord, errorMsg);
        }
    }

    /** 验证成功 */
    protected void verifySucceed(OnPassWordVerifyListener verifyListener,EditText etPassWord){
        if (verifyListener != null){
            verifyListener.onVerifySucceed(etPassWord);
        }
    }

    /** 验证失败 */
    protected void verifyFailure(OnPassWordVerifyListener verifyListener,EditText etPassWord){
        if (verifyListener != null){
            verifyListener.onVerifyFailure(etPassWord, errorMsg);
        }
    }

    /**
     * 根据指定的 命令 获取默认的请求包，其他字段需要自己加上
     * @param command 命令
     */
    protected void setRequestPackage(String command){
        try {
            if (TextUtils.isEmpty(command)) return;
            requestPackage.put(RequestPackage.packmd5, defaultValue);
            requestPackage.put(RequestPackage.ver, VersionUtils.getVersionName());

            requestPackage.put(RequestPackage.command, command);
            requestPackage.put(RequestPackage.errcode, Constant.ERRCODE);

            requestPackage.put(RequestPackage.timestamp, String.valueOf(System.currentTimeMillis()));
            requestPackage.put(RequestPackage.apptype, Constant.APP_TYPE_ANDROID); // APP类型: 1--Android，2--IOS
            requestPackage.put(RequestPackage.language, Constant.LANGUAGE_CHINESE); // 语言：1--中文（默认），2--英文

//            requestPackage.put(RequestPackage.field1, defaultValue);
//            requestPackage.put(RequestPackage.field2, defaultValue);
//            requestPackage.put(RequestPackage.field3, defaultValue);
//            requestPackage.put(RequestPackage.field4, defaultValue);
//            requestPackage.put(RequestPackage.field5, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isSupplementComplete() {
        return isSupplementComplete;
    }

    /** 是否补全请求包 -- 默认 true */
    protected void setIsSupplementComplete(boolean isSupplementComplete) {
        this.isSupplementComplete = isSupplementComplete;
    }

    /**
     * OkHttp post 请求
     * @param url 请求 url
     * @param tag 请求 tag，用于取消请求
     * @param requestPackage  请求参数
     * @param okHttpResponseHandler 请求回调
     */
    protected void sendPostRequest(@NonNull String url, Object tag, @NonNull JSONObject requestPackage,
                                   @NonNull OkHttpResponseHandler okHttpResponseHandler){
        //发送出去的请求包数据都用MD5加密一遍
        try {
            String mMD5 = MD5Utils.getPackmd5(requestPackage);
            requestPackage.put(RequestPackage.packmd5,mMD5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (okHttpResponseHandler instanceof OkHttpResponseHandlerImpl){
            OkHttpResponseHandlerImpl okHttpResponseHandlerImpl = (OkHttpResponseHandlerImpl)okHttpResponseHandler;
            // 发送请求
            OkHttpClientManager.okHttpPost(url, tag, requestPackage, okHttpResponseHandlerImpl);
        }else {
            // 发送请求
            OkHttpClientManager.okHttpPost(url, tag, requestPackage, okHttpResponseHandler);
        }
        Logger.d("net_RequestPackgelmpl","sendPostRequest()  -->  url：" + url);
    }

    /** 判断 json 字符串中是否包含 Key */
    public boolean containKey(String key){
        if (key == null) return false;
        if (requestPackage.has(key)){
            return true;
        }else {
            return false;
        }
    }
}
