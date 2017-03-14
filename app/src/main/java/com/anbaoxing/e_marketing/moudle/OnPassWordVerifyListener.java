package com.anbaoxing.e_marketing.moudle;

import android.widget.EditText;

/**
 * author：kang
 * time:  2016-11-23
 *
 * 验证成功或失败接口回调(忘记密码(验证验证码)，重置密码 中)
 */
public interface OnPassWordVerifyListener {

    /**
     * 验证成功后调用
     * @param etPassWord 密码 输入框
     */
    void onVerifySucceed(EditText etPassWord);

    /**
     * 验证失败后调用
     * @param etPassWord 密码 输入框
     */
    void onVerifyFailure(EditText etPassWord, String errorMsg);
}
