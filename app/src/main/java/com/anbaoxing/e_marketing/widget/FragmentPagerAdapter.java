package com.anbaoxing.e_marketing.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * viewpager适配器
 * Created by LENOVO on 2016/9/27.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
