package com.dubizzle.app.domain.usecases;

import android.support.annotation.NonNull;

import com.dubizzle.app.callbacks.DetailResponseCallback;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.domain.model.DetailedAsset;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetItemDetail extends UseCase<GetItemDetail.RequestValues, GetItemDetail.ResponseValue> {

    private final TmdbRepository repository;

    public GetItemDetail(TmdbRepository repository) {
        this.repository = checkNotNull(repository,"Repository cannot be null!");
    }


    @Override
    protected void executeUseCase(GetItemDetail.RequestValues requestValues) {
        repository.getItemDetails(requestValues.mIsMovie, requestValues.id, new DetailResponseCallback() {
                    @Override
                    public void onSuccess(DetailedAsset asset) {
                        GetItemDetail.ResponseValue responseValue = new GetItemDetail.ResponseValue(asset);
                        getUseCaseCallback().onSuccess(responseValue);
                    }

                    @Override
                    public void onFail(Throwable t) {
                        getUseCaseCallback().onError();
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String id;
        private boolean mIsMovie;


        public RequestValues(String mId,boolean isMovie) {
            id = mId;
            mIsMovie = isMovie;

        }


    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private DetailedAsset mItem;

        public ResponseValue(@NonNull DetailedAsset item) {
            mItem = checkNotNull(item, "list items cannot be null!");
        }

        public DetailedAsset getmItem() {
            return mItem;
        }
    }
}