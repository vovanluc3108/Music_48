package com.framgia.music_48.screen.GenresDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_48.R;
import com.framgia.music_48.data.SongRepository;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.data.source.local.SongLocalDataSource;
import com.framgia.music_48.data.source.remote.SongRemoteDataSource;
import com.framgia.music_48.screen.Genres.GenresContract;
import com.framgia.music_48.screen.Genres.GenresPresenter;
import com.framgia.music_48.screen.GenresDetail.Adapter.GenresDetailAdapter;
import java.util.List;
import java.util.Objects;

public class GenresDetailFragment extends Fragment implements GenresContract.View {
    private static final String ARGUMENT_GENRES = "ARGUMENT_GENRES";
    private GenresDetailAdapter mGenresDetailAdapter;

    public static GenresDetailFragment newInstance(String genres) {
        GenresDetailFragment genresDetailFragment = new GenresDetailFragment();
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
        mGenresDetailAdapter = new GenresDetailAdapter();
        recyclerViewDetailGenres.setAdapter(mGenresDetailAdapter);
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
        mGenresDetailAdapter.updateDataSongs(songs);
    }

    @Override
    public void onError(Exception ex) {
    }
}
