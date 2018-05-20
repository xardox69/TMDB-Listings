package com.dubizzle.app;

import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.domain.usecases.GetFilteredMovies;
import com.dubizzle.app.domain.usecases.GetMovies;
import com.dubizzle.app.movies.MoviesContract;
import com.dubizzle.app.movies.MoviesPresenter;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviesPresenterTest {



    @Captor
    private ArgumentCaptor<DiscoveryResponseCallback> mLoadMoviesCallbackCaptor;

    @Mock
    private TmdbRepository repository;

    @Mock
    private MoviesContract.View moviesView;

    private MoviesPresenter presenter;

    private DiscoveryResponse response;


    @Before
    public void setupMoviesPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = givenMoviesPresenter();
        when(moviesView.isActive()).thenReturn(true);
         ArrayList<TmdbAsset> assets = new ArrayList<>();
        assets.add(new TmdbAsset("Test","testpath",10,"1234"));
        assets.add(new TmdbAsset("Test1","testpath",10,"1234"));
        assets.add(new TmdbAsset("Test2","testpath",10,"1234"));
         response = new DiscoveryResponse(1,100,100,assets);

    }

    private MoviesPresenter givenMoviesPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler();
        GetMovies getMovies = new GetMovies(repository);
        GetFilteredMovies filteredMovies = new GetFilteredMovies(repository);

        return new MoviesPresenter(useCaseHandler,moviesView,getMovies,filteredMovies);
    }


    @Test
    public void loadPopularItemsIntoView() {

        presenter.loadItems(true);

        verify(moviesView).setLoadingIndicator(true);
        verify(repository).getPopularMovies(eq(1),mLoadMoviesCallbackCaptor.capture());
        mLoadMoviesCallbackCaptor.getValue().onSuccess(response);
        verify(moviesView).setLoadingIndicator(false);
        ArgumentCaptor<ArrayList> showItemsArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(moviesView).showItems(eq(0),showItemsArgumentCaptor.capture());
        assertTrue(showItemsArgumentCaptor.getValue().size() == 3);
    }

    @Test
    public void loadFilterItemsIntoView() {
        TMDBFilter filter = new TMDBFilter(2009,2010);
        presenter.updateFilters(filter);
        presenter.loadItems(true);

        verify(moviesView).setLoadingIndicator(true);
        verify(repository).getFilteredMovies(eq(2010),eq(2009),eq(1),mLoadMoviesCallbackCaptor.capture());
        mLoadMoviesCallbackCaptor.getValue().onSuccess(response);
        verify(moviesView).setLoadingIndicator(false);
        ArgumentCaptor<ArrayList> showItemsArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(moviesView).showItems(eq(0),showItemsArgumentCaptor.capture());
        assertTrue(showItemsArgumentCaptor.getValue().size() == 3);
    }
}
