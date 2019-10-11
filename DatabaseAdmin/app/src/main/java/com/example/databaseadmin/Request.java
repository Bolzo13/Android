package com.example.databaseadmin;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Request implements Runnable{




    private String type,query;
    private String[] payload;
    private String url;
    public Handler handler;

    Request(String type, String[] payload, String url, Handler handler){
        this.type=type;
        this.payload=payload;
        this.url=url;
        this.handler=handler;
    }

    Request(String type, String url,Handler handler){
        this.type=type;
        this.url=url;
        this.handler=handler;
    }

    Request(String type,String query, String url,Handler handler){
        this.type=type;
        this.url=url;
        this.query=query;
        this.handler=handler;
    }


    public int POSTRequest() throws IOException,JSONException {
        URL url =new URL(this.url);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        JSONObject jsonObject = buidJsonObject();
        setPostRequestContent(conn, jsonObject);
        conn.connect();
        return conn.getResponseCode();
    }

    public int GETRequest() throws IOException, JSONException {
        if(!(url.contains("getOrder")||url.contains("getHistoryOrder"))) {
            ArrayList<String> pizze;
            ArrayList<String> prezzi;
            ArrayList<String[]> ingredienti;
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println(content);
            String[] object = content.toString().substring(1, content.length() - 1).split("\\},\\{");
            object[0] += "}";
            pizze = new ArrayList<>(object.length);
            prezzi = new ArrayList<>(object.length);
            ingredienti = new ArrayList<>(object.length);
            pizze.add(new JSONObject(object[0]).get("name").toString());
            prezzi.add(new JSONObject(object[0]).get("prezzo").toString());
            ingredienti.add(new JSONObject(object[0]).get("ingredienti").toString().replace("[", "").replace("]", "").split(","));
            for (int i = 1; i < object.length; i++) {
                object[i] = "{" + object[i] + "}";
                System.out.println(object[i]);
                JSONObject jsonObject = new JSONObject(object[i]);
                pizze.add(jsonObject.get("name").toString());
                prezzi.add(jsonObject.get("prezzo").toString());
                String[] array = jsonObject.get("ingredienti").toString().replace("[", "").replace("]", "").split(",");
                ingredienti.add(array);
            }
            object[object.length - 1] = "{" + object[object.length - 1];
            bundle.putStringArrayList("pizze", pizze);
            bundle.putStringArrayList("prezzi", prezzi);
            bundle.putSerializable("ingredienti", ingredienti);
            msg.what = 2;
            msg.setData(bundle);
            handler.sendMessage(msg);
            in.close();
            con.disconnect();
            return con.getResponseCode();
        }else {
            ArrayList<String> username;
            ArrayList<String> prezzi;
            ArrayList<String> data;
            ArrayList<String[]> ordine;
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println(content);
            String[] object = content.toString().substring(1, content.length() - 1).split("\\},\\{");
            object[0] += "}";
            ordine = new ArrayList<>(object.length);
            prezzi = new ArrayList<>(object.length);
            username = new ArrayList<>(object.length);
            data = new ArrayList<>(object.length);
            username.add(new JSONObject(object[0]).get("username").toString());
            prezzi.add(new JSONObject(object[0]).get("totale").toString());
            ordine.add(new JSONObject(object[0]).get("ordine").toString().replace("[", "").replace("]", "").split(","));
            data.add(new JSONObject(object[0]).get("data").toString());
            System.out.println(object[0]);
            for (int i = 1; i < object.length; i++) {
                object[i] = "{" + object[i] + "}";
                System.out.println(object[i]);
                JSONObject jsonObject = new JSONObject(object[i]);
                username.add(jsonObject.get("username").toString());
                prezzi.add(jsonObject.get("totale").toString());
                String[] array = jsonObject.get("ordine").toString().replace("[", "").replace("]", "").split(",");
                data.add(jsonObject.get("data").toString());
                ordine.add(array);
            }

            object[object.length - 1] = object[object.length - 1].substring(0,object[object.length-1].length()-1);System.out.println(object[object.length-1]);
            bundle.putStringArrayList("username", username);
            bundle.putStringArrayList("prezzi", prezzi);
            bundle.putSerializable("ordini", ordine);
            bundle.putStringArrayList("date", data);
            msg.what = 1;
            msg.setData(bundle);
            handler.sendMessage(msg);
            in.close();
            con.disconnect();
            return con.getResponseCode();
        }
    }

    public int DELETERequest() throws IOException {
        /*URL url =new URL(this.url);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.header("Content-Type", "application/json");
        conn.setRequestProperty("name",query);
        conn.connect();
        System.out.println(conn.getResponseMessage());
        return conn.getResponseCode();*/
        DefaultHttpClient client=new DefaultHttpClient();
        HttpDelete delete=new HttpDelete(url);
        HttpResponse response=client.execute(delete);
        System.out.println(response.getStatusLine());
        return 200;
    }



    private JSONObject buidJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", payload[0]);
        jsonObject.put("ingredienti", new JSONArray(payload[1]));
        jsonObject.put("prezzo", payload[2]);
        System.out.println(jsonObject.get("ingredienti"));
        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,JSONObject jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        System.out.println(MainActivity.class.toString()+" "+ jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

    @Override
    public void run() {

        switch (type){
            case "POST":
                try {
                    if(POSTRequest()==200){
                        Message msg=handler.obtainMessage();
                        Bundle bundle=new Bundle();
                        System.out.println("Good");
                        bundle.putString("success","Aggiunta pizza");
                        msg.what=1;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }else {
                        System.err.println("Retry");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "GET":
                try {
                    if(GETRequest()==200){
                        System.out.println("Good");
                    }else {
                        System.err.println("Retry");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "DELETE":
                try {
                    if(DELETERequest()==200){
                        System.out.println("Good");
                    }else {
                        System.err.println("Retry");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }

    }
}

