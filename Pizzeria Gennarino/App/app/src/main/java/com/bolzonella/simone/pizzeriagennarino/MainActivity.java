package com.bolzonella.simone.pizzeriagennarino;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    String[] pizze;
    double[] prezzi;
    TextView benvenuto;
    private int totalePizze=0;
    String scontrino,connesso;
    ConstraintLayout connected,connecting;


    Handler mainHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                Bundle bundle = msg.getData();
                connesso = bundle.getString("connesso");
                pizze = bundle.getStringArray("Pizze");
                prezzi= bundle.getDoubleArray("Prezzi");
                benvenuto.setText(connesso);
                loadMenu(pizze,prezzi);
                connecting.setVisibility(View.GONE);
                connected.setVisibility(View.VISIBLE);
            }else{
                Bundle bundle = msg.getData();
                scontrino=bundle.getString("result");
                final TextView result=findViewById(R.id.result);
                result.setText("Totale scontrino: "+scontrino+"€");
            }

        }
    };



    MyThread myThread=new MyThread(mainHandler);
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit=findViewById(R.id.ordina);
        benvenuto=findViewById(R.id.textView5);
        connected=findViewById(R.id.connected);
        connecting=findViewById(R.id.connecting);
        thread=new Thread(myThread);
        thread.start();

        submit.setOnClickListener((View v)->{
            if(submit.getText().equals("ORDINA")) {
                int[] order=new int[pizze.length];
                for(int i=0;i<order.length;i++){
                    EditText pizza=findViewById(100+i);
                    totalePizze+=Integer.parseInt(pizza.getText().toString());
                    order[i]=Integer.parseInt(pizza.getText().toString());
                }
                    myThread.setOrdine(totalePizze, order);
                    thread = new Thread(myThread);
                    thread.start();
                    submit.setText("PAGA");
            }else{
                Intent i=new Intent(this,Paypal.class);
                i.putExtra("Totale",Double.parseDouble(scontrino));
                startActivityForResult(i,0);
            }
        });

    }

    private void loadMenu(String[] pizze,double[] prezzi){

        TableLayout tabella=findViewById(R.id.table);

        for (int i = 0; i < pizze.length; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setTag("row" + i);


            TextView pizza = new TextView(this);
            pizza.setId(300 + i);
            pizza.setSingleLine(false);
            pizza.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            pizza.setText(String.valueOf(pizze[i])+" "+prezzi[i] + "€");

            EditText number = new EditText(this);
            number.setId(200+i);
            number.setInputType(InputType.TYPE_CLASS_NUMBER);
            number.setWidth(500);
            number.setHeight(150);
            number.offsetLeftAndRight(10);
            number.setId(100+ i);
            number.setText("0");

            tableRow.addView(pizza);

            tableRow.addView(number);

            tabella.addView(tableRow);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode!=42) {
            Intent intent = new Intent(this, Success.class);
            startActivityForResult(intent, 42);
        }else {
            if(requestCode==42){
                finish();
            }
        }
    }
}