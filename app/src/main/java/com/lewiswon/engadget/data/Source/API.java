package com.lewiswon.engadget.data.Source;

/**
 * Created by Lordway on 16/4/25.
 */
public class API<T> extends JsonObject {
    private String code;
    private T data;
    private String msg;
    private String time;

    public String write(T data){

    return write(data,null);
    }
    public T getData(){

        return data;
    }
    public String write(T data,String error){
        this.data=data;
        this.time=System.currentTimeMillis()+"";
        this.msg="sucess";
        this.code="200";
        if (error!=null){
            this.msg=error;
            this.code=getCodeByError(error);
        }
        String result=null;
        try {
            result=toJson();
        }catch (Exception e){

        }finally {
            if (result==null){
                return "what fuck";
            }
        }
    return result;
    }

    private String getCodeByError(String error){


        return "200";
    }
}
