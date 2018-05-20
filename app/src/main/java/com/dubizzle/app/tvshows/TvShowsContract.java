package com.dubizzle.app.tvshows;

import com.dubizzle.app.common.BasePresenter;
import com.dubizzle.app.common.BaseView;
import com.dubizzle.app.common.ItemsBasePresenter;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.widget.AssetsAdapter;
import com.dubizzle.app.widget.MyScrollListener;

import java.util.ArrayList;

public interface TvShowsContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setLoadingIndicator(boolean active);

        void showLoadingItemsError();

        void showItemDetails(String id);

        ArrayList<TmdbAsset> getItems();

        void showItems(int position,ArrayList<TmdbAsset> items);

        void setFilter(TMDBFilter filter);

        void refreshItems();

    }


    interface Presenter extends ItemsBasePresenter {
        void loadItems(boolean forceUpdate);

        void updateFilters(TMDBFilter filter);
       // void setFiltering(TasksFilterType requestType);

      //  TasksFilterType getFiltering();

    }

}
