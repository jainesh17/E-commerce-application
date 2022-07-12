package com.example.orignalinternship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email,password;
    SharedPreferences sp;
    TextView create_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        login = findViewById(R.id.main_button);
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);
        create_account = findViewById(R.id.create_account);
        sp = getSharedPreferences(constantsp.pref,MODE_PRIVATE);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent  Intent = new Intent(MainActivity.this,signup_activity.class);
//                startActivity(Intent);
                new Commonmethod(MainActivity.this,signup_activity.class);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().equals("")) {
                    email.setError("invalid user id");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("invalid password");

                } else {
                    if (new ConnectionDetector(MainActivity.this).isConnectingToInternet()){
                        new dologin().execute();
                    }
                    else {
                        new ConnectionDetector(MainActivity.this).connectiondetect();
                    }
                    /*if (email.getText().toString().equals("jainesh@gmail.com") && password.getText().toString().equalsIgnoreCase("jainesh@1708")) {
                      *//*  //  Toast.makeText(MainActivity.this, "Submited", Toast.LENGTH_SHORT).show();
                      //  Snackbar.make(view, "login successfully", Snackbar.LENGTH_SHORT).show();

                        new Commonmethod(MainActivity.this,"Login Successfully");

                        sp.edit().putString(constantsp.Email,email.getText().toString()).commit();
                        sp.edit().putString(constantsp.Password,password.getText().toString()).commit();

//                        Intent intent = new Intent(MainActivity.this,homeactivity.class);
//                        startActivity(intent);
                        new Commonmethod(MainActivity.this,homeactivity.class);*//*

                    }
                    else {

                        *//*new Commonmethod(MainActivity.this, "Login Unsuccessfully");
                        new Commonmethod(view, "Login Unsuccessfully");*//*
                    }*/

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private class dologin extends AsyncTask<String,String,String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("please wait....");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();

            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());

            return new MakeServiceCall().MakeServiceCall(constanturl.LOGIN_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("Status")){
                    new Commonmethod(MainActivity.this,object.getString("Message"));
                    JSONArray array = object.getJSONArray("UserData");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        sp.edit().putString(constantsp.ID, jsonObject.getString("id")).commit();
                        sp.edit().putString(constantsp.NAME, jsonObject.getString("name")).commit();
                        sp.edit().putString(constantsp.EMAIL, jsonObject.getString("email")).commit();
                        sp.edit().putString(constantsp.CONTACT, jsonObject.getString("contact")).commit();
                        sp.edit().putString(constantsp.PASSWORD, jsonObject.getString("password")).commit();
                        sp.edit().putString(constantsp.GENDER, jsonObject.getString("gender")).commit();
                        sp.edit().putString(constantsp.CITY, jsonObject.getString("city")).commit();
                    }
                    new Commonmethod(MainActivity.this,homeactivity.class);
                }
                else{
                    new Commonmethod(MainActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new Commonmethod(MainActivity.this,e.getMessage());
            }
        }

    }
}