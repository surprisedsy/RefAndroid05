package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.ItemList.FragmentItemTab1;
import com.example.jeh80.refandroid05.ItemList.FragmentItemTab2;
import com.example.jeh80.refandroid05.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemListActivity extends AppCompatActivity {

    private static final String url = "https://api.myjson.com/bins/mi994";
    //private static final String url = "http://192.168.1.124:7777/refrigerator/db_list";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BottomBar bottomBar;

    private RequestQueue requestQueue;

    public String name, date;
    public int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        itemListView();

        Init();
        itemListParse();
        //passingDataToFragment();
        bottomBarClicked();

    }

    private void Init()
    {
        requestQueue = Volley.newRequestQueue(this);
    }

    private void itemListParse()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("list");

                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject item = jsonArray.getJSONObject(i);

                                name = item.getString("name");
                                amount = item.getInt("amount");
                                date = item.getString("edate");

                                FragmentManager fm = getSupportFragmentManager();
                                FragmentItemTab1 itemTab1 = new FragmentItemTab1();
                                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.bottomframe, itemTab1);
                                ft.commit();

                                Bundle bundle = new Bundle();
                                bundle.putString("1", name);
                                bundle.putString("2", date);
                                bundle.putInt("3", amount);

                                itemTab1.setArguments(bundle);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void passingDataToFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentItemTab1 itemTab1 = new FragmentItemTab1();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottomframe, itemTab1);
        ft.commit();

        Bundle bundle = new Bundle();
        bundle.putString("1", "test");
        bundle.putString("2", "111");
        bundle.putInt("3", 123);

        itemTab1.setArguments(bundle);
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

        tabLayout.addTab(tabLayout.newTab().setText("유통기한"));
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
