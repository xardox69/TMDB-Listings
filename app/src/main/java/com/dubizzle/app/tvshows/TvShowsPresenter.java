package com.dubizzle.app.tvshows;

import android.support.annotation.NonNull;

import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.domain.usecases.GetFilteredTVShows;
import com.dubizzle.app.domain.usecases.GetMovies;
import com.dubizzle.app.domain.usecases.GetTVShows;
import com.dubizzle.app.widget.MyScrollListener;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class TvShowsPresenter implements TvShowsContract.Presenter {

    private final TvShowsContract.View mShowsView;
    private final GetTVShows getTVShows;
    private final UseCaseHandler mUseCaseHandler;
    private final GetFilteredTVShows filteredTVShows;
    private TMDBFilter filter;

    private int page = 1;


    public TvShowsPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull TvShowsContract.View showsView,
                            @NonNull GetTVShows getShows,
                            @NonNull GetFilteredTVShows mFilteredTvShows){
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mShowsView = checkNotNull(showsView,"View cannot be null!");
        getTVShows = checkNotNull(getShows,"ShowsTask cannot be null!");
        filteredTVShows = checkNotNull(mFilteredTvShows,"filtered Tv Shows cannot be null");
        filter = new TMDBFilter(0,0);
    }
    @Override
    public void loadItems(boolean forceUpdate) {
        mShowsView.setLoadingIndicator(true);
        if(forceUpdate){
            page = 1;
        }
        if(filter.getMaxYear() == 0 && filter.getMinYear() == 0){
            loadPopularTVShows();
        }else {
            loadFilteredTVShows();
        }

    }

    public void loadPopularTVShows(){
        final GetTVShows.RequestValues requestValue = new GetTVShows.RequestValues(page);
        mUseCaseHandler.execute(getTVShows, requestValue, new UseCase.UseCaseCallback<GetTVShows.ResponseValue>() {
            @Override
            public void onSuccess(GetTVShows.ResponseValue response) {
                mShowsView.setLoadingIndicator(false);
                ArrayList<TmdbAsset> assets = mShowsView.getItems();
                int size = assets.size();
                mShowsView.showItems(size,response.getListItems());
                page++;

            }

            @Override
            public void onError() {
                mShowsView.setLoadingIndicator(false);
                mShowsView.showLoadingItemsError();
            }
        });
    }

    public void loadFilteredTVShows(){
        final GetFilteredTVShows.RequestValues requestValue = new GetFilteredTVShows.RequestValues(page,filter.getMaxYear(),filter.getMinYear());
        mUseCaseHandler.execute(filteredTVShows, requestValue, new UseCase.UseCaseCallback<GetFilteredTVShows.ResponseValue>() {
            @Override
            public void onSuccess(GetFilteredTVShows.ResponseValue response) {
                mShowsView.setLoadingIndicator(false);
                ArrayList<TmdbAsset> assets = mShowsView.getItems();
                int size = assets.size();
                mShowsView.showItems(size,response.getListItems());
                page++;

            }

            @Override
            public void onError() {
                mShowsView.setLoadingIndicator(false);
                mShowsView.showLoadingItemsError();
            }
        });
    }

    @Override
    public void updateFilters(TMDBFilter mFilter) {
        filter = mFilter;
        mShowsView.refreshItems();
    }

    @Override
    public void start() {
        if(mShowsView.isActive()) {
            loadItems(true);
        }else{
            page = 1;
        }
    }

}
