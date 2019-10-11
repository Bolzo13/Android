package com.example.databaseadmin;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeletePizza extends AppCompatActivity {
    private SetPizze mAdapter;
    SwipeController swipeController = null;

    private ArrayList<String> pizze;
    private ArrayList<String> prezzi;
    private ArrayList<String[]> ingredienti;


    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==2){
                Bundle bundle=msg.getData();
                pizze=bundle.getStringArrayList("pizze");
                prezzi=bundle.getStringArrayList("prezzi");
                ingredienti= (ArrayList<String[]>) bundle.getSerializable("ingredienti");
                //System.out.println(Arrays.toString(pizze.toArray())+" "+Arrays.toString(prezzi.toArray())+" "+Arrays.toString(ingredienti));

                setPlayersDataAdapter();
                setupRecyclerView();
            }
        }
    };

        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_pizza);
        Thread t=new Thread(new Request("GET","https://webhooks.mongodb-stitch.com/api/client/v2.0/app/rossopomodoro-hqlpx/service/HTTPizzeria/incoming_webhook/Pizzas",myHandler));
        t.start();
    }

    private void setPlayersDataAdapter() {

        List<Pizza> pizze = new ArrayList<>();
        System.out.println(this.pizze.size());
            for (int i = 0; i< this.pizze.size(); i++){
                Pizza pizza = new Pizza();
                pizza.setName(this.pizze.get(i));
                pizza.setIngredienti(Arrays.toString(ingredienti.get(i)));
                pizza.setPrezzo(Double.parseDouble(prezzi.get(i)));
                pizze.add(pizza);
            }

        mAdapter = new SetPizze(pizze);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                System.out.println(pizze.get(position));
                //Thread t=new Thread(new Request("DELETE",pizze.get(position),"https://webhooks.mongodb-stitch.com/api/client/v2.0/app/rossopomodoro-hqlpx/service/HTTPizzeria/incoming_webhook/DELETEPizze",myHandler));
                //t.start();
                mAdapter.pizzas.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}

