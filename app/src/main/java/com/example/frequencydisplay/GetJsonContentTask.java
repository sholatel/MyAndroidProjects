package com.example.frequencydisplay;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ext.DefaultHandler2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class GetJsonContentTask  extends AsyncTask <Void,Void,String> {
    TextView timeStamp, currentFrequency,frequencyChange, percentageChange,openingFrequency,lowestFrequency, highestFrequency;
    ProgressBar spinner;
    public GetJsonContentTask (TextView timeStamp, TextView currentFrequency, TextView frequencyChange,  TextView percentageChange
            , TextView openingFrequency, TextView lowestFrequency, TextView highestFrequency,ProgressBar spinner) {
        this.timeStamp=timeStamp;
        this.currentFrequency=currentFrequency;
        this.frequencyChange=frequencyChange;
        this.percentageChange=percentageChange;
        this.openingFrequency=openingFrequency;
        this.lowestFrequency=lowestFrequency;
        this.highestFrequency=highestFrequency;
        this.spinner=spinner;

    }

    @Override
    protected String doInBackground(Void... voids) {
        String jsonString="";
        try {
            HttpsURLConnection connection=(HttpsURLConnection)new URL("https://api.tcngridinfo.com/api/Frequency/GetFrequency").openConnection();
            InputStream input=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(input));
            StringBuilder sb=new StringBuilder();
            String line=null;
            while ((line=reader.readLine()) !=null) {
                sb.append(line+"\n");
            }
            jsonString= sb.toString();
         } catch (Exception e) {
            Log.e(getClass().getSimpleName(),e.toString(),e);
        }
        return  jsonString;
    }

        @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(response);

                String timeStampString =jsonObject.getString("date");  //object to take unfoormated data and time
                timeStampString=timeStampString.replace('T',' '); //remove T in timestamp and change to space
                timeStampString=timeStampString.substring(0,timeStampString.indexOf('.'));//remove additional milliseconds after main seconds

                //set values in UI
                timeStamp.setText(timeStampString);
                currentFrequency.setText(jsonObject.getString("min"));
                frequencyChange.setText(jsonObject.getString("change")+"Hz");
                percentageChange.setText(jsonObject.getString("percent")+"%");
                openingFrequency.setText(jsonObject.getString("open")+"Hz");
                lowestFrequency.setText(jsonObject.getString("low")+"Hz");
                highestFrequency.setText(jsonObject.getString("high")+"Hz");
                spinner.setVisibility(View.INVISIBLE);//hide spinner


            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

}
