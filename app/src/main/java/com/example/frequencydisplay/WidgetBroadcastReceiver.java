package com.example.frequencydisplay;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;

public class WidgetBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC,calendar.getTimeInMillis()+30000,pendingIntent);
        //update widget
        GetJsonContentTask getJsonContentTask = new GetJsonContentTask(context);
        getJsonContentTask.execute();
//        Toast.makeText(context, "Widget update....", Toast.LENGTH_LONG).show();

    }
}
