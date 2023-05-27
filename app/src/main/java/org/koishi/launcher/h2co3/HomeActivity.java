package org.koishi.launcher.h2co3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.koishi.launcher.h2co3.ui.custom.FuncFragment;
import org.koishi.launcher.h2co3.ui.home.HomeFragment;
import org.koishi.launcher.h2co3.ui.install.InstallFragment;
import org.koishi.launcher.h2co3.ui.manager.ManagerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentPagerAdapter mPagerAdapter;
    private SpannableStringBuilder style;
    private TabLayout tabLayout;
    private TextView bigTitle;

    private ImageView iv1,iv2,iv3,iv4;
    final List<String> titles = new ArrayList<>();
     ArrayList< Fragment > fgLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "Sans.ttf");
        bigTitle= (TextView) toolbar.getChildAt(0);
        bigTitle.setTypeface(tf);
        tabLayout = findViewById(R.id.home_tab);
        titles.add("fragment1");
        titles.add("fragment2");
        titles.add("fragment3");
        titles.add("fragment4");
        initView();
        LinearLayout l1 = findViewById(R.id.home_nav_1);
        LinearLayout l2 = findViewById(R.id.home_nav_2);
        LinearLayout l3 = findViewById(R.id.home_nav_3);
        LinearLayout l4 = findViewById(R.id.home_nav_4);
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView tv4 = findViewById(R.id.tv4);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv1.setImageDrawable(getResources().getDrawable(R.drawable.ic_btm_home));
        iv1.setSelected(true);
        iv2.setImageDrawable(getResources().getDrawable(R.drawable.ic_btm_install));
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.ic_btm_manager));
        iv4.setImageDrawable(getResources().getDrawable(R.drawable.ic_btm_custom));

        l1.setOnClickListener(v->{
            viewPager.setCurrentItem(0);
            style = new SpannableStringBuilder(getResources().getString(R.string.app_name));
            style.setSpan(new ForegroundColorSpan(getResources().getColor(cosine.boat.R.color.colorPrimary)), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(getResources().getColor(cosine.boat.R.color.appBlack_ff)), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            bigTitle.setText(style);
            iv1.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
            iv2.setBackground(getResources().getDrawable(R.color.empty));
            iv3.setBackground(getResources().getDrawable(R.color.empty));
            iv4.setBackground(getResources().getDrawable(R.color.empty));
        });
        l2.setOnClickListener(v->{
            viewPager.setCurrentItem(1);
            bigTitle.setText(getResources().getString(R.string.menu_install));
            bigTitle.setTextColor(getResources().getColor(R.color.appBlack_ff));
            iv1.setBackground(getResources().getDrawable(R.color.empty));
            iv2.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
            iv3.setBackground(getResources().getDrawable(R.color.empty));
            iv4.setBackground(getResources().getDrawable(R.color.empty));
        });
        l3.setOnClickListener(v->{
            viewPager.setCurrentItem(2);
            bigTitle.setText(getResources().getString(R.string.menu_manager));
            bigTitle.setTextColor(getResources().getColor(R.color.appBlack_ff));
            iv1.setBackground(getResources().getDrawable(R.color.empty));
            iv2.setBackground(getResources().getDrawable(R.color.empty));
            iv3.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
            iv4.setBackground(getResources().getDrawable(R.color.empty));
        });
        l4.setOnClickListener(v->{
            viewPager.setCurrentItem(3);
            bigTitle.setText(getResources().getString(R.string.menu_more));
            bigTitle.setTextColor(getResources().getColor(R.color.appBlack_ff));
            iv1.setBackground(getResources().getDrawable(R.color.empty));
            iv2.setBackground(getResources().getDrawable(R.color.empty));
            iv3.setBackground(getResources().getDrawable(R.color.empty));
            iv4.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
        });
    }

    private void initView() {
        /*
          viewpager初始化
         */
        viewPager = findViewById(R.id.main_viewpager);
        /*
          ViewPager的监听
         */
        setViewPagerListener();
        /*
          fragment相关
         */
        initFragment();

        //tab.setupWithViewPager(viewPager);
        viewPager.setAdapter(mPagerAdapter);   //设置适配器
        viewPager.setOffscreenPageLimit(4); //预加载所有页
        viewPager.setCurrentItem(0);
    }

    private void initFragment() {
        //底部导航栏有几项就有几个Fragment
        fgLists = new ArrayList<>(4);
        fgLists.add(new HomeFragment());
        fgLists.add(new InstallFragment());
        fgLists.add(new ManagerFragment());
        fgLists.add(new FuncFragment());
        //fgLists.add(new MoreFragment());

        //设置适配器用于装载Fragment,ViewPager的好朋友
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);  //得到Fragment
            }

            @Override
            public int getCount() {
                return fgLists.size();  //得到数量
            }

        };

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });

        tabLayout.setupWithViewPager(viewPager);
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
            startActivity(new Intent(HomeActivity.this,SettingsActivity.class));
            //this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //这里有3中滑动过程,我们用点击后就可以
    private void setViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffset == 0){
                } else if (position == 1){
                } else if (position == 2){
                }
            }

            @Override
            public void onPageSelected(int position) {
                //滑动页面后做的事，这里与BottomNavigationView结合，使其与正确page对应
                if (position == 0){
                    iv1.setSelected(true);
                    iv2.setSelected(false);
                    iv3.setSelected(false);
                    iv4.setSelected(false);
                    iv1.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
                    iv2.setBackground(getResources().getDrawable(R.color.empty));
                    iv3.setBackground(getResources().getDrawable(R.color.empty));
                    iv4.setBackground(getResources().getDrawable(R.color.empty));
                } else if (position == 1){
                    bigTitle.setText(getResources().getString(R.string.menu_install));
                    bigTitle.setTextColor(getResources().getColor(R.color.appBlack_ff));
                    iv1.setSelected(false);
                    iv2.setSelected(true);
                    iv3.setSelected(false);
                    iv4.setSelected(false);
                    iv1.setBackground(getResources().getDrawable(R.color.empty));
                    iv2.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
                    iv3.setBackground(getResources().getDrawable(R.color.empty));
                    iv4.setBackground(getResources().getDrawable(R.color.empty));
                } else if (position == 2){
                    bigTitle.setText(getResources().getString(R.string.menu_manager));
                    bigTitle.setTextColor(getResources().getColor(R.color.appBlack_ff));
                    iv1.setSelected(false);
                    iv2.setSelected(false);
                    iv3.setSelected(true);
                    iv4.setSelected(false);
                    iv1.setBackground(getResources().getDrawable(R.color.empty));
                    iv2.setBackground(getResources().getDrawable(R.color.empty));
                    iv3.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
                    iv4.setBackground(getResources().getDrawable(R.color.empty));
                } else if (position == 3){
                    bigTitle.setText(getResources().getString(R.string.menu_more));
                    bigTitle.setTextColor(getResources().getColor(R.color.appBlack_ff));
                    iv1.setSelected(false);
                    iv2.setSelected(false);
                    iv3.setSelected(false);
                    iv4.setSelected(true);
                    iv1.setBackground(getResources().getDrawable(R.color.empty));
                    iv2.setBackground(getResources().getDrawable(R.color.empty));
                    iv3.setBackground(getResources().getDrawable(R.color.empty));
                    iv4.setBackground(getResources().getDrawable(R.drawable.ic_btm_selected_bg));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}