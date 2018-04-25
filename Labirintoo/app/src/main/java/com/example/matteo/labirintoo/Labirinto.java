package com.example.matteo.labirintoo;

public class Labirinto {
    public static int x,y;

    public static void destra(){
        if(haDestra()){
            y++;
        }
    }

    public static void sinistra(){
        if(haSinistra()){
            y--;
        }
    }

    public static void sotto(){
        if(haSotto()){
            x++;
        }
    }

    public static void sopra(){
        if(haSopra()){
            x--;
        }
    }

    public static boolean haDestra(){
        if((y+1<=2)||(Labirinto.x==2&&Labirinto.y==2)) return true;
        else return false;
    }

    public static boolean haSinistra(){
        if(y-1>=0) return true;
        else return false;
    }

    public static boolean haSotto(){
        if(x+1<=2) return true;
        else return false;
    }

    public static boolean haSopra(){
        if(x-1>=0) return true;
        else return false;
    }

    public static boolean checkVictory(){
        if (x==2 && y==3) return true;
        else return false;
    }

}
