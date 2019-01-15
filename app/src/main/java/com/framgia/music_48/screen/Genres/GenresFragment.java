package com.framgia.music_48.screen.Genres;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Genres;
import com.framgia.music_48.screen.Genres.Adapter.GenresAdapter;
import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends Fragment {
    public static final String TAG = GenresFragment.class.getSimpleName();

    public static GenresFragment newInstance() {
        return new GenresFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGenres);
        GenresAdapter genresAdapter = new GenresAdapter(getContext(), getListGenres());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(genresAdapter);
    }

    private List<Genres> getListGenres() {
        List<Genres> genresList = new ArrayList<>();
        genresList.add(new Genres(getString(R.string.audio)));
        genresList.add(new Genres(getString(R.string.alternative_rock)));
        genresList.add(new Genres(getString(R.string.ambient)));
        genresList.add(new Genres(getString(R.string.classical)));
        genresList.add(new Genres(getString(R.string.country)));
        return genresList;
    }
}
