package com.framgia.music_48.screen.playmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.utils.Constant;

public class PlayMusicActivity extends AppCompatActivity {

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
        Song mSong = getIntent().getParcelableExtra(Constant.BUNDLE_MUSIC);
        int position = getIntent().getExtras().getInt(Constant.BUNDLE_POSITION);
    }
}
