package com.mihaia.ecamin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mihaia.ecamin.DataContracts.InfoUser;
import com.mihaia.ecamin.DataContracts.User;

import org.w3c.dom.Text;

public class PaginaPrincipala extends AppCompatActivity {

    BottomNavigationView buttomBar;

    TextView tvUser, tvCameraUser;

    LinearLayout layoutMeniuSus;
    Button butArataMeniu;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;
    ImageButton btnLogout;

    private static User UserLogat;
    Context context;
    private static InfoUser infoUserLogat;

    public static User getUserLogat() {
        return UserLogat;
    }

    public static InfoUser getInfoUserLogat() {
        return infoUserLogat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala);

        context = this;

        btnLogout =(ImageButton) findViewById(R.id.btn_Logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Logout");
                builder.setMessage("Sunteti sigur?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

                builder.setNegativeButton("Nu",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        tvCameraUser = (TextView) findViewById(R.id.tv_Principala_CameraUser);
        tvCameraUser.setText("Camera " + Utils.cameraUserLogat.Numar);
        tvUser = (TextView) findViewById(R.id.tv_Principala_user);
        getCurrentUser();


        /*********Tab-uri sus si adaptor */////
        ////TabLayout navigationUP
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.opt1_tab1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.opt1_tab2));

        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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
                        if(adapter.getCount() == 1){
                            tabLayout.addTab(tabLayout.newTab());
                            adapter.mNumOfTabs = 2;
                        }

                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setText(R.string.opt1_tab1);
                        tabLayout.getTabAt(1).setText(R.string.opt1_tab2);
                        break;

                    case R.id.action_2:
                        adapter.setSectiuneCurenta(Utils.Sectiuni.Plangeri);
                        if(adapter.getCount() == 1){
                            tabLayout.addTab(tabLayout.newTab());
                            adapter.mNumOfTabs = 2;

                        }

                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setText(R.string.opt2_tab1);
                        tabLayout.getTabAt(1).setText(R.string.opt2_tab2);
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.action_3:
                        adapter.setSectiuneCurenta(Utils.Sectiuni.Programari);
                        if(adapter.getCount() == 1){
                            tabLayout.addTab(tabLayout.newTab());
                            adapter.mNumOfTabs = 2;
                        }

                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setText(R.string.opt3_tab1);
                        tabLayout.getTabAt(1).setText(R.string.opt3_tab2);
                        break;

                    case R.id.action_4:
                        adapter.setSectiuneCurenta(Utils.Sectiuni.Setari);
                        if(adapter.getCount() == 2){
                            tabLayout.removeTab(tabLayout.getTabAt(1));
                            adapter.mNumOfTabs = 1;
                        }
                        adapter.notifyDataSetChanged();
                        tabLayout.getTabAt(0).setText("Setari");
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


    public void initViewPager()
    {
        viewPager.setVisibility(View.VISIBLE);
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

    public void getCurrentUser() {
        Intent intent = getIntent();
        String jsonUser = intent.getStringExtra(Utils.CurrentUser);

        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);

        if(user == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.tilu_relogat);
            builder.setMessage(R.string.relogare);
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();


            finish();
            dialog.show();
        }else {
            this.UserLogat = user;
            tvUser.setText(Utils.infoUserLogat.Nume + " " + Utils.infoUserLogat.Prenume);
        }
    }

}
