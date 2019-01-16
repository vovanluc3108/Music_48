package com.framgia.music_48.screen.listmusicdetail.adapter;

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
import com.framgia.music_48.utils.OnItemClickListener;
import com.framgia.music_48.utils.ParseDuration;
import java.util.ArrayList;
import java.util.List;

public class ListMusicAdapter
        extends RecyclerView.Adapter<ListMusicAdapter.ListMusicHolder> {
    private List<Song> mSongs;
    private OnItemClickListener<Integer> mListener;

    public ListMusicAdapter() {
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

    public void setListener(OnItemClickListener<Integer> listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ListMusicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_genres_detail, viewGroup, false);
        return new ListMusicHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMusicHolder genresDetailHolder, int i) {
        genresDetailHolder.bindData(mSongs.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    static class ListMusicHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView mImageViewPoster;
        private TextView mTextViewSong;
        private TextView mTextViewSinger;
        private TextView mDuration;
        private OnItemClickListener<Integer> mListener;

        ListMusicHolder(@NonNull View itemView, OnItemClickListener<Integer> listener) {
            super(itemView);
            mListener = listener;
            mImageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            mTextViewSong = itemView.findViewById(R.id.textViewSong);
            mTextViewSinger = itemView.findViewById(R.id.textViewSinger);
            mDuration = itemView.findViewById(R.id.textViewFullDuration);
            itemView.setOnClickListener(this);
        }

        void bindData(Song song) {
            mTextViewSong.setText(song.getTitle());
            mTextViewSinger.setText(song.getSinger());
            mDuration.setText(ParseDuration.parseDurationToStringTime(
                    Long.parseLong(song.getFullDuration())));
            Glide.with(itemView.getContext())
                    .applyDefaultRequestOptions(
                            new RequestOptions().placeholder(R.drawable.song_noimage).circleCrop())
                    .load(song.getPoster())
                    .into(mImageViewPoster);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClickListener(getAdapterPosition());
            }
        }
    }
}
