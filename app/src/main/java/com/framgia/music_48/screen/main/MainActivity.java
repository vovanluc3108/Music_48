package com.framgia.music_48.screen.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.screen.main.adapter.ViewPagerAdapter;
import com.framgia.music_48.screen.playmusic.PlayMusicActivity;
import com.framgia.music_48.service.MusicService;
import com.framgia.music_48.service.ServiceContract;
import com.framgia.music_48.utils.OptionTab;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements ServiceContract.onMediaPlayerMini, View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mNavigationView;
    private ViewPager mViewPagerMain;
    private MusicService mMusicService;
    private boolean mIsBound;
    private boolean mIsPlay;
    private View mViewMiniControler;
    private ImageView mImageViewNextMini;
    private ImageView mImageViewPreviousMini;
    private ImageView mImageViewPlayMini;
    private TextView mTextViewSongMini;
    private TextView mTextViewSingerMini;
    private ImageView mImageViewPosterMini;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.BindService bindService = (MusicService.BindService) service;
            mMusicService = bindService.mMusicService();
            mMusicService.setOnMediaPlayerMini(MainActivity.this);
            mIsBound = true;
            if (mMusicService.checkMediaPlayer()) {
                mViewMiniControler.setVisibility(View.VISIBLE);
            } else {
                mViewMiniControler.setVisibility(View.GONE);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewMini();
        initDrawerLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMusicService.setOnMediaPlayer(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBound) {
            unbindService(mConnection);
        }
        mIsBound = false;
    }

    private void initViewMini() {
        mImageViewNextMini = findViewById(R.id.imageViewNextMini);
        mImageViewPlayMini = findViewById(R.id.imageViewPlayMini);
        mImageViewPreviousMini = findViewById(R.id.imageViewPreviousMini);
        mImageViewPosterMini = findViewById(R.id.imageViewPosterMini);
        mTextViewSingerMini = findViewById(R.id.textViewSingerMini);
        mTextViewSongMini = findViewById(R.id.textViewSongMini);
        mViewMiniControler = findViewById(R.id.viewMiniController);
        mTextViewSongMini.setSelected(true);
        mImageViewPlayMini.setOnClickListener(this);
        mImageViewPreviousMini.setOnClickListener(this);
        mImageViewNextMini.setOnClickListener(this);
        mViewMiniControler.setOnClickListener(this);
    }

    private void initDrawerLayout() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.close,
                        R.string.open);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initView() {
        mViewPagerMain = findViewById(R.id.viewPagerMain);
        mDrawerLayout = findViewById(R.id.drawerLayoutMain);
        mToolbar = findViewById(R.id.toolbarMain);
        mNavigationView = findViewById(R.id.bottomNavigationView);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPagerMain.setAdapter(pagerAdapter);
        mNavigationView.setOnNavigationItemSelectedListener(this);
        mViewPagerMain.addOnPageChangeListener(this);
    }

    @Override
    public void onUpdateUIListener(Song song) {
        if (song != null) {
            updateSong(song);
        }
    }

    private void updateSong(Song song) {
        mTextViewSongMini.setText(song.getTitle());
        mTextViewSingerMini.setText(song.getSinger());
        Glide.with(this)
                .applyDefaultRequestOptions(
                        new RequestOptions().placeholder(R.drawable.song_noimage).circleCrop())
                .load(song.getPoster())
                .into(mImageViewPosterMini);
    }

    @Override
    public void onUpdateButtonStateListener(boolean isPlaying) {
        if (isPlaying) {
            mImageViewPlayMini.setImageResource(R.drawable.ic_pause);
        } else {
            mImageViewPlayMini.setImageResource(R.drawable.ic_play);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewPlayMini:
                mMusicService.playPauseSong();
                break;
            case R.id.imageViewNextMini:
                mMusicService.musicPlayNext();
                break;
            case R.id.imageViewPreviousMini:
                mMusicService.musicPlayPrevious();
                break;
            case R.id.viewMiniController:
                startActivity(PlayMusicActivity.getPlayMusicIntent(MainActivity.this,
                        mMusicService.getCurrentSong()));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuHome:
                mViewPagerMain.setCurrentItem(OptionTab.TAB_HOME);
                break;
            case R.id.menuGenres:
                mViewPagerMain.setCurrentItem(OptionTab.TAB_GENRES);
                break;
            case R.id.menuSearch:
                mViewPagerMain.setCurrentItem(OptionTab.TAB_SEARCH);
                break;
            default:
                mViewPagerMain.setCurrentItem(OptionTab.TAB_GENRES);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case OptionTab.TAB_HOME:
                mNavigationView.getMenu().getItem(OptionTab.TAB_HOME).setChecked(true);
                break;
            case OptionTab.TAB_GENRES:
                mNavigationView.getMenu().getItem(OptionTab.TAB_GENRES).setChecked(true);
                break;
            case OptionTab.TAB_SEARCH:
                mNavigationView.getMenu().getItem(OptionTab.TAB_SEARCH).setChecked(true);
                break;
            default:
                mNavigationView.getMenu().getItem(OptionTab.TAB_GENRES).setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
