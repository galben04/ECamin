package com.mihaia.ecamin.Programari;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.GetAsyncTask;
import com.mihaia.ecamin.AsyncTaskuri.GetMasiniLibereAsyncTask;
import com.mihaia.ecamin.AsyncTaskuri.InsertAsyncTask;
import com.mihaia.ecamin.CustomArrayAdapter;
import com.mihaia.ecamin.DataContracts.Masina_Spalat;
import com.mihaia.ecamin.DataContracts.Programare;
import com.mihaia.ecamin.PaginaPrincipala;
import com.mihaia.ecamin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class ProgramareNouaFragment2 extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ArrayList<String> spinnnerDataValues;
    private ArrayAdapter<String> adapterMasini;
    Button btnTrimite;
    private String currentMasinaSelection;
    AlertDialog.Builder builder;
    public ProgramareNouaFragment2() {
        // Required empty public constructor
    }


    ListView lvOra, lvMasini;
    Spinner spinnerData;

    CustomArrayAdapter<String> adapter;

    public static ProgramareNouaFragment2 newInstance(String param1, String param2) {
        ProgramareNouaFragment2 fragment = new ProgramareNouaFragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_programare_noua2, container, false);

        lvOra = (ListView) view.findViewById(R.id.listview_ProgNoua_Ora);
        lvMasini = (ListView) view.findViewById(R.id.listview_ProgNoua_Masini);
        btnTrimite = (Button) view.findViewById(R.id.btn_ProgNoua_Trimite);

        spinnerData = (Spinner) view.findViewById(R.id.spinner_ProgNoua_Data);
        spinnerData.setOnItemSelectedListener(spinnerDataListener);
        populareSpinnerData();

        adapter = new CustomArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_single_choice, new ArrayList<String>());

        adapterMasini = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_single_choice, new ArrayList<String>());

        lvOra.setAdapter(adapter);
        lvMasini.setAdapter(adapterMasini);

        lvOra.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lvMasini.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        lvOra.setOnItemClickListener(customItemClickListener);
        lvMasini.setOnItemClickListener(masiniItemClickListener);

        builder = new AlertDialog.Builder(this.getContext());

        btnTrimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentHourSelection == null) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.eroare_programare, Toast.LENGTH_SHORT).show();
                } else {
                    Programare itemToInsert = getProgramareFromForm();
                    new InsertAsyncTask<Programare>("Programari"){
                        @Override
                        protected void onPostExecute(Integer status) {
                            super.onPostExecute(status);

                            if(status == null || status != 1)
                            {
                                builder.setMessage("Eroare la comunicarea cu serverul!");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else if(status == 1)
                            {

                                builder.setMessage("Programarea a fost inserta cu succes!");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }.execute(itemToInsert);
                }
            }
        });

        return view;
    }


    AdapterView.OnItemClickListener masiniItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position >= 0) {
                String[] parts = lvMasini.getItemAtPosition(position).toString().split("\\.");
                currentMasinaSelection = parts[0];
            }
        }
    };


    private String currentHourSelection;
    AdapterView.OnItemClickListener customItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position >= 0) {
                currentHourSelection = lvOra.getItemAtPosition(position).toString();

                lvOra.setSelection(position);
                String data = spinnerData.getSelectedItem().toString();
                String[] dateParts = data.split("\\.");
                if(dateParts.length < 3) {
                    Toast.makeText(parent.getContext(),
                            getResources().getText(R.string.eroare_data_programare), Toast.LENGTH_SHORT).show();
                } else {
                    new GetMasiniLibereAsyncTask("Programari/MasiniLibere") {
                        @Override
                        protected void onPostExecute(Collection<Masina_Spalat> masini) {
                            super.onPostExecute(masini);

                            if (masini != null)
                                populareSpinnerMasiniDisponibile(masini);

                        }
                    }.execute(dateParts[0], dateParts[1], dateParts[2], parent.getItemAtPosition(position).toString());
                }
            }
        }
    };


    AdapterView.OnItemSelectedListener spinnerDataListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            lvOra.setEnabled(false);

            adapter.clear();
            adapter.notifyDataSetChanged();

            lvMasini.setVisibility(View.GONE);
            //.setVisibility(View.GONE);

            if(position > 0) {
                String[] dateParts = (parent.getItemAtPosition(position).toString()).split("\\.");
                if(dateParts.length < 3) {
                    Toast.makeText(parent.getContext(),
                            getResources().getText(R.string.eroare_data_programare), Toast.LENGTH_SHORT).show();
                } else {
                    new GetAsyncTask("Programari/OreLibere"){
                        @Override
                        protected void onPostExecute(Collection<Integer> ore) {
                            super.onPostExecute(ore);

                            if(ore != null)
                                populareSpinnerOreDisponibile(ore);

                        }
                    }.execute(dateParts[0], dateParts[1], dateParts[2]);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void populareSpinnerData() {
        spinnnerDataValues = new ArrayList<String>();
        spinnnerDataValues.add(getResources().getString(R.string.select_data));

        spinnnerDataValues.add("03.07.2017");
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        spinnnerDataValues.add(df.format(calendar.getTime()));

        for(int i=0; i < 8; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            spinnnerDataValues.add(df.format(calendar.getTime()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, spinnnerDataValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerData.setAdapter(adapter);
    }


    private void populareSpinnerOreDisponibile(Collection<Integer> ore) {
        lvOra.setEnabled(true);

        ////Pregatim lista orele libere
        ArrayList<String> lvOreValues = new ArrayList<String>();

        for (Integer i: ore) {
            lvOreValues.add(String.valueOf(i));
        }

        ///Setam adaptorul pentru spinner
        adapter.clear();
        adapter.addAll(lvOreValues);
        adapter.notifyDataSetChanged();

    }


    public void populareSpinnerMasiniDisponibile(Collection<Masina_Spalat> masini) {
        lvMasini.setVisibility(View.VISIBLE);
       // tvOptional.setVisibility(View.VISIBLE);

        ArrayList<String> masiniValues = new ArrayList<String>();

        for (Masina_Spalat i: masini) {
            masiniValues.add(i.Id_Masina + ". " + i.Nume + " - Etaj " + i.Etaj);
        }

        ///Setam adaptorul pentru spinner
        adapterMasini.clear();
        adapterMasini.addAll(masiniValues);
        adapterMasini.notifyDataSetChanged();

    }

    private Programare getProgramareFromForm() {
        Programare p = new Programare();

        p.Id_User = PaginaPrincipala.getUserLogat().Id_User;
        int ora = Integer.valueOf(currentHourSelection);
        p.Id_Masina  = Integer.valueOf(currentMasinaSelection);
        p.IsDel = false;

        ///Creaza Data in formatul potrivit:
        String data = spinnerData.getSelectedItem().toString();
        String[] dateParts = data.split("\\.");
        p.Data_Ora = crateDate(dateParts[0], dateParts[1], dateParts[2], currentHourSelection);

        return p;
    }

    private Date crateDate(String zi, String luna, String an, String ora) {

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH");
        formatter.setLenient(false);
        Date d = null;
        try {
            d = formatter.parse(luna + "/" + zi + "/" + an + " " + ora);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
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
