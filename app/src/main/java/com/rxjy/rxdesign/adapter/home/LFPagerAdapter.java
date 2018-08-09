package com.rxjy.rxdesign.adapter.home;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 阿禹 on 2018/7/30.
 */

public class LFPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> arrayList;

    public LFPagerAdapter(FragmentManager fm, ArrayList<Fragment> arrayList, ArrayList<String> titleList) {
        super(fm);
        this.arrayList = arrayList;
        this.titleList = titleList;
    }

    ArrayList<String> titleList;

    public LFPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}
