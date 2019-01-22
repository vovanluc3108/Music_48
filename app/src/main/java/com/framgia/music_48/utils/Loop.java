package com.framgia.music_48.utils;

import android.support.annotation.IntDef;

@IntDef({
        Loop.NON_LOOP, Loop.ONE_LOOP, Loop.ALL_LOOP
})
public @interface Loop {
    int NON_LOOP = 0;
    int ONE_LOOP = 1;
    int ALL_LOOP = 2;
}
