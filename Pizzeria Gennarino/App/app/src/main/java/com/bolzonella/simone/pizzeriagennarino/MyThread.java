package com.bolzonella.simone.pizzeriagennarino;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MyThread implements Runnable {

    String[] pizze;
    String[] prezziString;
    int[] ordini=null;
    double[] prezzi;
    static int nPizze;

    private String address="simonebolzonella.ddns.net";
    Handler myHandler;

    public MyThread(Handler handler) {
        this.myHandler=handler;
    }


    @Override
    public void run() {
        try {
            System.out.println(ordini==null);
            if(ordini==null){
                Socket socket=new Socket(address,56780);
                BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output=new PrintWriter(socket.getOutputStream(),true);
                String inputLine = input.readLine();
                if(inputLine.contains("Benvenuto")) {
                    output.println("OK");
                    pizze = input.readLine().split(",");
                    pizze[0] = pizze[0].substring(1, pizze[0].length());
                    pizze[pizze.length - 1] = pizze[pizze.length - 1].substring(0, pizze[0].length() - 1);
                    output.println("OK");
                    inputLine = input.readLine();
                    prezziString = inputLine.split(",");
                    prezziString[0] = prezziString[0].substring(1, prezziString[0].length());
                    prezziString[prezziString.length - 1] = prezziString[prezziString.length - 1].substring(0, prezziString[prezziString.length - 1].length() - 1);
                    prezzi = new double[prezziString.length];
                    for (int i = 0; i < prezziString.length; i++) {
                        prezzi[i] = Double.parseDouble(prezziString[i]);
                    }
                    Message msg=myHandler.obtainMessage();
                    Bundle bundle=new Bundle();
                    bundle.putStringArray("Pizze",pizze);
                    bundle.putDoubleArray("Prezzi",prezzi);
                    bundle.putString("connesso","Benvenuto alla Pizzeria da Gennarino");
                    msg.what=1;
                    msg.setData(bundle);
                    myHandler.sendMessage(msg);
                }
                socket.close();
            }else{
                Socket socketReturn=new Socket(address,56780);
                BufferedReader inputReturn=new BufferedReader(new InputStreamReader(socketReturn.getInputStream()));
                PrintWriter outputReturn=new PrintWriter(socketReturn.getOutputStream(),true);
                System.out.println(inputReturn.readLine());
                outputReturn.println("ORDINE");
                System.out.println(inputReturn.readLine());
                outputReturn.println(nPizze);
                System.out.println(nPizze);
                if(nPizze>0) {
                    for (int i = 0; i < ordini.length; i++) {
                        System.out.println(inputReturn.readLine());
                        outputReturn.println(ordini[i]);
                        System.out.println(ordini[i]);
                    }
                }
                String scontrino=inputReturn.readLine();
                System.out.println(scontrino);
                Message msg = myHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("result",scontrino);
                msg.what=2;
                msg.setData(bundle);
                myHandler.sendMessage(msg);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOrdine(int totPizze,int[] ordine){
        nPizze=totPizze;
        this.ordini=ordine;

    }
}