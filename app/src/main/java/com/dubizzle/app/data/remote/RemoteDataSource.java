package com.dubizzle.app.data.remote;

import android.content.Context;

import com.dubizzle.app.R;
import com.dubizzle.app.callbacks.DetailResponseCallback;
import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.data.DataSource;
import com.dubizzle.app.domain.model.DetailedAsset;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.utils.ApiConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;
    private String baseUrl;
    private Context context;
    private TmbdService service;

    /**
     * API key generated
     */
    private String key;

    public static RemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(context);
        }
        return INSTANCE;
    }

    private RemoteDataSource(Context context) {
        this.context = context;

        initValues();
    }

    private void initValues() {
        this.baseUrl = context.getString(R.string.base_url) + "3/";
        key = context.getString(R.string.key);
        service = getService(getRetrofit(getHTTPClinet()));

    }

    private OkHttpClient getHTTPClinet(){
       return  new OkHttpClient.Builder()
                .cache(getCache()) // 10 MB
                .addInterceptor(getLoggingInterceptor()) //for logging requests
                //.addNetworkInterceptor(new CacheControlInterceptor(context)) //for caching purpose
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
    }

    private Cache getCache(){
        return new Cache(context.getCacheDir(), 10 * 1024 * 1024);
    }

    private Retrofit getRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private TmbdService getService(Retrofit retrofit){
        return retrofit.create(TmbdService.class);
    }



    private HttpLoggingInterceptor getLoggingInterceptor(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        //TODO: For now just log
        boolean isDebug = true;
        if (isDebug) {
            logger.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            logger.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return logger;
    }





    @Override
    public void getPopularShows(int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback) {
        Callback<DiscoveryResponse> callback = new Callback<DiscoveryResponse>() {
            @Override
            public void onResponse(Call<DiscoveryResponse> call, Response<DiscoveryResponse> response) {
                discoveryResponseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DiscoveryResponse> call, Throwable t) {
                discoveryResponseCallback.onFail(t);
            }
        };
        Call<DiscoveryResponse> call = service.getPopularTVShows(key, ApiConstants.MOST_POPULAR, pageNumber);
        call.enqueue(callback);

    }


    @Override
    public void getPopularMovies(int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback) {
        Callback<DiscoveryResponse> callback = new Callback<DiscoveryResponse>() {
            @Override
            public void onResponse(Call<DiscoveryResponse> call, Response<DiscoveryResponse> response) {
                discoveryResponseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DiscoveryResponse> call, Throwable t) {
                discoveryResponseCallback.onFail(t);
            }
        };
        Call<DiscoveryResponse> call = service.getPopularMovies(key, ApiConstants.MOST_POPULAR, pageNumber);
        call.enqueue(callback);
    }


    @Override
    public void getMovieDetails(String id, final DetailResponseCallback detailResponseCallback ) {
        Callback<DetailedAsset> callback = new Callback<DetailedAsset>() {
            @Override
            public void onResponse(Call<DetailedAsset> call, Response<DetailedAsset> response) {
                detailResponseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DetailedAsset> call, Throwable t) {
                detailResponseCallback.onFail(t);
            }
        };
        Call<DetailedAsset> call = service.getMovie(id, key);
        call.enqueue(callback);

    }


    @Override
    public void getShowsDetails(String id, final DetailResponseCallback detailResponseCallback) {
        Callback<DetailedAsset> callback = new Callback<DetailedAsset>() {
            @Override
            public void onResponse(Call<DetailedAsset> call, Response<DetailedAsset> response) {
                detailResponseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DetailedAsset> call, Throwable t) {
                detailResponseCallback.onFail(t);
            }
        };
        Call<DetailedAsset> call = service.getShow(id, key);
        call.enqueue(callback);

    }

    @Override
    public void getItemDetails(boolean isMovie, String id, final DetailResponseCallback detailResponseCallback) {
        Callback<DetailedAsset> callback = new Callback<DetailedAsset>() {
            @Override
            public void onResponse(Call<DetailedAsset> call, Response<DetailedAsset> response) {
                detailResponseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DetailedAsset> call, Throwable t) {
                detailResponseCallback.onFail(t);
            }
        };
        Call<DetailedAsset> call;
        if(isMovie){
             call = service.getMovie(id, key);
        }else{
             call = service.getShow(id, key);
        }
        call.enqueue(callback);
    }


    @Override
    public void getFilteredMovies(int maxYear, int minYear, int pageNumber, final DiscoveryResponseCallback discoveryResponseCallback) {
        Callback<DiscoveryResponse> callback = new Callback<DiscoveryResponse>() {
            @Override
            public void onResponse(Call<DiscoveryResponse> call, Response<DiscoveryResponse> response) {
                discoveryResponseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DiscoveryResponse> call, Throwable t) {
                discoveryResponseCallback.onFail(t);
            }
        };
        Call<DiscoveryResponse> call = service.getPopularFilteredMovies(key, ApiConstants.MOST_POPULAR, pageNumber, minYear+"-1-1", maxYear+"-1-1");
        call.enqueue(callback);
    }


    @Override
    public void getFilteredSeries(int maxYear, int minYear, int pageNumber,
                                  final DiscoveryResponseCallback discoveryResponseCallback){
        Callback<DiscoveryResponse> callback = new Callback<DiscoveryResponse>() {
            @Override
            public void onResponse(Call<DiscoveryResponse> call, Response<DiscoveryResponse> response) {
                discoveryResponseCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DiscoveryResponse> call, Throwable t) {
                discoveryResponseCallback.onFail(t);
            }
        };
        Call<DiscoveryResponse> call = service.getPopularFilteredShows(key, ApiConstants.MOST_POPULAR, pageNumber, minYear+"-1-1", maxYear+"-1-1");
        call.enqueue(callback);
    }
}
