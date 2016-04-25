package com.lewiswon.engadget.Pages.HomePostList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lewiswon.engadget.Pages.BaseFragment;
import com.lewiswon.engadget.Pages.PostDetail.PostDetailActivity;
import com.lewiswon.engadget.R;
import com.lewiswon.engadget.Utils.RecyclerTouchListener;
import com.lewiswon.engadget.data.Post;

import java.util.ArrayList;

/**
 * Created by Lordway on 16/4/25.
 */
public class PostListFragment  extends BaseFragment implements ViewContract.View{
    private Presenter  mPresenter;
    private int currentPage=0;
    private PostAdapter  postAdapter;
    private RecyclerView  recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frament_home,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter=new Presenter(this);
        mPresenter.getPosts(currentPage);
        setUpRecycler();
    }
    private void setUpRecycler(){
        recyclerView= (RecyclerView) getView().findViewById(R.id.recyclerview);
        postAdapter=new PostAdapter(new ArrayList<Post>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(postAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.Listener() {
            @Override
            public void onClick(View view, int postion) {
                PostDetailActivity.open(getActivity(),postAdapter.getItem(postion).getUrl());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    @Override
    public void loadPosts(ArrayList<Post> list) {
        for (Post p:list){
            Log.i("post:",p.toJson());
        }
        postAdapter.setList(list);
    }

    @Override
    public void showMessage(String message) {

    }
}
