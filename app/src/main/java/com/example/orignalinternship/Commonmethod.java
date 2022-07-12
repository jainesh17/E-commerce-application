package com.example.orignalinternship;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Commonmethod {

    Commonmethod(Context context, String message){
      //  Toast.makeText(context, "login", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    Commonmethod(View view, String message){
      //  Snackbar.make(view, "login successfully", Snackbar.LENGTH_SHORT).show();
        Snackbar.make(view, message ,Snackbar.LENGTH_SHORT).show();
    }

    Commonmethod(Context context , Class<?> nextclass){
        Intent Intent = new Intent(context,nextclass);
        context.startActivity(Intent);

    }
}
