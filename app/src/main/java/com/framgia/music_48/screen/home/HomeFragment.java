package com.framgia.music_48.screen.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.framgia.music_48.screen.playmusic.PlayMusicActivity;
import com.framgia.music_48.service.MusicService;
import com.framgia.music_48.utils.OnItemClickListener;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment
        implements HomeContract.View, OnItemClickListener<Integer>,
        SwipeRefreshLayout.OnRefreshListener {

    public static final int REQUEST_CODE = 111;
    private final static String ARGUMENT_PAGE = "ARGUMENT_PAGE";
    private final static String ARGUMENT_TITLE = "ARGUMENT_TITLE";
    private HomeAdapter mHomeAdapter;
    private List<Song> mSongs;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HomeContract.Presenter mPresenter;

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
        mHomeAdapter.setListener(this);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initPresenter() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getsInstance(
                Objects.requireNonNull(getActivity()).getContentResolver());
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getsInstance();
        SongRepository songRepository =
                SongRepository.getsInstance(songLocalDataSource, songRemoteDataSource);
        mPresenter = new HomePresenter(songRepository);
        mPresenter.setView(this);
        mPresenter.getSongsLocal();
    }

    @Override
    public void onGetSongsLocalSuccess(List<Song> songs) {
        if (songs != null) {
            mHomeAdapter.updateDataSongs(songs);
        }
        mSongs = songs;
    }

    @Override
    public void onGetSongsLocalError() {
        Toast.makeText(getContext(), getString(R.string.message_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickListener(Integer position) {
        startActivity(PlayMusicActivity.getPlayMusicIntent(getContext(), mSongs.get(position)));
        if (getActivity() != null) {
            Intent intent = MusicService.getIntentService(getActivity(), mSongs, position);
            getActivity().startService(intent);
        }
    }

    private boolean checkPermission() {
        if (getContext() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                            REQUEST_CODE);
                    return false;
                }
                return true;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter.getSongsLocal();
        }
    }

    @Override
    public void onRefresh() {
        if (checkPermission()) {
            mPresenter.getSongsLocal();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
