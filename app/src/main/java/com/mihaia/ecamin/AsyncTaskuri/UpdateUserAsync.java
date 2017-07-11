package com.mihaia.ecamin.AsyncTaskuri;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mihaia.ecamin.DataContracts.User;
import com.mihaia.ecamin.Utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mihaia on 7/11/2017.
 */

public  class UpdateUserAsync extends AsyncTask<User, Void ,Integer> {
    String server_response;
    String section;

    public UpdateUserAsync(String section) {
        this.section = section;
    }

    @Override
    protected Integer doInBackground(User... params) {

        if(params.length < 1)
            return 0;

        URL url;
        HttpURLConnection urlConnection = null;

        Gson gson = new GsonBuilder().setDateFormat("dd MM yyyy HH").serializeNulls().create();
        String jsonString = gson.toJson(params[0]);

        try {
            url = new URL(Utils.URLConectare + section + "/Update");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            urlConnection.connect();

            // Post Json
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(jsonString);
            outputStreamWriter.close();

            // Receive Response from server
            int statusCode = urlConnection.getResponseCode();
            Log.d("debug", "The response is: " + statusCode);

            if(statusCode == HttpURLConnection.HTTP_OK){
                return 1;
            } else {
                return  0;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


//    @Override
//    protected void onPostExecute(Integer result) {
//        super.onPostExecute(result);
//
//    }
}
