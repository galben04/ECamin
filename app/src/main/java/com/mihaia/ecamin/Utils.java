package com.mihaia.ecamin;

import com.mihaia.ecamin.DataContracts.Camera;
import com.mihaia.ecamin.DataContracts.InfoUser;
import com.mihaia.ecamin.DataContracts.Masina_Spalat;
import com.mihaia.ecamin.DataContracts.Stare_Plangere;
import com.mihaia.ecamin.DataContracts.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mihaia on 6/27/2017.
 */

public class Utils {

    public static String CurrentUser;
    public static String valuta = "RON";

    public static InfoUser infoUserLogat;
    public static Camera cameraUserLogat;

    public enum Sectiuni{
        Plati,
        Plangeri,
        Programari,
        Setari
    }

    public static String URLConectare = "http://192.168.1.78:51133/";
    //"http://169.254.237.217:51133/" ;//"http://192.168.0.129:51133/";
    //"http://192.168.1.78:51133/" Munca;


    public static ArrayList<Stare_Plangere> StariPlageri = new ArrayList<Stare_Plangere>();

    public static String getNumeStarebyId(int id_stare) {
        for (Stare_Plangere stare: StariPlageri) {
            if(stare.Id_Stare == id_stare)
                return stare.Nume;
        }

        return null;
    }

    public static ArrayList<Masina_Spalat> MasiniSpalat = new ArrayList<Masina_Spalat>();

    public static Masina_Spalat getMasinabyId(int id_masina) {
        for(Masina_Spalat masina : MasiniSpalat) {
            if(masina.Id_Masina == id_masina)
                return masina;
        }

        return null;
    }

    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
