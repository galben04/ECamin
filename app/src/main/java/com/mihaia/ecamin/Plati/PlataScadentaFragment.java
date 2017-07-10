package com.mihaia.ecamin.Plati;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.GetPlataAsyncTask;
import com.mihaia.ecamin.DataContracts.EvidentaPlata;
import com.mihaia.ecamin.R;
import com.mihaia.ecamin.Utils;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlataScadentaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    TextView tvSuma, tvData, tvStatus;
    Context context;

    public PlataScadentaFragment() {
        // Required empty public constructor
    }

    public static PlataScadentaFragment newInstance(String param1, String param2) {
        PlataScadentaFragment fragment = new PlataScadentaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_plata_scadenta, container, false);

        tvData = (TextView) view.findViewById(R.id.tv_plata_data);
        tvSuma = (TextView) view.findViewById(R.id.tv_plata_suma);
        tvStatus = (TextView) view.findViewById(R.id.tv_status);

        getPlataScadenta();

        return  view;
    }

    private void getPlataScadenta() {
        new GetPlataAsyncTask("Plati") {
            @Override
            protected void onPostExecute(EvidentaPlata evidentaPlata) {
                super.onPostExecute(evidentaPlata);

                if(evidentaPlata != null) {
                    tvSuma.setText(String.valueOf(evidentaPlata.SumaPlata) + " " + Utils.valuta);
                    SimpleDateFormat df = new SimpleDateFormat("01.MM.yyyy");
                    tvData.setText(df.format(new Date()));

                    if(evidentaPlata.IsPlatita) {
                        tvStatus.setText("Achitat");
                        tvStatus.setTextColor(ContextCompat.getColor(context, R.color.verdeAndrei));
                    } else {
                        tvStatus.setText("Neachitat");
                        tvStatus.setTextColor(ContextCompat.getColor(context, R.color.portocaliuING));
                    }
                }
                else {
                    Toast.makeText(context, R.string.eorare_conexiune_server, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(String.valueOf(1) ,String.valueOf(new GregorianCalendar().get(Calendar.MONTH) + 1));
    }


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
