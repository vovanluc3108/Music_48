package com.framgia.music_48.utils;

import android.support.annotation.IntDef;

@IntDef({
        GenresTab.AUDIO, GenresTab.ALTERNATIVE_ROCK, GenresTab.AMBIENT, GenresTab.CLASSICAL,
        GenresTab.COUNTRY
})
public @interface GenresTab {
    int AUDIO = 0;
    int ALTERNATIVE_ROCK = 1;
    int AMBIENT = 2;
    int CLASSICAL = 3;
    int COUNTRY = 4;
}
