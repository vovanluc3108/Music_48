package com.framgia.music_48.screen.playmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.utils.Constant;
import com.framgia.music_48.utils.ParseDuration;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayMusicActivity extends AppCompatActivity {
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
    private TextView mTextViewtDuration;
    private CircleImageView mCircleImageViewPoster;
    private Song mSong;

    public static Intent getPlayMusicIntent(Context context, Song song, int position) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_MUSIC, song);
        bundle.putInt(Constant.BUNDLE_POSITION, position);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initView();
        getData();
        updateUI();
    }

    private void getData() {
        mSong = getIntent().getParcelableExtra(Constant.BUNDLE_MUSIC);
    }

    private void updateUI() {
        mTextViewSong.setText(mSong.getTitle());
        mTextViewSinger.setText(mSong.getSinger());
        mTextViewFullDuration.setText(
                ParseDuration.parseDurationToStringTime(Long.parseLong(mSong.getFullDuration())));
        Glide.with(this)
                .applyDefaultRequestOptions(
                        new RequestOptions().placeholder(R.drawable.song_noimage).circleCrop())
                .load(mSong.getPoster())
                .into(mCircleImageViewPoster);
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
        mTextViewtDuration = findViewById(R.id.textViewDuration);
        mTextViewSinger = findViewById(R.id.textViewSinger);
        mTextViewSong = findViewById(R.id.textViewSong);
    }
}
