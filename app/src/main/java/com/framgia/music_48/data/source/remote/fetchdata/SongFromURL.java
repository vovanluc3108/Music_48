package com.framgia.music_48.data.source.remote.fetchdata;

import android.os.AsyncTask;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.data.source.remote.DataRemoteListener;
import com.framgia.music_48.utils.Constant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SongFromURL extends AsyncTask<String, Void, List<Song>> {
    private static final int TIME_OUT = 15000;
    private static final String METHOD_GET = "GET";
    private static final String COLLECTION = "collection";
    private DataRemoteListener mDataRemoteListener;
    private Exception mExceptionError;

    public SongFromURL(DataRemoteListener dataRemoteListener) {
        mDataRemoteListener = dataRemoteListener;
    }

    @Override
    protected List<Song> doInBackground(String... strings) {
        List<Song> songs = null;
        try {
            String json = getJSonFromURL(strings[0]);
            songs = readJSonFromURL(json);
        } catch (IOException | JSONException e) {
            mExceptionError = e;
        }
        return songs;
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        if (songs != null) {
            mDataRemoteListener.onFetchSongSuccess(songs);
        }
        if (mExceptionError != null) {
            mDataRemoteListener.onFetchSongError(mExceptionError);
        }
    }

    private String getJSonFromURL(String urlString) throws IOException {
        URL apiUrl = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod(METHOD_GET);
        connection.setReadTimeout(TIME_OUT);
        connection.setConnectTimeout(TIME_OUT);
        connection.connect();
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    private List<Song> readJSonFromURL(String json) throws JSONException {
        List<Song> songs = new ArrayList<>();
        JSONObject songObject = new JSONObject(json);
        JSONArray songArray = songObject.getJSONArray(COLLECTION);
        for (int i = 0; i < songArray.length(); i++) {
            JSONObject objectCollection =
                    songArray.getJSONObject(i).getJSONObject(Song.SongEntry.TRACK);
            String id = objectCollection.getString(Song.SongEntry.ID);
            String title = objectCollection.getString(Song.SongEntry.TITLE);
            String artwork_url = objectCollection.getString(Song.SongEntry.ARTWORK_URL);
            String full_duration = objectCollection.getString(Song.SongEntry.FULL_DURATION);
            String download_url = objectCollection.getString(Song.SongEntry.DOWNLOAD_URL);
            String stream_url = Constant.SITE_TRACKS+ Constant.TRACKS+
                    id+ Constant.STREAM + Constant.API_KEY_TRACK;
            String downloadable = objectCollection.getString(Song.SongEntry.DOWNLOADABLE);
            JSONObject objectUser = objectCollection.getJSONObject(Song.SongEntry.USER);
            String artist = objectUser.getString(Song.SongEntry.ARTIST);
            Song song = new Song.SongBuilder().setFullDuration(full_duration)
                    .setID(id)
                    .setPoster(artwork_url)
                    .setTitle(title)
                    .setSinger(artist)
                    .setUrl(download_url)
                    .setStreamUrl(stream_url)
                    .setDownload(Boolean.parseBoolean(downloadable))
                    .build();
            songs.add(song);
        }
        return songs;
    }
}
