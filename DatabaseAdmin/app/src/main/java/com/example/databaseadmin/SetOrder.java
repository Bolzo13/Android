package com.example.databaseadmin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetOrder extends RecyclerView.Adapter<SetOrder.PlayerViewHolder> {

    ArrayList<String> pizze;

    public List<Ordini> ordini;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView name, data, ordine,prezzo;

        public PlayerViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.name);
            data =  view.findViewById(R.id.data);
            ordine=view.findViewById(R.id.ordine);
            prezzo =  view.findViewById(R.id.prezzo);
        }
    }

    public SetOrder(List<Ordini> ordini,ArrayList<String> pizze) {
        this.ordini = ordini;
        this.pizze=pizze;
        System.out.println(Arrays.toString(pizze.toArray()));
    }

    @Override
    public SetOrder.PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row, parent, false);

        return new SetOrder.PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SetOrder.PlayerViewHolder holder, int position) {
        String order="";
        Ordini ordine = ordini.get(position);
        holder.name.setText(Integer.toString(ordine.getCliente()));
        holder.data.setText(ordine.getData());
        String[] pizze=ordine.getOrdine().substring(1,ordine.getOrdine().length()-1).split(",");
        for(int i=0;i<pizze.length;i++){
                order+=pizze[i]+": "+this.pizze.get(i)+"\n";
        }
        holder.ordine.setText(order);
        holder.prezzo.setText(Double.toString(ordine.getTotale())+"€");
    }

    @Override
    public int getItemCount() {
        return ordini.size();
    }

}
