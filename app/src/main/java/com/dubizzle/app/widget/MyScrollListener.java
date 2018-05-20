package com.dubizzle.app.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dubizzle.app.common.BasePresenter;
import com.dubizzle.app.common.ItemsBasePresenter;
import com.dubizzle.app.tvshows.TvShowsPresenter;

import static com.dubizzle.app.utils.AppConsts.ASSETS_TO_LOAD;

public class MyScrollListener extends RecyclerView.OnScrollListener  {
    private GridLayoutManager layoutManager;
    boolean isLoading;
    private ItemsBasePresenter presenter;


    public MyScrollListener(GridLayoutManager layoutManager,ItemsBasePresenter presenter) {
        this.layoutManager = layoutManager;
        isLoading = false;
        this.presenter = presenter;

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int total = layoutManager.getItemCount();
        int lastVisibleItemCount = layoutManager.findLastVisibleItemPosition();
        if (!isLoading) {
            if ( lastVisibleItemCount >= (total - ASSETS_TO_LOAD)) {
                isLoading = true;
                presenter.loadItems(false);
            }

        }
    }



    public void setLoading(boolean loading) {
        isLoading = loading;
    }



}
