package com.mihaia.ecamin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Mihai on 3/19/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                PlataScadentaFragment tab1 = new PlataScadentaFragment();
                fragment =  tab1;

                break;
            case 1:
                IstoricFragment tab2 = new IstoricFragment();
                fragment =  tab2;
                break;
            case 2:
                IstoricFragment tab3 = new IstoricFragment();
                fragment =  tab3;
                break;
            default:
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}

