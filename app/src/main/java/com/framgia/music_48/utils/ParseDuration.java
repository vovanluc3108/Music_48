package com.framgia.music_48.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ParseDuration {
    private static String FORMAT_TIME = "%02d:%02d";

    public static String parseDurationToStringTime(long duration) {
        return String.format(Locale.getDefault(), FORMAT_TIME,
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
