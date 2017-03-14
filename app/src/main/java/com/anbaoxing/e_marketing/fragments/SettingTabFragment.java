package com.anbaoxing.e_marketing.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anbaoxing.e_marketing.MyApplication;
import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.activitys.AboutActivity;
import com.anbaoxing.e_marketing.activitys.ChangePasswordActivity;
import com.anbaoxing.e_marketing.activitys.IssueActivity;
import com.anbaoxing.e_marketing.activitys.LoginActivity;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.moudle.AppUpdateBiz;
import com.anbaoxing.e_marketing.moudle.MyOnAppUpdateListener;
import com.blankj.utilcode.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 * Created by LENOVO on 2017/2/7.
 */

public class SettingTabFragment extends BaseFragment {

    @BindView(R.id.setting_fra_local_city)
    TextView settingFraLocalCity;
    @BindView(R.id.setting_fra_ima)
    ImageView settingFraIma;
    @BindView(R.id.setting_fra_usename)
    TextView settingFraUsename;
    @BindView(R.id.setting_fra_yu_e)
    TextView settingFraYuE;
    @BindView(R.id.setting_fra_flow)
    TextView settingFraFlow;
    @BindView(R.id.setting_fra_update)
    LinearLayout settingFraUpdate;
    @BindView(R.id.setting_fra_about)
    LinearLayout settingFraAbout;
    @BindView(R.id.setting_fra_issue)
    LinearLayout settingFraIssue;
    @BindView(R.id.setting_fra_passsword)
    LinearLayout settingFraPasssword;
    @BindView(R.id.setting_fra_login)
    LinearLayout settingFraLogin;
    @BindView(R.id.setting_fra_logout)
    Button settingFraLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /** 项目升级,1007 - 测试时使用的命令 */
    protected void appUpdate(){
        AppUpdateBiz appUpdateBiz = AppUpdateBiz.getAppUpdateBiz(getActivity());
        appUpdateBiz.setCommand(RequestPackage.APP_UPDATE_COMMAND);
        appUpdateBiz.haveNewVersion(MyApplication.getUseBeen().getUsername(), true, true, new MyOnAppUpdateListener());
    }

    @OnClick({R.id.setting_fra_local_city, R.id.setting_fra_ima, R.id.setting_fra_update, R.id.setting_fra_about, R.id.setting_fra_issue, R.id.setting_fra_passsword, R.id.setting_fra_login, R.id.setting_fra_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_fra_local_city:
                break;
            case R.id.setting_fra_ima:
                ToastUtils.showLongToast("注意，本机将在5秒内爆炸！");
                break;
            case R.id.setting_fra_update:
                appUpdate();
                break;
            case R.id.setting_fra_about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
            case R.id.setting_fra_issue:
                startActivity(new Intent(mContext, IssueActivity.class));
                break;
            case R.id.setting_fra_passsword:
                startActivity(new Intent(mContext, ChangePasswordActivity.class));
                break;
            case R.id.setting_fra_login:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.setting_fra_logout:
                MyApplication.setUseBeen(null);
                getActivity().finish();
                break;
        }
    }
}
