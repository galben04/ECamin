package com.mihaia.ecamin.Plati;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mihaia.ecamin.DataContracts.EvidentaPlata;
import com.mihaia.ecamin.R;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by mihaia on 3/23/2017.
 */

/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)*****************************************************************
/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)*****************************************************************
/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)*****************************************************************
/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)******************************************************************/

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<EvidentaPlata> mDataset;

    public void addAll(Collection<EvidentaPlata> evidentaPlati) {
        mDataset.clear();
        mDataset.addAll(evidentaPlati);
        this.notifyItemRangeChanged(0, getItemCount() - 1);
    }

    public void clear() {
        mDataset.clear();
        this.notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView lunaTitlu, sumaTitlu;
        public ImageButton btnDetalii;
        public TableLayout layoutDetalii;
        TableRow rowVizibil;

        TextView ziPlata, lunaPlata, ziScadenta, lunaScadenta;

        public ViewHolder(View view) {
            super(view);

            rowVizibil = (TableRow) view.findViewById(R.id.tableRow_Vizibil_Plati);
            lunaTitlu = (TextView) view.findViewById(R.id.textViewIstoricLuna);
            sumaTitlu = (TextView) view.findViewById(R.id.textViewIstoricSuma);
            btnDetalii = (ImageButton) view.findViewById(R.id.imgBtnIstoricDetalii);

            layoutDetalii = (TableLayout) view.findViewById(R.id.tableLayoutDetalii);
            layoutDetalii.setVisibility(View.GONE);

            ziPlata = (TextView) view.findViewById(R.id.textViewIstoricZiPlata);
            lunaPlata = (TextView) view.findViewById(R.id.textViewIstoricLunaPlata);

            ziScadenta = (TextView) view.findViewById(R.id.textViewIstoricZiScadenta);
            lunaScadenta = (TextView) view.findViewById(R.id.textViewIstoricLunaScadenta);

            btnDetalii.setOnClickListener(clickExtinde);
            rowVizibil.setOnClickListener(clickExtinde);
        }

        View.OnClickListener clickExtinde = new View.OnClickListener() {
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
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(List<EvidentaPlata> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_lista_istoric, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int poz) {

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        holder.lunaTitlu.setText(df.format(mDataset.get(poz).DataPlata));
        holder.sumaTitlu.setText(mDataset.get(poz).SumaPlata.toString());

        df = new SimpleDateFormat("dd");
        SimpleDateFormat dfLuna = new SimpleDateFormat("MM");

            holder.ziPlata.setText("" + df.format(mDataset.get(poz).DataPlata) + ".");
            holder.lunaPlata.setText("" + dfLuna.format(mDataset.get(poz).DataPlata));


            holder.ziScadenta.setText(" 01.");
            holder.lunaScadenta.setText("" + dfLuna.format(new Date()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}