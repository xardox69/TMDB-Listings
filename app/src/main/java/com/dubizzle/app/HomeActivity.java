package com.dubizzle.app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dubizzle.app.R;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.data.remote.RemoteDataSource;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.model.TMDBFilter;
import com.dubizzle.app.domain.usecases.GetMovies;
import com.dubizzle.app.domain.usecases.GetTVShows;
import com.dubizzle.app.movies.MoviesFragment;
import com.dubizzle.app.movies.MoviesPresenter;
import com.dubizzle.app.tvshows.TvShowsFragment;
import com.dubizzle.app.tvshows.TvShowsPresenter;
import com.dubizzle.app.utils.ActivityUtils;
import com.dubizzle.app.utils.AppConsts;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private MoviesFragment moviesFragment;
    private TvShowsFragment showsFragment;
    public TMDBFilter currentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        currentFilter = new TMDBFilter(0,0);
        showsFragment = TvShowsFragment.newInstance();
        moviesFragment = MoviesFragment.newInstance();

        if(savedInstanceState  == null){
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), showsFragment, R.id.contentFrame,showsFragment.TAG);
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item_tv_shows:
                                if(getSupportFragmentManager().findFragmentById(R.id.contentFrame) instanceof TvShowsFragment){
                                    break ;
                                }else {
                                   showsFragment = (TvShowsFragment) getSupportFragmentManager().findFragmentByTag(TvShowsFragment.TAG) ;
                                    //getSupportFragmentManager().
                                    if(showsFragment == null){
                                        showsFragment = TvShowsFragment.newInstance();
                                    }

                                    ActivityUtils.addFragmentToActivity(
                                            getSupportFragmentManager(), showsFragment, R.id.contentFrame, TvShowsFragment.TAG);
                                }

                                break;
                            case R.id.list_navigation_menu_item_movies:
                                if(getSupportFragmentManager().findFragmentById(R.id.contentFrame) instanceof MoviesFragment){
                                    break ;
                                }else {
                                    moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(MoviesFragment.TAG);
                                    if(moviesFragment == null){
                                        moviesFragment = MoviesFragment.newInstance();
                                    }
                                    ActivityUtils.addFragmentToActivity(
                                            getSupportFragmentManager(), moviesFragment, R.id.contentFrame, MoviesFragment.TAG);
                                }


                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                      //  menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConsts.FILTER_SCREEN && resultCode == Activity.RESULT_OK) {
            Fragment mFragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);

            int minYear = data.getIntExtra(AppConsts.MIN_YEAR, 0);
            int maxYear = data.getIntExtra(AppConsts.MAX_YEAR, 0);

            currentFilter.setMaxYear(maxYear);
            currentFilter.setMinYear(minYear);



            if (mFragment instanceof TvShowsFragment) {
                // updates series fragment
                showsFragment.setFilter(currentFilter);
                showsFragment.startPresenter();

                if(moviesFragment.mPresenter!=null) {
                    moviesFragment.setFilter(currentFilter);
                }
            } else if (mFragment instanceof MoviesFragment) {
                //updates movies fragment
                moviesFragment.setFilter(currentFilter);
                moviesFragment.startPresenter();

                if(showsFragment.mPresenter!=null) {
                    showsFragment.setFilter(currentFilter);
                }
            }

        }else{
            // doing nothing for cancel case
        }
        super.onActivityResult(requestCode, resultCode, data);

    }



}
