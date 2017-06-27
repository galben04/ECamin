package com.mihaia.ecamin;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

public class PaginaPrincipala extends AppCompatActivity {

    BottomNavigationView buttomBar;

    LinearLayout layoutMeniuSus;
    Button butArataMeniu;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala);

        /*********Tab-uri sus si adaptor */////
        ////TabLayout navigationUP
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.opt1_tab1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.opt1_tab2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.opt1_tab3));

        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        layoutMeniuSus = (LinearLayout) findViewById(R.id.layoutBaraMeniuSus);
        layoutMeniuSus.setVisibility(View.GONE);

        /*****************ButtomBar*****///////////////
        buttomBar  = (BottomNavigationView) findViewById(R.id.navigationDown);
        //buttomNavView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
        buttomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.action_1:
                        adapter.setSectiuneCurenta(Utils.Sectiuni.Plati);
                        if(adapter.getCount() == 2){
                            tabLayout.addTab(tabLayout.newTab());
                            adapter.mNumOfTabs = 3;
                        }

                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setText(R.string.opt1_tab1);
                        tabLayout.getTabAt(1).setText(R.string.opt1_tab2);
                        tabLayout.getTabAt(2).setText(R.string.opt1_tab3);
                        break;

                    case R.id.action_2:
                        adapter.setSectiuneCurenta(Utils.Sectiuni.Plangeri);
                        if(adapter.getCount() == 3){
                            tabLayout.removeTab(tabLayout.getTabAt(2));
                            adapter.mNumOfTabs = 2;

                        }

                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setText(R.string.opt2_tab1);
                        tabLayout.getTabAt(1).setText(R.string.opt2_tab2);
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.action_3:
                        adapter.setSectiuneCurenta(Utils.Sectiuni.Programari);
                        if(adapter.getCount() == 3){
                            tabLayout.removeTab(tabLayout.getTabAt(2));
                            adapter.mNumOfTabs = 2;
                        }

                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setText(R.string.opt3_tab1);
                        tabLayout.getTabAt(1).setText(R.string.opt3_tab2);
                        break;

                    case R.id.action_4:
                        adapter.setSectiuneCurenta(Utils.Sectiuni.Setari);
                        Toast.makeText(getApplicationContext(), "OPT 1", Toast.LENGTH_LONG).show();
                    break;
                }
                return true;
            }
        });


        ///********ViewPager********************/
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_1:
                Toast.makeText(getApplicationContext(), "OPT 1", Toast.LENGTH_LONG);
                break;
            case R.id.action_2:
                Toast.makeText(getApplicationContext(), "OPT 2", Toast.LENGTH_LONG);
                break;
            case R.id.action_3:
                Toast.makeText(getApplicationContext(), "OPT 3", Toast.LENGTH_LONG);
                break;
            case R.id.action_4:
                Toast.makeText(getApplicationContext(), "OPT 4", Toast.LENGTH_LONG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
