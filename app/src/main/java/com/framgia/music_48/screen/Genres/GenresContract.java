package com.framgia.music_48.screen.Genres;

import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.utils.BasePresenter;
import java.util.List;

public interface GenresContract {
    interface View {
        void onGetSongsWithGenresSuccess(List<Song> songs);

        void onError(Exception ex);
    }

    interface Presenter extends BasePresenter<View> {
        void getSongsWithGenres(String genres);
    }
}
