package com.mihaia.ecamin.Plangeri;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mihaia.ecamin.DataContracts.Plangere;
import com.mihaia.ecamin.DataContracts.Programare;
import com.mihaia.ecamin.PaginaPrincipala;
import com.mihaia.ecamin.Programari.ProgramariRecyclerViewAdapter;
import com.mihaia.ecamin.Programari.ProgramarileMeleFragment;
import com.mihaia.ecamin.R;
import com.mihaia.ecamin.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.mihaia.ecamin.Utils.URLConectare;
import static com.mihaia.ecamin.Utils.readStream;


public class PlangerileMeleFragment extends Fragment {

    RecyclerView recyclerView;
    List<Plangere> data = new ArrayList<Plangere>();

    private Context context;

    PlangeriRecyclerViewAdapter mRecycleViewAdaper;
    private OnFragmentInteractionListener mListener;


    ////Pentru a vedea daca fragmentul este vizibil
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {

        }
    }


    private void getData() throws IOException {
//        if(mRecycleViewAdaper != null) {
//            mRecycleViewAdaper.clear();
//            mRecycleViewAdaper.notifyDataSetChanged();
//        }
        new SelectMethodAsync("Plangeri").execute(String.valueOf(PaginaPrincipala.getUserLogat().Id_User));
    }


    public PlangerileMeleFragment() {
        // Required empty public constructor
    }

    public static PlangerileMeleFragment newInstance(String param1, String param2) {
        PlangerileMeleFragment fragment = new PlangerileMeleFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plangerile_mele, container, false);

        data.add(new Plangere(1, 1, 1, new Date(), null, false, false, "Chiuveta este fisurata, curge apa"));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListaPlangeri);

        mRecycleViewAdaper = new PlangeriRecyclerViewAdapter(this.getContext(), data);

        context = this.getContext();
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(mRecycleViewAdaper);

        return view;
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

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        if(!menuVisible) {

        }else {
            try {
                getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mRecycleViewAdaper != null)
            mRecycleViewAdaper.notifyDataSetChanged();

    }

    public class SelectMethodAsync extends AsyncTask<String , Void ,String> {
        String server_response;
        String section;

        public SelectMethodAsync(String section) {
            this.section = section;
        }

        @Override
        protected String doInBackground(String... strings) {


            try {
                URL url;
                HttpURLConnection urlConnection = null;

                url = new URL(URLConectare + section + "/All(" + strings[0] + ")");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10 * 1000);
                urlConnection.setConnectTimeout(100 * 1000);
                urlConnection.setRequestMethod("GET");
                //urlConnection.setDoInput(true);
                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();
                Log.d("Response from server:", String.valueOf(responseCode));

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.d("Response Stream:", server_response);
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

            Gson gson =  new GsonBuilder().setDateFormat("dd MM yyyy HH").create();

            Type collectionType = new TypeToken<ArrayList<Plangere>>(){}.getType();
            Collection<Plangere> plangeri = null;
            try {
                plangeri = gson.fromJson(server_response, collectionType);
            }catch(IllegalStateException | JsonSyntaxException exception){
                exception.printStackTrace();
            }

            //data.add(p1);
            if(plangeri != null) {
                mRecycleViewAdaper.clear();
                mRecycleViewAdaper.addAll(plangeri);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mRecycleViewAdaper);

                Toast.makeText(getContext(), "Reusit!", Toast.LENGTH_SHORT).show();
                Log.e("Response", "" + server_response);
            }

        }
    }
}
