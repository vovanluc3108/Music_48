package com.framgia.music_48.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.framgia.music_48.screen.genres.GenresFragment;

public class Navigator {
    public void addFragment(FragmentManager fragmentManager, int containerViewId,
            Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);
        if (currentFragment == null || tag.equals(GenresFragment.TAG)) {
            currentFragment = fragment;
            transaction.add(containerViewId, fragment);
        }
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        showFragment(fragmentManager, transaction, currentFragment);
    }

    private void showFragment(FragmentManager fragmentManager, FragmentTransaction transaction,
            Fragment fragment) {
        for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
            transaction.hide(fragmentManager.getFragments().get(i));
        }
        transaction.show(fragment);
        transaction.commit();
    }
}
