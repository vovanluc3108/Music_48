package com.framgia.music_48.data;

import com.framgia.music_48.data.source.local.DataLocalListener;

public interface SongDataSource {
    interface localDataSource {
        void getDataLocal(DataLocalListener songDataLocalListener);
    }

    interface remoteDataSource {

    }
}
