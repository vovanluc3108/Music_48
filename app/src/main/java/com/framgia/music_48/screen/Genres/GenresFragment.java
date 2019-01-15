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
import com.framgia.music_48.screen.GenresDetail.GenresDetailFragment;
import com.framgia.music_48.utils.Navigator;
import com.framgia.music_48.utils.OnRecyclerViewClickListener;
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

public class GenresFragment extends Fragment implements OnRecyclerViewClickListener {
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

    private List<Genres> getListGenres() {
        List<Genres> genresList = new ArrayList<>();
        genresList.add(new Genres(getString(R.string.audio)));
        genresList.add(new Genres(getString(R.string.alternative_rock)));
        genresList.add(new Genres(getString(R.string.ambient)));
        genresList.add(new Genres(getString(R.string.classical)));
        genresList.add(new Genres(getString(R.string.country)));
        return genresList;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case AUDIO:
                mNavigator.addFragment(getFragmentManager(), R.id.fragment,
                        GenresDetailFragment.newInstance(AUDIO_GENRES), true, GenresFragment.TAG);
                break;
            case ALTERNATIVE_ROCK:
                mNavigator.addFragment(getFragmentManager(), R.id.fragment,
                        GenresDetailFragment.newInstance(ALTERNATIVE_ROCK_GENRES), true,
                        GenresFragment.TAG);
                break;
            case AMBIENT:
                mNavigator.addFragment(getFragmentManager(), R.id.fragment,
                        GenresDetailFragment.newInstance(AMBIENT_GENRES), true, GenresFragment.TAG);
                break;
            case CLASSICAL:
                mNavigator.addFragment(getFragmentManager(), R.id.fragment,
                        GenresDetailFragment.newInstance(CLASSICAL_GENRES), true,
                        GenresFragment.TAG);
                break;
            case COUNTRY:
                mNavigator.addFragment(getFragmentManager(), R.id.fragment,
                        GenresDetailFragment.newInstance(COUNTRY_GENRES), true, GenresFragment.TAG);
                break;
        }
    }
}
