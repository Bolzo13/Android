package com.example.databaseadmin;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add=findViewById(R.id.add);
        Button delete=findViewById(R.id.delete);
        Button order=findViewById(R.id.order);
        Button history=findViewById(R.id.history);




        add.setOnClickListener((View v)->{
            Intent i=new Intent(this,AddPizza.class);
            startActivity(i);
        });

        delete.setOnClickListener((View v)->{
            Intent i=new Intent(this,DeletePizza.class);
            startActivity(i);
        });

        order.setOnClickListener((View v)->{
            Intent i=new Intent(this,InsertOrder.class);
            startActivity(i);
        });

        history.setOnClickListener((View v)->{
            Intent i=new Intent(this,ShowHistory.class);
            startActivity(i);
        });

    }
}
