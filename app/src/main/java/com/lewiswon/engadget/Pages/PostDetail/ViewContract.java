package com.lewiswon.engadget.Pages.PostDetail;

import com.lewiswon.engadget.data.PostDetail;

/**
 * Created by Lordway on 16/4/26.
 */
public interface ViewContract {

    interface View{
        void showMessage(String message);
        void loadDetail(PostDetail postDetail);
    }

    interface Action{
        void getDetail(String url);
    }
}
