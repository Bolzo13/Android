package com.example.databaseadmin;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowHistory extends AppCompatActivity {

    private SetOrder mAdapter;
    SwipeController swipeController = null;

    private ArrayList<String> username;
    private ArrayList<String> prezzi;
    private ArrayList<String> data;
    private ArrayList<String[]> ordini;

    private ArrayList<String> pizze;

    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                Bundle bundle = msg.getData();
                ordini = (ArrayList<String[]>) bundle.getSerializable("ordini");
                prezzi = bundle.getStringArrayList("prezzi");
                System.out.println(prezzi.get(0));
                data = bundle.getStringArrayList("date");
                username = bundle.getStringArrayList("username");

                setPlayersDataAdapter();
                setupRecyclerView();
            }else{
                if(msg.what==2){
                    Bundle bundle=msg.getData();
                    pizze=bundle.getStringArrayList("pizze");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_history);
        Thread t2=new Thread(new Request("GET","",myHandler));
        t2.start();
        try {
            t2.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t=new Thread(new Request("GET","",myHandler));
        t.start();
    }

    private void setPlayersDataAdapter()  {

        List<Ordini> ordinis = new ArrayList<>();
        System.out.println(this.ordini.size());
        for (int i = 0; i< this.ordini.size(); i++){
            Ordini ordini = new Ordini();
            try {
                ordini.setCliente(Integer.parseInt(new JSONObject(this.username.get(i)).get("$numberInt").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ordini.setData(data.get(i));
            ordini.setOrdine(Arrays.toString(this.ordini.get(i)));
            ordini.setTotale(Double.parseDouble(prezzi.get(i)));
            ordinis.add(ordini);
        }
        mAdapter = new SetOrder(ordinis,pizze);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                System.out.println(ordini.get(position));
                //Thread t=new Thread(new Request("DELETE",pizze.get(position),"",myHandler));
                //t.start();
                mAdapter.ordini.remove(position);
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
