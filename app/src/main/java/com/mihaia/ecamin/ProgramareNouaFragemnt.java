package com.mihaia.ecamin;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mihaia.ecamin.DataContracts.Programare;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgramareNouaFragemnt.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgramareNouaFragemnt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramareNouaFragemnt extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btnTrimite;
    TextView textViewIdMasina, textViewIdUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProgramareNouaFragemnt() {
        // Required empty public constructor
    }

    public static ProgramareNouaFragemnt newInstance(String param1, String param2) {
        ProgramareNouaFragemnt fragment = new ProgramareNouaFragemnt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programare_noua, container, false);

        textViewIdMasina = (TextView) view.findViewById(R.id.tv_ProgramareNoua_IdMasina);
        textViewIdUser = (TextView) view.findViewById(R.id.tv_ProgramareNoua_IdUser);

        btnTrimite = (Button) view.findViewById(R.id.btn_ProgramareNoua_Trimite);

        btnTrimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Programare newItem = new Programare();
                newItem.Id_User  = (Integer.valueOf(textViewIdUser.getText().toString()));
                newItem.Id_Masina = Integer.valueOf(textViewIdMasina.getText().toString());
                newItem.Data_Ora = new Date();
                newItem.IsDel = false;

                new InsertMethodAsync().execute(newItem);
                }
        });

        return view;
    }


    public class InsertMethodAsync extends AsyncTask<Programare , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(Programare... programari) {

            URL url;
            HttpURLConnection urlConnection = null;

            Gson gson = new Gson();

            String jsonString = gson.toJson(programari[0]);

           // JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("Data_Ora", "\\/Date(1497884881680+0300)\\/");
//                jsonObject.put("Id_Masina", 1);
//                jsonObject.put("Id_User", 1);
//                jsonObject.put("IsDel", false);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            try {
                url = new URL("http://192.168.192.1:51133/Programari/Insert");
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
                    server_response = GoiWCF.readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//             //Programari programari = null;
//            String JSON  = gson.toJson(programare);
//            Type collectionType = new TypeToken<ArrayList<Programare>>(){}.getType();
//            Collection<Programare> programari = null;
//            try {
//                programari = gson.fromJson(server_response,collectionType);
//            }catch(IllegalStateException | JsonSyntaxException exception){
//                exception.printStackTrace();
//            }
//
//            data.addAll((Collection<? extends Programare>) programari);
//            Toast.makeText(getContext(), "Reusit!", Toast.LENGTH_LONG);
//            Log.e("Response", "" + server_response);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
