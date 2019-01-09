package com.framgia.music_48.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private String mId;
    private String mTitle;
    private String mSinger;
    private String mPoster;
    private String mFullDuration;
    private String mUrl;

    private Song(SongBuilder songBuilder) {
        mId = songBuilder.mId;
        mTitle = songBuilder.mTitle;
        mSinger = songBuilder.mSinger;
        mPoster = songBuilder.mPoster;
        mFullDuration = songBuilder.mFullDuration;
        mUrl = songBuilder.mUrl;
    }

    protected Song(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mSinger = in.readString();
        mPoster = in.readString();
        mFullDuration = in.readString();
        mUrl = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSinger() {
        return mSinger;
    }

    public void setSinger(String singer) {
        mSinger = singer;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public String getFullDuration() {
        return mFullDuration;
    }

    public void setFullDuration(String fullDuration) {
        mFullDuration = fullDuration;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mSinger);
        dest.writeString(mPoster);
        dest.writeString(mFullDuration);
        dest.writeString(mUrl);
    }

    public static class SongBuilder {
        private String mId;
        private String mTitle;
        private String mSinger;
        private String mPoster;
        private String mFullDuration;
        private String mUrl;

        public SongBuilder setID(String id) {
            mId = id;
            return this;
        }

        public SongBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public SongBuilder setSinger(String singer) {
            mSinger = singer;
            return this;
        }

        public SongBuilder setPoster(String poster) {
            mPoster = poster;
            return this;
        }

        public SongBuilder setFullDuration(String fullDuration) {
            mFullDuration = fullDuration;
            return this;
        }

        public SongBuilder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }
}
