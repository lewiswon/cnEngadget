package com.lewiswon.engadget.Utils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Lordway on 16/4/29.
 */
public class ExceptionUtils {

    public void mapException2messsage(Exception e){
        String message;
        if (e instanceof SocketTimeoutException){}
        else if(e instanceof UnknownHostException){}
        else if(e instanceof JsonParseException){}
        else if(e instanceof JsonSyntaxException){}
        else if (e instanceof IllegalStateException){}
        else if (e instanceof NullPointerException){}
        else {

        }

    }
}
