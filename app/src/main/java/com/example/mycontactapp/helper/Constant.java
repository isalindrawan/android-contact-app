package com.example.mycontactapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Constant {

    public static boolean checkPermission(Context context, Activity activity, String permission)
    {
        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{permission}, 100);
            return false;
        }

        return true;
    }
}
