package com.example.testsheet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class AddItem extends AppCompatActivity {

    EditText etName, etTelephone, etEmail;
    Button btnSend;
    ProgressDialog progressDialog;
    final String urlScript = "https://script.google.com/home/projects/1tKqYBnPLn4ODVOPM9Gv7ILaWp4ot3N7VPhcEGBY0LGyQgMFQ8fk5bU-D/edit";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        etName = (EditText) findViewById(R.id.editTextTextPersonName);
        etTelephone = (EditText) findViewById(R.id.editTextTextPersonTelephone);
        etEmail = (EditText) findViewById(R.id.editTextTextPersonEmail);
        btnSend = (Button) findViewById(R.id.buttonSend);
        progressDialog = new ProgressDialog(AddItem.this);
        progressDialog.setMessage("Loading ...");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
                progressDialog.show();
            }
        });
    }

    public void addStudent() {
        String sName = etName.getText().toString();
        String sTelephone = etTelephone.getText().toString();
        String sEmail = etEmail.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "Add Your Web App URL",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Toast.makeText(AddItem.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action", "addItem");
                parmas.put("name", sName);
                parmas.put("telephone", sTelephone);
                parmas.put("email", sEmail);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);

    }
}