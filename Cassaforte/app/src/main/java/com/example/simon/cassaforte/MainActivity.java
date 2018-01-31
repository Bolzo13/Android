package com.example.simon.cassaforte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String PASSWORD="1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView display=findViewById(R.id.textView);

        bottoneCliccato(findViewById(R.id.uno));
        bottoneCliccato(findViewById(R.id.due));
        bottoneCliccato(findViewById(R.id.tre));
        bottoneCliccato(findViewById(R.id.quattro));
        bottoneCliccato(findViewById(R.id.cinque));
        bottoneCliccato(findViewById(R.id.sei));
        bottoneCliccato(findViewById(R.id.sette));
        bottoneCliccato(findViewById(R.id.otto));
        bottoneCliccato(findViewById(R.id.nove));
        bottoneCliccato(findViewById(R.id.zero));

        Button del=findViewById(R.id.del);
        del.setOnClickListener((View v)->{
            display.setText(display.getText().toString().substring(0,display.length()-1));
            if(display.getText().toString().equals(PASSWORD)){
                display.setTextColor(getResources().getColor(R.color.GREEN));

            }else{
                display.setTextColor(getResources().getColor(R.color.RED));

            }
        });

        Intent i=new Intent(this,ActivityTwo.class);

        Button ok=findViewById(R.id.ok);
        ok.setOnClickListener((View v)->{
            if(display.getText().toString().equals(PASSWORD)) {
                startActivity(i);
            }else{
                Toast.makeText(this,"Password errata!!!",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bottoneCliccato(Button b){

        TextView display=findViewById(R.id.textView);

        b.setOnClickListener((View v)->{
            display.setText(display.getText().toString()+b.getText().toString());
            if(display.getText().toString().equals(PASSWORD)){
                display.setTextColor(getResources().getColor(R.color.GREEN));

            }else{
                display.setTextColor(getResources().getColor(R.color.RED));

            }
        });
    }
}
