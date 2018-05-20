package com.dubizzle.app;

import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.common.UseCase;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.DetailedAsset;
import com.dubizzle.app.domain.usecases.ValidateInput;
import com.dubizzle.app.filter.FilterContract;
import com.dubizzle.app.filter.FilterPresenter;
import com.dubizzle.app.filter.ValidationCallback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilterPresenterTest {
    
    @Mock
    FilterContract.View view;



    
    private FilterPresenter presenter;


    ValidateInput validateInput;


    @Captor
    private ArgumentCaptor<ValidationCallback> mValidCallbackCaptor;


    @Mock
    ValidationCallback callback;


    @Before
    public void setupFilterPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = givenFilterPresenter();
        when(view.isActive()).thenReturn(true);

    }

    private FilterPresenter givenFilterPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler();
        validateInput = new ValidateInput();
        return new FilterPresenter(useCaseHandler,view,validateInput);
    }



    @Test
    public void shouldValidate_correct_input(){
        presenter.isValidInput("2009", "2010",callback);
        verify(callback).onValid(2010,2009);
    }


    @Test
    public void shouldInValidate_incorrect_input(){
        presenter.isValidInput("9", "10",callback);
        verify(callback).onInvalid(R.string.invalid_txt);
    }
}
