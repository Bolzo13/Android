package com.example.simon.trismodificato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by simon on 03/02/2018.
 */

public class ActivityTwo extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        TextView text =findViewById(R.id.textView);
        Button restart = findViewById(R.id.restart);

        restart.setOnClickListener((View view) -> {
            Tris.clear();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        if(getIntent().getCharExtra("winner",' ')=='n') text.setText("Pareggio");
        else text.setText("Ha Vinto il Giocatore "+getIntent().getCharExtra("winner",' '));
    }
}
