package com.dubizzle.app.movies;

import android.support.annotation.NonNull;

import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.domain.usecases.GetFilteredMovies;
import com.dubizzle.app.domain.usecases.GetMovies;
import com.dubizzle.app.domain.usecases.GetTVShows;
import com.dubizzle.app.tvshows.TvShowsContract;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    private final MoviesContract.View mMoviesView;
    private final GetMovies getMovies;
    private final GetFilteredMovies filteredMovies;
    private final UseCaseHandler mUseCaseHandler;
    private TMDBFilter filter;

    private int page = 1;


    public MoviesPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull MoviesContract.View moviesView,
                            @NonNull GetMovies moviesCase,
                           @NonNull GetFilteredMovies mfilteredMovies){
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mMoviesView = checkNotNull(moviesView,"View cannot be null!");
        getMovies = checkNotNull(moviesCase,"ShowsTask cannot be null!");
        filteredMovies = checkNotNull(mfilteredMovies,"filtered movies cannot be null!");
        filter = new TMDBFilter(0,0);

    }

    @Override
    public void updateFilters(TMDBFilter mFilter) {
        filter = mFilter;
        mMoviesView.refreshItems();
    }

    @Override
    public void loadItems(boolean forceUpdate) {
        mMoviesView.setLoadingIndicator(true);
        if(forceUpdate){
            page =1;
        }
        if(filter.getMaxYear() == 0 && filter.getMinYear() == 0){
            loadPopularMovies();
        }else{
            loadFilteredMovies();
        }


    }

    @Override
    public void loadFilteredMovies() {
        final GetFilteredMovies.RequestValues requestValue = new GetFilteredMovies.RequestValues(page,filter.getMaxYear(),filter.getMinYear());
        mUseCaseHandler.execute(filteredMovies, requestValue, new UseCase.UseCaseCallback<GetFilteredMovies.ResponseValue>() {
            @Override
            public void onSuccess(GetFilteredMovies.ResponseValue response) {
                mMoviesView.setLoadingIndicator(false);
                ArrayList<TmdbAsset> assets = mMoviesView.getItems();
                int size = assets.size();
                mMoviesView.showItems(size,response.getListItems());
                page++;

            }

            @Override
            public void onError() {
                mMoviesView.setLoadingIndicator(false);
                mMoviesView.showLoadingItemsError();
            }
        });
    }

    @Override
    public void loadPopularMovies(){
        final GetMovies.RequestValues requestValue = new GetMovies.RequestValues(page);
        mUseCaseHandler.execute(getMovies, requestValue, new UseCase.UseCaseCallback<GetMovies.ResponseValue>() {
            @Override
            public void onSuccess(GetMovies.ResponseValue response) {
                mMoviesView.setLoadingIndicator(false);
                ArrayList<TmdbAsset> assets = mMoviesView.getItems();
                int size = assets.size();
                mMoviesView.showItems(size,response.getListItems());
                page++;

            }

            @Override
            public void onError() {
                mMoviesView.setLoadingIndicator(false);
                mMoviesView.showLoadingItemsError();
            }
        });
    }

    @Override
    public void start() {
        if(mMoviesView.isActive()) {
            loadItems(true);
        }
    }

}
