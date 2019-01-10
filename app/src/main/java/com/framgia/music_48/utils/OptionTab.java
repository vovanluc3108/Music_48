package com.framgia.music_48.utils;

import android.support.annotation.IntDef;

@IntDef({
        OptionTab.TAB_GENRES, OptionTab.TAB_COUNT, OptionTab.TAB_HOME, OptionTab.TAB_SEARCH
})
public @interface OptionTab {
    int TAB_HOME = 0;
    int TAB_GENRES = 1;
    int TAB_SEARCH = 2;
    int TAB_COUNT = 3;
}
