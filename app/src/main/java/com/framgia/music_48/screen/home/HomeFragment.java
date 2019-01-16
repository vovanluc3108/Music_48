package com.framgia.music_48.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.framgia.music_48.R;
import com.framgia.music_48.data.SongRepository;
import com.framgia.music_48.data.model.Song;
import com.framgia.music_48.data.source.local.SongLocalDataSource;
import com.framgia.music_48.data.source.remote.SongRemoteDataSource;
import com.framgia.music_48.screen.home.Adapter.HomeAdapter;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements HomeContract.View {

    private final static String ARGUMENT_PAGE = "ARGUMENT_PAGE";
    private final static String ARGUMENT_TITLE = "ARGUMENT_TITLE";
    private HomeAdapter mHomeAdapter;

    public static HomeFragment newInstance(String title, int page) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_PAGE, title);
        bundle.putInt(ARGUMENT_TITLE, page);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initPresenter();
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerViewHome = view.findViewById(R.id.recyclerViewHome);
        mHomeAdapter = new HomeAdapter();
        recyclerViewHome.setAdapter(mHomeAdapter);
    }

    private void initPresenter() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getsInstance(
                Objects.requireNonNull(getActivity()).getContentResolver());
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getsInstance();
        SongRepository songRepository =
                SongRepository.getsInstance(songLocalDataSource, songRemoteDataSource);
        HomeContract.Presenter presenter = new HomePresenter(songRepository);
        presenter.setView(this);
        presenter.getSongsLocal();
    }

    @Override
    public void onGetSongsLocalSuccess(List<Song> songs) {
        if (songs != null) {
            mHomeAdapter.updateDataSongs(songs);
        }
    }

    @Override
    public void onGetSongsLocalError() {
        Toast.makeText(getContext(), getString(R.string.message_error), Toast.LENGTH_SHORT).show();
    }
}
