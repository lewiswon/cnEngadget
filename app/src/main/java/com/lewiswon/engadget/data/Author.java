package com.lewiswon.engadget.data;

import com.lewiswon.engadget.data.Source.JsonObject;

import java.io.Serializable;

/**
 * Created by lewis on 16/4/25.
 */
public class Author extends JsonObject implements Serializable{

    private String name;
    private String email;
    private String headpic;
    private String weibo;
    private String profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }



}
