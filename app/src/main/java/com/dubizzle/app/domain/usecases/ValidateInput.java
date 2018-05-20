package com.dubizzle.app.domain.usecases;

import com.dubizzle.app.R;
import com.dubizzle.app.common.UseCase;

import java.util.Calendar;

public class ValidateInput extends UseCase<ValidateInput.RequestValues, ValidateInput.ResponseValue> {


    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if(getUseCaseCallback() == null)
            return;
        if(requestValues.minYear.length() !=4 || requestValues.maxYear.length() !=4 ){
            getUseCaseCallback().onSuccess(new ResponseValue(0,0,R.string.invalid_txt,false));
            return ;
        }
        int minYearInt;
        int maxYearInt;
        try {
            minYearInt = Integer.parseInt(requestValues.minYear);
            maxYearInt = Integer.parseInt(requestValues.maxYear);
        }catch (Exception e){
            e.printStackTrace();
            getUseCaseCallback().onSuccess(new ResponseValue(0,0,R.string.invalid_txt,false));
            return ;
        }

        Calendar minDate = Calendar.getInstance();
        minDate.setLenient(false);
        minDate.set(minYearInt,1,1);

        Calendar maxDate = Calendar.getInstance();
        maxDate.setLenient(false);
        maxDate.set(maxYearInt,1,1);

        /* max year is greater than current year*/
        if(maxDate.get(Calendar.YEAR) > Calendar.getInstance().get(Calendar.YEAR) ){
            getUseCaseCallback().onSuccess(new ResponseValue(0,0,R.string.invalid_max,false));
            return;
        }

        /* min year is greater than current year*/
        if(minDate.get(Calendar.YEAR) > Calendar.getInstance().get(Calendar.YEAR)){
            getUseCaseCallback().onSuccess(new ResponseValue(0,0,R.string.invalid_min,false));
            return;
        }
        /* max year is less than min year*/
        if(maxDate.get(Calendar.YEAR)<minDate.get(Calendar.YEAR)){
            getUseCaseCallback().onSuccess(new ResponseValue(0,0,R.string.max_less,false));
            return;
        }

        if(maxDate.get(Calendar.YEAR)< 1000){
            getUseCaseCallback().onSuccess(new ResponseValue(0,0,R.string.invalid_max,false));
            return;
        }

        if(minDate.get(Calendar.YEAR)< 1000){
            getUseCaseCallback().onSuccess(new ResponseValue(0,0,R.string.invalid_min,false));
            return;
        }

        getUseCaseCallback().onSuccess(new ResponseValue(minYearInt,maxYearInt,0,true));
        return ;

    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String minYear;
        private String maxYear;


        public RequestValues(String sMinYear,String sMaxYear) {
           minYear = sMinYear;
           maxYear = sMaxYear;
        }


    }


    public static final class ResponseValue implements UseCase.ResponseValue {
        private int minYear;
        private int maxYear;
        private int id;
        private boolean isValid;

        public int getMinYear() {
            return minYear;
        }

        public int getMaxYear() {
            return maxYear;
        }

        public int getId() {
            return id;
        }

        public boolean isValid() {
            return isValid;
        }

        public ResponseValue(int minYear, int maxYear, int mId, boolean mIsValid) {
            this.minYear = minYear;
            this.maxYear = maxYear;
            this.id = mId;
            this.isValid = mIsValid;
        }
    }
}
