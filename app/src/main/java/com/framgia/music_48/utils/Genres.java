package com.framgia.music_48.utils;

import android.support.annotation.StringDef;

@StringDef({
        Genres.ALTERNATIVE_ROCK_GENRES, Genres.AUDIO_GENRES, Genres.CLASSICAL_GENRES,
        Genres.AMBIENT_GENRES, Genres.COUNTRY_GENRES
})
public @interface Genres {
    String AUDIO_GENRES = "all-audio";
    String ALTERNATIVE_ROCK_GENRES = "alternative rock";
    String AMBIENT_GENRES = "ambient";
    String CLASSICAL_GENRES = "classical";
    String COUNTRY_GENRES = "country";
}
