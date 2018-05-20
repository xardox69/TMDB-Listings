package com.dubizzle.app.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dubizzle.app.R;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.widget.AssetsAdapter;
import com.dubizzle.app.widget.MyScrollListener;

import java.util.ArrayList;

import static com.dubizzle.app.utils.AppConsts.ASSETS_TO_SHOW_IN_GRID;

public abstract class BaseTMDBFragment extends Fragment implements View.OnClickListener {

    public AssetsAdapter mListAdapter;
    public RecyclerView recyclerView;
    public GridLayoutManager layoutManager;
    // snackbar to show api messages
    public Snackbar snackBar;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new AssetsAdapter(new ArrayList<TmdbAsset>(), getContext(),this);
        Log.v("tag",this.getTag().toString());
        setPresenter();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_homepage, container, false);

        snackBar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), getString(R.string.loading_assets), Snackbar.LENGTH_INDEFINITE);
        layoutManager = new GridLayoutManager(getContext(), ASSETS_TO_SHOW_IN_GRID);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_top_series);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mListAdapter);
        setHasOptionsMenu(true);

        setActionBarTitle();
        setScrollListener();
        startPresenter();


        return root;
    }

    public abstract void setActionBarTitle();
    public abstract void onAssetClicked(String id);
    public abstract void startPresenter();
    public abstract void setPresenter();
    public abstract void setScrollListener();
    public abstract void startFilterActivity();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.assets_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_filter:
                startFilterActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
        int position = holder.getAdapterPosition();
        onAssetClicked(mListAdapter.getItems().get(position).getId());
    }



}
