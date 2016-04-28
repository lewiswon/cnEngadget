package com.lewiswon.engadget.data.Source;

import android.util.Log;

import com.google.gson.internal.Excluder;
import com.lewiswon.engadget.data.Author;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * Created by lewis on 16/4/25.
 */
public class AuthorDataSource {
    public void  getAuthor(String  url,Listener listener){
        Author  author=null;
        try {
            Element  pageElement= Jsoup.connect(url).get();
            author=parseAuthor(pageElement);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (author==null){
                listener.onError("error");
            }else{
                listener.onSucess(author);
            }
        }

    }
    private Author parseAuthor(Element ele){
        Author  author=new Author();
        Element  bio=ele.select(".bio").first();
        author.setHeadpic(bio.select("img").first().attr("abs:src"));
        author.setName(bio.select("img").first().attr("alt"));
        author.setProfile(bio.select(".history").first().select("p").first().text());
        Element social=ele.select(".social").first();
        author.setEmail(social.select(".email").first().select("a").first().text());
        if (social.select(".twitter").first()!=null)
        author.setWeibo(social.select(".twitter").first().select("a").attr("abs:href"));

        return author;
    }

    public interface Listener{
        void onSucess(Author  author);
        void onError(String error);

    }
}
