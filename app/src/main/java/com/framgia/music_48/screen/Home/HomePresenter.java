package com.framgia.music_48.screen.Home;

import com.framgia.music_48.data.SongRepository;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.data.source.local.DataLocalListener;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private SongRepository mSongRepository;

    public HomePresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    @Override
    public void getSongsLocal() {
        mSongRepository.getDataLocal(new DataLocalListener() {
            @Override
            public void onGetSongLocalSuccess(List<Song> songs) {
                mView.onGetSongsLocalSuccess(songs);
            }

            @Override
            public void onGetSongLocalError() {
                mView.onGetSongsLocalError();
            }
        });
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }
}
