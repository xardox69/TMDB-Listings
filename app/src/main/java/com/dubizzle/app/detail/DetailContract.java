package com.dubizzle.app.detail;

import com.dubizzle.app.common.BasePresenter;
import com.dubizzle.app.common.BaseView;

public interface DetailContract {

    interface View extends BaseView<DetailContract.Presenter> {
        void setLoadingIndicator(boolean active);
        void setBackDropImage(String imageUrl);
        void setTitle(String title);
        void setDetail(String tagLine, String overview,String releaseDate);
        void showLoadingError();
        boolean isActive();


    }


    interface Presenter extends BasePresenter {
        void loadAssetDetail(String id, boolean isMovie);
    }
}
