package com.dubizzle.app.data.remote;

import com.dubizzle.app.domain.model.DetailedAsset;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.utils.ApiConstants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.dubizzle.app.utils.ApiConstants.MOVIE_ID;
import static com.dubizzle.app.utils.ApiConstants.TV_ID;

public interface TmbdService {

    @GET("discover/tv")
    Call<DiscoveryResponse> getPopularTVShows(@Query(ApiConstants.PARAM_API_KEY) String key,
                                              @Query(ApiConstants.PARAM_SORTBY) String sortBy,
                                              @Query(ApiConstants.PARAM_PAGE) int page);


    @GET("discover/movie")
    Call<DiscoveryResponse>  getPopularMovies(@Query(ApiConstants.PARAM_API_KEY) String key,
                                              @Query(ApiConstants.PARAM_SORTBY) String sortBy,
                                              @Query(ApiConstants.PARAM_PAGE) int page);


    @GET("movie/{movie_id}")
    Call<DetailedAsset>  getMovie(@Path(MOVIE_ID)String id, @Query(ApiConstants.PARAM_API_KEY) String key);

    @GET("tv/{tv_id}")
    Call<DetailedAsset>  getShow(@Path(TV_ID)String id, @Query(ApiConstants.PARAM_API_KEY) String key);


    @GET("discover/tv")
    Call<DiscoveryResponse>  getPopularFilteredShows(@Query(ApiConstants.PARAM_API_KEY) String key,
                                                     @Query(ApiConstants.PARAM_SORTBY) String sortBy,
                                                     @Query(ApiConstants.PARAM_PAGE) int page,
                                                     @Query(ApiConstants.PARAM_SERIES_GREATER_THAN) String minYear,
                                                     @Query(ApiConstants.PARAM_SERIES_LESS_THAN) String maxYear);


    @GET("discover/movie")
    Call<DiscoveryResponse>  getPopularFilteredMovies(@Query(ApiConstants.PARAM_API_KEY) String key,
                                                      @Query(ApiConstants.PARAM_SORTBY) String sortBy,
                                                      @Query(ApiConstants.PARAM_PAGE) int page,
                                                      @Query(ApiConstants.PARAM_MOVIE_GREATER_THAN) String minYear,
                                                      @Query(ApiConstants.PARAM_MOVIE_LESS_THAN) String maxYear);
}
