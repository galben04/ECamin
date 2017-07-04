package com.mihaia.ecamin;

import android.content.Context;
import android.content.res.Resources;
import java.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mihaia.ecamin.DataContracts.Programare;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    Spinner spinnerData, spinnerOra;

    Context context;
    ArrayList<String> spinnnerDataValues;

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

        spinnerData = (Spinner) view.findViewById(R.id.spinner_ProgramareNoua_Data);
        spinnerOra = (Spinner) view.findViewById(R.id.spinner_ProgramareNoua_Ora);
        spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();

                String[] dateParts = parent.getItemAtPosition(position).toString().split(".");
                new GetAsyncTask<Integer>("OreLibere").execute(dateParts[0], dateParts[1], dateParts[2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        populareSpinnerData();

        btnTrimite = (Button) view.findViewById(R.id.btn_ProgramareNoua_Trimite);
        btnTrimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Programare newItem = getProgramareFromForm();

                new InsertAsyncTask<Programare>("Progrmari").execute(new Programare());
            }
        });

        this.context = getContext();
        return view;
    }

    private void populareSpinnerData() {
        spinnnerDataValues = new ArrayList<String>();
        spinnnerDataValues.add(getResources().getString(R.string.select_data));

        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.YYYY");
        spinnnerDataValues.add(df.format(calendar.getTime()));

        for(int i=0; i < 8; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            spinnnerDataValues.add(df.format(calendar.getTime()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                 android.R.layout.simple_spinner_item, spinnnerDataValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerData.setAdapter(adapter);
    }

    private Programare getProgramareFromForm() {
        Programare p = new Programare();
        p.Id_User = 1;
        p.Id_Masina  = 1;
        p.IsDel = false;

        return p;
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
