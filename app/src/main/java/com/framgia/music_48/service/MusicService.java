package com.framgia.music_48.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.utils.Constant;
import com.framgia.music_48.utils.Loop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicService extends Service
        implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    public static final int DEFAULT_ONE = 1;
    public static final int DEFAULT_ZERO = 0;
    public static final int NEGATIVE_ONE = -1;
    private IBinder mIBinder = new BindService();
    private MediaPlayer mPlayer;
    private int mPosition;
    private int mLoopState;
    private boolean mIsShuffle;
    private List<Song> mSongs = new ArrayList<>();
    private ServiceContract.onMediaPlayer mOnMediaPlayer;
    private ServiceContract.onMediaPlayerMini mOnMediaPlayerMini;

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

    public void setOnMediaPlayerMini(ServiceContract.onMediaPlayerMini onMediaPlayerMini) {
        mOnMediaPlayerMini = onMediaPlayerMini;
    }

    @Override
    public void onCreate() {
        mIBinder = new BindService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            List<Song> songs = intent.getParcelableArrayListExtra(Constant.BUNDLE_LIST_MUSIC);
            if (songs != null) {
                mSongs = songs;
                mPosition = intent.getIntExtra(Constant.BUNDLE_POSITION, 0);
                mIsShuffle = false;
            }
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

    public boolean checkMediaPlayer() {
        if (mSongs.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void playPauseSong() {
        if (isPlaying()) {
            mPlayer.pause();
            if (mOnMediaPlayerMini != null) {
                mOnMediaPlayerMini.onUpdateButtonStateListener(false);
            }
        } else {
            mPlayer.start();
            if (mOnMediaPlayerMini != null) {
                mOnMediaPlayerMini.onUpdateButtonStateListener(true);
            }
        }
    }

    public Song getCurrentSong() {
        return mSongs.get(mPosition);
    }

    private void updateUI(Song song) {
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onUpdateUIListener(song);
            mOnMediaPlayer.onUpdateButtonStateListener(false);
        }
        if (mOnMediaPlayerMini != null) {
            mOnMediaPlayerMini.onUpdateUIListener(song);
            mOnMediaPlayerMini.onUpdateButtonStateListener(false);
        }
    }

    private void stopMusic() {
        if (mPlayer != null && isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    public void setShuffleListener() {
        mIsShuffle = !mIsShuffle;
        if (mIsShuffle) {
            Random random = new Random();
            mPosition = random.nextInt(mSongs.size() - DEFAULT_ONE);
        }
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onShuffleStateListener(mIsShuffle);
        }
    }

    public void setLoopListener() {
        switch (mLoopState) {
            case Loop.NON_LOOP:
                mLoopState = Loop.ONE_LOOP;
                break;
            case Loop.ONE_LOOP:
                mLoopState = Loop.ALL_LOOP;
                break;
            case Loop.ALL_LOOP:
                mLoopState = Loop.NON_LOOP;
                break;
            default:
                mLoopState = Loop.NON_LOOP;
        }
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onLoopStateListener(mLoopState);
        }
    }

    private void musicLoop() {
        switch (mLoopState) {
            case Loop.NON_LOOP:
                musicPlayNext();
                break;
            case Loop.ONE_LOOP:
                musicPlay();
                break;
            case Loop.ALL_LOOP:
                if (mPosition >= mSongs.size()) {
                    mPosition = NEGATIVE_ONE;
                }
                musicPlayNext();
                break;
            default:
                mLoopState = Loop.NON_LOOP;
        }
    }

    private void getMusic(Song song) {
        mPlayer.reset();
        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(song.getStreamUrl());
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
        mOnMediaPlayerMini.onUpdateButtonStateListener(true);
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

    public void downloadSong() {
        DownloadManager manager = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);
        String linkDownload = mSongs.get(mPosition).getStreamUrl();
        if (linkDownload != null) {
            Uri uri = Uri.parse(linkDownload);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                    .setDescription(getString(R.string.download))
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            mSongs.get(mPosition).getTitle() + ".mp3");
            request.allowScanningByMediaScanner();
            assert manager != null;
            manager.enqueue(request);
        }
    }

    public boolean checkDownloadable() {
        return mSongs.get(mPosition).isDownloadable();
    }

    public MediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onUpdateSeekBarListener();
        }
        musicLoop();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mp.setOnCompletionListener(this);
        if (mOnMediaPlayer != null) {
            mOnMediaPlayer.onUpdateSeekBarListener();
            mOnMediaPlayer.onUpdateButtonStateListener(true);
        }
        if (mOnMediaPlayerMini != null) {
            mOnMediaPlayerMini.onUpdateButtonStateListener(true);
        }
    }

    public class BindService extends Binder {
        public MusicService mMusicService() {
            return MusicService.this;
        }
    }
}
