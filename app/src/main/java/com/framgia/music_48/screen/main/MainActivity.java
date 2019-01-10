package com.framgia.music_48.screen.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.framgia.music_48.R;
import com.framgia.music_48.screen.main.Adapter.ViewPagerAdapter;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.close,
                        R.string.open);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initView() {
        ViewPager viewPagerMain = findViewById(R.id.viewPagerMain);
        mDrawerLayout = findViewById(R.id.drawerLayoutMain);
        mToolbar = findViewById(R.id.toolbarMain);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPagerMain.setAdapter(pagerAdapter);
    }
}
