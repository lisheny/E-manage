package com.anbaoxing.e_marketing.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.activitys.WebViewActivity;
import com.anbaoxing.e_marketing.beens.Cilist;
import com.anbaoxing.e_marketing.beens.ListMessageBeen;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.moudle.DoNetWork;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.utils.Constant;
import com.anbaoxing.e_marketing.widget.MainTabRecyclerviewAdapter;
import com.anbaoxing.e_marketing.widget.ManageRecyclerviewAdapter;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 社群经营
 * Created by LENOVO on 2017/2/7.
 */

public class GroupManageFragment extends BaseFragment {
    @BindView(R.id.manage_fragment_recyclerview)
    RecyclerView manageFragmentRecyclerview;

    private List<Cilist> mTestDatas;
    private ManageRecyclerviewAdapter adapter;
    private ListMessageBeen listMessageBeen;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_manage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取列表数据
     *
     * @param command
     */
    private void getMessege(String command) {
        DoNetWork doNetWork = new DoNetWork(command);
        doNetWork.getListMessage(new MyOkHttpResponseHandlerImpl() {
            @Override
            public void onSucceed(String response, int id) {
                Logger.d(response);

                Gson gson = new Gson();
                Type type = new TypeToken<ListMessageBeen>() {
                }.getType();
                listMessageBeen = gson.fromJson(response, type);
                if (listMessageBeen.getError().equals("1")) {
                    ToastUtils.showShortToast(listMessageBeen.getErrmsg());
                    Logger.i(listMessageBeen.getErrmsg());

                    mTestDatas.clear();
                    for (int i = 0; i < listMessageBeen.getCilist().size(); i++) {
                        mTestDatas.add(listMessageBeen.getCilist().get(i));
                    }
                    adapter.notifyDataSetChanged();
                    dismissProgressDialog();
                } else {
                    ToastUtils.showShortToast(listMessageBeen.getErrmsg());
                }
            }

            @Override
            public void onFailure(Call call, Exception e, int id) {
                ToastUtils.showShortToast("网络出错了");
                dismissProgressDialog();
            }
        });
    }

    private void initView(){
        mTestDatas = new ArrayList<Cilist>();
        adapter = new ManageRecyclerviewAdapter(mContext, mTestDatas);
        manageFragmentRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        manageFragmentRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickLitener(new MainTabRecyclerviewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShortToast("点击了item：" + String.valueOf(position));
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Constant.SHOW_URL,mTestDatas.get(position).getMsgurl());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initData() {
        getMessege(RequestPackage.MANAGE_MESSAGE);
        showProgressDialog();
    }

    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.setTitle("");
        progressDialog.setMessage("正在加载中...");
        progressDialog.show();
    }

    private void dismissProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
