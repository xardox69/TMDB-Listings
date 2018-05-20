package com.dubizzle.app.domain.usecases;

import android.support.annotation.NonNull;

import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.domain.model.TmdbAsset;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetMovies  extends UseCase<GetMovies.RequestValues, GetMovies.ResponseValue> {

    private final TmdbRepository repository;

    public GetMovies(TmdbRepository repository) {
        this.repository = checkNotNull(repository,"Repository cannot be null!");
    }


    @Override
    protected void executeUseCase(RequestValues requestValues) {
        repository.getPopularMovies(requestValues.pageNumber, new DiscoveryResponseCallback() {
            @Override
            public void onSuccess(DiscoveryResponse response) {

                if(response.getAssets().size() <= 0){
                    getUseCaseCallback().onError();
                }
                GetMovies.ResponseValue responseValue = new GetMovies.ResponseValue(response.getAssets());
                getUseCaseCallback().onSuccess(responseValue);
            }


            @Override
            public void onFail(Throwable t) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
            private int pageNumber;


        public RequestValues(int number) {
            pageNumber = number;
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
