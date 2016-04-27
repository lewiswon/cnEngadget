package com.lewiswon.engadget.data.Source;

import android.util.Log;

import com.lewiswon.engadget.data.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class PostDataSource {

    public void getPostHeader(String url,Listener listener){
        try {
            Element pageElement = Jsoup.connect(url).get();
            ArrayList<Post> alwaysTopList = parseAlwaysTop(pageElement);
            if (alwaysTopList != null && alwaysTopList.size() > 0) {

                listener.onSucess(new API<ArrayList<Post>>().write(alwaysTopList));
            }
        }catch (Exception e){
            listener.onError(e.getMessage());
        }
    }
    public void getPosts(String url,Listener listener) {
        ArrayList<Post> postList=null;
        try {
        Element pageElement = Jsoup.connect(url).get();
           postList = parseArticleList(pageElement);

        if (postList != null && postList.size() > 0) {

           listener.onSucess(new API<ArrayList<Post>>().write(postList));
        }else{
            listener.onError("error");
        }
        }catch (Exception e){
            listener.onError(e.getMessage()+"error");
        }finally {
            if (postList==null){
                listener.onError("error");
            }
        }
    }

        private ArrayList<Post> parseArticleList(Element pageElement){
            ArrayList<Post>  list=new ArrayList<>();
            Elements articleElements =pageElement.getElementsByAttributeValueContaining("itemtype","http://schema.org/BlogPosting");
            for (Element element:articleElements){

                Post item=new Post();
                item.setImg(element.attr("abs:data-image"));

                Element headline=element.select(".headline").first();
                Element info=element.select(".info").first();

                item.setTitle(headline.select("a").first().text());
                item.setUrl(headline.select("a").first().attr("abs:href"));

                item.setAuthor(info.select("a").first().text());
                item.setAuthorLink(info.select("a").first().attr("abs:href"));
                Element weibo=info.select(".weibo").first();
                if (weibo!=null){
                    Log.i("weibo",info.select(".weibo").first().select("a").first().attr("abs:href"));
                    item.setAuthorWeibo(info.select(".weibo").first().select("a").first().attr("abs:href"));
                }
                if(info.hasClass("weibo")){
                    item.setAuthorWeibo(info.select(".weibo").first().select("a").first().attr("abs:href"));
                    Log.i("weibo",info.select(".weibo").first().select("a").first().attr("abs:href"));
                };
                item.setTime(info.select("time").first().attr("datetime"));
                Element  postBody=element.select(".post-body").first();
                item.setSummary(postBody.select(".copy").first().text());
                list.add(item);
            }

            return list;
        }
        private ArrayList<Post>  parseAlwaysTop(Element pageElement){
            Element alwaysTopElement=pageElement.getElementById("carousel");
            Elements alwaysTop2Elements=alwaysTopElement.getElementsByAttributeValueContaining("class","always top2");
            Elements alwaysTopElements=alwaysTopElement.getElementsByAttributeValueContaining("class","always");
            ArrayList<Post>  alwaysTooList=new ArrayList();
            for (Element ele:alwaysTop2Elements){
                Element link=ele.select("a").first();
                Element img=ele.select("img").first();
                Element title=ele.select(".title").first();
                Element author=ele.select(".byline").first();
                Post   item=new Post();
                item.setTitle(title.text());
                item.setAuthor(author.text());
                item.setImg(img.attr("abs:src"));
                item.setUrl(link.attr("abs:href"));
                alwaysTooList.add(item);
            }
            for (Element ele:alwaysTopElements){
                Element url=ele.select("a").first();
                Element  img=ele.select("img").first();
                Element  title=ele.select(".headline").first();
                Post item = new Post();
                item.setTitle(title.text());
                item.setUrl(url.attr("abs:href"));
                item.setImg(img.attr("abs:src"));
                alwaysTooList.add(item);
            }
            return alwaysTooList;
        }

    public interface Listener{
        void onSucess(String result);
        void onError(String error);
    }
}
