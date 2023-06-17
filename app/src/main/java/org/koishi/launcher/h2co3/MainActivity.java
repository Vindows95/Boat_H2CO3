package org.koishi.launcher.h2co3;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.koishi.launcher.h2co3.application.H2CO3Activity;
import org.koishi.launcher.h2co3.ui.custom.CustomFragment;
import org.koishi.launcher.h2co3.ui.home.HomeFragment;
import org.koishi.launcher.h2co3.ui.install.InstallFragment;
import org.koishi.launcher.h2co3.ui.manager.ManagerFragment;
import org.koishi.launcher.h2co3.ui.version.VersionFragment;

import java.util.Objects;

public class MainActivity extends H2CO3Activity implements NavigationView.OnNavigationItemSelectedListener {

    //private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Dialog mDialog;
    private Toolbar toolbar1;

    private HomeFragment homeFragment;
    private VersionFragment versionFragment;
    private ManagerFragment managerFragment;
    private InstallFragment installFragment;
    private CustomFragment customFragment;

    //private int mPrevSelectedId;
    //private int mSelectedId;
    private NavigationView navigationView;
    private String fragmentId;
    private View dialogBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogBg = findViewById(R.id.dialog_bg);

        toolbar1 = findViewById(R.id.toolbar);
        actionBar();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        navigationView.setCheckedItem(R.id.fragment_home);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.menu_home));
        initFragment1();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            drawer.closeDrawer(GravityCompat.START);
            new Handler().postDelayed(() -> {
                navigationView.setCheckedItem(R.id.fragment_custom);
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.menu_more));
                initFragment5();
            }, 350);
        }
        return super.onOptionsItemSelected(item);
    }

    public void actionBar() {
        setSupportActionBar(toolbar1);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fragment_home:
                drawer.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(() -> {
                    navigationView.setCheckedItem(R.id.fragment_home);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.menu_home));
                    initFragment1();
                }, 350);

                break;
            case R.id.fragment_version:
                drawer.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(() -> {
                    navigationView.setCheckedItem(R.id.fragment_version);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.menu_ver));
                    initFragment2();
                }, 350);
                break;
            case R.id.fragment_manager:
                drawer.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(() -> {
                    navigationView.setCheckedItem(R.id.fragment_manager);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.menu_manager));
                    initFragment3();
                }, 350);
                break;
            case R.id.fragment_install:
                drawer.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(() -> {
                    navigationView.setCheckedItem(R.id.fragment_install);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.menu_install));
                    initFragment4();
                }, 350);
                break;
            case R.id.fragment_custom:
                drawer.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(() -> {
                    navigationView.setCheckedItem(R.id.fragment_custom);
                    Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.menu_more));
                    initFragment5();
                }, 350);
                break;
            case R.id.activity_terminal:
                new Handler().postDelayed(() -> startActivity(new Intent(this, TerminalActivity.class)), 350);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!Objects.requireNonNull(getSupportActionBar().getTitle()).equals(getResources().getString(R.string.menu_home))) {
                navigationView.setCheckedItem(R.id.fragment_home);
                getSupportActionBar().setTitle(getResources().getString(R.string.menu_home));
                initFragment1();
                drawer.closeDrawer(GravityCompat.START);
            } else {
                finish();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            dialogBg.setVisibility(View.GONE);
        } else {
            dialogBg.setVisibility(View.VISIBLE);
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    }

    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        //transaction.replace(R.id.fragment_home, homeFragment);
        transaction.replace(R.id.content, homeFragment);
        //隐藏所有fragment
        //hideFragment(transaction);
        //显示需要显示的fragment
        //transaction.show(homeFragment);

        //第二种方式(replace)，初始化fragment
//        if(f1 == null){
//            f1 = new MyFragment("消息");
//        }
//        transaction.replace(R.id.main_frame_layout, f1);


        //提交事务
        transaction.commit();
    }

    private void initFragment2() {
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        //if (versionFragment == null) {
        //    versionFragment = new VersionFragment();
        //}
        //transaction.replace(R.id.content, versionFragment);
        //transaction.commit();
        startActivity(new Intent(MainActivity.this, VersionsActivity.class));
    }

    private void initFragment3() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (managerFragment == null) {
            managerFragment = new ManagerFragment();
        }
        transaction.replace(R.id.content, managerFragment);
        transaction.commit();
    }

    private void initFragment4() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (installFragment == null) {
            installFragment = new InstallFragment();
        }
        transaction.replace(R.id.content, installFragment);
        transaction.commit();
    }

    private void initFragment5() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (customFragment == null) {
            customFragment = new CustomFragment();
        }
        transaction.replace(R.id.content, customFragment);
        transaction.commit();
    }

}