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
import com.mihaia.ecamin.R;
import com.mihaia.ecamin.Utils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mihai on 7/6/2017.
 */

public class PlangeriRecyclerViewAdapter extends RecyclerView.Adapter<PlangeriRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private List<Plangere> mDataset;
    public com.mihaia.ecamin.Plangeri.PlangeriRecyclerViewAdapter ReftoThis;
    private LayoutInflater inflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView IdPlangere, data, stare, dataSolutionare, descriere;
        public CheckBox solutionata;
        public TextView IsDel;

        public ImageButton btnDetalii, btnDelete;

        public TableLayout layoutDetalii;
        public TableRow tableRowSus, tableRowDataSolutionare;

        public MyViewHolder(View view) {
            super(view);

            layoutDetalii = (TableLayout) view.findViewById(R.id.tableLayoutPlangeri_Detalii);
            tableRowSus = (TableRow) view.findViewById(R.id.tableRow_Vizibil);
            tableRowDataSolutionare = (TableRow) view.findViewById(R.id.tablerow_dataSolutionare);

            btnDetalii = (ImageButton) view.findViewById(R.id.iBtnPlangeri_Detalii);
            btnDelete = (ImageButton) view.findViewById(R.id.iBtnPlangeri_Delete);

            IdPlangere = (TextView) view.findViewById(R.id.tv_listaPlangeri_IdPlangere);
            data = (TextView) view.findViewById(R.id.tv_listaPlangeri_Data);
            stare = (TextView) view.findViewById(R.id.tv_listaPlangeri_Stare);
            dataSolutionare = (TextView) view.findViewById(R.id.tv_listaPlangeri_DataSolutionare);

            descriere = (TextView) view.findViewById(R.id.editText_Plangeri_Descriere);
            descriere.setEnabled(false);

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
        inflater = LayoutInflater.from(context);
        this.ReftoThis = this;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) inflater.inflate(R.layout.element_lista_plangeri, parent, false);

//        new GetStariPlangeriAsync("StariPlangeri") {
//            @Override
//            protected void onPostExecute(Collection<Stare_Plangere> stari) {
//                super.onPostExecute(stari);
//                if(stari != null)
//                    Utils.StariPlageri.addAll(stari);
//                    ReftoThis.notifyDataSetChanged();
//            }
//        }.execute();

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int poz) {

        holder.IdPlangere.setText(String.valueOf(mDataset.get(poz).Id_Plangere));

        if(Utils.getNumeStarebyId(mDataset.get(poz).Id_Stare) != null)
            holder.stare.setText(Utils.getNumeStarebyId(mDataset.get(poz).Id_Stare));
        else
            holder.stare.setText(String.valueOf(mDataset.get(poz).Id_Stare));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");

        if(mDataset.get(poz).Data != null)
            holder.data.setText(dateFormat.format(mDataset.get(poz).Data));
        else
            holder.data.setText("null");

        holder.descriere.setText(mDataset.get(poz).Descriere);

        if(mDataset.get(poz).IsClosed) {
            holder.solutionata.setChecked(true);
            holder.tableRowDataSolutionare.setVisibility(View.VISIBLE);
            holder.dataSolutionare.setText(dateFormat.format(mDataset.get(poz).DataFinalizare));
        } else {
            holder.solutionata.setChecked(false);
            holder.tableRowDataSolutionare.setVisibility(View.GONE);
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
                                    Toast.makeText(context, R.string.plangere_anulata, Toast.LENGTH_SHORT).show();
                                    ReftoThis.notifyDataSetChanged();
                                }

                                else
                                {
                                    Toast.makeText(context, R.string.eroare_anulare_plangere, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute("Plangeri", String.valueOf(mDataset.get(poz).Id_Plangere));

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(R.string.nu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, R.string.anulare_stergere_plangere, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

//    private void getNumeStare(Stare_Plangere stare) {
//        this.NumeStare =  stare.Nume;
//    }

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



