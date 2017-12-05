package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments.LecturesListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netikras on 17.12.5.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private List<LecturesListFragment> tabs = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }


    public void addTabFragment(LecturesListFragment fragment) {
        tabs.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
