package com.nelson.gifdownloaderv1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nelson.gifdownloaderv1.R;
import com.nelson.gifdownloaderv1.model.Image;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Copyright yammer (C) 2016
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {

    private Context _Context;
    private List<Image> _ImageList = new ArrayList<>();
    private final PublishSubject<Image> onClickSubject = PublishSubject.create();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView trendingImage;

        ViewHolder(View view) {
            super(view);
            trendingImage = (ImageView) view.findViewById(R.id.item_image_view);
        }
    }

    public GridViewAdapter(Context context, List<Image> imageList) {
        _Context = context;
        _ImageList.clear();
        _ImageList.addAll(imageList);
    }

    @Override
    public GridViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_view_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Image image = _ImageList.get(position);
        if (image != null) {
            Glide.with(_Context).load(image.getUrl()).centerCrop().into(holder.trendingImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSubject.onNext(image);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return _ImageList.size();
    }

    public void updateImages(List<Image> imageList) {
        _ImageList.clear();
        _ImageList.addAll(imageList);
        notifyDataSetChanged();
    }

    public Observable<Image> getPositionClicks() {
        return onClickSubject.asObservable();
    }
}