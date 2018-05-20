package com.dubizzle.app.detail;

import android.support.annotation.NonNull;

import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.usecases.GetItemDetail;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailPresenter implements DetailContract.Presenter {


    private final DetailContract.View mDetailView;
    private final UseCaseHandler mUseCaseHandler;
    private final GetItemDetail mGetDetail;



    public DetailPresenter(@NonNull UseCaseHandler useCaseHandler,
                           @NonNull DetailContract.View detailView,
                           @NonNull GetItemDetail getDetail){
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null!");
        mDetailView = checkNotNull(detailView,"Detail view cannot be null!");
        mGetDetail = checkNotNull(getDetail,"Get detail usecase cannot be null!");

    }


    @Override
    public void loadAssetDetail(String id, final boolean isMovie) {
        mDetailView.setLoadingIndicator(true);


            final GetItemDetail.RequestValues requestValue = new GetItemDetail.RequestValues(id,isMovie);
            mUseCaseHandler.execute(mGetDetail, requestValue, new UseCase.UseCaseCallback<GetItemDetail.ResponseValue>() {
                @Override
                public void onSuccess(GetItemDetail.ResponseValue response) {
                    mDetailView.setLoadingIndicator(false);

                    mDetailView.setBackDropImage(response.getmItem().getBackdropPath());
                    if (isMovie) {
                        mDetailView.setTitle(response.getmItem().getTitle());

                        mDetailView.setDetail(response.getmItem().getTagline(), response.getmItem().getOverview(), response.getmItem().getReleaseDate());
                    } else {
                        /* sets series title*/
                        mDetailView.setTitle(response.getmItem().getName());
                        mDetailView.setDetail("", response.getmItem().getOverview(), response.getmItem().getFirstAirDate());
                    }
                }

                @Override
                public void onError() {
                    mDetailView.setLoadingIndicator(false);
                    mDetailView.showLoadingError();
                }
            });


    }


    @Override
    public void start() {

    }
}
