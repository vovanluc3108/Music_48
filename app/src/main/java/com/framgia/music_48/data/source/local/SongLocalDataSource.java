package com.framgia.music_48.data.source.local;

import android.content.ContentResolver;
import com.framgia.music_48.data.SongDataSource;
import com.framgia.music_48.data.source.local.getlocal.SongLocal;

public class SongLocalDataSource implements SongDataSource.localDataSource {
    private static SongLocalDataSource sInstance;
    private ContentResolver mContentResolver;

    public SongLocalDataSource(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public static SongLocalDataSource getsInstance(ContentResolver contentResolver){
        if (sInstance == null){
            sInstance = new SongLocalDataSource(contentResolver);
        }
        return sInstance;
    }

    @Override
    public void getDataLocal(DataLocalListener songDataLocalListener) {
        SongLocal songLocal = new SongLocal(songDataLocalListener, mContentResolver);
        songLocal.getSongLocal();
    }
}
