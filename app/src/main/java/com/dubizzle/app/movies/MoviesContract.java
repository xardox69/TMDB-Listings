package com.dubizzle.app.movies;

import com.dubizzle.app.common.BasePresenter;
import com.dubizzle.app.common.BaseView;
import com.dubizzle.app.common.ItemsBasePresenter;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.tvshows.TvShowsContract;

import java.util.ArrayList;

public interface MoviesContract {


    interface View extends BaseView<MoviesContract.Presenter> {
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
        void updateFilters(TMDBFilter filter);
        void loadItems(boolean forceUpdate);
        void loadFilteredMovies();
        void loadPopularMovies();
    }
}
