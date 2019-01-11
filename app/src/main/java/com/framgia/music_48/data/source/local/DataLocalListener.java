package com.framgia.music_48.data.source.local;

import com.framgia.music_48.data.model.Song;
import java.util.List;

public interface DataLocalListener {
    void onSuccess(List<Song> songs);
    void onError();
}
