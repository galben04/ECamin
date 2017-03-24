package com.mihaia.ecamin;

import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IstoricFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IstoricFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IstoricFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerViewAdapter mRecycleViewAdaper;
    private RecyclerView recyclerView;
    ArrayList<InformatiiPlata> arrayList;

    private OnFragmentInteractionListener mListener;

    public IstoricFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IstoricFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IstoricFragment newInstance(String param1, String param2) {
        IstoricFragment fragment = new IstoricFragment();
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


    private void initListTemp() {
        GregorianCalendar dataScadenta = new GregorianCalendar();

        dataScadenta.add(GregorianCalendar.DAY_OF_MONTH, 10);
        dataScadenta.add(GregorianCalendar.MONTH, 1);

        GregorianCalendar dataPlata = new GregorianCalendar();
        dataPlata.add(GregorianCalendar.DAY_OF_MONTH, 5);
        dataPlata.add(GregorianCalendar.MONTH, 1);

        arrayList = new ArrayList<InformatiiPlata>();

        arrayList.add(new InformatiiPlata("Ianuarie", (float)88.3, dataPlata, dataScadenta));

        dataScadenta.add(GregorianCalendar.MONTH, 2);
        dataPlata.add(GregorianCalendar.MONTH, 2);
        arrayList.add(new InformatiiPlata("Februarie", (float)88.3, dataPlata, dataScadenta));

        dataScadenta.add(GregorianCalendar.MONTH, 3);
        dataPlata.add(GregorianCalendar.MONTH, 3);
        arrayList.add(new InformatiiPlata("Martie", (float)88.3, dataPlata, dataScadenta));

        dataScadenta.add(GregorianCalendar.MONTH, 4);
        dataPlata.add(GregorianCalendar.MONTH, 4);
        arrayList.add(new InformatiiPlata("Aprilie", (float)88.3, dataPlata, dataScadenta));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_istoric_plati, container, false);

        initListTemp();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewIstoric);
        mRecycleViewAdaper = new RecyclerViewAdapter(arrayList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mRecycleViewAdaper);
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
