package com.lewiswon.engadget.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lewiswon.engadget.R;

public class LoadMoreRecyclerView extends RecyclerView {
    public static int TYPE_NORMAL = 0;
    public static int TYPE_HEADER = 1;
    public static int TYPE_FOOTER = 2;
    private int mLoadMorePosition;
    public boolean mIsFooterEnable = false;
    private boolean mIsLoading = false;
    private AutoLoadAdapter mAutoLoadAdapter;
    private LoadMoreListener mLoadMoreListener;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //add a scroller listener to recyclerview
    private void init() {
        super.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLoadMoreListener != null && mIsFooterEnable && !mIsLoading && dy > 0) {
                    int lastVisiablePosition = getLastVisiablePosition();
                    int total = mAutoLoadAdapter.getItemCount();
                    if (lastVisiablePosition + 1 == total) {
                        setLoading(true);
                        mLoadMorePosition = lastVisiablePosition;
                        mLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    //get recycler last visiable position
    private int getLastVisiablePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    //set adapter
    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAutoLoadAdapter = new AutoLoadAdapter(adapter);
        }
        super.setAdapter(mAutoLoadAdapter);
    }

    //set load more enable
    public void setAutoLoadEnable(boolean autoload) {
        mIsFooterEnable = autoload;
    }


    public void setLoading(boolean loading) {
        this.mIsLoading = loading;
    }

    //when load is complete,notify adapter to refresh data and remove footer
    public void onLoadMoreComplete(boolean hasMore) {
        setAutoLoadEnable(hasMore);
        getAdapter().notifyItemRemoved(mLoadMorePosition);
        mIsLoading = false;
    }

    //is loading
    public boolean isLoading() {
        return mIsLoading;
    }

    //set listener
    public void setLoadMoreListener(LoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }


    public class AutoLoadAdapter extends Adapter<ViewHolder> {
        private Adapter mDataAdater;

        public AutoLoadAdapter(Adapter adapter) {
            mDataAdater = adapter;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_footer_more, parent, false));
            } else {
                return mDataAdater.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type != TYPE_FOOTER && type != TYPE_HEADER) {
                mDataAdater.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemViewType(int position) {
            int footerPosition = getItemCount() - 1;
            if (footerPosition == position && mIsFooterEnable) {
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public int getItemCount() {
            int count = mDataAdater.getItemCount();
            if (mIsFooterEnable) count++;
            return count;
        }

        public class FooterViewHolder extends ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
