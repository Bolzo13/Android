package com.example.databaseadmin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class AddPizza extends AppCompatActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pizza);

        EditText nome=findViewById(R.id.nome);
        EditText ingredienti=findViewById(R.id.data);
        EditText prezzo=findViewById(R.id.prezzo);
        TextView error=findViewById(R.id.error);
        TextView succes=findViewById(R.id.textView5);

        Handler myHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){
                    succes.setVisibility(View.VISIBLE);
                    finish();
                }
            }
        };

        Button add=findViewById(R.id.add);

        add.setOnClickListener((View v)->{
            if(nome.getText().toString().isEmpty()&&ingredienti.getText().toString().isEmpty()&&prezzo.getText().toString().isEmpty()){
                error.setVisibility(View.VISIBLE);
            }else{
                error.setVisibility(View.GONE);
                String[] ingredientis=ingredienti.getText().toString().split(",");
                String[] payload = {nome.getText().toString(),Arrays.toString(ingredientis),prezzo.getText().toString()};
                Thread t=new Thread(new Request("POST",payload,"",myHandler));
                t.start();
            }
        });

    }


}
