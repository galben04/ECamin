package com.mihaia.ecamin;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.UpdateUserAsync;
import com.mihaia.ecamin.DataContracts.User;


public class SetariFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public Context context;
    AlertDialog.Builder builder;

    TextView tvParola2;
    EditText edNumeCont, edParola, edParola2;
    Button btnSalveaza;
    ImageButton btnRon, btnValuta, btnOptCont, btnInfoCont;

    LinearLayout containerValuta;
    android.support.v7.widget.GridLayout containerOptCont, containerInfoCont;

    LinearLayout layoutValuta, layoutOptCont, layoutInfoCont;

    EditText etNumeContInfo, etNume, etPrenume, etTelefon;

    public SetariFragment() {
        // Required empty public constructor
    }

    public static SetariFragment newInstance(String param1, String param2) {
        SetariFragment fragment = new SetariFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_setari, container, false);

        builder = new AlertDialog.Builder(context);

        edNumeCont = (EditText) view.findViewById(R.id.et_Setari_NumeCont);
        edParola = (EditText) view.findViewById(R.id.et_Setari_Parola);
        edParola2 = (EditText) view.findViewById(R.id.et_setari_parola2);
        btnSalveaza = (Button) view.findViewById(R.id.btn_salveaza);
        tvParola2 = (TextView) view.findViewById(R.id.tv_setari_parola2);

        containerValuta = (LinearLayout) view.findViewById(R.id.linearL_Setari_Valuta);
        containerOptCont = (android.support.v7.widget.GridLayout) view.findViewById(R.id.gridLayout_Setari_schParola);
        containerInfoCont = (android.support.v7.widget.GridLayout) view.findViewById(R.id.gridLayout_Setari_infCont);

        layoutValuta = (LinearLayout) view.findViewById(R.id.layoutValuta);
        layoutInfoCont = (LinearLayout) view.findViewById(R.id.layoutInfoCont);
        layoutOptCont = (LinearLayout) view.findViewById(R.id.layoutOptCont);

        btnValuta = (ImageButton) view.findViewById(R.id.iBtn_Setari_Valuta);
        btnOptCont = (ImageButton) view.findViewById(R.id.ibtn_Setari_SchParola);
        btnInfoCont = (ImageButton) view.findViewById(R.id.ibtn_Setari_InfCont); 

        etNumeContInfo = (EditText) view.findViewById(R.id.et_SetariInfo_NumeUtilizator);
        etNume = (EditText) view.findViewById(R.id.et_SetariInfo_Nume);
        etPrenume = (EditText) view.findViewById(R.id.et_setariInfo_prenume);
        etTelefon = (EditText) view.findViewById(R.id.et_setariInfo_telefon);
        populateInfoUser();

        edParola2.setVisibility(View.GONE);
        tvParola2.setVisibility(View.GONE);

        edNumeCont.setText(PaginaPrincipala.getUserLogat().Cont);

        edParola.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                edParola2.setVisibility(View.VISIBLE);
                tvParola2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edParola2.setVisibility(View.VISIBLE);
                tvParola2.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edParola.getText().toString().compareTo(edParola2.getText().toString()) != 0)
                    Toast.makeText(context, getResources().getString(R.string.parolele_difera), Toast.LENGTH_SHORT);
                else{
                    User userToUpdate = PaginaPrincipala.getUserLogat();
                    userToUpdate.Parola = edParola.getText().toString();

                    new UpdateUserAsync("Users"){
                        @Override
                        protected void onPostExecute(Integer integer) {
                            super.onPostExecute(integer);

                            if(integer == 0){
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
                            else if(integer == 1)
                            {

                                builder.setMessage("Parola a fost schimbata cu succes!");
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
                    }.execute(userToUpdate);

                    resetForm();
                }

            }
        });


        btnValuta.setOnClickListener(valutaOnClickListener);
        layoutValuta.setOnClickListener(valutaOnClickListener);

        btnOptCont.setOnClickListener(optContOnClickListener);
        layoutOptCont.setOnClickListener(optContOnClickListener);

        btnInfoCont.setOnClickListener(infoContOnClickListener);
        layoutInfoCont.setOnClickListener(infoContOnClickListener);

        return view;
    }

    private void populateInfoUser() {

        etNumeContInfo.setText(PaginaPrincipala.getUserLogat().Cont);
        etNume.setText(Utils.infoUserLogat.Nume);
        etPrenume.setText(Utils.infoUserLogat.Prenume);
        etTelefon.setText(Utils.infoUserLogat.Telefon);
    }

    View.OnClickListener valutaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(containerValuta.getVisibility() == View.GONE){
                containerValuta.setVisibility(View.VISIBLE);
                btnValuta.setBackgroundResource(R.drawable.arrow_up_24);
            } else {
                containerValuta.setVisibility(View.GONE);
                btnValuta.setBackgroundResource(R.drawable.arrow_down_24);
            }
        }
    };
    
    View.OnClickListener  optContOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(containerOptCont.getVisibility() == View.GONE){
                containerOptCont.setVisibility(View.VISIBLE);
                btnOptCont.setBackgroundResource(R.drawable.arrow_up_24);
            } else {
                containerOptCont.setVisibility(View.GONE);
                btnOptCont.setBackgroundResource(R.drawable.arrow_down_24);

                edParola2.setText("");
                edParola2.setVisibility(View.GONE);
                tvParola2.setVisibility(View.GONE);
            }
        }
    };
    
    View.OnClickListener infoContOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(containerInfoCont.getVisibility() == View.GONE){
                containerInfoCont.setVisibility(View.VISIBLE);
                btnInfoCont.setBackgroundResource(R.drawable.arrow_up_24);
            } else {
                containerInfoCont.setVisibility(View.GONE);
                btnInfoCont.setBackgroundResource(R.drawable.arrow_down_24);
            }
        }
    };
    
    private void resetForm() {
        edParola.setText("");
        edParola2.setText("");
        edParola2.setVisibility(View.GONE);
        tvParola2.setVisibility(View.GONE);
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
