package com.example.orignalinternship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class productActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<productlist>arrayList;

    String [] productnamearray = {"iphone7","iphone11","iphone13","ihonexsmax"};
    int [] productimagearray = {R.drawable.iphone7,R.drawable.iphone11,R.drawable.iphone13,R.drawable.iphonexsmax};
    String [] productpricearray = {"15000","50000","95000","29000"};
    String [] productitemarray = {"16GB","128GB","256GB","64GB"};
    String [] productdecripssion = {"MEMORY\tCard slot\tNo\n" +
            "Internal\t32GB 2GB RAM, 128GB 2GB RAM, 256GB 2GB RAM\n" +
            " \tNVMe\n" +
            "MAIN CAMERA\tSingle\t12 MP, f/1.8, 28mm (wide), 1/3\", PDAF, OIS\n" +
            "Features\tQuad-LED dual-tone flash, HDR\n" +
            "Video\t4K@30fps, 1080p@30/60/120fps, 720p@240fps\n" +
            "SELFIE CAMERA\tSingle\t7 MP, f/2.2, 32mm (standard)\n" +
            "Features\tFace detection, HDR, panorama\n" +
            "Video\t1080p@30fps"
            ,"MEMORY\tCard slot\tNo\n" +
            "Internal\t64GB 4GB RAM, 128GB 4GB RAM, 256GB 4GB RAM\n" +
            " \tNVMe\n" +
            "MAIN CAMERA\tDual\t12 MP, f/1.8, 26mm (wide), 1/2.55\", 1.4µm, dual pixel PDAF, OIS\n" +
            "12 MP, f/2.4, 120˚, 13mm (ultrawide), 1/3.6\"\n" +
            "Features\tDual-LED dual-tone flash, HDR (photo/panorama)\n" +
            "Video\t4K@24/30/60fps, 1080p@30/60/120/240fps, HDR, stereo sound rec.\n" +
            "SELFIE CAMERA\tDual\t12 MP, f/2.2, 23mm (wide), 1/3.6\"\n" +
            "SL 3D, (depth/biometrics sensor)\n" +
            "Features\tHDR\n" +
            "Video\t4K@24/30/60fps, 1080p@30/60/120fps, gyro-EIS",
            "MEMORY\tCard slot\tNo\n" +
                    "Internal\t128GB 4GB RAM, 256GB 4GB RAM, 512GB 4GB RAM\n" +
                    " \tNVMe\n" +
                    "MAIN CAMERA\tDual\t12 MP, f/1.6, 26mm (wide), 1.7µm, dual pixel PDAF, sensor-shift OIS\n" +
                    "12 MP, f/2.4, 120˚, 13mm (ultrawide)\n" +
                    "Features\tDual-LED dual-tone flash, HDR (photo/panorama)\n" +
                    "Video\t4K@24/30/60fps, 1080p@30/60/120/240fps, HDR, Dolby Vision HDR (up to 60fps), stereo sound rec.\n" +
                    "SELFIE CAMERA\tDual\t12 MP, f/2.2, 23mm (wide), 1/3.6\"\n" +
                    "SL 3D, (depth/biometrics sensor)\n" +
                    "Features\tHDR\n" +
                    "Video\t4K@24/25/30/60fps, 1080p@30/60/120fps, gyro-EIS",
            "MEMORY\tCard slot\tNo\n" +
                    "Internal\t64GB 4GB RAM, 256GB 4GB RAM, 512GB 4GB RAM\n" +
                    " \tNVMe\n" +
                    "MAIN CAMERA\tDual\t12 MP, f/1.8, 26mm (wide), 1/2.55\", 1.4µm, dual pixel PDAF, OIS\n" +
                    "12 MP, f/2.4, 52mm (telephoto), 1/3.4\", 1.0µm, PDAF, OIS, 2x optical zoom\n" +
                    "Features\tQuad-LED dual-tone flash, HDR (photo/panorama)\n" +
                    "Video\t4K@24/30/60fps, 1080p@30/60/120/240fps, HDR, stereo sound rec.\n" +
                    "SELFIE CAMERA\tDual\t7 MP, f/2.2, 32mm (standard)\n" +
                    "SL 3D, (depth/biometrics sensor)\n" +
                    "Features\tHDR\n" +
                    "Video\t1080p@30/60fps, gyro-EIS"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setTitle("product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.product_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(productActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();
        for (int i=0; i<productnamearray.length; i++){
            productlist list = new productlist();
            list.setName(productnamearray[i]);
            list.setImage(productimagearray[i]);
            list.setItem(productitemarray[i]);
            list.setPrice(productpricearray[i]);
            list.setDescription(productdecripssion[i]);
            arrayList.add(list);

        }

        productllistadepter adepter = new productllistadepter(productActivity.this,arrayList);
        recyclerView.setAdapter(adepter);
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