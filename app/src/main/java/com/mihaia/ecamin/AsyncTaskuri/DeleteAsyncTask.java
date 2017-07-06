package com.mihaia.ecamin.AsyncTaskuri;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mihaia.ecamin.DataContracts.Programare;
import com.mihaia.ecamin.Utils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mihaia on 7/4/2017.
 */

public abstract class DeleteAsyncTask extends AsyncTask<String, Void ,Integer> {

    String server_response;

    @Override
    protected Integer doInBackground(String...params) {

        if(params.length < 2)
            return 0;

        URL url;
        HttpURLConnection urlConnection = null;

//        Gson gson = new GsonBuilder().setDateFormat("dd MM yyyy HH:mm:ssXXX").create();
//        String jsonString = gson.toJson(programari[0]);

        try {
            url = new URL(Utils.URLConectare + "/" + params[0] + "/Delete(" + params[1] + ")");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(5 * 1000);
            urlConnection.setConnectTimeout(5 * 1000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            urlConnection.connect();

            // Post Json
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
//            outputStreamWriter.write(jsonString);
//            outputStreamWriter.close();

            // Receive Response from server
            int statusCode = urlConnection.getResponseCode();
            Log.d("debug", "The response is: " + statusCode);

            if(statusCode == HttpURLConnection.HTTP_OK){
//                server_response = ProgramarileMeleFragment.readStream(urlConnection.getInputStream());
//                Log.v("CatalogClient", server_response);
                return 1;
            } else {
                return 0;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

//    @Override
//    protected void onPostExecute(Integer result) {
//        super.onPostExecute(result);
//
//        if(result == 1)
//            Toast.makeText()
//       // Toast.makeText(context, R.string.programare_adaugata, Toast.LENGTH_LONG).show();
////             //Programari programari = null;
////            String JSON  = gson.toJson(programare);
////            Type collectionType = new TypeToken<ArrayList<Programare>>(){}.getType();
////            Collection<Programare> programari = null;
////            try {
////                programari = gson.fromJson(server_response,collectionType);
////            }catch(IllegalStateException | JsonSyntaxException exception){
////                exception.printStackTrace();
////            }
////
////            data.addAll((Collection<? extends Programare>) programari);
////            Toast.makeText(getContext(), "Reusit!", Toast.LENGTH_LONG);
////            Log.e("Response", "" + server_response);
//
//    }
}