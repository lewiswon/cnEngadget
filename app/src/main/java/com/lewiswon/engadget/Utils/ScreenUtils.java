package com.lewiswon.engadget.Utils;

import android.content.Context;

/**
 * Created by Lordway on 16/4/26.
 */
public class ScreenUtils {

    public static int getScreenWidth(Context  context){
       return context.getResources().getDisplayMetrics().widthPixels;
    }
}
