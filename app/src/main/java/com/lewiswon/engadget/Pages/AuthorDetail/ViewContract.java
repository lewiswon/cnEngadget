package com.lewiswon.engadget.Pages.AuthorDetail;

import com.lewiswon.engadget.Pages.BaseView;
import com.lewiswon.engadget.data.Author;

/**
 * Created by Lordway on 16/4/27.
 */
public interface ViewContract {

    interface View extends BaseView{
        void loadData(Author author);

    }
    interface Action{

        void getAuthor(String url);
    }
}
