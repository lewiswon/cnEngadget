package com.lewiswon.engadget.Pages.HomePostList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lewiswon.engadget.Pages.AuthorDetail.AuthorDetailActivity;
import com.lewiswon.engadget.R;
import com.lewiswon.engadget.Utils.ScreenUtils;
import com.lewiswon.engadget.data.Author;
import com.lewiswon.engadget.data.Post;
import com.lewiswon.engadget.data.Source.AuthorDataSource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimePrinter;

import java.util.ArrayList;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lordway on 16/4/25.
 */
public class PostAdapter  extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList  <Post> posts;
    public PostAdapter(ArrayList<Post> posts){
        this.posts=posts;
    }
    public void setList(ArrayList<Post> post,boolean needClear){
        if (needClear)posts.clear();
        this.posts.addAll(post);
        notifyDataSetChanged();
    }
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.setItems(posts.get(position));
    }
    public Post getItem(int postion){

        return posts.get(postion);
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        private View view;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
        }
        public void setItems(Post post){
            TextView title= (TextView) view.findViewById(R.id.title);
            TextView  author=(TextView)view.findViewById(R.id.author);
            TextView  summary=(TextView)view.findViewById(R.id.summary);
            TextView  timeTv=(TextView)view.findViewById(R.id.time);
            if (post.getImg()!=null){
                ImageView  imageView= (ImageView) view.findViewById(R.id.imageView);
               ViewGroup.LayoutParams params=imageView.getLayoutParams();
                params.height= (int) (ScreenUtils.getScreenWidth(view.getContext())/1.575);
//                imageView.setLayoutParams(params);
                Glide.with(view.getContext()).load(post.getImg()).into(imageView);
            }

            timeTv.setText(getTimeStr(post.getTime()));
            title.setText(post.getTitle());
            author.setText(post.getAuthor());
            summary.setText(post.getSummary());
//            getAuthor(post.getAuthorLink());

        }
        public String getTimeStr(String timeStr){
            try{
                DateTime  time=DateTime.parse(timeStr);
                return time.toString("yyyy-MM-dd hh:mm");
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
        public void setAuthor(final Author author){
            TextView  authorTv= (TextView) view.findViewById(R.id.author);
            authorTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AuthorDetailActivity.open(v.getContext(),author);
                }
            });
            if (author.getWeibo()!=null){
               view.findViewById(R.id.weibo).setVisibility(View.VISIBLE);
            }else{
                view.findViewById(R.id.weibo).setVisibility(View.INVISIBLE);
            }
        }
        private void getAuthor(final String url){
            Observable<Author>  observable=Observable.create(new Observable.OnSubscribe<Author>() {
                @Override
                public void call(final Subscriber<? super Author> subscriber) {
                    new AuthorDataSource().getAuthor(url, new AuthorDataSource.Listener() {
                        @Override
                        public void onSucess(Author author) {
                            subscriber.onNext(author);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            });
            Subscriber<Author>  subscriber=new Subscriber<Author>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Author author) {
                    setAuthor(author);
                }
            };
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        }
    }
}
