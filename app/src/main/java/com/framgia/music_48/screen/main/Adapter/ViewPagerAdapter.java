package com.framgia.music_48.screen.main.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.music_48.R;
import com.framgia.music_48.screen.Genres.GenresFragment;
import com.framgia.music_48.screen.Home.HomeFragment;

import static com.framgia.music_48.utils.OptionTab.TAB_COUNT;
import static com.framgia.music_48.utils.OptionTab.TAB_GENRES;
import static com.framgia.music_48.utils.OptionTab.TAB_HOME;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_HOME:
                return HomeFragment.newInstance(mContext.getString(R.string.home), TAB_HOME);
            case TAB_GENRES:
                return GenresFragment.newInstance();
            default:
                return HomeFragment.newInstance(mContext.getString(R.string.home), TAB_HOME);
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
