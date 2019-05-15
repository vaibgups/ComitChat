package com.example.comitchat.adapter.viewpager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<String> fragmentTileArrayList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    public void addFragment(Fragment fragment, String title) {
        this.fragmentArrayList.add(fragment);
        this.fragmentTileArrayList.add(title);
    }

    @Override
    public Fragment getItem(int possition) {

        switch (possition) {
            case 0:
                return fragmentArrayList.get(possition);
            case 1:
                return fragmentArrayList.get(possition);
            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTileArrayList.get(position);
    }
}
