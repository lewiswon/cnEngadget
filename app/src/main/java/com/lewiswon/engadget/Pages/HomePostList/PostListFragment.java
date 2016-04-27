package com.lewiswon.engadget.Pages.HomePostList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.lewiswon.engadget.widget.LoadMoreRecyclerView;

import java.util.ArrayList;

/**
 * Created by Lordway on 16/4/25.
 */
public class PostListFragment  extends BaseFragment implements ViewContract.View{
    private Presenter  mPresenter;
    private int currentPage=1;
    private PostAdapter  postAdapter;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout  swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frament_home,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter=new Presenter(this);
        setUpRecycler();
        setupSwipeRefreshLayout();
        mPresenter.getPosts(currentPage);
        toggleRefresh(true);
    }
    private void setupSwipeRefreshLayout(){
        swipeRefreshLayout= (SwipeRefreshLayout) getView().findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage=1;
                mPresenter.getPosts(currentPage);
            }
        });
    }
    private void setUpRecycler(){
        recyclerView= (LoadMoreRecyclerView) getView().findViewById(R.id.recyclerview);
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
        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                mPresenter.getPosts(currentPage);
            }
        });
    }
    public void toggleRefresh(boolean toogle){
        swipeRefreshLayout.setRefreshing(toogle);
    }
    @Override
    public void loadPosts(ArrayList<Post> list) {
        recyclerView.onLoadMoreComplete(true);
        toggleRefresh(false);
        boolean needClear=false;
        if (currentPage<=1)needClear=true;
        postAdapter.setList(list,needClear);
    }

    @Override
    public void showMessage(String message) {
        recyclerView.onLoadMoreComplete(true);
        toggleRefresh(false);
    }
}
