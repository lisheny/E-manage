package com.anbaoxing.e_marketing.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.RegisterBeen;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.moudle.GetAuthCode;
import com.anbaoxing.e_marketing.moudle.Login;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandler;
import com.anbaoxing.e_marketing.moudle.Register;
import com.anbaoxing.e_marketing.utils.MainUtils;
import com.anbaoxing.e_marketing.widget.CommonTitle;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//response：{"error":"1","errmsg":"注册成功","username":"18875910364","nickname":"yai","pwd":"202CB962AC59075B964B07152D234B70"}
//response：{"error":"-1","errmsg":"验证码输入错误!","username":"18875910364","nickname":"yai","pwd":"202CB962AC59075B964B07152D234B70"}
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_aty_commontitle)
    CommonTitle registerAtyCommontitle;
    @BindView(R.id.aty_register_number)
    EditText atyRegisterNumber;
    @BindView(R.id.aty_register_password00)
    EditText atyRegisterPassword00;
    @BindView(R.id.aty_register_password01)
    EditText atyRegisterPassword01;
    @BindView(R.id.aty_register_getname)
    EditText atyRegisterGetname;
    @BindView(R.id.aty_register_code)
    EditText atyRegisterCode;
    @BindView(R.id.aty_register_goto_getcode)
    TextView atyRegisterGotoGetcode;
    @BindView(R.id.aty_register_doregister)
    ImageButton atyRegisterDoregister;
    @BindView(R.id.aty_register_login)
    TextView atyRegisterLogin;

    private RegisterBeen registerBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerAtyCommontitle.setTitle("圈友注册");
        registerBeen = new RegisterBeen();
    }

    /**
     * 获取验证码
     */
    private void getAuthCode(EditText editText) {
        GetAuthCode getAuthCode = new GetAuthCode(GetAuthCode.GET_AUTH_CODE_COMMAND);
        getAuthCode.getAuthCode(MainUtils.getText(editText), Login.LOGIN_TYPE_PHONE_NUMBER,
                GetAuthCode.REQUEST_TYPE_ALL_DEFAULT, new GetAuthCode.OnGetAuthCodeListener() {
                    @Override
                    public void onVerifySucceed(String userName, String loginType, String requestType) {
                        String info = "onVerifySucceed()  -->  userName：" + userName +
                                "  loginType：" + loginType + "  requestType：" + requestType;
                        Logger.i(info);
                    }

                    @Override
                    public void onVerifyFailure(String userName, String loginType, String requestType, String errorMsg) {
                        String info = "onVerifyFailure()  -->  userName：" + userName +
                                "  loginType：" + loginType + "  requestType：" + requestType + "  errorMsg：" + errorMsg;
                        Logger.i(info);
                    }

                    @Override
                    public void onGetAuthCodeBefore(String userName, String loginType, String requestType, JSONObject requestPackage) {
                        String info = "onGetAuthCodeBefore()  -->  userName：" + userName +
                                "  loginType：" + loginType + "  requestType：" + requestType + "  requestPackage：" + requestPackage;
                        Logger.i(info);
                    }
                }, new MyOkHttpResponseHandler());
    }

    /**
     * 注册 - 手机号码
     */
    private void register() {
        if (TextUtils.isEmpty(MainUtils.getText(atyRegisterNumber))) {
            ToastUtils.showShortToast("手机号为空");
            return;
        }
        if (TextUtils.isEmpty(MainUtils.getText(atyRegisterPassword00))) {
            ToastUtils.showShortToast("密码为空");
            return;
        }
        if (TextUtils.isEmpty(MainUtils.getText(atyRegisterPassword01))) {
            ToastUtils.showShortToast("验证密码为空");
            return;
        }
        if (!MainUtils.getText(atyRegisterPassword01).equals(MainUtils.getText(atyRegisterPassword00))) {
            ToastUtils.showShortToast("两次输入密码不一致");
            return;
        }
        if (TextUtils.isEmpty(MainUtils.getText(atyRegisterCode))) {
            ToastUtils.showShortToast("验证码为空");
            return;
        }
        if (TextUtils.isEmpty(MainUtils.getText(atyRegisterGetname))) {
            ToastUtils.showShortToast("姓名为空");
            return;
        }
        Register register = new Register(Register.REGISTER_COMMAND);
        register.setAuthcode(MainUtils.getText(atyRegisterCode));
        register.setNickname(MainUtils.getText(atyRegisterGetname));
        register.setCommand(RequestPackage.REGISTER_COMMAND);
        register.register(atyRegisterNumber, atyRegisterPassword00, Register.REGISTER_TYPE_PHONE_NUMBER, new Register.OnRegisterListener() {
            @Override
            public void onRegisterBefore(EditText etUserName, EditText etPassWord, JSONObject requestPackage) {
                String info = "onRegisterBefore()  -->     UserName：" + MainUtils.getText(etUserName) +
                        "  PassWord：" + MainUtils.getText(etPassWord) + " requestPackage：" + requestPackage;
                Logger.i(info);
            }

            @Override
            public void onVerifySucceed(EditText editText, EditText etPassWord) {
                String info = "onVerifySucceed()  -->  " + "  UserName：" + MainUtils.getText(editText) +
                        "  PassWord：" + MainUtils.getText(etPassWord);
                Logger.i(info);
            }

            @Override
            public void onVerifyFailure(EditText editText, EditText etPassWord, String errorMsg) {
                String info = "onVerifyFailure()  -->  " + "  UserName：" + MainUtils.getText(editText) +
                        "  PassWord：" + MainUtils.getText(etPassWord) + "  errorMsg：" + errorMsg;
                Logger.i(info);
            }
        }, new MyOkHttpResponseHandler() {
            @Override
            public void onSucceed(String response, int id) {
                super.onSucceed(response, id);

                Gson gson = new Gson();
                Type type = new TypeToken<RegisterBeen>() {
                }.getType();
                registerBeen = gson.fromJson(response, type);
                if (registerBeen.getError().equals("1")) {
                    Logger.i(registerBeen.getErrmsg());
                    finish();
                }
                ToastUtils.showShortToast(registerBeen.getErrmsg());
            }
        });
    }

    @OnClick({R.id.aty_register_goto_getcode, R.id.aty_register_doregister, R.id.aty_register_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aty_register_goto_getcode:
//                ToastUtils.showLongToast("点击了获取验证码");
                getAuthCode(atyRegisterNumber);
                break;
            case R.id.aty_register_doregister:
//                ToastUtils.showLongToast("正在注册中...");
                register();
                break;
            case R.id.aty_register_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
