package com.mihaia.ecamin;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoiWCF {
    String urlpath = "http://localhost:51133/Programari/All";

    public String postData(String urlpath, JSONObject json)
    {

        HttpURLConnection connection = null;
        try {

            URL url=new URL(urlpath);

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            Log.d("write data",json.toString());

            streamWriter.write(json.toString());
            streamWriter.flush();

            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("HTTP_OK response", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("else response", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e("test", exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

    }

    public String GetData(String urlpath, JSONObject json)
    {

        HttpURLConnection connection = null;
        try {

            URL url=new URL(urlpath);

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            Log.d("write data",json.toString());

            streamWriter.write(json.toString());
            streamWriter.flush();

            StringBuilder stringBuilder = new StringBuilder();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("HTTP_OK response", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("else response", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e("test", exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

    }
}