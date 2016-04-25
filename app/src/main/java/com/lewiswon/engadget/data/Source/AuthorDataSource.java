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
    public Author  getAuthor(String  url,Listener listener){
        Author  author=new Author();
        try {
            Element  pageElement= Jsoup.connect(url).get();
            parseAuthor(pageElement);
        }catch (Exception e){
            e.printStackTrace();
            Log.i("error","error");
        }


        return  author;
    }
    private void parseAuthor(Element ele){
        Author  author=new Author();
        Element  bio=ele.select(".bio").first();
        author.setHeadpic(bio.select("img").first().attr("abs:src"));
        author.setName(bio.select("img").first().attr("alt"));
        author.setProfile(bio.select(".history").first().select("p").first().text());
        Element social=ele.select(".social").first();
        author.setEmail(social.select(".email").first().select("a").first().text());
        author.setWeibo(social.select(".twitter").first().select("a").attr("abs:href"));
        Log.i("author",author.toJson());
    }

    public interface Listener{
        void onSucess(Author  author);
        void onError(String error);

    }
}
