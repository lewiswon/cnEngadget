package com.lewiswon.engadget.Pages.HomePostList;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lewiswon.engadget.Pages.AuthorDetail.AuthorDetailActivity;
import com.lewiswon.engadget.Pages.PostDetail.PostDetailActivity;
import com.lewiswon.engadget.R;
import com.lewiswon.engadget.Utils.DateUtils;
import com.lewiswon.engadget.Utils.ScreenUtils;
import com.lewiswon.engadget.data.Author;
import com.lewiswon.engadget.data.Post;
import com.lewiswon.engadget.data.PostDetail;
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
        FragmentPagerAdapter i;
        FragmentStatePagerAdapter o;
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

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private View view;
        private Post  mPost;
        public PostViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        if (mPost==null){
            return;
        }
            PostDetailActivity.open(v.getContext(),mPost.getUrl());
        }
        public void setItems(final Post post){
            this.mPost=post;
            TextView title= (TextView) view.findViewById(R.id.title);
            TextView  author=(TextView)view.findViewById(R.id.author);
            TextView  summary=(TextView)view.findViewById(R.id.summary);
            TextView  timeTv=(TextView)view.findViewById(R.id.time);
            if (post.getImg()!=null){ImageView  imageView= (ImageView) view.findViewById(R.id.imageView);
               ViewGroup.LayoutParams params=imageView.getLayoutParams();
                params.height= (int) (ScreenUtils.getScreenWidth(view.getContext())/1.575);
                Glide.with(view.getContext()).load(post.getImg()).into(imageView);
            }
            if (post.getAuthorWeibo()!=null){
                view.findViewById(R.id.weibo).setVisibility(View.VISIBLE);
            }else{
                view.findViewById(R.id.weibo).setVisibility(View.INVISIBLE);
            }
            timeTv.setText(DateUtils.getTimeStr(post.getTime()));
            title.setText(post.getTitle());
            author.setText(post.getAuthor().toUpperCase());
            summary.setText(post.getSummary());
            author.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AuthorDetailActivity.open(v.getContext(),post.getAuthorLink());
                }
            });
        }

    }
}
