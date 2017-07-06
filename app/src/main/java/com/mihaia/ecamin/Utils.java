package com.mihaia.ecamin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by mihaia on 6/27/2017.
 */

public class Utils {

    public enum Sectiuni{
        Plati,
        Plangeri,
        Programari,
        Setari
    }

    public static String URLConectare = "http://192.168.0.129:51133/";
    //"http://192.168.1.78:51133/";

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
