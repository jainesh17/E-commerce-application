package com.example.orignalinternship;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class categoryadepter extends RecyclerView.Adapter<categoryadepter.MyHolder> {

    Context context;
    ArrayList<categorylist>arrayList;
    SharedPreferences sp;


    public categoryadepter( Context homeactivity , ArrayList<categorylist> arrayList) {
        this.context = homeactivity;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(constantsp.pref,Context.MODE_PRIVATE);
    }


  /*  public categoryadepter(Context homeactivity , int[] categoryarray, String[] categoryname) {

        context = homeactivity;
        this.categoryarray = categoryarray;
        this.categoryname = categoryname;
    }*/

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_category_image);
            name = itemView.findViewById(R.id.custom_category_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(arrayList.get(position).getImage()).placeholder(R.drawable.loading_new).into(holder.imageView);
        holder.name.setText(arrayList.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            sp.edit().putString(constantsp.CATEGORY_ID,arrayList.get(position).getId()).commit();
            new Commonmethod(context, productActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
