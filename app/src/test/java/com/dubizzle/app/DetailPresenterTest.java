package com.dubizzle.app;

import com.dubizzle.app.callbacks.DetailResponseCallback;
import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.detail.DetailContract;
import com.dubizzle.app.detail.DetailPresenter;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.DetailedAsset;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.domain.usecases.GetFilteredMovies;
import com.dubizzle.app.domain.usecases.GetItemDetail;
import com.dubizzle.app.domain.usecases.GetMovies;
import com.dubizzle.app.movies.MoviesContract;
import com.dubizzle.app.movies.MoviesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailPresenterTest {




    @Captor
    private ArgumentCaptor<DetailResponseCallback> mLoadDetailCallbackCaptor;

    @Mock
    private TmdbRepository repository;

    @Mock
    private DetailContract.View view;

    private DetailPresenter presenter;

    private DetailedAsset response;

    private GetItemDetail getItemDetail;


    @Before
    public void setupDetailsPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = givenDetailsPresenter();
        when(view.isActive()).thenReturn(true);
        response = new DetailedAsset("Test Item");

    }

    private DetailPresenter givenDetailsPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler();
        getItemDetail  = new GetItemDetail(repository);
        return new DetailPresenter(useCaseHandler,view,getItemDetail);
    }


    @Test
    public void loadPopularItemDetailIntoView() {

        presenter.loadAssetDetail("1234A",true);

        verify(view).setLoadingIndicator(true);
        verify(repository).getItemDetails(eq(true),eq("1234A"),mLoadDetailCallbackCaptor.capture());
        mLoadDetailCallbackCaptor.getValue().onSuccess(response);
        verify(view).setLoadingIndicator(false);
        ArgumentCaptor<String> showStringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(view).setTitle(showStringArgumentCaptor.capture());
        assertTrue(showStringArgumentCaptor.getValue().equalsIgnoreCase(response.getTitle()));
    }
}
