package com.example.myapplication.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.myapplication.R;
import com.example.myapplication.model.MovieModel;
import com.example.myapplication.response.MovieSearchResponse;
import com.example.myapplication.util.Connection;
import com.example.myapplication.util.Essentials;
import com.example.myapplication.viewmodels.MovieViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class InternetReceiver extends BroadcastReceiver {

    MovieViewModel viewModel;

    public InternetReceiver(MovieViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Connection.isConnectedToInternet(context)) {
            //there is no internet connection

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
            builder.setView(view);
            MaterialButton buttonTryAgain = view.findViewById(R.id.buttonTryAgain);

            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
            alertDialog.show();
            buttonTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    onReceive(context, intent);
                    viewModel.getMovies(Essentials.API_KEY,1).observe((LifecycleOwner) context, new Observer<MovieSearchResponse>() {
                        @Override
                        public void onChanged(MovieSearchResponse movieSearchResponse) {


                        }

                    });
                    viewModel.getTopRatedMovies(Essentials.API_KEY,1).observe((LifecycleOwner) context, new Observer<MovieSearchResponse>() {
                        @Override
                        public void onChanged(MovieSearchResponse movieSearchResponse) {

                        }
                    });
                }
            });

        }
    }
}
