package com.framgia.music_48.data;

import com.framgia.music_48.data.source.local.DataLocalListener;
import com.framgia.music_48.data.source.remote.DataRemoteListener;

public interface SongDataSource {
    interface localDataSource {
        void getDataLocal(DataLocalListener songDataLocalListener);
    }

    interface remoteDataSource {
        void SongsByGenresRemote(String genres, DataRemoteListener dataRemoteListener);
    }
}
