package com.example.orignalinternship;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class productActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<productlist>arrayList;


    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setTitle("product");
        sp = getSharedPreferences(constantsp.pref, Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.product_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(productActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        arrayList = new ArrayList<>();
//        for (int i=0; i<productnamearray.length; i++){
//            productlist list = new productlist();
//            list.setName(productnamearray[i]);
//            list.setImage(productimagearray[i]);
//            list.setItem(productitemarray[i]);
//            list.setPrice(productpricearray[i]);
//            list.setDescription(productdecripssion[i]);
//            arrayList.add(list);
//
//        }
//
//        productllistadepter adepter = new productllistadepter(productActivity.this,arrayList);
//        recyclerView.setAdapter(adepter);
//    }
        if (new ConnectionDetector(productActivity.this).isConnectingToInternet()) {
            new getProductData().execute();
        } else {
            new ConnectionDetector(productActivity.this).connectiondetect();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private class getProductData extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(productActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (sp.getString(constantsp.CATEGORY_ID, "").equalsIgnoreCase("")) {
                return new MakeServiceCall().MakeServiceCall(constanturl.PRODUCT_ALL_URL, MakeServiceCall.GET, new HashMap<>());
            }
            else{
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("categoryid",sp.getString(constantsp.CATEGORY_ID,""));
                return new MakeServiceCall().MakeServiceCall(constanturl.PRODUCT_URL, MakeServiceCall.POST, hashMap);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("status") == true) {
                    JSONArray jsonArray = object.getJSONArray("productdata");
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        productlist list = new productlist();
                        list.setName(jsonObject.getString("name"));
                        list.setPrice(jsonObject.getString("price"));
                      //  list.setUnit(jsonObject.getString("unit"));
                        list.setDescription(jsonObject.getString("description"));
                        list.setImage(jsonObject.getString("image"));
                        list.setUrl(jsonObject.getString("url"));
                        arrayList.add(list);
                    }
                    productllistadepter productAdapter = new productllistadepter(productActivity.this, arrayList);
                    recyclerView.setAdapter(productAdapter);
                } else {
                    new Commonmethod(productActivity.this, object.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new Commonmethod(productActivity.this, e.getMessage());
            }
        }
    }
}