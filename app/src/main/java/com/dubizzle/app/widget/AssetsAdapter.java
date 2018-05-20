package com.dubizzle.app.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dubizzle.app.R;
import com.dubizzle.app.domain.model.TmdbAsset;
import com.dubizzle.app.utils.ImageUtils;

import java.util.ArrayList;

public class AssetsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<TmdbAsset> items;
    private Context context;
    private View.OnClickListener clickListener;

    public AssetsAdapter(ArrayList<TmdbAsset> items, Context context, View.OnClickListener listener) {
        this.items = items;
        this.context = context;
        this.clickListener = listener;
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content_item, parent, false);
        v.setOnClickListener(clickListener);
        return new ContentHolderItem(v);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TmdbAsset item =  items.get(position);
        ContentHolderItem contentItem = (ContentHolderItem)holder;
        ImageUtils.loadImage(context,contentItem.image,item.getPosterPath());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ContentHolderItem extends RecyclerView.ViewHolder{
        ImageView image;
        public ContentHolderItem(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.poster);
        }
    }

    public ArrayList<TmdbAsset> getItems() {
        return items;
    }


}
