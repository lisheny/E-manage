package com.anbaoxing.e_marketing.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.anbaoxing.e_marketing.MyApplication;
import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.moudle.DoNetWork;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.widget.CommonTitle;
import com.blankj.utilcode.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class IssueActivity extends BaseActivity {

    @BindView(R.id.issue_aty_commontitle)
    CommonTitle issueAtyCommontitle;
    @BindView(R.id.aty_issue_contact)
    EditText atyIssueContact;
    @BindView(R.id.aty_issue_suggest)
    EditText atyIssueSuggest;
    @BindView(R.id.aty_issue_btn_submit)
    Button atyIssueBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        ButterKnife.bind(this);
        issueAtyCommontitle.setTitle("问题反馈");
    }

    private void commitIssue(){
        DoNetWork doNetWork = new DoNetWork(DoNetWork.ISSUE_COMMIT);
        doNetWork.feedback(MyApplication.getUseBeen().getUsername(), atyIssueContact, atyIssueSuggest, new MyOkHttpResponseHandlerImpl() {
            @Override
            public void onSucceed(String response, int id) {
                ToastUtils.showShortToast(response);
            }

            @Override
            public void onFailure(Call call, Exception e, int id) {
                ToastUtils.showShortToast(String.valueOf(e));
            }
        });
    }

    @OnClick(R.id.aty_issue_btn_submit)
    public void onClick() {
//        ToastUtils.showLongToast("提交不了的");
        commitIssue();
    }
}
