package com.dubizzle.app.domain.usecases;

import android.support.annotation.NonNull;

import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.domain.model.TmdbAsset;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetTVShows extends UseCase<GetTVShows.RequestValues, GetTVShows.ResponseValue> {

    private final TmdbRepository repository;

    public GetTVShows(TmdbRepository repository) {
        this.repository = checkNotNull(repository,"Repository cannot be null!");
    }


    @Override
    protected void executeUseCase(final GetTVShows.RequestValues requestValues) {
        repository.getPopularShows(requestValues.pageNumber, new DiscoveryResponseCallback() {
                    @Override
                    public void onSuccess(DiscoveryResponse response) {

                            if(response.getAssets().size() <= 0){
                                getUseCaseCallback().onError();
                            }
                            GetTVShows.ResponseValue responseValue = new GetTVShows.ResponseValue(response.getAssets());
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
