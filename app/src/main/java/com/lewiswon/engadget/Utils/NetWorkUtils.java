package com.lewiswon.engadget.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Lordway on 16/4/29.
 */
public class NetWorkUtils {
    public static boolean isAvainable(Context  context){
        ConnectivityManager  manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  info=manager.getActiveNetworkInfo();
        if (info!=null){
            return  info.isAvailable();
        }
        return false;
    }
}
