package com.example.frequencydisplay;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

public class GetJsonContentTask  extends AsyncTask <Void,Void,ArrayList> {
    TextView date,  currentFrequecy,  lowFrequency,  startTime;

    public GetJsonContentTask (TextView date, TextView currentFrequecy, TextView lowFrequency, TextView startTime) {
        this.date=date;
        this.currentFrequecy=currentFrequecy;
        this.lowFrequency=lowFrequency;
        this.startTime=startTime;

    }

    @Override
    protected ArrayList doInBackground(Void... voids) {
        ArrayList <String>jsonContentArray=new <String >ArrayList();
        String jsonString;
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
            JSONObject jsonObject=new JSONObject(jsonString);
            jsonContentArray.add(0,jsonObject.getString("date"));
            jsonContentArray.add(1,String.valueOf(jsonObject.getDouble("open")));
            jsonContentArray.add(2,String.valueOf(jsonObject.getDouble("low")));
            jsonContentArray.add(3,jsonObject.getString("creationTime"));
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(),e.toString(),e);
        }
        return  jsonContentArray;
    }

        @Override
    protected void onPostExecute(ArrayList list) {
        super.onPostExecute(list);
        date.setText( list.get(0).toString());
        currentFrequecy.setText(list.get(1).toString());
        lowFrequency.setText(list.get(2).toString());
        startTime.setText(list.get(3).toString());
    }

}
