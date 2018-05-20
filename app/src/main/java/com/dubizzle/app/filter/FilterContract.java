package com.dubizzle.app.filter;

import com.dubizzle.app.common.BasePresenter;
import com.dubizzle.app.common.BaseView;
import com.dubizzle.app.common.ItemsBasePresenter;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.domain.usecases.ValidateInput;
import com.dubizzle.app.movies.MoviesContract;

public interface FilterContract  {


    interface View extends BaseView<FilterContract.Presenter> {


        void showInvalidMessage(int id);

        boolean isActive();

        void finishActivity();




    }

    interface Presenter extends BasePresenter {

        /**
         * Validates the input dates
         * @param minYearStr the minimum year
         * @param maxYearStr the maximum year
         * @return
         */
        void isValidInput(String minYearStr, String maxYearStr,ValidationCallback callback);

    }
}
