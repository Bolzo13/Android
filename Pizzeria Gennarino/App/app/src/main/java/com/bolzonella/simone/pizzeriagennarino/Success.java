package com.bolzonella.simone.pizzeriagennarino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Success extends AppCompatActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);

        Button newOrder=findViewById(R.id.newOrder);
        Button exit=findViewById(R.id.Exit);

        newOrder.setOnClickListener((View v)->{
            Intent i=new Intent(this,MainActivity.class);
            finish();
            startActivity(i);
        });

        exit.setOnClickListener((View c)->{
            finish();
        });

    }
}
