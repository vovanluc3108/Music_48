package com.framgia.music_48.screen.home;

import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.utils.BasePresenter;
import java.util.List;

public interface HomeContract {
    interface View {
        void onGetSongsLocalSuccess(List<Song> songs);

        void onGetSongsLocalError();
    }

    interface Presenter extends BasePresenter<View> {
        void getSongsLocal();
    }
}
