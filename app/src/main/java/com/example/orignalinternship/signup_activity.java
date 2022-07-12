package com.example.orignalinternship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class signup_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name,email,contect,password,confirmpassword,dateofbirth;
    Button signup,login;
    Calendar calender;
    RadioButton male,female,other;
    RadioGroup radioGroup;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String sgender, scity;

    Spinner spinner;
           String [] city = {"Ahmedabad","Rajkot","Surendranagar","vadodra","Surat","Botad","Gandhinagar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        male = findViewById(R.id.signup_male);
        female = findViewById(R.id.signup_female);
        other = findViewById(R.id.signup_Other);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contect = findViewById(R.id.signup_mobile_number);
        password = findViewById(R.id.signup_password);
        confirmpassword = findViewById(R.id.signup_confirm_password);
        signup = findViewById(R.id.signup_Signup);
        login = findViewById(R.id.signup_login);
       dateofbirth = findViewById(R.id.signup_Date_of_birth);
       calender = Calendar.getInstance();
      spinner = findViewById(R.id.spinner);
      ArrayAdapter adapter = new ArrayAdapter(signup_activity.this, android.R.layout.simple_list_item_1,city);
      adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
      spinner.setAdapter(adapter);

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              scity =city[i];
              new Commonmethod(signup_activity.this,scity);
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });


        radioGroup = findViewById(R.id.signup_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radiobutton = findViewById(i);
                sgender = radiobutton.getText().toString();
                new Commonmethod(signup_activity.this,sgender);
            }
        });

       DatePickerDialog.OnDateSetListener dataclick = new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
               calender.set(Calendar.YEAR,i);
               calender.set(Calendar.MONTH,i1);
               calender.set(Calendar.DAY_OF_MONTH,i2);

               SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
               dateofbirth.setText(dateFormat.format(calender.getTime()));

           }
       };

       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (name.getText().toString().trim().equalsIgnoreCase("")){
                   name.setError("name Required");
               }
               else  if (email.getText().toString().trim().equalsIgnoreCase("")){
                   email.setError("Email Id Required");
               }
               else if (!email.getText().toString().matches(emailPattern)){
                   email.setError("please valid email id");
               }
               else  if (password.getText().toString().trim().equalsIgnoreCase("")){
                   password.setError("password Required");
               }
               else  if (password.getText().toString().trim().length()<8){
                   password.setError("please enter the minimum 8 digit");
               }
               else  if (confirmpassword.getText().toString().trim().equalsIgnoreCase("")){
                   confirmpassword.setError("please confirm your paasword");
               }
               else if(!password.getText().toString().matches(confirmpassword.getText().toString())){
                   confirmpassword.setError("password does not match");
               }
               else  if (contect.getText().toString().trim().equalsIgnoreCase("")){
                   contect.setError("Mobile number Required");
               }
               else  if (contect.getText().toString().trim().length()<10){
                   contect.setError("enter your valid Mobile number");
               }
               else  if (dateofbirth.getText().toString().trim().equalsIgnoreCase("")){
                   dateofbirth.setError("Date Of Birth Required");
               }
               else if(radioGroup.getCheckedRadioButtonId()==-1){
                   new Commonmethod(signup_activity.this,"please select your gender");
               }

               else {

                   if (new ConnectionDetector(signup_activity.this).isConnectingToInternet()){
                      new dosignup().execute();
                   }
                   else {
                       new ConnectionDetector(signup_activity.this).connectiondetect();
                   }
               }
           }
       });

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });

       dateofbirth.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             DatePickerDialog dialog = new DatePickerDialog(signup_activity.this,dataclick,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.MONTH));
             dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
             dialog.show();
           }
       });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class dosignup extends AsyncTask<String,String,String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(signup_activity.this);
            pd.setMessage("please wait....");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String>hashMap = new HashMap<>();
            hashMap.put("name",name.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("contact",contect.getText().toString());
            hashMap.put("password",password.getText().toString());
            hashMap.put("dob",dateofbirth.getText().toString());
            hashMap.put("city",scity);
            hashMap.put("gender",sgender);
            return new MakeServiceCall().MakeServiceCall(constanturl.SIGNUP_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("Status")){

                    new Commonmethod(signup_activity.this,object.getString("Message"));
                    new Commonmethod(signup_activity.this,"successfully created");
                   onBackPressed();
                }
                else{
                    new Commonmethod(signup_activity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new Commonmethod(signup_activity.this,e.getMessage());
            }
        }
    }



}