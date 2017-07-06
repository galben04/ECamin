package com.mihaia.ecamin.DataContracts;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mihai on 6/21/2017.
 */
public class Plangere
{

    public int Id_Plangere;


    public int Id_Stare;


    public int Id_User;


    public Date Data;


    public Date DataFinalizare;


    public boolean IsClosed;


    public boolean IsDel;

    public String Descriere;

    public Plangere(int id_Plangere, int id_Stare, int id_User, Date data, Date dataFinalizare, boolean isClosed, boolean isDel, String descriere) {
        Id_Plangere = id_Plangere;
        Id_Stare = id_Stare;
        Id_User = id_User;
        Data = data;
        DataFinalizare = dataFinalizare;
        IsClosed = isClosed;
        IsDel = isDel;
        Descriere = descriere;
    }
}
