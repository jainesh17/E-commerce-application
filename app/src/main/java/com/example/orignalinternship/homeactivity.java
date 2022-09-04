package com.example.orignalinternship;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class homeactivity extends AppCompatActivity {

    SharedPreferences sp;
    RecyclerView recyclerView, productrecycleview;

    ArrayList<categorylist> arrayList;
    ArrayList<productlist> productarraylist;

    TextView title;
    TextView viewall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.home_title);
        sp = getSharedPreferences(constantsp.pref, MODE_PRIVATE);

        title.setText("welcome to\n" + sp.getString(constantsp.EMAIL, ""));

        viewall = findViewById(R.id.home_view_all);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(constantsp.CATEGORY_ID, "").commit();
                new Commonmethod(homeactivity.this, productActivity.class);
            }
        });


        recyclerView = findViewById(R.id.home_Category_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


//        arrayList = new ArrayList<>();
//        for (int i=0; i<categoryname.length; i++){
//            categorylist list = new categorylist();
//            list.setName(categoryname[i]);
//            list.setImage(categoryarray[i]);
//            arrayList.add(list);
//
//        }
//
//        categoryadepter adepter = new categoryadepter(homeactivity.this,arrayList);
//        recyclerView.setAdapter(adepter);


        productrecycleview = findViewById(R.id.home_product_recyclerview);
        productrecycleview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        productrecycleview.setItemAnimator(new DefaultItemAnimator());
        productrecycleview.setNestedScrollingEnabled(false);

//        productarraylist = new ArrayList<>();
//        for (int i=0; i<productnamearray.length; i++){
//            productlist list = new productlist();
//            list.setName(productnamearray[i]);
//            list.setImage(productimagearray[i]);
//            list.setItem(productitemarray[i]);
//            list.setPrice(productpricearray[i]);
//            list.setDescription(productdecripssion[i]);
//            productarraylist.add(list);
//
//        }
//             Productadepter productadepter = new Productadepter(homeactivity.this,productarraylist);
//              productrecycleview.setAdapter(productadepter);

        if (new ConnectionDetector(homeactivity.this).isConnectingToInternet()) {
            new getcategorydata().execute();
            new getproductdata().execute();
        } else {
            new ConnectionDetector(homeactivity.this).connectiondetect();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        if (id == R.id.home_menu_logout) {
            sp.edit().clear().commit();
            new Commonmethod(homeactivity.this, MainActivity.class);
        }
        if (id == R.id.home_menu_chat) {
            new Commonmethod(homeactivity.this, chatActivity.class);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        finishAffinity();
        alertmethod();
    }

    private void alertmethod() {
        AlertDialog.Builder builder = new AlertDialog.Builder(homeactivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Exit Alert!");
        builder.setMessage("Are You Sure Want To Exit");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNeutralButton("Rate us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                new Commonmethod(homeactivity.this, "Rate Us");
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private class getproductdata extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(homeactivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return new MakeServiceCall().MakeServiceCall(constanturl.PRODUCT_ALL_URL, MakeServiceCall.GET, new HashMap<>());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("status") == true) {
                    JSONArray jsonArray = object.getJSONArray("productdata");
                    productarraylist = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        productlist list = new productlist();
                        list.setName(jsonObject.getString("name"));
                        list.setPrice(jsonObject.getString("price"));
                        // list.setUnit(jsonObject.getString("unit"));
                        list.setDescription(jsonObject.getString("description"));
                        list.setImage(jsonObject.getString("image"));
                        list.setUrl(jsonObject.getString("url"));
                        productarraylist.add(list);
                    }
                    Productadepter productAdapter = new Productadepter(homeactivity.this, productarraylist);
                    productrecycleview.setAdapter(productAdapter);
                } else {
                    new Commonmethod(homeactivity.this, object.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new Commonmethod(homeactivity.this, e.getMessage());
            }
        }
    }

    private class getcategorydata extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(homeactivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return new MakeServiceCall().MakeServiceCall(constanturl.CATEGORY_URL, MakeServiceCall.GET, new HashMap<>());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("status") == true) {
                    JSONArray jsonArray = object.getJSONArray("categorydata");
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        categorylist list = new categorylist();
                        list.setId(jsonObject.getString("id"));
                        list.setName(jsonObject.getString("name"));
                        list.setImage(jsonObject.getString("image"));
                        arrayList.add(list);
                    }
                    categoryadepter adapter = new categoryadepter(homeactivity.this, arrayList);
                    recyclerView.setAdapter(adapter);
                } else {
                    new Commonmethod(homeactivity.this, object.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new Commonmethod(homeactivity.this, e.getMessage());
            }
        }
    }
}

