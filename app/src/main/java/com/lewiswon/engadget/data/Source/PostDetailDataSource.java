package com.lewiswon.engadget.data.Source;

import android.util.Log;

import com.lewiswon.engadget.Utils.RecyclerTouchListener;
import com.lewiswon.engadget.data.PostDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * Created by Lordway on 16/4/26.
 */
public class PostDetailDataSource  {

    public void getDetail(String url, Listener listener){
            PostDetail postDetail=null;
        try {
            Element pageElement= Jsoup.connect(url).get();
            postDetail=parse(pageElement);
        }catch (Exception e){
            listener.onError(e.getMessage()+"unknow error");
        }finally {
            if (postDetail==null) {
                listener.onError("fetch data error");
            }else{
                Log.i("post detial",postDetail.getContent());
                listener.onSuccess(postDetail);
            }
        }
    }
    private PostDetail parse(Element element){
        PostDetail detail=new PostDetail();
        Element content=element.getElementById("page").select("#body").first().select("div").get(1);
//        content.select("div").get(1).remove();
        content.select(".read-more").first().remove();
        Log.i("content",content.toString());
        detail.setContent(content.toString());

        return detail;
    }


    public interface Listener{
        void onSuccess(PostDetail  postDetail);
        void onError(String error);
    }
}
