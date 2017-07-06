package com.mihaia.ecamin.Plangeri;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.DeleteAsyncTask;
import com.mihaia.ecamin.DataContracts.Plangere;
import com.mihaia.ecamin.DataContracts.Programare;
import com.mihaia.ecamin.Plati.RecyclerViewAdapter;
import com.mihaia.ecamin.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mihai on 7/6/2017.
 */

public class PlangeriRecyclerViewAdapter extends RecyclerView.Adapter<PlangeriRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Plangere> mDataset;
    public com.mihaia.ecamin.Plangeri.PlangeriRecyclerViewAdapter ReftoThis;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView IdPlangere, data, stare, dataSolutionare;
        public CheckBox solutionata;
        public TextView IsDel;
        public ImageButton btnDetalii, btnDelete;
        public TableLayout layoutDetalii;
        public TableRow tableRowSus;


        public ViewHolder(View view) {
            super(view);

            layoutDetalii = (TableLayout) view.findViewById(R.id.tableLayoutPlangeri_Detalii);
            tableRowSus = (TableRow) view.findViewById(R.id.tableRow_Vizibil);

            btnDetalii = (ImageButton) view.findViewById(R.id.iBtnPlangeri_Detalii);
            btnDelete = (ImageButton) view.findViewById(R.id.iBtnPlangeri_Delete);

            IdPlangere = (TextView) view.findViewById(R.id.tv_listaPlangeri_IdPlangere);
            data = (TextView) view.findViewById(R.id.tv_listaPlangeri_Data);
            stare = (TextView) view.findViewById(R.id.tv_listaPlangeri_Stare);
            dataSolutionare = (TextView) view.findViewById(R.id.tv_listaPlangeri_DataSolutionare);

            solutionata = (CheckBox) view.findViewById(R.id.checkbox_plageri_solutionata);

            layoutDetalii.setVisibility(View.GONE);

            btnDetalii.setOnClickListener(onClickListenerExtinde);
            tableRowSus.setOnClickListener(onClickListenerExtinde);
        }

        View.OnClickListener onClickListenerExtinde =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutDetalii.getVisibility() == View.VISIBLE){
                    layoutDetalii.setVisibility(View.GONE);
                    btnDetalii.setBackgroundResource(R.drawable.more_24);
                } else {
                    layoutDetalii.setVisibility(View.VISIBLE);
                    btnDetalii.setBackgroundResource(R.drawable.less_24);
                }

            }
        };

        private void extindeLayoutDetaliii()
        {
            if(layoutDetalii.getVisibility() == View.VISIBLE){
                layoutDetalii.setVisibility(View.GONE);
                btnDetalii.setBackgroundResource(R.drawable.more_24);
            } else {
                layoutDetalii.setVisibility(View.VISIBLE);
                btnDetalii.setBackgroundResource(R.drawable.less_24);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlangeriRecyclerViewAdapter(Context context, List<Plangere> dataset) {
        this.context = context;
        mDataset = dataset;
        this.ReftoThis = this;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_lista_plangeri, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int poz) {

        holder.IdPlangere.setText(String.valueOf(mDataset.get(poz).Id_Plangere));
        holder.stare.setText(String.valueOf(mDataset.get(poz).Id_Stare));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
        if(mDataset.get(poz).Data != null)
            holder.data.setText(dateFormat.format(mDataset.get(poz).Data));
        else
            holder.data.setText("null");


        if(mDataset.get(poz).IsClosed) {
            holder.solutionata.setChecked(true);
            holder.data.setText(dateFormat.format(mDataset.get(poz).DataFinalizare));
        } else {
            holder.solutionata.setChecked(false);
            holder.data.setText("null");
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage(R.string.dialog_ProgramareSterge_Mesaj)
                        .setTitle(R.string.dialog_ProgramareSterge_Titlu);

                builder.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(context, "A ales Da", Toast.LENGTH_SHORT).show();
                        new DeleteAsyncTask() {
                            @Override
                            protected void onPostExecute(Integer integer) {
                                super.onPostExecute(integer);
                                if(integer == 1)
                                {
                                    Toast.makeText(context, R.string.programare_anulata, Toast.LENGTH_SHORT).show();
                                    ReftoThis.notifyDataSetChanged();
                                }

                                else
                                {
                                    Toast.makeText(context, R.string.eroare_anulare_programare, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute("Plageri", String.valueOf(mDataset.get(poz).Id_Plangere));

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(R.string.nu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, R.string.anulare_stergere_programare, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void clear()
    {
        int size = mDataset.size();
        mDataset.clear();
        this.notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}



