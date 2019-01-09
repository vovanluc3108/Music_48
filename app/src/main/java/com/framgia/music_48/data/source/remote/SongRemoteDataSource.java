package com.framgia.music_48.data.source.remote;

import com.framgia.music_48.data.SongDataSource;

public class SongRemoteDataSource implements SongDataSource.remoteDataSource {
    private static SongRemoteDataSource sInstance;

    private SongRemoteDataSource() {
    }

    public static SongRemoteDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new SongRemoteDataSource();
        }
        return sInstance;
    }
}
