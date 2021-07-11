package com.example.frequencydisplay;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class GetJsonContentTask  extends AsyncTask <Void,Void,String> {
    TextView timeStamp, currentFrequency,frequencyChange, percentageChange,openingFrequency,lowestFrequency, highestFrequency;
    ProgressBar spinner;
    Context context;
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
        //set context to null as default
        this.context=null;
    }

    public GetJsonContentTask (Context context) {
        this.context=context;
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


                if(context==null) {// it means it was called by the main activity
                    //format time
                    String timeStampString = jsonObject.getString("date");  //object to take unfoormated data and time
                    timeStampString = timeStampString.replace('T', ' '); //remove T in timestamp and change to space
                    timeStampString = timeStampString.substring(0, timeStampString.indexOf('.'));//remove additional milliseconds after main seconds
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH);
                    SimpleDateFormat sourceformatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss", Locale.ENGLISH);
                    sourceformatter.setTimeZone(TimeZone.getTimeZone("Africa/Accra"));
                    formatter.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
                    Date date = sourceformatter.parse(timeStampString);
                    String formattedDateString = formatter.format(date);

                    //set values in UI
                    timeStamp.setText(formattedDateString);
                    currentFrequency.setText(jsonObject.getString("min"));
                    frequencyChange.setText(jsonObject.getString("change") + "Hz");
                    percentageChange.setText(jsonObject.getString("percent") + "%");
                    openingFrequency.setText(jsonObject.getString("open") + "Hz");
                    lowestFrequency.setText(jsonObject.getString("low") + "Hz");
                    highestFrequency.setText(jsonObject.getString("high") + "Hz");
                    spinner.setVisibility(View.INVISIBLE);//hide spinner
                }
                else {
                    // Instruct the widget manager to update the widget
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.grid_widget);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    ComponentName widget = new ComponentName( context, GridWidget.class );
                    views.setTextViewText(R.id.appwidget_text, jsonObject.getString("min")+"Hz");
                    appWidgetManager.updateAppWidget(widget, views);

                    //check if frequency is lower than expected
                    GridDataClass dataClass = new GridDataClass();
                    double frequecy= Double.parseDouble(jsonObject.getString("min"));
                    double minimumAllowableFrequency = Double.parseDouble(dataClass.getData(context,"minimumAllowableFrequency"));
                    double maximumAllowableFrequencyEdit = Double.parseDouble(dataClass.getData(context,"maximumAllowableFrequencyEdit"));
                    if(minimumAllowableFrequency>frequecy || frequecy>maximumAllowableFrequencyEdit){
                        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        Ringtone r ;//
                        r = RingtoneManager.getRingtone(context,alarm);
                        r.play();
                        Toast.makeText(context, "Abnormal Grid Frequency!", Toast.LENGTH_SHORT).show();

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



    }

}
