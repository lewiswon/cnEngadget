package com.lewiswon.engadget.Pages.HomePostList;

import com.lewiswon.engadget.data.Post;

import java.util.ArrayList;

/**
 * Created by Lordway on 16/4/25.
 */
public interface ViewContract {

    interface View{
        void loadPosts(ArrayList<Post> list);
        void showMessage(String message);
        void back2Top();
    }
    interface Action{
        void getPosts(int page);
    }
}
