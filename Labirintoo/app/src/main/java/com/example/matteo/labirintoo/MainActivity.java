package com.example.matteo.labirintoo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    Stanza f1;
    public Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // portrait mode
        if (findViewById(R.id.fragment) != null) {

            f1 = new Stanza();
            Labirinto.x=0;
            Labirinto.y=0;

            i=new Intent(this,Vittoria.class);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, f1);
            ft.commit();
        }
    }

    float x1,x2,y1,y2;
    final static float MIN_DISTANCE = 250.0f;
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2=event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1 && Labirinto.haSinistra()) goLeft();
                    // Right to left swipe action
                    if (x2 < x1 && Labirinto.haDestra()) goRight();
                }
                if(Math.abs(deltaY) > MIN_DISTANCE) {
                    //Up to down swipe action
                    if (y2 < y1 && Labirinto.haSotto()) goDown();
                    //Down to up swipe action
                    if (y2 > y1 && Labirinto.haSopra()) goUp();
                }
                if(Labirinto.checkVictory()) startActivity(i);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void goRight(){
        if(Labirinto.x==2&&Labirinto.y==2) {
            startActivity(i);
        }
        else{
            FragmentManager fm = getFragmentManager();
            Stanza nextFragment;
            Labirinto.destra();
            nextFragment = new Stanza();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.fade);
            ft.replace(R.id.fragment, nextFragment);
            ft.commit();
        }
    }

    public void goLeft(){
        FragmentManager fm = getFragmentManager();
        Stanza nextFragment;
        Labirinto.sinistra();
        nextFragment = new Stanza();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade);
        ft.replace(R.id.fragment, nextFragment);
        ft.commit();
    }

    public void goDown(){
        FragmentManager fm = getFragmentManager();
        Stanza nextFragment;
        Labirinto.sotto();
        nextFragment = new Stanza();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_top, R.animator.fade);
        ft.replace(R.id.fragment, nextFragment);
        ft.commit();
    }

    public void goUp(){
        FragmentManager fm = getFragmentManager();
        Stanza nextFragment;
        Labirinto.sopra();
        nextFragment = new Stanza();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_down, R.animator.fade);
        ft.replace(R.id.fragment, nextFragment);
        ft.commit();
    }
}
