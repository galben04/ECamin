package com.mihaia.ecamin.DataContracts;

import org.joda.time.DateTime;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mihai on 6/21/2017.
 */

public class Programare {

    public int Id_Programare;

    public Date Data_Ora;

    public int Id_Masina;

    public int Id_User;

    public boolean IsDel;

    public Programare(int id_Programare, Date data_Ora, int id_Masina, int id_User, boolean isDel) {
        Id_Programare = id_Programare;
        Data_Ora = data_Ora;
        Id_Masina = id_Masina;
        Id_User = id_User;
        IsDel = isDel;
    }

    public Programare(){}
}
