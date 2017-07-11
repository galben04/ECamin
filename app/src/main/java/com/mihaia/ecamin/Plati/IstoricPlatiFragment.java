package com.mihaia.ecamin.Plati;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.SelectPlatiAsyncTask;
import com.mihaia.ecamin.DataContracts.EvidentaPlata;
import com.mihaia.ecamin.InformatiiPlata;
import com.mihaia.ecamin.PaginaPrincipala;
import com.mihaia.ecamin.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;


public class IstoricPlatiFragment extends Fragment {

    private RecyclerViewAdapter mRecycleViewAdaper;
    private RecyclerView recyclerView;
    ArrayList<EvidentaPlata> arrayList;

    Context context;

    private OnFragmentInteractionListener mListener;

    public IstoricPlatiFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            getData();
        }
    }

    private void getData() {
        new SelectPlatiAsyncTask("Plati") {
            @Override
            protected void onPostExecute(Collection<EvidentaPlata> evidentaPlati) {
                super.onPostExecute(evidentaPlati);

                if(evidentaPlati != null)
                {
                    mRecycleViewAdaper.clear();
                    mRecycleViewAdaper.addAll(evidentaPlati);


                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mRecycleViewAdaper);
                } else
                {

                    Toast.makeText(context, R.string.eorare_conexiune_server, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(String.valueOf(PaginaPrincipala.getUserLogat().Id_User));
    }

    public static IstoricPlatiFragment newInstance(String param1, String param2) {
        IstoricPlatiFragment fragment = new IstoricPlatiFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }


    private void initListTemp() {
//        GregorianCalendar dataScadenta = new GregorianCalendar();
//
//        dataScadenta.add(GregorianCalendar.DAY_OF_MONTH, 10);
//        dataScadenta.add(GregorianCalendar.MONTH, 1);
//
//        GregorianCalendar dataPlata = new GregorianCalendar();
//        dataPlata.add(GregorianCalendar.DAY_OF_MONTH, 5);
//        dataPlata.add(GregorianCalendar.MONTH, 1);
//
//        arrayList = new ArrayList<InformatiiPlata>();
//
//        arrayList.add(new InformatiiPlata("Ianuarie", (float)88.3, dataPlata, dataScadenta));
//
//        dataScadenta.add(GregorianCalendar.MONTH, 2);
//        dataPlata.add(GregorianCalendar.MONTH, 2);
//        arrayList.add(new InformatiiPlata("Februarie", (float)88.3, dataPlata, dataScadenta));
//
//        dataScadenta.add(GregorianCalendar.MONTH, 3);
//        dataPlata.add(GregorianCalendar.MONTH, 3);
//        arrayList.add(new InformatiiPlata("Martie", (float)88.3, dataPlata, dataScadenta));
//
//        dataScadenta.add(GregorianCalendar.MONTH, 4);
//        dataPlata.add(GregorianCalendar.MONTH, 4);
//        arrayList.add(new InformatiiPlata("Aprilie", (float)88.3, dataPlata, dataScadenta));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_istoric_plati, container, false);

        //initListTemp();
        arrayList = new ArrayList<EvidentaPlata>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewIstoric);
        mRecycleViewAdaper = new RecyclerViewAdapter(arrayList);
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
}
