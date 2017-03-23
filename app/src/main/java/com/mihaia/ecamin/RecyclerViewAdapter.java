package com.mihaia.ecamin;

import android.icu.util.Calendar;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by mihaia on 3/23/2017.
 */

/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)*****************************************************************
/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)*****************************************************************
/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)*****************************************************************
/**********************************************NU UITA SA STERGI COMENTARIILE(luat de pe android developer)******************************************************************/

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private List<InformatiiPlata> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView lunaTitlu, sumaTitlu;
        public ImageButton btnDetalii;
        public TableLayout layoutDetalii;

        TextView ziPlata, lunaPlata, ziScadenta, lunaScadenta;

        public ViewHolder(View view) {
            super(view);

            lunaTitlu = (TextView) view.findViewById(R.id.textViewIstoricLuna);
            sumaTitlu = (TextView) view.findViewById(R.id.textViewIstoricSuma);
            btnDetalii = (ImageButton) view.findViewById(R.id.imgBtnIstoricDetalii);

            layoutDetalii = (TableLayout) view.findViewById(R.id.tableLayoutDetalii);

            ziPlata = (TextView) view.findViewById(R.id.textViewIstoricZiPlata);
            lunaPlata = (TextView) view.findViewById(R.id.textViewIstoricLunaPlata);

            ziScadenta = (TextView) view.findViewById(R.id.textViewIstoricZiScadenta);
            lunaScadenta = (TextView) view.findViewById(R.id.textViewIstoricLunaScadenta);

            btnDetalii.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(layoutDetalii.getVisibility() == View.VISIBLE){
                        layoutDetalii.setVisibility(View.GONE);
                        btnDetalii.setBackgroundResource(R.drawable.less_24);
                    } else {
                        layoutDetalii.setVisibility(View.VISIBLE);
                        btnDetalii.setBackgroundResource(R.drawable.more_24);
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleViewAdapter(List<InformatiiPlata> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.lunaTitlu.setText(mDataset.get(poz).getLuna());
        holder.sumaTitlu.setText(mDataset.get(poz).getSuma().toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.ziPlata.setText(mDataset.get(poz).getDataPlata().get(Calendar.MONTH)) = (TextView) view.findViewById(R.id.textViewIstoricZiPlata);
        }
        lunaPlata = (TextView) view.findViewById(R.id.textViewIstoricLunaPlata);

        ziScadenta = (TextView) view.findViewById(R.id.textViewIstoricZiScadenta);
        lunaScadenta = (TextView) view.findViewById(R.id.textViewIstoricLunaScadenta);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}