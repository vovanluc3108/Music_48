package com.framgia.music_48.utils;

import com.framgia.music_48.BuildConfig;

public class Constant {
    public static final String SITE_GENRES = "https://api-v2.soundcloud.com/";
    public static final String CHARTS = "charts?";
    public static final String KIND = "kind=top";
    public static final String GENRES = "&genre=soundcloud%3Agenres%3A";
    public static final String API_KEY = "&client_id=" + BuildConfig.API_KEY;
    public static final String API_KEY_TRACK = "?client_id=" + BuildConfig.API_KEY;
    public static final String SITE_TRACKS = "https://api.soundcloud.com/";
    public static final String TRACKS = "tracks/";
    public static final String STREAM = "/stream";
    public static final String BUNDLE_MUSIC = "BUNDLE_MUSIC";
    public static final String BUNDLE_POSITION = "BUNDLE_POSITION";
    public static final String BUNDLE_LIST_MUSIC = "BUNDLE_LIST_MUSIC";
}
