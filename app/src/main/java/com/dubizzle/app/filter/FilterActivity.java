package com.dubizzle.app.filter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dubizzle.app.R;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.usecases.ValidateInput;
import com.dubizzle.app.utils.AppConsts;

import java.util.Calendar;

public class FilterActivity  extends AppCompatActivity implements FilterContract.View {
    private EditText minYear;
    private EditText maxYear;
    private FilterPresenter filterPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        minYear = (EditText) findViewById(R.id.minYear);
        maxYear = (EditText) findViewById(R.id.maxYear);
        setPresenter();
    }


    public void onCancelClicked(View v){
        //Nothing should happen here
        setResult(Activity.RESULT_CANCELED);
        finish();

    }

    /**
     * callback for done button
     * @param v
     */
    public void onDoneClicked(View v){

        String minYearStr = minYear.getText().toString();
        String maxYearStr = maxYear.getText().toString();

        filterPresenter.isValidInput(minYearStr,maxYearStr,callback);

        }

    public ValidationCallback callback = new ValidationCallback() {
        @Override
        public void onValid(int maxYear, int minYear) {
            getIntent().putExtra(AppConsts.MAX_YEAR,maxYear);
            getIntent().putExtra(AppConsts.MIN_YEAR,minYear);
            finishActivity();
        }

        @Override
        public void onInvalid(int id) {
            showInvalidMessage(id);
        }
    };




    /**
     * Clears the filters
     * @param v the view
     */
    public void onResetClicked(View v){
        maxYear.getText().clear();
        minYear.getText().clear();
        getIntent().putExtra(AppConsts.MAX_YEAR,0);
        getIntent().putExtra(AppConsts.MIN_YEAR,0);
        finishActivity();
    }





    @Override
    public void showInvalidMessage(int id) {
        Toast.makeText(this,getString(id),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return this.isFinishing();
    }

    @Override
    public void finishActivity() {
        setResult(Activity.RESULT_OK,getIntent());
        finish();
    }


    @Override
    public void setPresenter() {
        filterPresenter = new FilterPresenter(UseCaseHandler.getInstance(),this,new ValidateInput());
    }


    UseCase.UseCaseCallback<ValidateInput.ResponseValue> caseCallback = new UseCase.UseCaseCallback<ValidateInput.ResponseValue>() {
        @Override
        public void onSuccess(ValidateInput.ResponseValue response) {
            getIntent().putExtra(AppConsts.MAX_YEAR,response.getMaxYear());
            getIntent().putExtra(AppConsts.MIN_YEAR,response.getMinYear());
            setResult(Activity.RESULT_OK,getIntent());
            finish();
        }

        @Override
        public void onError() {
            showInvalidMessage(R.string.invalid_txt);
        }
    };
}
