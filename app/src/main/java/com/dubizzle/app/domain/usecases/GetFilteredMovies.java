package com.dubizzle.app.domain.usecases;

import android.support.annotation.NonNull;

import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.domain.model.TmdbAsset;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetFilteredMovies extends UseCase<GetFilteredMovies.RequestValues, GetFilteredMovies.ResponseValue> {

    private final TmdbRepository repository;

    public GetFilteredMovies(TmdbRepository repository) {
        this.repository = checkNotNull(repository,"Repository cannot be null!");
    }


    @Override
    protected void executeUseCase(GetFilteredMovies.RequestValues requestValues) {
        repository.getFilteredMovies(requestValues.maxYear, requestValues.minYear, requestValues.pageNumber, new DiscoveryResponseCallback() {
            @Override
            public void onSuccess(DiscoveryResponse response) {
                if(response.getAssets().size() <= 0){
                    getUseCaseCallback().onError();
                }else {
                    GetFilteredMovies.ResponseValue responseValue = new GetFilteredMovies.ResponseValue(response.getAssets());
                    getUseCaseCallback().onSuccess(responseValue);
                }
            }

            @Override
            public void onFail(Throwable t) {
                getUseCaseCallback().onError();
            }
        });
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

        public ResponseValue(@NonNull ArrayList<TmdbAsset> mListItems) {
            listItems = checkNotNull(mListItems, "list items cannot be null!");
        }

        public ArrayList<TmdbAsset> getListItems() {
            return listItems;
        }
    }
}
