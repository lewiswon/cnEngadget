package com.lewiswon.engadget.Pages.HomePostList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lewiswon.engadget.R;
import com.lewiswon.engadget.data.Author;
import com.lewiswon.engadget.data.Post;
import com.lewiswon.engadget.data.Source.AuthorDataSource;

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
    public void setList(ArrayList<Post> post){
        this.posts=post;
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
            title.setText(post.getTitle());
            author.setText(post.getAuthor());
            summary.setText(post.getSummary());
            getAuthor(post.getAuthorLink());
        }
        public void setAuthor(Author author){

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
