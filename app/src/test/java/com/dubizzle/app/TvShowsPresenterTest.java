package com.dubizzle.app;

import com.dubizzle.app.callbacks.DiscoveryResponseCallback;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.DiscoveryResponse;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.domain.usecases.GetFilteredTVShows;
import com.dubizzle.app.domain.usecases.GetTVShows;
import com.dubizzle.app.tvshows.TvShowsContract;
import com.dubizzle.app.tvshows.TvShowsPresenter;

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

public class TvShowsPresenterTest {

    @Captor
    private ArgumentCaptor<DiscoveryResponseCallback> mLoadMoviesCallbackCaptor;

    @Mock
    private TmdbRepository repository;

    @Mock
    private TvShowsContract.View  seriesView;

    private TvShowsPresenter presenter;

    private DiscoveryResponse response;


    @Before
    public void setupTVShowsPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = givenSeriesPresenter();
        when(seriesView.isActive()).thenReturn(true);
        ArrayList<TmdbAsset> assets = new ArrayList<>();
        assets.add(new TmdbAsset("Test","testpath",10,"1234"));
        assets.add(new TmdbAsset("Test1","testpath",10,"1234"));
        assets.add(new TmdbAsset("Test2","testpath",10,"1234"));
        response = new DiscoveryResponse(1,100,100,assets);

    }

    private TvShowsPresenter givenSeriesPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler();
        GetTVShows getTVShows = new GetTVShows(repository);
        GetFilteredTVShows filteredShows = new GetFilteredTVShows(repository);

        return new TvShowsPresenter(useCaseHandler,seriesView,getTVShows,filteredShows);
    }


    @Test
    public void loadPopularItemsIntoView() {

        presenter.loadItems(true);

        verify(seriesView).setLoadingIndicator(true);
        verify(repository).getPopularShows(eq(1),mLoadMoviesCallbackCaptor.capture());
        mLoadMoviesCallbackCaptor.getValue().onSuccess(response);
        verify(seriesView).setLoadingIndicator(false);
        ArgumentCaptor<ArrayList> showItemsArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(seriesView).showItems(eq(0),showItemsArgumentCaptor.capture());
        assertTrue(showItemsArgumentCaptor.getValue().size() == 3);
    }

    @Test
    public void loadFilterItemsIntoView() {
        TMDBFilter filter = new TMDBFilter(2009,2010);
        presenter.updateFilters(filter);
        presenter.loadItems(true);

        verify(seriesView).setLoadingIndicator(true);
        verify(repository).getFilteredSeries(eq(2010),eq(2009),eq(1),mLoadMoviesCallbackCaptor.capture());
        mLoadMoviesCallbackCaptor.getValue().onSuccess(response);
        verify(seriesView).setLoadingIndicator(false);
        ArgumentCaptor<ArrayList> showItemsArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(seriesView).showItems(eq(0),showItemsArgumentCaptor.capture());
        assertTrue(showItemsArgumentCaptor.getValue().size() == 3);
    }
}
