package com.framgia.music_48.service;

import com.framgia.music_48.data.model.Song;

public interface ServiceContract {
    interface onMediaPlayer {
        void onUpdateUIListener(Song song);

        void onUpdateButtonStateListener(boolean isPlaying);

        void onUpdateSeekBarListener();

        void onLoopStateListener(int loopState);

        void onShuffleStateListener(boolean isShuffleState);
    }

    interface onMediaPlayerMini {
        void onUpdateUIListener(Song song);

        void onUpdateButtonStateListener(boolean isPlaying);
    }
}
