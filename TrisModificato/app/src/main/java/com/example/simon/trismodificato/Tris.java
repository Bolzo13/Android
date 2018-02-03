package com.example.simon.trismodificato;

/**
 * Created by simon on 03/02/2018.
 */

import android.content.Intent;
import android.view.View;

public class Tris {

    private static char[][] bottone;
    private static boolean vittoria;

    public static void set(View view,char premuto){
        switch (view.getId()) {
            case R.id.uno: bottone[0][0] = premuto; break;
            case R.id.due: bottone[0][1] = premuto; break;
            case R.id.tre: bottone[0][2] = premuto; break;
            case R.id.quattro: bottone[1][0] = premuto; break;
            case R.id.cinque: bottone[1][1] = premuto; break;
            case R.id.sei: bottone[1][2] = premuto; break;
            case R.id.sette: bottone[2][0] = premuto; break;
            case R.id.otto: bottone[2][1] = premuto; break;
            case R.id.nove: bottone[2][2] = premuto; break;
        }

    }

    public static boolean controlloVittoria(Intent i){

        vittoria =false;

        if(bottone[0][0]==bottone[0][1] && bottone[0][0]==bottone[0][2] && bottone[0][0]!=' ') { i.putExtra("winner",bottone[0][0]) ; vittoria =true; }
        if(bottone[1][0]==bottone[1][1] && bottone[1][0]==bottone[1][2] && bottone[1][0]!=' ') { i.putExtra("winner",bottone[1][0]) ; vittoria =true; }
        if(bottone[2][0]==bottone[2][1] && bottone[2][0]==bottone[2][2] && bottone[2][0]!=' ') { i.putExtra("winner",bottone[2][0]) ; vittoria =true; }

        if(bottone[0][0]==bottone[1][0] && bottone[0][0]==bottone[2][0] && bottone[0][0]!=' ') { i.putExtra("winner",bottone[0][0]) ; vittoria =true; }
        if(bottone[0][1]==bottone[1][1] && bottone[0][1]==bottone[2][1] && bottone[0][1]!=' ') { i.putExtra("winner",bottone[0][1]) ; vittoria =true; }
        if(bottone[0][2]==bottone[1][2] && bottone[0][2]==bottone[2][2] && bottone[0][2]!=' ') { i.putExtra("winner",bottone[0][2]) ; vittoria =true; }

        if(bottone[0][0]==bottone[1][1] && bottone[0][0]==bottone[2][2] && bottone[0][0]!=' ') { i.putExtra("winner",bottone[0][0]) ; vittoria =true; }
        if(bottone[0][2]==bottone[1][1] && bottone[0][2]==bottone[2][0] && bottone[0][2]!=' ') { i.putExtra("winner",bottone[0][2]) ; vittoria =true; }

        return vittoria;

    }

    public static void pareggio(Intent i){
        i.putExtra("winner",'n');
    }

    public static void clear(){
        bottone= new char[][] {
                {' ',' ',' '},
                {' ',' ',' '},
                {' ',' ',' '}
        };
        vittoria =false;
    }

}