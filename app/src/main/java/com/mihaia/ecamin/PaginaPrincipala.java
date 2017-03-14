package com.mihaia.ecamin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PaginaPrincipala extends AppCompatActivity {

    LinearLayout layoutMeniuSus;
    Button butArataMeniu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala);

        layoutMeniuSus = (LinearLayout) findViewById(R.id.layoutBaraMeniuSus);
        butArataMeniu = (Button) findViewById(R.id.buttonArataMeniuSus);

        butArataMeniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutMeniuSus.getVisibility() == View.VISIBLE)
                    layoutMeniuSus.setVisibility(View.GONE);

                else if(layoutMeniuSus.getVisibility() == View.GONE)
                    layoutMeniuSus.setVisibility(View.VISIBLE);
            }
        });
    }
}
