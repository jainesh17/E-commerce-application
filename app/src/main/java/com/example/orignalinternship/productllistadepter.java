package com.example.orignalinternship;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productllistadepter  extends RecyclerView.Adapter<productllistadepter.Myholder> {

    Context context;
    ArrayList<productlist> arrayList;


    public productllistadepter(Context homeactivity , ArrayList<productlist> productarraylist) {
        this.context =homeactivity;
        this.arrayList = productarraylist;
    }

    @NonNull
    @Override
    public productllistadepter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_list,parent,false);
        return new productllistadepter.Myholder(view);
    }


    public class Myholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_product_list_image);
            name = itemView.findViewById(R.id.custom_product_list_name);
            price = itemView.findViewById(R.id.custom_product_list_price);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull productllistadepter.Myholder holder, @SuppressLint("RecyclerView") int position) {
    //    holder.imageView.setImageResource(arrayList.get(position).getImage());
        Picasso.get().load(arrayList.get(position).getImage()).placeholder(R.drawable.loading_new).into(holder.imageView);
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(context.getResources().getString(R.string.price_symbol)+arrayList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context,productdetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("name",arrayList.get(position).getName());
                bundle.putString("price",context.getResources().getString(R.string.price_symbol)+arrayList.get(position).getPrice()+"/" + arrayList.get(position).getItem());
                bundle.putString("desc",arrayList.get(position).getDescription());
                bundle.putString("image",arrayList.get(position).getImage());
                intent.putExtras(bundle);

                context.startActivity(intent);*/

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(arrayList.get(position).getUrl()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
