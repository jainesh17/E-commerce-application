package com.example.orignalinternship;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Productadepter extends RecyclerView.Adapter<Productadepter.Myholder> {

    Context context;
    ArrayList<productlist> arrayList;


    public Productadepter(Context homeactivity , ArrayList<productlist> productarraylist) {
        this.context =homeactivity;
        this.arrayList = productarraylist;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
        return new Myholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(arrayList.get(position).getImage());
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(context.getResources().getString(R.string.price_symbol)+arrayList.get(position).getPrice()+"/" + arrayList.get(position).getItem());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,productdetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("name",arrayList.get(position).getName());
                bundle.putString("price",context.getResources().getString(R.string.price_symbol)+arrayList.get(position).getPrice()+"/" + arrayList.get(position).getItem());
                bundle.putString("desc",arrayList.get(position).getDescription());
                bundle.putInt("image",arrayList.get(position).getImage());
                intent.putExtras(bundle);

            context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price;

        public Myholder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.custom_product_image);
            name = itemView.findViewById(R.id.custom_product_name);
            price = itemView.findViewById(R.id.custom_product_price);
        }
    }
}