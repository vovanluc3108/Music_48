package com.framgia.music_48.data.source.remote;

import com.framgia.music_48.data.SongDataSource;
import com.framgia.music_48.data.source.remote.fetchdata.SongFromURL;
import com.framgia.music_48.utils.Constant;

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

    @Override
    public void getDataRemote(DataRemoteListener dataRemoteListener, String genres) {
        String url = Constant.SITE_GENRES
                + Constant.CHARTS
                + Constant.KIND
                + Constant.API_KEY
                + Constant.GENRES
                + genres;
        new SongFromURL(dataRemoteListener).execute(url);
    }
}
