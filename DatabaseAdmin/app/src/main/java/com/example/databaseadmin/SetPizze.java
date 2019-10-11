package com.example.databaseadmin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


class SetPizze extends RecyclerView.Adapter<SetPizze.PlayerViewHolder> {
    public List<Pizza> pizzas;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView name, ingredienti, age;

        public PlayerViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.name);
            ingredienti =  view.findViewById(R.id.data);
            age =  view.findViewById(R.id.prezzo);
        }
    }

    public SetPizze(List<Pizza> pizze) {
        this.pizzas = pizze;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row, parent, false);

        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Pizza pizza = pizzas.get(position);
        holder.name.setText(pizza.getName());
        holder.ingredienti.setText(pizza.getIngredienti());
        holder.age.setText(Double.toString(pizza.getPrezzo())+"€");
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }
}