package com.mihaia.ecamin.Programari;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mihaia.ecamin.AsyncTaskuri.DeleteAsyncTask;
import com.mihaia.ecamin.AsyncTaskuri.GetMasiniAsyncTask;
import com.mihaia.ecamin.AsyncTaskuri.GetMasiniLibereAsyncTask;
import com.mihaia.ecamin.DataContracts.Masina_Spalat;
import com.mihaia.ecamin.DataContracts.Programare;
import com.mihaia.ecamin.R;
import com.mihaia.ecamin.Utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;


public class ProgramariRecyclerViewAdapter extends RecyclerView.Adapter<ProgramariRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Programare> mDataset;
    public ProgramariRecyclerViewAdapter ReftoThis;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView IdProgramare, data, EtajMasina, IdMasina;
        public TextView IsDel;
        public ImageButton btnDetalii, btnDelete;
        public TableLayout layoutDetalii;
        public TableRow tableRowSus;


        public ViewHolder(View view) {
            super(view);

            layoutDetalii = (TableLayout) view.findViewById(R.id.tableLayoutProgramari_Detalii);
            tableRowSus = (TableRow) view.findViewById(R.id.tableRow_Vizibil);

            btnDetalii = (ImageButton) view.findViewById(R.id.iBtnProgramari_Detalii);
            btnDelete = (ImageButton) view.findViewById(R.id.iBtnProgramari_Delete);

            IdProgramare = (TextView) view.findViewById(R.id.tvProgramari_IdProgramare);
            data = (TextView) view.findViewById(R.id.tvProgramari_Data);
            EtajMasina = (TextView) view.findViewById(R.id.tvProgramari_Etaj);
            IdMasina = (TextView) view.findViewById(R.id.tvProgramari_NumeMasina);
            IsDel = (TextView) view.findViewById(R.id.isDel);

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
    public ProgramariRecyclerViewAdapter(Context context,List<Programare> dataset) {
        this.context = context;
        mDataset = dataset;
        this.ReftoThis = this;

        new GetMasiniAsyncTask("MasiniSpalat") {
            @Override
            protected void onPostExecute(Collection<Masina_Spalat> masini) {
                super.onPostExecute(masini);
                if(masini != null)
                    Utils.MasiniSpalat.addAll(masini);
                ReftoThis.notifyDataSetChanged();
            }
        }.execute();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProgramariRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.element_lista_programari, parent, false);


        ProgramariRecyclerViewAdapter.ViewHolder vh = new ProgramariRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProgramariRecyclerViewAdapter.ViewHolder holder, final int poz) {

        holder.IdProgramare.setText(String.valueOf(mDataset.get(poz).Id_Programare));

        if(Utils.getMasinabyId(mDataset.get(poz).Id_Masina) != null) {
            holder.IdMasina.setText(Utils.getMasinabyId(mDataset.get(poz).Id_Masina).Nume);
            holder.EtajMasina.setText(String.valueOf(Utils.getMasinabyId(mDataset.get(poz).Id_Masina).Etaj));
        } else{
            holder.IdMasina.setText(String.valueOf(mDataset.get(poz).Id_Masina));
            holder.EtajMasina.setText("-");
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
        if(mDataset.get(poz).Data_Ora != null)
            holder.data.setText(dateFormat.format(mDataset.get(poz).Data_Ora));
        else
            holder.data.setText("null");

        if(mDataset.get(poz).IsDel == true)
            holder.IsDel.setText("1");
        else
            holder.IsDel.setText("0");

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
                                    ReftoThis.mDataset.remove(poz);
                                    ReftoThis.notifyDataSetChanged();
                                }

                                else
                                {
                                    Toast.makeText(context, R.string.eroare_anulare_programare, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute("Programari", String.valueOf(mDataset.get(poz).Id_Programare));

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

    public void addAll(Collection<Programare> programari) {
        mDataset.addAll(programari);
        this.notifyDataSetChanged();
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

