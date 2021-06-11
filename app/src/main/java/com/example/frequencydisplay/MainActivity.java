package com.example.frequencydisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AbstractPermissionActivity {

    TextView date, currentFrequecy, lowFrequency, startTime;
    GetJsonContentTask getJsonContentTask;

    @Override
    public void onPermissionDenied() {
        Toast.makeText(this,"Permission problem",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReady(Bundle state) {
        
        




        setContentView(R.layout.activity_main);
        date = findViewById(R.id.date);
        currentFrequecy = findViewById(R.id.currentFreq);
        lowFrequency = findViewById(R.id.lowestFreq);
        startTime = findViewById(R.id.startTime);
    }

    @Override
    public String[] getPermission() {
        return new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE};
    }





   @Override
    protected void onStart() {
        super.onStart();
        getJsonContentTask = new GetJsonContentTask(date, currentFrequecy, lowFrequency, startTime);
        getJsonContentTask.execute();
    }




    @Override
    protected void onStop() {
        getJsonContentTask.cancel(true);
        super.onStop();
    }



}