package com.lewiswon.engadget.data;

import com.lewiswon.engadget.data.Source.JsonObject;

/**
 * Created by Lordway on 16/4/25.
 */
public class Post extends JsonObject{
    String title;
    String summary;
    String author;
    String time;
    String img;
    String url;
    String authorLink;
    String authorWeibo;
    public void setAuthorWeibo(String weibo){this.authorWeibo=weibo;}
    public void setAuthorLink(String link){this.authorLink=link;}
    public void setTitle(String title) {
        this.title = title;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTime(String date) {
        this.time = date;
    }

    public void setImg(String thumb) {
        this.img = thumb;
    }
    public void setUrl(String detail) {
        this.url = detail;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getTime() {
        return time;
    }

    public String getImg() {
        return img;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthorLink() {
        return authorLink;
    }

    public String getAuthorWeibo() {
        return authorWeibo;
    }
}
