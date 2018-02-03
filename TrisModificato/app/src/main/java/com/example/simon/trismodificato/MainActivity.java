package com.example.simon.trismodificato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int j=0;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = new Intent(this,ActivityTwo.class);
        Tris.clear();

    }


    public void clickListener(View view){

        Button cliccato = (Button)(view);
        char premuto;

        if (j % 2 == 0) { cliccato.setText("X"); premuto='X'; }
        else { cliccato.setText("O"); premuto='O'; }
        cliccato.setEnabled(false);
        j++;
        switch (view.getId()) {
            case R.id.uno: Tris.set(view,premuto); break;
            case R.id.due: Tris.set(view,premuto); break;
            case R.id.tre: Tris.set(view,premuto); break;
            case R.id.quattro: Tris.set(view,premuto); break;
            case R.id.cinque: Tris.set(view,premuto); break;
            case R.id.sei: Tris.set(view,premuto); break;
            case R.id.sette: Tris.set(view,premuto); break;
            case R.id.otto: Tris.set(view,premuto); break;
            case R.id.nove: Tris.set(view,premuto); break;
        }

        if(j<9 && Tris.controlloVittoria(i)) { startActivity(i); finish(); }
        if(j==9) { Tris.pareggio(i); startActivity(i); finish();}
    }


}