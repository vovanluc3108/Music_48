package com.framgia.music_48.data;

import com.framgia.music_48.data.source.local.DataLocalListener;

public class SongRepository {
    private static SongRepository sInstance;
    private SongDataSource.localDataSource mLocalDataSource;
    private SongDataSource.remoteDataSource mRemoteDataSourece;

    private SongRepository(SongDataSource.localDataSource localDataSource,
            SongDataSource.remoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSourece = remoteDataSource;
    }

    public static SongRepository getsInstance(SongDataSource.localDataSource localDataSource,
            SongDataSource.remoteDataSource remoteDataSource){
        if (sInstance == null){
            sInstance = new SongRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }

    public void getDataLocal(DataLocalListener songDataLocalListener){
        mLocalDataSource.getDataLocal(songDataLocalListener);
    }
}
