package com.example.frequencydisplay;

import static android.os.SystemClock.sleep;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class MainActivity extends AbstractPermissionActivity {

    TextView timeStamp, currentFrequency,frequencyChange, percentageChange,openingFrequency,lowestFrequency, highestFrequency;
    GetJsonContentTask getJsonContentTask;
    ProgressBar spinner;
    Handler refreshHandler = new Handler();
    Runnable run = new Runnable() {
        @Override
        public void run() {
            spinner.setVisibility(View.VISIBLE);
            getJsonContentTask = new GetJsonContentTask(timeStamp, currentFrequency, frequencyChange,
                    percentageChange,openingFrequency, lowestFrequency,highestFrequency,spinner);
            getJsonContentTask.execute();
            refreshHandler.postDelayed(this, 15000);
        }
    };

    @Override
    public void onPermissionDenied() {
        Toast.makeText(this,"Permission problem",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReady(Bundle state) {
        setContentView(R.layout.activity_main);
        timeStamp = findViewById(R.id.timeStamp);
        currentFrequency = findViewById(R.id.currentFrequency);
        frequencyChange = findViewById(R.id.frequencyChange);
        percentageChange = findViewById(R.id.percentageChange);
        openingFrequency = findViewById(R.id.openingFrequency);
        lowestFrequency = findViewById(R.id.lowestFrequency);
        highestFrequency = findViewById(R.id.highestFrequency);
        spinner = findViewById(R.id.progressBar);
    }

    @Override
    public String[] getPermission() {
        return new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE};
    }


   @Override
    protected void onStart() {
        super.onStart();
       refreshHandler.postDelayed(run, 0);
    }



    @Override
    protected void onStop() {
        getJsonContentTask.cancel(true);
        super.onStop();
    }



}