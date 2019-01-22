package com.framgia.music_48.screen.playmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.service.MusicService;
import com.framgia.music_48.service.ServiceContract;
import com.framgia.music_48.utils.Constant;
import com.framgia.music_48.utils.Loop;
import com.framgia.music_48.utils.ParseDuration;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayMusicActivity extends AppCompatActivity
        implements ServiceContract.onMediaPlayer, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {
    public static final int REQUEST_CODE = 148;
    private ImageView mImageViewBack;
    private ImageView mImageViewPlay;
    private ImageView mImageViewDownload;
    private ImageView mImageViewNext;
    private ImageView mImageViewPrevious;
    private ImageView mImageViewShuffle;
    private ImageView mImageViewLoop;
    private TextView mTextViewSong;
    private TextView mTextViewSinger;
    private TextView mTextViewFullDuration;
    private TextView mTextViewDuration;
    private SeekBar mSeekBarMusic;
    private boolean mIsPlay;
    private boolean mIsBound;
    private CircleImageView mCircleImageViewPoster;
    private MusicService mMusicService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.BindService bindService = (MusicService.BindService) service;
            mMusicService = bindService.mMusicService();
            mMusicService.setOnMediaPlayer(PlayMusicActivity.this);
            mMusicService.musicPlay();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    public static Intent getPlayMusicIntent(Context context, Song song) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_MUSIC, song);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initView();
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBound) {
            unbindService(mConnection);
        }
        mIsBound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMusicService.setOnMediaPlayer(null);
    }

    private void getData() {
        Song song = getIntent().getParcelableExtra(Constant.BUNDLE_MUSIC);
        updateUI(song);
    }

    private void updateUI(Song song) {
        mTextViewSong.setText(song.getTitle());
        mTextViewSinger.setText(song.getSinger());
        mTextViewFullDuration.setText(
                ParseDuration.parseDurationToStringTime(Long.parseLong(song.getFullDuration())));
        Glide.with(this)
                .applyDefaultRequestOptions(
                        new RequestOptions().placeholder(R.drawable.song_noimage).circleCrop())
                .load(song.getPoster())
                .into(mCircleImageViewPoster);
        mSeekBarMusic.setMax(Integer.parseInt(song.getFullDuration()));
    }

    private void initView() {
        mCircleImageViewPoster = findViewById(R.id.circleImagePoster);
        mImageViewBack = findViewById(R.id.imageViewBack);
        mImageViewDownload = findViewById(R.id.imageViewDownload);
        mImageViewPrevious = findViewById(R.id.imageViewPrevious);
        mImageViewNext = findViewById(R.id.imageViewNext);
        mImageViewPlay = findViewById(R.id.imageViewPlay);
        mImageViewLoop = findViewById(R.id.imageViewLoop);
        mImageViewShuffle = findViewById(R.id.imageViewShuffle);
        mTextViewFullDuration = findViewById(R.id.textViewFullDuration);
        mTextViewDuration = findViewById(R.id.textViewDuration);
        mTextViewSinger = findViewById(R.id.textViewSinger);
        mTextViewSong = findViewById(R.id.textViewSong);
        mSeekBarMusic = findViewById(R.id.seekBarMusic);
        mImageViewShuffle = findViewById(R.id.imageViewShuffle);
        mImageViewPlay.setOnClickListener(this);
        mImageViewPrevious.setOnClickListener(this);
        mImageViewNext.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        mImageViewLoop.setOnClickListener(this);
        mImageViewShuffle.setOnClickListener(this);
        mSeekBarMusic.setOnSeekBarChangeListener(this);
        mImageViewDownload.setOnClickListener(this);
    }

    @Override
    public void onUpdateUIListener(Song song) {
        if (song != null) {
            updateUI(song);
        }
    }

    @Override
    public void onUpdateButtonStateListener(boolean isPlaying) {
        if (isPlaying) {
            mImageViewPlay.setImageResource(R.drawable.ic_button_pause);
        } else {
            mImageViewPlay.setImageResource(R.drawable.ic_button_play);
        }
    }

    @Override
    public void onUpdateSeekBarListener() {
        final int TIME_DELAY = 100;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextViewDuration.setText(ParseDuration.parseDurationToStringTime(
                        mMusicService.getMediaPlayer().getCurrentPosition()));
                mSeekBarMusic.setProgress(mMusicService.getMediaPlayer().getCurrentPosition());
                handler.postDelayed(this, TIME_DELAY);
            }
        }, TIME_DELAY);
    }

    @Override
    public void onLoopStateListener(int loopState) {
        updateLoop(loopState);
    }

    private void updateLoop(int loopState) {
        switch (loopState) {
            case Loop.NON_LOOP:
                mImageViewLoop.setImageResource(R.drawable.ic_button_non_loop);
                break;
            case Loop.ONE_LOOP:
                mImageViewLoop.setImageResource(R.drawable.ic_button_loop_one);
                break;
            case Loop.ALL_LOOP:
                mImageViewLoop.setImageResource(R.drawable.ic_button_loop_all);
                break;
        }
    }

    @Override
    public void onShuffleStateListener(boolean isShuffleState) {
        if (isShuffleState) {
            mImageViewShuffle.setImageResource(R.drawable.ic_button_active_shuffle);
        } else {
            mImageViewShuffle.setImageResource(R.drawable.ic_button_non_shuffle);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewPlay:
                setEventPlay();
                break;
            case R.id.imageViewPrevious:
                mMusicService.musicPlayPrevious();
                break;
            case R.id.imageViewNext:
                mMusicService.musicPlayNext();
                break;
            case R.id.imageViewLoop:
                mMusicService.setLoopListener();
                break;
            case R.id.imageViewShuffle:
                mMusicService.setShuffleListener();
                break;
            case R.id.imageViewDownload:
                if (mMusicService.checkDownloadable()) {
                    mMusicService.downloadSong();
                } else {
                    Toast.makeText(this, R.string.download_not_able, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imageViewBack:
                finish();
                break;
        }
    }

    private void setEventPlay() {
        if (mIsPlay) {
            mMusicService.reset();
            mIsPlay = false;
        } else {
            mIsPlay = true;
            mMusicService.musicPause();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMusicService.getMediaPlayer().seekTo(mSeekBarMusic.getProgress());
    }
}
