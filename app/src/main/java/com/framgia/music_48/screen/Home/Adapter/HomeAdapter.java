package com.framgia.music_48.screen.Home.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.utils.ParseDuration;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Song> mSongs;

    public HomeAdapter() {
        mSongs = new ArrayList<>();
    }

    public void updateDataSongs(List<Song> songs) {
        if (mSongs != null) {
            mSongs.clear();
        }
        assert mSongs != null;
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_home, viewGroup, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder viewHolder, int i) {
        viewHolder.bindData(mSongs.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewPoster;
        private TextView mTextViewSong;
        private TextView mTextViewSinger;
        private TextView mTextViewFullDuration;

        HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            mTextViewSong = itemView.findViewById(R.id.textViewSong);
            mTextViewSinger = itemView.findViewById(R.id.textViewSinger);
            mTextViewFullDuration = itemView.findViewById(R.id.textViewFullDuration);
        }

        void bindData(Song song) {
            mTextViewSong.setText(song.getTitle());
            mTextViewSinger.setText(song.getSinger());
            mTextViewFullDuration.setText(ParseDuration.parseDurationToStringTime(
                    Long.parseLong(song.getFullDuration())));
            Glide.with(itemView.getContext())
                    .applyDefaultRequestOptions(
                            new RequestOptions().placeholder(R.drawable.song_noimage))
                    .load(song.getPoster())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageViewPoster);
        }
    }
}
