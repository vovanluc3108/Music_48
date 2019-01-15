package com.framgia.music_48.data.source.remote;

import com.framgia.music_48.data.model.Song;
import java.util.List;

public interface DataRemoteListener {
    void onFetchSongSuccess(List<Song> songs);

    void onFetchSongError(Exception ex);
}
