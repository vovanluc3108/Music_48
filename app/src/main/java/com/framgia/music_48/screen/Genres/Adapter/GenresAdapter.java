package com.framgia.music_48.screen.Genres.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Genres;
import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresHolder> {
    private List<Genres> mGenres;
    private Context mContext;

    public GenresAdapter(Context context, List<Genres> genres) {
        mContext = context;
        mGenres = genres;
    }

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_genres, viewGroup, false);
        return new GenresHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresHolder genresHolder, int i) {
        genresHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    static class GenresHolder extends RecyclerView.ViewHolder {
        public static final String DRAWABLE = "drawable";
        private ImageView mImageViewGenres;
        private Context mContext;

        public GenresHolder(Context context, @NonNull View itemView) {
            super(itemView);
            mContext = context;
            mImageViewGenres = itemView.findViewById(R.id.imageGenres);
        }

        void bindData(Genres genres) {
            mImageViewGenres.setImageResource(mContext.getResources()
                    .getIdentifier(genres.getImageGenres(), DRAWABLE, mContext.getPackageName()));
        }
    }
}
