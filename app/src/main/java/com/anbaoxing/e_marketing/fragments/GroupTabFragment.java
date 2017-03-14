package com.anbaoxing.e_marketing.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.activitys.PopWindowActivity;
import com.anbaoxing.e_marketing.beens.MessageEvent;
import com.anbaoxing.e_marketing.widget.FragmentPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的小圈子
 * Created by LENOVO on 2017/2/7.
 */

public class GroupTabFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fragment_group_tab_share)
    TextView fragmentGroupTabShare;
    @BindView(R.id.fragment_group_tab_manage)
    TextView fragmentGroupTabManage;
    @BindView(R.id.fragment_group_tab_massage)
    TextView fragmentGroupTabMassage;
    @BindView(R.id.fragment_group_tab_news)
    TextView fragmentGroupTabNews;
    @BindView(R.id.tab_of_manage)
    TextView tabOfManage;
    @BindView(R.id.tab_of_message)
    TextView tabOfMessage;
    @BindView(R.id.tab_of_news)
    TextView tabOfNews;

    private ArrayList<Fragment> fragments;
    private MessageEvent messageEvent;

    public static final int MANAGE_FRAGMENT = 0;
    public static final int MASSAGE_FRAGMENT = 1;
    public static final int NEWS_FRAGMENT = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmemt_group_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        changeTab(R.color.title_bg,R.color.black,R.color.black,
                R.color.title_bg,R.color.write,R.color.write);

        fragments = new ArrayList<Fragment>();
        fragments.add(new GroupManageFragment());
        fragments.add(new GroupNewsFragment());
        fragments.add(new GroupMessageFragment());

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(
                getActivity().getSupportFragmentManager(), fragments
        );
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(this);


    }

    public void changeTab(int manageText,int massageText,int newsText,int manageBg,int messageBg,int newsBg){
        fragmentGroupTabManage.setTextColor(getResources().getColor(manageText));
        fragmentGroupTabMassage.setTextColor(getResources().getColor(massageText));
        fragmentGroupTabNews.setTextColor(getResources().getColor(newsText));
        tabOfManage.setBackgroundColor(getResources().getColor(manageBg));
        tabOfMessage.setBackgroundColor(getResources().getColor(messageBg));
        tabOfNews.setBackgroundColor(getResources().getColor(newsBg));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 监听判断当前是哪个页面
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        switch (position){
            case MANAGE_FRAGMENT:
                changeTab(R.color.title_bg,R.color.black,R.color.black,
                        R.color.title_bg,R.color.write,R.color.write);
                break;
            case MASSAGE_FRAGMENT:
                changeTab(R.color.black,R.color.title_bg,R.color.black,
                        R.color.write,R.color.title_bg,R.color.write);
                break;
            case NEWS_FRAGMENT:
                changeTab(R.color.black,R.color.black,R.color.title_bg,
                        R.color.write,R.color.write,R.color.title_bg);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.fragment_group_tab_share, R.id.fragment_group_tab_manage, R.id.fragment_group_tab_massage, R.id.fragment_group_tab_news})
    public void onClick(View view) {
        //发出事件
        messageEvent = new MessageEvent();
        messageEvent.setmPosition(0);  //这里值为零，测试用，看看MainActivity中的事件处理成不成功
        EventBus.getDefault().post(messageEvent);

        switch (view.getId()) {
            case R.id.fragment_group_tab_share:
                startActivity(new Intent(getActivity(), PopWindowActivity.class));
                break;
            case R.id.fragment_group_tab_manage:
                viewpager.setCurrentItem(MANAGE_FRAGMENT);
                changeTab(R.color.title_bg,R.color.black,R.color.black,
                        R.color.title_bg,R.color.write,R.color.write);
                break;
            case R.id.fragment_group_tab_massage:
                viewpager.setCurrentItem(MASSAGE_FRAGMENT);
                changeTab(R.color.black,R.color.title_bg,R.color.black,
                        R.color.write,R.color.title_bg,R.color.write);
                break;
            case R.id.fragment_group_tab_news:
                viewpager.setCurrentItem(NEWS_FRAGMENT);
                changeTab(R.color.black,R.color.black,R.color.title_bg,
                        R.color.write,R.color.write,R.color.title_bg);
                break;
        }
    }

    /**
     * 事件处理
     * @param messageEvent 事件消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageDeal(MessageEvent messageEvent) {
        switch (messageEvent.getmPosition()){
            case MANAGE_FRAGMENT:
                break;
            case MASSAGE_FRAGMENT:
                break;
            case NEWS_FRAGMENT:
                break;
            default:
                break;
        }
    }
}
