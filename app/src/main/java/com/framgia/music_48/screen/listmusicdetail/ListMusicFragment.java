package com.framgia.music_48.screen.listmusicdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.framgia.music_48.R;
import com.framgia.music_48.data.SongRepository;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.data.source.local.SongLocalDataSource;
import com.framgia.music_48.data.source.remote.SongRemoteDataSource;
import com.framgia.music_48.screen.genres.GenresContract;
import com.framgia.music_48.screen.genres.GenresPresenter;
import com.framgia.music_48.screen.listmusicdetail.adapter.ListMusicAdapter;
import com.framgia.music_48.screen.playmusic.PlayMusicActivity;
import com.framgia.music_48.utils.OnItemClickListener;
import java.util.List;
import java.util.Objects;

public class ListMusicFragment extends Fragment
        implements GenresContract.View, OnItemClickListener<Integer> {
    private static final String ARGUMENT_GENRES = "ARGUMENT_GENRES";
    private ListMusicAdapter mListMusicAdapter;
    private List<Song> mSongs;

    public static ListMusicFragment newInstance(String genres) {
        ListMusicFragment genresDetailFragment = new ListMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_GENRES, genres);
        genresDetailFragment.setArguments(bundle);
        return genresDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres_detail, container, false);
        initView(view);
        initPresenter();
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerViewDetailGenres = view.findViewById(R.id.recyclerViewGenresDetail);
        recyclerViewDetailGenres.setHasFixedSize(true);
        mListMusicAdapter = new ListMusicAdapter();
        mListMusicAdapter.setListener(this);
        recyclerViewDetailGenres.setAdapter(mListMusicAdapter);
    }

    private void initPresenter() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getsInstance(
                Objects.requireNonNull(getContext()).getContentResolver());
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getsInstance();
        SongRepository songRepository =
                SongRepository.getsInstance(songLocalDataSource, songRemoteDataSource);
        GenresPresenter genresPresenter = new GenresPresenter(songRepository);
        genresPresenter.setView(this);
        assert getArguments() != null;
        genresPresenter.getSongsWithGenres(getArguments().getString(ARGUMENT_GENRES));
    }

    @Override
    public void onGetSongsWithGenresSuccess(List<Song> songs) {
        if (songs != null) {
            mListMusicAdapter.updateDataSongs(songs);
        }
        mSongs = songs;
    }

    @Override
    public void onError(Exception ex) {
        Toast.makeText(getContext(), "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickListener(Integer position) {
        startActivity(
                PlayMusicActivity.getPlayMusicIntent(getContext(), mSongs.get(position), position));
    }
}
