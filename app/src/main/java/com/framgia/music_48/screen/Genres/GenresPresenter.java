package com.framgia.music_48.screen.Genres;

import com.framgia.music_48.data.SongRepository;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.data.source.remote.DataRemoteListener;
import java.util.List;

public class GenresPresenter implements GenresContract.Presenter {
    private GenresContract.View mView;
    private SongRepository mSongRepository;

    public GenresPresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    @Override
    public void setView(GenresContract.View view) {
        mView = view;
    }

    @Override
    public void getSongsWithGenres(String genres) {
        mSongRepository.getSongsByGenresRemote(genres, new DataRemoteListener() {
            @Override
            public void onFetchSongSuccess(List<Song> songs) {
                mView.onGetSongsWithGenresSuccess(songs);
            }

            @Override
            public void onFetchSongError(Exception ex) {
                mView.onError(ex);
            }
        });
    }
}
