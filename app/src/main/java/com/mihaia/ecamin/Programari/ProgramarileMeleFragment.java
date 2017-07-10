package com.mihaia.ecamin.Programari;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mihaia.ecamin.DataContracts.Programare;
import com.mihaia.ecamin.PaginaPrincipala;
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

import static com.mihaia.ecamin.Utils.readStream;


public class ProgramarileMeleFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private ProgramariRecyclerViewAdapter mRecycleViewAdaper;
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private List<Programare> data = new ArrayList<Programare>();
    private Context context;

    private boolean isFirstTime = true;
    public ProgramarileMeleFragment() {

    }

    public static ProgramarileMeleFragment newInstance(String param1, String param2) {
        ProgramarileMeleFragment fragment = new ProgramarileMeleFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

        ////Pentru a vedea daca fragmentul este vizibil
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            isFirstTime = false;
            try {
                getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_programari, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListaProgramari);

        data.add(new Programare(1, new Date(), 1,1,true));

        mRecycleViewAdaper = new ProgramariRecyclerViewAdapter(this.getContext(), data);

//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(mRecycleViewAdaper);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        try {
//            getData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public class SelectMethodAsync extends AsyncTask<String , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {


            try {
                URL url;
                HttpURLConnection urlConnection = null;

                url = new URL(strings[0]);
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
            Programare programare = new Programare();


            Type collectionType = new TypeToken<ArrayList<Programare>>(){}.getType();
            Collection<Programare> programari = null;
            Programare p1 = null;
            try {
                programari = gson.fromJson(server_response, collectionType);
            }catch(IllegalStateException | JsonSyntaxException exception){
                exception.printStackTrace();
            }

            //data.add(p1);
            if(programari != null) {
                mRecycleViewAdaper.clear();
                mRecycleViewAdaper.addAll(programari);

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mRecycleViewAdaper);

                Toast.makeText(getContext(), "Reusit!", Toast.LENGTH_SHORT).show();
                Log.e("Response", "" + server_response);
            }

        }
    }


    private void getData() throws IOException {
        if(mRecycleViewAdaper != null) {
            mRecycleViewAdaper.clear();
            mRecycleViewAdaper.notifyDataSetChanged();
        }
        new SelectMethodAsync().execute(Utils.URLConectare + "Programari/All(" + PaginaPrincipala.getUserLogat().Id_User + ")");
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
