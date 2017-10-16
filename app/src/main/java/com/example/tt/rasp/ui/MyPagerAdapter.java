package com.example.tt.rasp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by tt on 14.10.17.
 */

class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ChildFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 6;
    }
}
