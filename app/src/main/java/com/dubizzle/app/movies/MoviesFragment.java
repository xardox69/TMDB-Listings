package com.dubizzle.app.movies;

import android.content.Intent;

import com.dubizzle.app.HomeActivity;
import com.dubizzle.app.R;
import com.dubizzle.app.common.BaseTMDBFragment;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.data.remote.RemoteDataSource;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.domain.usecases.GetFilteredMovies;
import com.dubizzle.app.domain.usecases.GetMovies;
import com.dubizzle.app.filter.FilterActivity;
import com.dubizzle.app.detail.DetailActivity;
import com.dubizzle.app.widget.MyScrollListener;

import java.util.ArrayList;

import static com.dubizzle.app.utils.AppConsts.ASSET_ID;
import static com.dubizzle.app.utils.AppConsts.FILTER_SCREEN;
import static com.dubizzle.app.utils.AppConsts.IS_MOVIE;
import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesFragment extends BaseTMDBFragment implements MoviesContract.View {
    public static final String TAG = "MoviesFragment";
    public MoviesContract.Presenter mPresenter;
    public MyScrollListener myScrollListener;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }


    public MoviesFragment() {
        // Requires empty public constructor
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        myScrollListener.setLoading(active);
        if(active){
            snackBar.show();
        }else{
            snackBar.dismiss();
        }


    }

    @Override
    public void showLoadingItemsError() {

    }

    @Override
    public void showItemDetails(String id) {
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra(ASSET_ID,id);
        intent.putExtra(IS_MOVIE,true);
        /* start detail activity*/
        startActivity(intent);

    }

    @Override
    public ArrayList<TmdbAsset> getItems() {
        return mListAdapter.getItems();
    }

    @Override
    public void showItems(int position, ArrayList<TmdbAsset> items) {
        mListAdapter.getItems().addAll(items);
        mListAdapter.notifyItemInserted(position);
    }

    @Override
    public void setFilter(TMDBFilter filter) {
        mPresenter.updateFilters(filter);
    }

    @Override
    public void refreshItems() {
        mListAdapter.getItems().clear();
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter() {
        mPresenter = new MoviesPresenter(UseCaseHandler.getInstance(),this,
                new GetMovies(TmdbRepository.getInstance(RemoteDataSource.getInstance(getContext()))),
                new GetFilteredMovies(TmdbRepository.getInstance(RemoteDataSource.getInstance(getContext()))));
        setFilter(  ((HomeActivity)getActivity()).currentFilter);

    }

    @Override
    public void setActionBarTitle() {
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.movies_title));
    }

    @Override
    public void onAssetClicked(String id) {
        showItemDetails(id);
    }

    @Override
    public void startPresenter() {
        mPresenter.start();
    }

    @Override
    public void setScrollListener() {
        myScrollListener = new MyScrollListener(layoutManager, mPresenter);
        recyclerView.setOnScrollListener(myScrollListener);

    }

    @Override
    public void startFilterActivity() {
        Intent intent = new Intent(getActivity(),FilterActivity.class);
        getActivity().startActivityForResult(intent,FILTER_SCREEN);
    }
}