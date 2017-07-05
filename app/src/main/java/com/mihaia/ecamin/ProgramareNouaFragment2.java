package com.mihaia.ecamin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgramareNouaFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgramareNouaFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramareNouaFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProgramareNouaFragment2() {
        // Required empty public constructor
    }


    ListView lvOra, lvMasini;
    Spinner spinnerData;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgramareNouaFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgramareNouaFragment2 newInstance(String param1, String param2) {
        ProgramareNouaFragment2 fragment = new ProgramareNouaFragment2();
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
        View view = inflater.inflate(R.layout.fragment_programare_noua2, container, false);

        lvOra = (ListView) view.findViewById(R.id.listview_ProgNoua_Ora);
        lvMasini = (ListView) view.findViewById(R.id.listview_ProgNoua_Masini);

        spinnerData = (Spinner) view.findViewById(R.id.spinner_ProgNoua_Data);

        ArrayList<String> oreList = new ArrayList<String>();
        oreList.add("11");
        oreList.add("12");
        oreList.add("13");
        oreList.add("14");
        oreList.add("15");
        oreList.add("16");

        ArrayList<String> masiniList = new ArrayList<String>();
        masiniList.add("2. Masina 1 - Etaj 1");
        masiniList.add("3. Masina 2 - Etaj 1");
        masiniList.add("5. Masina 6 - Etaj 2");
        masiniList.add("7. Masina 7 - Etaj 2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.custom_simple_list_1, oreList);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.custom_simple_list_1, masiniList);

        lvOra.setAdapter(adapter);
        lvMasini.setAdapter(adapter2);

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
