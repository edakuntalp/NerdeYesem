package com.example.nerdeyesem.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nerdeyesem.FragmentMap;
import com.example.nerdeyesem.FragmentRestaurants;
import com.example.nerdeyesem.R;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Top Rated fragment activity
                return new FragmentMap();
            case 1:
                // Games fragment activity
                return new FragmentRestaurants();
        }

        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Map Vıew";
            case 1:
                return "Lıst Vıew";
            default:
                return null;
        }
    }
}