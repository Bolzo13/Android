package com.bolzonella.simone.pizzeriagennarino;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

public class Paypal extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;

    final String API_GET_TOKEN = "http://simonebolzonella.ddns.net/braintree/main.php";
    final String API_CHECK_OUT= "http://simonebolzonella.ddns.net/braintree/checkout.php";

    String token,amount;
    HashMap<String,String> paramsHash;

    Button button_pay;
    EditText edit_amount;
    LinearLayout group_waiting,group_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypal);

        Bundle bundle=getIntent().getExtras();
        double scontrino=bundle.getDouble("Totale");


        group_payment = findViewById(R.id.payment_group);
        group_waiting = findViewById(R.id.waiting_group);

        button_pay = findViewById(R.id.button_pay);
        edit_amount = findViewById(R.id.edit_amount);
        System.out.println(scontrino);
        edit_amount.setText(Integer.toString((int) scontrino));
        new getToken().execute();

        button_pay.setOnClickListener((View v)->{
            submitPayment();
        });

    }

    private void submitPayment() {
        DropInRequest dropInRequest =new DropInRequest().clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this),REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                if (!edit_amount.getText().toString().isEmpty()){
                    amount=edit_amount.getText().toString();
                    paramsHash=new HashMap<>();
                    paramsHash.put("amount",amount);
                    paramsHash.put("nonce",strNonce);

                    sendPayment();
                }

                else{
                    Toast.makeText(this,"Inserire valore valido!!", Toast.LENGTH_SHORT).show();;
                }
            }
            else if (requestCode == RESULT_CANCELED)
                Toast.makeText(this, "Annullato", Toast.LENGTH_SHORT).show();
            else {
                try {
                    Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                    Log.d("EDMT_ERROR", error.toString());
                }catch (NullPointerException e){
                    System.err.println("ERROR STRING!!!");
                }
            }
        }
    }


    private void sendPayment() {
        RequestQueue queue = Volley.newRequestQueue(Paypal.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Successful")) {
                            Toast.makeText(Paypal.this, "Transazione eseguita con successo!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(Paypal.this,"Transazione fallita!",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("EDMT_LOG",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("EDMT_ERROR",error.toString());
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramsHash == null){
                    return null;
                }
                else{
                    Map<String,String> params=new HashMap<>();
                    for (String key:paramsHash.keySet()){
                        params.put(key,paramsHash.get(key));
                    }
                    return params;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded" );
                return  params;
            }
        };

        queue.add(stringRequest);


    }

    public class getToken extends AsyncTask{

        ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(Paypal.this, android.R.style.Theme_DeviceDefault_Dialog);
            nDialog.setCancelable(false);
            nDialog.setMessage("Please Wait");
            nDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            group_waiting.setVisibility(View.GONE);

                            group_payment.setVisibility(View.VISIBLE);

                            token= responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Log.d("ERROR TO GET TOKEN",exception.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            nDialog.dismiss();
        }
    }
}
