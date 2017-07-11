package com.mihaia.ecamin.Plangeri;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.InsertAsyncTask;
import com.mihaia.ecamin.DataContracts.Plangere;
import com.mihaia.ecamin.DataContracts.Programare;
import com.mihaia.ecamin.PaginaPrincipala;
import com.mihaia.ecamin.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlangereNouaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    EditText editTextDescriere;
    TextView dataStatic;
    Button btnTrimite;
    SimpleDateFormat dateFormat;

    AlertDialog.Builder builder;

    public PlangereNouaFragment() {
        // Required empty public constructor
    }

    public static PlangereNouaFragment newInstance(String param1, String param2) {
        PlangereNouaFragment fragment = new PlangereNouaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plangere_noua, container, false);

        editTextDescriere = (EditText) view.findViewById(R.id.editText_PlangereNoua_Descriere);
        dataStatic = (TextView) view.findViewById(R.id.tv_PlangereNoua_Data);
        btnTrimite = (Button) view.findViewById(R.id.btn_PlangereNoua_Trimite);

        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dataStatic.setText(dateFormat.format(new Date()));

        builder = new AlertDialog.Builder(this.getContext());

        btnTrimite.setOnClickListener(listenerButonTrimite);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    View.OnClickListener listenerButonTrimite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Plangere itemToInsert = getPlanegereFromForm();
            if (itemToInsert.Descriere != null) {
                new InsertAsyncTask<Plangere>("Plangeri") {
                    @Override
                    protected void onPostExecute(Integer status) {
                        super.onPostExecute(status);

                        if (status == null || status != 1) {
                            builder.setMessage("Eroare la comunicarea cu serverul!");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else if (status == 1) {
                            builder.setMessage("Plangerea a fost inserta cu succes!");
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
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.eroare_planegere_descriere_lipsa, Toast.LENGTH_SHORT).show();
            }

            editTextDescriere.setText("");
        }
    };

    private Plangere getPlanegereFromForm() {
        Plangere plangere = new Plangere();
        plangere.Id_User = PaginaPrincipala.getUserLogat().Id_User;

        plangere.Descriere = editTextDescriere.getText().toString();
        plangere.Data = new Date();
        plangere.Id_Stare = 3; //INREGISTRATA
        plangere.IsClosed = false;
        plangere.DataFinalizare = new Date();
        plangere.IsDel = false;

        return plangere;
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
        void onFragmentInteraction(Uri uri);
    }
}
