package com.dubizzle.app.domain.usecases;

import android.support.annotation.NonNull;

import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.domain.model.TmdbAsset;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetFilteredTVShows extends UseCase<GetFilteredTVShows.RequestValues, GetFilteredTVShows.ResponseValue> {

    private final TmdbRepository repository;

    public GetFilteredTVShows(TmdbRepository repository) {
        this.repository = checkNotNull(repository,"Repository cannot be null!");
    }


    @Override
    protected void executeUseCase(GetFilteredTVShows.RequestValues requestValues) {
        repository.getFilteredSeries(requestValues.maxYear, requestValues.minYear, requestValues.pageNumber,
                new DiscoveryResponseCallback() {
                    @Override
                    public void onSuccess(DiscoveryResponse response) {
                        if(response.getAssets().size() <= 0){
                            getUseCaseCallback().onError();
                        }else {
                            GetFilteredTVShows.ResponseValue responseValue = new GetFilteredTVShows.ResponseValue(response.getAssets());
                            getUseCaseCallback().onSuccess(responseValue);
                        }
                    }

                    @Override
                    public void onFail(Throwable t) {
                        getUseCaseCallback().onError();
                    }
                });




                /*new Callback<DiscoveryResponse>() {
            @Override
            public void onResponse(Call<DiscoveryResponse> call, Response<DiscoveryResponse> response) {

                if(response.isSuccessful()) {
                    if(response.body().getAssets().size() <= 0){
                        getUseCaseCallback().onError();
                    }
                    GetFilteredTVShows.ResponseValue responseValue = new GetFilteredTVShows.ResponseValue(response.body().getAssets());
                    getUseCaseCallback().onSuccess(responseValue);
                }else{
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onFailure(Call<DiscoveryResponse> call, Throwable t) {
                getUseCaseCallback().onError();
            }
        });*/
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int pageNumber;
        private int maxYear;
        private int minYear;


        public RequestValues(int number,int mMaxYear,int mMinYear) {
            pageNumber = number;
            maxYear = mMaxYear;
            minYear = mMinYear;
        }


    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private ArrayList<TmdbAsset> listItems;
        private String message;

        public ResponseValue(@NonNull ArrayList<TmdbAsset> items) {
            checkNotNull(items, "tasks cannot be null!");
            listItems = items;

        }

        public ResponseValue(@NonNull String errorMessage){
            this.message  = errorMessage;
        }

        public ArrayList<TmdbAsset> getListItems() {
            return listItems;
        }

        public String getMessage() {
            return message;
        }
    }
}