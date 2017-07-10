package com.mihaia.ecamin.AsyncTaskuri;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mihaia.ecamin.DataContracts.EvidentaPlata;
import com.mihaia.ecamin.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static com.mihaia.ecamin.Utils.readStream;

/**
 * Created by Mihai on 7/8/2017.
 */

public class GetPlataAsyncTask extends AsyncTask<String , Void , EvidentaPlata> {
    String server_response;
    String section;

    public GetPlataAsyncTask(String section) {
        this.section = section;
    }

    @Override
    protected EvidentaPlata doInBackground(String... strings) {

       // Type collectionType = new TypeToken<ArrayList<Integer>>(){}.getType();
        //Collection<Integer> ore = null;
        EvidentaPlata evidenta = null;

        try {
            URL url;
            HttpURLConnection urlConnection = null;

            url = new URL(Utils.URLConectare + section + "("  + strings[0] + "," + strings[1]+")");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            Log.d("Response from server:", String.valueOf(responseCode));

            if(responseCode == HttpURLConnection.HTTP_OK){
                server_response = readStream(urlConnection.getInputStream());

                Gson gson =  new GsonBuilder().setDateFormat("dd MM yyyy HH").create();
                try {
                    evidenta = gson.fromJson(server_response, EvidentaPlata.class);
                }catch(IllegalStateException | JsonSyntaxException exception){
                    exception.printStackTrace();
                }

                urlConnection.disconnect();
                Log.d("Response Stream:", server_response);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return evidenta;
    }


}