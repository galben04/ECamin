package com.mihaia.ecamin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mihaia.ecamin.Plangeri.PlangereNouaFragment;
import com.mihaia.ecamin.Plangeri.PlangerileMeleFragment;
import com.mihaia.ecamin.Plati.IstoricPlatiFragment;
import com.mihaia.ecamin.Plati.PlataScadentaFragment;
import com.mihaia.ecamin.Programari.ProgramareNouaFragment2;
import com.mihaia.ecamin.Programari.ProgramarileMeleFragment;

/**
 * Created by Mihai on 3/19/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Utils.Sectiuni sectiuneCurenta;
    Fragment tab1, tab2, tab3;

    public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
        setSectiuneCurenta(Utils.Sectiuni.Plati);
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment =  tab1;
                break;

            case 1:
                fragment =  tab2;
                break;

            case 2:
                fragment = tab3;
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


    public Utils.Sectiuni getSectiuneCurenta() {
        return sectiuneCurenta;
    }

    public void setSectiuneCurenta(Utils.Sectiuni sectiuneCurenta) {
        this.sectiuneCurenta = sectiuneCurenta;

        switch (sectiuneCurenta)
        {
            default:
            case Plati:
                tab1 = new PlataScadentaFragment();
                tab2 = new IstoricPlatiFragment();
                tab3 = new PlataScadentaFragment();
                break;

            case Plangeri:
                tab1 = new PlangerileMeleFragment();
                tab2 = new PlangereNouaFragment();
                tab3 = null;
                break;

            case Programari:
                tab1 = new ProgramarileMeleFragment();
                tab2 = new ProgramareNouaFragment2();
                tab3 = null;
                break;

            case Setari:
                tab1 = new PlataScadentaFragment();
                tab2 = new IstoricPlatiFragment();
                tab3 = new PlataScadentaFragment();
                break;
        }

    }
}

