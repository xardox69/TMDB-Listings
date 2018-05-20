package com.dubizzle.app.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubizzle.app.R;
import com.dubizzle.app.data.TmdbRepository;
import com.dubizzle.app.data.remote.RemoteDataSource;
import com.dubizzle.app.domain.UseCaseHandler;
import com.dubizzle.app.domain.usecases.GetItemDetail;
import com.dubizzle.app.utils.AppConsts;
import com.dubizzle.app.utils.ImageUtils;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    /* Imageview for Backdrop image */
    private ImageView backdrop;
    CollapsingToolbarLayout collapsingToolbarLayout;
    DetailPresenter presenter;
    /* Textviews for  asset details*/
    private TextView tagLineTxt,overviewTxt,releaseDateTxt;

    /* Asset id from extras*/
    private String id;
    private boolean isMovie = false;
    private Snackbar snackbar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        setPresenter();
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        //loading asset
        presenter.loadAssetDetail(id,isMovie);
    }

    /**
     * Initializes views
     */
    private void initViews() {
        snackbar = Snackbar
                .make(findViewById(android.R.id.content), getString(R.string.loading_assets), Snackbar.LENGTH_INDEFINITE);
        backdrop = (ImageView) findViewById(R.id.toolbarImage);
        tagLineTxt = (TextView)  findViewById(R.id.tag_line);
        overviewTxt= (TextView)  findViewById(R.id.overview);
        releaseDateTxt = (TextView) findViewById(R.id.release_date);

        // getting asset id
        id = getIntent().getStringExtra(AppConsts.ASSET_ID);
        isMovie = getIntent().getBooleanExtra(AppConsts.IS_MOVIE,false);
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if(active){
            snackbar.show();
        }else{
            snackbar.dismiss();
        }

    }

    @Override
    public void setBackDropImage( String imageUrl) {
        ImageUtils.loadImage(this,backdrop,imageUrl);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void setDetail(String tagLine, String overview,String releaseDate) {
        tagLineTxt.setText(tagLine);
        overviewTxt.setText(getString(R.string.synopsis) + overview);
        releaseDateTxt.setText(getString(R.string.release_date) + releaseDate);

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public boolean isActive() {
        return this.isFinishing();
    }


    @Override
    public void setPresenter() {
        presenter = new DetailPresenter(UseCaseHandler.getInstance(),this,
                new GetItemDetail(TmdbRepository.getInstance(RemoteDataSource.getInstance(this))));
    }
}