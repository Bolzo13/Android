package com.example.matteo.labirintoo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;


public class Stanza extends Fragment {
    final int layouts[][]={
            {R.layout.basso_destra,R.layout.destra_sinistra_basso,R.layout.sinistra_basso},
            {R.layout.destra_basso_alto,R.layout.centrale,R.layout.sinistra_basso_alto},
            {R.layout.destra_alto,R.layout.destra_sinistra_alto,R.layout.destra_sinistra_alto}
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getLayout();
        return inflater.inflate(layouts[Labirinto.x][Labirinto.y], container, false);
    }
}