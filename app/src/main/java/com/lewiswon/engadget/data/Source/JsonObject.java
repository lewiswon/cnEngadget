package com.lewiswon.engadget.data.Source;

import com.google.gson.Gson;

import java.lang.reflect.Field;

/**
 * Created by Lordway on 16/4/25.
 */
public class JsonObject {
    public String toJson(){
        return new Gson().toJson(this);
    }
}
