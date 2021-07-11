package com.example.frequencydisplay;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class GridDataClass {
     final String ACCOUNT_PREFS_NAME = "TCNGridInfo";

    public  void saveData(Context context,String data, String value){
        try {
            JSONObject dataObject = loadData(context);
            dataObject.put(data,value);
            SharedPreferences prefs= context.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("dataObject", dataObject.toString());
            edit.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public  String getData(Context context,String data){
        JSONObject dataObject = loadData(context);
        try {
            return (dataObject.getString(data));
        } catch (JSONException e) {
            e.printStackTrace();
            return("0.00");
        }
    }


    public  JSONObject loadData(Context context) {
        SharedPreferences prefs= context.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        JSONObject dataObject=new JSONObject();

        String length = prefs.getString("dataObject", null);
        if (length == null) {
            return dataObject;
        }
        try {
            dataObject=new JSONObject(prefs.getString("dataObject",null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataObject;
    }



}
