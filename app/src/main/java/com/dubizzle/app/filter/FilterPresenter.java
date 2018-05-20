package com.dubizzle.app.filter;

import android.widget.Toast;

import com.dubizzle.app.R;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.usecases.GetFilteredMovies;
import com.dubizzle.app.domain.usecases.ValidateInput;

import java.util.Calendar;

public class FilterPresenter implements FilterContract.Presenter {

    private FilterContract.View filterView;
    private final UseCaseHandler handler;
    private final ValidateInput validateInput;


    public FilterPresenter(UseCaseHandler mHandler,FilterContract.View filterView,ValidateInput mValidate) {
        this.filterView = filterView;
        this.handler = mHandler;
        this.validateInput = mValidate;
    }




    @Override
    public void isValidInput(String minYearStr, String maxYearStr, final ValidationCallback callback) {
        final ValidateInput.RequestValues requestValue = new ValidateInput.RequestValues(minYearStr,maxYearStr);
        handler.execute(validateInput, requestValue, new UseCase.UseCaseCallback<ValidateInput.ResponseValue>() {
            @Override
            public void onSuccess(ValidateInput.ResponseValue response) {
                if(response.isValid() && callback!=null){
                    callback.onValid(response.getMaxYear(),response.getMinYear());
                }else if(callback!=null){
                    callback.onInvalid(response.getId());
                }
            }

            @Override
            public void onError() {

            }
        });


    }

    @Override
    public void start() {

    }
}
