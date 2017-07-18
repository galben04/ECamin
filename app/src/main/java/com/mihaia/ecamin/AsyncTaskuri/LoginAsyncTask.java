package com.mihaia.ecamin.AsyncTaskuri;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mihaia.ecamin.DataContracts.User;
import com.mihaia.ecamin.Utils;
import com.mihaia.ecamin.localDB.UtilizatoriDB;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.mihaia.ecamin.Utils.readStream;

/**
 * Created by mihaia on 3/7/2017.
 */

public abstract class LoginAsyncTask extends AsyncTask<String, Void, User> {

    public LoginAsyncTask(Context context, AppCompatActivity curActivity) {
        this.context = context.getApplicationContext();
        this.parent = curActivity;
    }

    protected Context context;
    private String server_response;
    String section;
    private AppCompatActivity parent;

    public String getServer_response() {
        return server_response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected User doInBackground(String... params) {

        User loginUser = new User();
        loginUser.Cont = params[0];
        loginUser.Parola = params[1];

        User responseUser = null;

        URL url;
        HttpURLConnection urlConnection = null;

        Gson gson = new GsonBuilder().setDateFormat("dd MM yyyy HH").serializeNulls().create();
        String jsonString = gson.toJson(loginUser);

        try {
            url = new URL(Utils.URLConectare + "Users/Login");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(5 * 1000);
            urlConnection.setConnectTimeout(5 * 1000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            urlConnection.connect();

            // Post Json
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(jsonString);
            outputStreamWriter.close();

            // Receive Response from server
            final int statusCode = urlConnection.getResponseCode();
            Log.d("debug", "The response is: " + statusCode);

            if(statusCode == HttpURLConnection.HTTP_OK){
                server_response = readStream(urlConnection.getInputStream());
                if(server_response != null){
                    try {
                        responseUser = gson.fromJson(server_response, User.class);
                    }catch(IllegalStateException | JsonSyntaxException exception){
                        exception.printStackTrace();
                    }

                } else{
                    return null;
                }

            } else {
//                parent.runOnUiThread(new Runnable() {
//                    public void run() {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                        builder.setMessage("Eroare la comunicarea cu serverul! Error Code: " + statusCode);
//                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                    }
//                });
                            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseUser;
    }
}

