package com.example.frequencydisplay;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public abstract class AbstractPermissionActivity extends Activity {
    public abstract void onPermissionDenied();
    public abstract void onReady(Bundle state);
    public abstract String[] getPermission ();

    int PERM_CODE = 121;
    Bundle state;
    boolean isPermission = false;
    String STATE_IN_PERMISSION="permission+State";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state=savedInstanceState;

            if (state !=null) {
                isPermission=state.getBoolean(STATE_IN_PERMISSION,false);
            }

            if (hasAllPermssion(getPermission())) {
                onReady(state);
            }

            else if (!isPermission) {
                isPermission=true;
                ActivityCompat.requestPermissions(this,getUngrantedPermission(getPermission()),PERM_CODE);


            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isPermission=false;

        if (requestCode==PERM_CODE) {
            if (hasAllPermssion(getPermission()))
                onReady(state);
            else
                onPermissionDenied();
        }

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_IN_PERMISSION,isPermission);
        super.onRestoreInstanceState(savedInstanceState);

    }

    protected boolean hasPermission(String perm) {
        if (ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean hasAllPermssion(String[] perms) {
       for (String perm:perms) {
           if (!hasPermission(perm)) {
               return false;
           }

       }
     return true;
    }

    protected String[] getUngrantedPermission (String[] perms ) {
        ArrayList <String>arrayList=new <String>ArrayList();

        for (String perm:perms) {
            if (!hasPermission(perm)) {
                arrayList.add(perm);
            }
        }
        return arrayList.toArray(new String[arrayList.size()]);

    }


}
