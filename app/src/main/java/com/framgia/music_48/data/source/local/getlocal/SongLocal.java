package com.framgia.music_48.data.source.local.getlocal;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.data.source.local.DataLocalListener;
import java.util.ArrayList;

public class SongLocal {
    private DataLocalListener mDataLocalListener;
    private ContentResolver mContentResolver;

    public SongLocal(DataLocalListener dataLocalListener, ContentResolver resolver) {
        mDataLocalListener = dataLocalListener;
        mContentResolver = resolver;
    }

    public void getSongLocal() {
        ArrayList<Song> songs = new ArrayList<>();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = mContentResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int file = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int id = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int title = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int duration = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int poster = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int artist = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                String songId = musicCursor.getString(id);
                String songFile = musicCursor.getString(file);
                String songTittle = musicCursor.getString(title);
                String songArtist = musicCursor.getString(artist);
                String songDuration = musicCursor.getString(duration);
                String songPoster = musicCursor.getString(poster);

                Song song = new Song.SongBuilder().setID(songId)
                        .setPoster(songPoster)
                        .setSinger(songArtist)
                        .setUrl(songFile)
                        .setFullDuration(songDuration)
                        .setTitle(songTittle)
                        .build();
                songs.add(song);
            } while (musicCursor.moveToNext());
            musicCursor.close();
            mDataLocalListener.onSuccess(songs);
        } else {
            mDataLocalListener.onError();
        }
    }
}
