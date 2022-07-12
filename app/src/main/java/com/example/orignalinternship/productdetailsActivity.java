package com.example.orignalinternship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class productdetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name,price,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        imageView = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_Description);

        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setTitle(bundle.getString("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageView.setImageResource(bundle.getInt("image"));
        name.setText(bundle.getString("name"));
        price.setText(bundle.getString("price"));
        description.setText(bundle.getString("desc"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}