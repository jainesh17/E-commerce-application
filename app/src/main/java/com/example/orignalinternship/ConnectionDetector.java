package com.example.orignalinternship;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.Window;
import android.widget.Button;

public class ConnectionDetector {

    private final Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
             NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo networkInfo : info)
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public boolean connectiondetect() {

        if (isConnectingToInternet()) {
            return true;
        } else {
            final Dialog dialog = new Dialog(_context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.connection_checker);
            Button retry = dialog.findViewById(R.id.dialog_ok);
            Button connect = dialog.findViewById(R.id.dialog_cancel);
            dialog.show();
            dialog.setCancelable(false);

            retry.setOnClickListener(view -> {
                dialog.dismiss();
                isConnectingToInternet();
            });
            connect.setOnClickListener(view -> {
                dialog.dismiss();
                _context.startActivity(new Intent(Settings.ACTION_SETTINGS));

            });
            return false;
        }

    }
}