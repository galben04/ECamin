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

public class PaginaPrincipala extends AppCompatActivity {

    BottomNavigationView buttomNavView;

    LinearLayout layoutMeniuSus;
    Button butArataMeniu;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));

        layoutMeniuSus = (LinearLayout) findViewById(R.id.layoutBaraMeniuSus);
        butArataMeniu = (Button) findViewById(R.id.buttonArataMeniuSus);

        buttomNavView  = (BottomNavigationView) findViewById(R.id.navigationDown);
        //buttomNavView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
        buttomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.action_1:
                        break;
                    case R.id.action_2:
                        break;
                    case R.id.action_3:
                        break;
                    case R.id.action_4:
                        break;
                }

                return true;
            }
        });

        layoutMeniuSus.setVisibility(View.GONE);
        butArataMeniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutMeniuSus.getVisibility() == View.VISIBLE)
                    layoutMeniuSus.setVisibility(View.GONE);

                else if(layoutMeniuSus.getVisibility() == View.GONE)
                    layoutMeniuSus.setVisibility(View.VISIBLE);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

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
}
