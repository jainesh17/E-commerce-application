package com.example.orignalinternship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class categoryadepter extends RecyclerView.Adapter<categoryadepter.MyHolder> {

    Context context;
    ArrayList<categorylist>arrayList;


    public categoryadepter( Context homeactivity , ArrayList<categorylist> arrayList) {
        this.context = homeactivity;
        this.arrayList = arrayList;
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
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
    holder.imageView.setImageResource(arrayList.get(position).getImage());
    holder.name.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
