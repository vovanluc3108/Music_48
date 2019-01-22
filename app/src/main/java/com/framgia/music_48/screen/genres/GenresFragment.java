package com.framgia.music_48.screen.genres;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_48.R;
import com.framgia.music_48.data.model.Genre;
import com.framgia.music_48.screen.genres.adapter.GenresAdapter;
import com.framgia.music_48.screen.listmusicdetail.ListMusicFragment;
import com.framgia.music_48.utils.Navigator;
import com.framgia.music_48.utils.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.music_48.utils.Genres.ALTERNATIVE_ROCK_GENRES;
import static com.framgia.music_48.utils.Genres.AMBIENT_GENRES;
import static com.framgia.music_48.utils.Genres.AUDIO_GENRES;
import static com.framgia.music_48.utils.Genres.CLASSICAL_GENRES;
import static com.framgia.music_48.utils.Genres.COUNTRY_GENRES;
import static com.framgia.music_48.utils.GenresTab.ALTERNATIVE_ROCK;
import static com.framgia.music_48.utils.GenresTab.AMBIENT;
import static com.framgia.music_48.utils.GenresTab.AUDIO;
import static com.framgia.music_48.utils.GenresTab.CLASSICAL;
import static com.framgia.music_48.utils.GenresTab.COUNTRY;

public class GenresFragment extends Fragment implements OnItemClickListener<Integer> {
    public static final String TAG = GenresFragment.class.getSimpleName();
    private Navigator mNavigator;

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
        mNavigator = new Navigator();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGenres);
        GenresAdapter genresAdapter = new GenresAdapter(getListGenres());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(genresAdapter);
        genresAdapter.setItemClickListener(this);
    }

    private List<Genre> getListGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(getString(R.string.audio)));
        genres.add(new Genre(getString(R.string.alternative_rock)));
        genres.add(new Genre(getString(R.string.ambient)));
        genres.add(new Genre(getString(R.string.classical)));
        genres.add(new Genre(getString(R.string.country)));
        return genres;
    }

    @Override
    public void onClickListener(Integer position) {
        switch (position) {
            case AUDIO:
                mNavigator.addFragment(getFragmentManager(), R.id.drawerLayoutMain,
                        ListMusicFragment.newInstance(AUDIO_GENRES), true, GenresFragment.TAG);
                break;
            case ALTERNATIVE_ROCK:
                mNavigator.addFragment(getFragmentManager(), R.id.drawerLayoutMain,
                        ListMusicFragment.newInstance(ALTERNATIVE_ROCK_GENRES), true,
                        GenresFragment.TAG);
                break;
            case AMBIENT:
                mNavigator.addFragment(getFragmentManager(), R.id.drawerLayoutMain,
                        ListMusicFragment.newInstance(AMBIENT_GENRES), true, GenresFragment.TAG);
                break;
            case CLASSICAL:
                mNavigator.addFragment(getFragmentManager(), R.id.drawerLayoutMain,
                        ListMusicFragment.newInstance(CLASSICAL_GENRES), true,
                        GenresFragment.TAG);
                break;
            case COUNTRY:
                mNavigator.addFragment(getFragmentManager(), R.id.drawerLayoutMain,
                        ListMusicFragment.newInstance(COUNTRY_GENRES), true, GenresFragment.TAG);
                break;
        }
    }
}
