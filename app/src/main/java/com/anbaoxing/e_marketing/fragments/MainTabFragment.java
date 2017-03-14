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
import android.widget.ImageView;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.activitys.PayActivity;
import com.anbaoxing.e_marketing.activitys.PopWindowActivity;
import com.anbaoxing.e_marketing.activitys.WebViewActivity;
import com.anbaoxing.e_marketing.beens.Cilist;
import com.anbaoxing.e_marketing.beens.ListMessageBeen;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.moudle.DoNetWork;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.utils.Constant;
import com.anbaoxing.e_marketing.widget.MainTabRecyclerviewAdapter;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 主页
 * Created by LENOVO on 2017/2/7.
 */

public class MainTabFragment extends BaseFragment {

    @BindView(R.id.main_fragment_recyclerview)
    RecyclerView mainFragmentRecyclerview;
    @BindView(R.id.local_city)
    TextView localCity;
    @BindView(R.id.fragment_main_imv00)
    ImageView fragmentMainImv00;
    @BindView(R.id.fragment_main_id)
    TextView fragmentMainId;
    @BindView(R.id.fragment_main_money)
    TextView fragmentMainMoney;
    @BindView(R.id.fragment_main_imv01)
    ImageView fragmentMainImv01;
    @BindView(R.id.fragment_main_starttime)
    TextView fragmentMainStarttime;
    @BindView(R.id.fragment_main_onlinetime)
    TextView fragmentMainOnlinetime;
    @BindView(R.id.fragment_main_usedflow)
    TextView fragmentMainUsedflow;
    @BindView(R.id.fragment_main_online)
    TextView fragmentMainOnline;
    @BindView(R.id.fragment_main_outline)
    TextView fragmentMainOutline;
    @BindView(R.id.fragment_main_pay)
    TextView fragmentMainPay;
    @BindView(R.id.fragment_main_share)
    TextView fragmentMainShare;
    @BindView(R.id.fragment_main_check_pay)
    TextView fragmentMainCheckPay;
    @BindView(R.id.fragment_main_news)
    TextView fragmentMainNews;

    private List<Cilist> mTestDatas;
    private MainTabRecyclerviewAdapter adapter;
    private ListMessageBeen listMessageBeen;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        mTestDatas = new ArrayList<Cilist>();
        adapter = new MainTabRecyclerviewAdapter(mContext, mTestDatas);
        mainFragmentRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mainFragmentRecyclerview.setAdapter(adapter);
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
        try {
            if (progressDialog != null){
                progressDialog.dismiss();
            }
        } catch (Exception ignored) {

        }
    }

    private void changeTab(int checkBg, int checkTextColor, int NewsBg, int NewsTextColor) {
        fragmentMainCheckPay.setBackgroundResource(checkBg);
        fragmentMainCheckPay.setTextColor(getResources().getColor(checkTextColor));
        fragmentMainNews.setBackgroundResource(NewsBg);
        fragmentMainNews.setTextColor(getResources().getColor(NewsTextColor));
    }

    @OnClick({R.id.local_city, R.id.fragment_main_imv00, R.id.fragment_main_imv01, R.id.fragment_main_online, R.id.fragment_main_outline, R.id.fragment_main_pay, R.id.fragment_main_share, R.id.fragment_main_check_pay, R.id.fragment_main_news})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_city:
                break;
            case R.id.fragment_main_imv00:
                break;
            case R.id.fragment_main_imv01:
                if ("******".equals(fragmentMainMoney.getText().toString())){
                    fragmentMainMoney.setText("¥ 8888.00");
                }else {
                    fragmentMainMoney.setText("******");
                }
                break;
            case R.id.fragment_main_online:
                break;
            case R.id.fragment_main_outline:
                break;
            case R.id.fragment_main_pay:
                startActivity(new Intent(getActivity(), PayActivity.class));
                break;
            case R.id.fragment_main_share:
                startActivity(new Intent(getActivity(), PopWindowActivity.class));
                break;
            case R.id.fragment_main_check_pay:
                getMessege(RequestPackage.MANAGE_MESSAGE);
                changeTab(R.drawable.main_fragment_btn_message_record2, R.color.write,
                        R.drawable.main_fragment_btn_news2, R.color.title_bg);
                break;
            case R.id.fragment_main_news:
                getMessege(RequestPackage.MANAGE_MESSAGE);
                changeTab(R.drawable.main_fragment_btn_message_record, R.color.title_bg,
                        R.drawable.main_fragment_btn_news, R.color.write);
                break;
        }
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

}
