package com.anbaoxing.e_marketing.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anbaoxing.e_marketing.MyApplication;
import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.UseBeen;
import com.anbaoxing.e_marketing.moudle.Login;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.utils.ActivityCollection;
import com.anbaoxing.e_marketing.utils.MainUtils;
import com.anbaoxing.e_marketing.widget.CommonTitle;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_aty_commontitle)
    CommonTitle loginAtyCommontitle;
    @BindView(R.id.aty_login_number)
    EditText atyLoginNumber;
    @BindView(R.id.aty_login_password)
    EditText atyLoginPassword;
    @BindView(R.id.aty_login_dologin)
    ImageButton atyLoginDologin;
    @BindView(R.id.aty_login_register)
    TextView atyLoginRegister;
    @BindView(R.id.aty_login_forget_password)
    TextView atyLoginForgetPassword;

    private UseBeen useBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginAtyCommontitle.setTitle("圈友登录");
        useBeen = new UseBeen();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MyApplication.getUseBeen() == null) {
            loginAtyCommontitle.setGone(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (MyApplication.getUseBeen() == null) {
            {
                ActivityCollection.finishAll();
            }
        }
    }

    /**
     * 登录  - 手机号码登录
     */
    private void login() {
        Login login = new Login(Login.LOGIN_COMMAND);
        login.setField5("Field5");
        login.login(atyLoginNumber, atyLoginPassword, Login.LOGIN_TYPE_PHONE_NUMBER,
                new Login.OnLoginListener() {
                    /**
                     * 验证成功后调用
                     * @param etUserName 用户名
                     * @param etPassWord 密码
                     */
                    @Override
                    public void onVerifySucceed(EditText etUserName, EditText etPassWord) {
                        String info = "onVerifySucceed()  -->  " + "  userName：" + MainUtils.getText(etUserName) +
                                "  passWord：" + MainUtils.getText(etPassWord);
                        Logger.i(info);
                    }

                    /**
                     * 验证失败后调用
                     * @param etUserName 用户名
                     * @param etPassWord 密码
                     */
                    @Override
                    public void onVerifyFailure(EditText etUserName, EditText etPassWord, String errorMsg) {
                        String info = "onVerifyFailure()  -->  " + "  userName：" + MainUtils.getText(etUserName) + "  passWord："
                                + MainUtils.getText(etPassWord) + "  errorMsg：" + errorMsg;
                        Logger.i(info);
                    }

                    /**
                     * 登录之前调用,获取请求包 -- 此方法在 Ui 线程中调用
                     * @param etUserName 用户名
                     * @param etPassWord 密码
                     * @param requestPackage 请求包
                     */
                    @Override
                    public void onLoginBefore(EditText etUserName, EditText etPassWord, JSONObject requestPackage) {
                        String info = "onLoginBefore()  -->  " + "  userName：" + MainUtils.getText(etUserName) + "  passWord：" +
                                MainUtils.getText(etPassWord) + "  requestPackage：" + requestPackage;
                        Logger.i(info);
                    }
                }, new MyOkHttpResponseHandlerImpl() {
                    @Override
                    public void onSucceed(String response, int id) {
                        super.onSucceed(response, id);

                        try {
                            Gson gson = new Gson();
                            Type type = new TypeToken<UseBeen>() {
                            }.getType();
                            useBeen = gson.fromJson(response, type);
                            MyApplication.setUseBeen(useBeen);
                            if (MyApplication.getUseBeen().getError().equals("1")) {
                                Logger.i(useBeen.getErrmsg());
                                ToastUtils.showShortToast(MyApplication.getUseBeen().getErrmsg());
                                finish();
                            } else {
                                ToastUtils.showShortToast(MyApplication.getUseBeen().getErrmsg());
                            }

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.aty_login_dologin, R.id.aty_login_register, R.id.aty_login_forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aty_login_dologin:
                login();
                break;
            case R.id.aty_login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.aty_login_forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                finish();
                break;
        }
    }

}
