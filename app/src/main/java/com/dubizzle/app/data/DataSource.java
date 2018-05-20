package com.dubizzle.app.data;

import com.dubizzle.app.callbacks.DetailResponseCallback;
import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.domain.model.DetailedAsset;

import retrofit2.Callback;

public interface DataSource {

    /**
     * Returns popular TV shows from network
     *
     * @param pageNumber pagination number
     * @param discoveryResponseCallback api callback
     */
    public void getPopularShows(int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback);



    /**
     * Returns the most popular movies from network
     *
     * @param pageNumber for pagination
     * @param discoveryResponseCallback api callback
     */
    public void getPopularMovies(int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback);


    /**
     * Returns the details of a movie
     *
     * @param id       ID of the movie
     * @param detailResponseCallback api callback
     */
    public void getMovieDetails(String id, final DetailResponseCallback detailResponseCallback);

    /**
     * Returns the Details of the TV Shows
     *
     * @param id       the id of tv show
     * @param detailResponseCallback api callback
     */
    public void getShowsDetails(String id, final DetailResponseCallback detailResponseCallback);

    public void getItemDetails(boolean isMovie,String id, final DetailResponseCallback detailResponseCallback);


    /**
     * Returns movies based on max and min year
     * @param maxYear  maximum year
     * @param minYear minimum year
     * @param pageNumber page to show
     * @param discoveryResponseCallback api callback
     */
    public void getFilteredMovies(int maxYear, int minYear, int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback);

    /**
     * Returns tv shows based on max and min years
     * @param maxYear maximum year
     * @param minYear minimum year
     * @param pageNumber page to show
     * @param discoveryResponseCallback api callback
     */
    public void getFilteredSeries(int maxYear, int minYear, int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback);


}
