package com.dubizzle.app.data;

import android.support.annotation.NonNull;

import com.dubizzle.app.callbacks.DetailResponseCallback;
import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.domain.model.DetailedAsset;

import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;

public class TmdbRepository implements DataSource {

    private static TmdbRepository INSTANCE = null;

    private final DataSource mTasksRemoteDataSource;






    // Prevent direct instantiation.
    private TmdbRepository(@NonNull DataSource tasksRemoteDataSource) {
        mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource);
    }


    public static TmdbRepository getInstance(DataSource tasksRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TmdbRepository(tasksRemoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getPopularShows(int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback) {
        checkNotNull(discoveryResponseCallback);
        mTasksRemoteDataSource.getPopularShows(pageNumber,discoveryResponseCallback);

    }

    @Override
    public void getPopularMovies(int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback) {
        checkNotNull(discoveryResponseCallback);
        mTasksRemoteDataSource.getPopularMovies(pageNumber,discoveryResponseCallback);

    }

    @Override
    public void getMovieDetails(String id, DetailResponseCallback callback) {
        checkNotNull(callback);
        mTasksRemoteDataSource.getMovieDetails(id, callback);

    }

    @Override
    public void getItemDetails(boolean isMovie , String id, DetailResponseCallback callback) {
        checkNotNull(callback);
        if(isMovie) {
            mTasksRemoteDataSource.getMovieDetails(id, callback);
        }else{
            mTasksRemoteDataSource.getShowsDetails(id, callback);
        }

    }

    @Override
    public void getShowsDetails(String id, DetailResponseCallback callback) {
        checkNotNull(callback);
        mTasksRemoteDataSource.getShowsDetails(id, callback);

    }

    @Override
    public void getFilteredMovies(int maxYear, int minYear, int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback) {
        checkNotNull(discoveryResponseCallback);
        mTasksRemoteDataSource.getFilteredMovies(maxYear, minYear, pageNumber, discoveryResponseCallback);

    }

    @Override
    public void getFilteredSeries(int maxYear, int minYear, int pageNumber,  final DiscoveryResponseCallback discoveryResponseCallback) {
        checkNotNull(discoveryResponseCallback);
        mTasksRemoteDataSource.getFilteredSeries(maxYear, minYear, pageNumber, discoveryResponseCallback);

    }
}
