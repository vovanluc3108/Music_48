package com.framgia.music_48.screen.Genres.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Genres;
import com.framgia.music_48.utils.OnRecyclerViewClickListener;
import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresHolder> {
    private List<Genres> mGenres;
    private OnRecyclerViewClickListener mListener;

    public GenresAdapter(List<Genres> genres) {
        mGenres = genres;
    }

    public void setItemClickListener(OnRecyclerViewClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_genres, viewGroup, false);
        return new GenresHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresHolder genresHolder, int i) {
        genresHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    static class GenresHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public static final String DRAWABLE = "drawable";
        private ImageView mImageViewGenres;
        private OnRecyclerViewClickListener mListener;

        public GenresHolder(@NonNull View itemView, OnRecyclerViewClickListener listener) {
            super(itemView);
            mImageViewGenres = itemView.findViewById(R.id.imageGenres);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        void bindData(Genres genres) {
            mImageViewGenres.setImageResource(itemView.getResources()
                    .getIdentifier(genres.getImageGenres(), DRAWABLE,
                            itemView.getContext().getPackageName()));
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
