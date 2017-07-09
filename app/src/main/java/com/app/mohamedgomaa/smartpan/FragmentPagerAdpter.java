package com.app.mohamedgomaa.smartpan;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragmentPagerAdpter extends FragmentPagerAdapter {
    ArrayList<android.support.v4.app.Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    void addFragment(android.support.v4.app.Fragment fragment, String tabTile) {
        this.fragments.add(fragment);
        this.tabTitles.add(tabTile);
    }

    public FragmentPagerAdpter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

}
