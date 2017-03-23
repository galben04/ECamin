package com.mihaia.ecamin;

import android.icu.util.Calendar;
import android.support.design.widget.TabLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mihaia on 3/23/2017.
 */

public class InformatiiPlata {

   private String luna;
   private Float suma;
   private Calendar dataPlata, dataScandeta;

    public InformatiiPlata(String luna, Float suma, Calendar dataPlata, Calendar dataScandeta) {
        this.luna = luna;
        this.suma = suma;
        this.dataPlata = dataPlata;
        this.dataScandeta = dataScandeta;
    }

    public InformatiiPlata() {
    }

    public String getLuna() {

        return luna;
    }

    public void setLuna(String luna) {
        this.luna = luna;
    }

    public Float getSuma() {
        return suma;
    }

    public void setSuma(Float suma) {
        this.suma = suma;
    }

    public Calendar getDataPlata() {
        return dataPlata;
    }

    public void setDataPlata(Calendar dataPlata) {
        this.dataPlata = dataPlata;
    }

    public Calendar getDataScandeta() {
        return dataScandeta;
    }

    public void setDataScandeta(Calendar dataScandeta) {
        this.dataScandeta = dataScandeta;
    }
}
