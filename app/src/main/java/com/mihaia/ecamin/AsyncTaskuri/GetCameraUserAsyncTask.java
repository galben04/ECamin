package com.mihaia.ecamin.AsyncTaskuri;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mihaia.ecamin.DataContracts.Camera;
import com.mihaia.ecamin.DataContracts.InfoUser;
import com.mihaia.ecamin.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.mihaia.ecamin.Utils.readStream;

/**
 * Created by mihaia on 7/11/2017.
 */

public class GetCameraUserAsyncTask  extends AsyncTask<String , Void ,Camera> {
    String server_response;
    String section;

    public GetCameraUserAsyncTask(String section) {
        this.section = section;
    }

    @Override
    protected Camera doInBackground(String... strings) {
        Camera camera = null;

        if (strings.length < 1)
            return null;

        try {
            URL url;
            HttpURLConnection urlConnection = null;

            url = new URL(Utils.URLConectare + section + "/User(" + strings[0] + ")");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            Log.d("Response from server:", String.valueOf(responseCode));

            if (responseCode == HttpURLConnection.HTTP_OK) {
                server_response = readStream(urlConnection.getInputStream());

                Gson gson = new GsonBuilder().setDateFormat("dd MM yyyy HH").create();
                try {
                    camera = gson.fromJson(server_response, Camera.class);
                } catch (IllegalStateException | JsonSyntaxException exception) {
                    exception.printStackTrace();
                }

                Log.d("Response Stream:", server_response);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return camera;
    }


}
