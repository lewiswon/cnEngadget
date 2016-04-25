package com.lewiswon.engadget.data.Source;

import java.util.ArrayList;

/**
 * Created by Lordway on 16/4/25.
 */
public class JsonArray extends ArrayList<JsonObject>{
    public String toJson() throws Exception{
        if (size()==0)return "";
        StringBuilder stringBuilder=new StringBuilder("[");
        for (int i=0;i<size();i++){
            JsonObject  item=get(i);
            stringBuilder.append(item.toJson());
            if (i<size()-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
