package com.framgia.music_48.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicService extends Service
        implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    public static final int DEFAULT_ONE = 1;
    public static final int DEFAULT_ZERO = 0;
    private IBinder mIBinder = new BindService();
    private MediaPlayer mPlayer;
    private int mPosition;
    private List<Song> mSongs = new ArrayList<>();
    private ServiceContract.onMediaPlayer mOnMediaPlayer;

    public static Intent getIntentService(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.BUNDLE_LIST_MUSIC,
                (ArrayList<? extends Parcelable>) songs);
        bundle.putInt(Constant.BUNDLE_POSITION, position);
        intent.putExtras(bundle);
        return intent;
    }

    public void setOnMediaPlayer(ServiceContract.onMediaPlayer onMediaPlayer) {
        mOnMediaPlayer = onMediaPlayer;
    }

    @Override
    public void onCreate() {
        mIBinder = new BindService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            mSongs = Objects.requireNonNull(intent.getExtras())
                    .getParcelableArrayList(Constant.BUNDLE_LIST_MUSIC);
            mPosition = intent.getExtras().getInt(Constant.BUNDLE_POSITION);
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void musicPlay() {
        Song song = mSongs.get(mPosition);
        stopMusic();
        initMediaPlayer();
        getMusic(song);
        updateUI(song);
    }

    private void updateUI(Song song) {
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onUpdateUIListener(song);
            mOnMediaPlayer.onUpdateButtonStateListener(false);
        }
    }

    private void stopMusic() {
        if (mPlayer != null && isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    private void getMusic(Song song) {
        mPlayer.reset();
        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(Constant.SITE_TRACKS
                    + Constant.TRACKS
                    + song.getId()
                    + Constant.STREAM
                    + Constant.API_KEY_TRACK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setOnPreparedListener(MusicService.this);
        mPlayer.prepareAsync();
    }

    private void initMediaPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
    }

    public void musicPause() {
        if (isPlaying()) {
            mPlayer.pause();
            mOnMediaPlayer.onUpdateButtonStateListener(false);
        }
    }

    public void reset() {
        int currentDuration = mPlayer.getCurrentPosition();
        mPlayer.seekTo(currentDuration);
        mPlayer.start();
        mOnMediaPlayer.onUpdateButtonStateListener(true);
    }

    public void musicPlayNext() {
        mPosition++;
        if (mPosition >= mSongs.size()) {
            mPosition = mSongs.size() - DEFAULT_ONE;
        }
        musicPlay();
    }

    public void musicPlayPrevious() {
        mPosition--;
        if (mPosition < DEFAULT_ZERO) {
            mPosition = DEFAULT_ZERO;
        }
        musicPlay();
    }

    public MediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onUpdateSeekBarListener();
        }
        musicPlayNext();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mp.setOnCompletionListener(this);
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onUpdateSeekBarListener();
            mOnMediaPlayer.onUpdateButtonStateListener(true);
        }
    }

    public class BindService extends Binder {
        public MusicService mMusicService() {
            return MusicService.this;
        }
    }
}
