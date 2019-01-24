package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jeh80.refandroid05.ItemList.FragmentItemTab1;
import com.example.jeh80.refandroid05.ItemList.FragmentItemTab2;
import com.example.jeh80.refandroid05.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ItemListActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        itemListView();
        bottomBarClicked();

    }

    private void bottomBarClicked()
    {
        bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTabPosition(0);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if(tabId == R.id.btm_home)
                {
                    Intent homeIntent = new Intent(ItemListActivity.this, TempActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                if(tabId == R.id.btm_photo)
                {
                    Intent photoIntent = new Intent(ItemListActivity.this, PhotoActivity.class);
                    startActivity(photoIntent);
                    finish();
                }
            }
        });
    }

    private void viewInit()
    {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void itemListView()
    {
        viewInit();

        tabLayout.addTab(tabLayout.newTab().setText("재고"));
        tabLayout.addTab(tabLayout.newTab().setText("경과일"));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private static final int COUNT = 2;

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 1:
                    return new FragmentItemTab2();
                default:
                    return new FragmentItemTab1();
            }
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }

}
