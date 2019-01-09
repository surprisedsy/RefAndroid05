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
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.ItemList.FragmentItemTab1;
import com.example.jeh80.refandroid05.ItemList.FragmentItemTab2;
import com.example.jeh80.refandroid05.ItemList.ItemAdapter;
import com.example.jeh80.refandroid05.ItemList.ItemInfo;
import com.example.jeh80.refandroid05.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private static final String url = "https://api.myjson.com/bins/mi994";
    //private static final String url = "http://192.168.1.124:7777/refrigerator/db_list";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BottomBar bottomBar;

    private List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
    private ItemAdapter itemAdapter;
    private ListView listView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        itemListView();

        Init();
        itemListParse();
        bottomBarClicked();
    }

    private void Init()
    {
        requestQueue = Volley.newRequestQueue(this);

        listView = (ListView) findViewById(R.id.itemlistview);
        itemAdapter = new ItemAdapter(this, itemInfoList);
        listView.setAdapter(itemAdapter);
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

                                ItemInfo itemInfo = new ItemInfo();

                                String name = item.getString("name");
                                int amount = item.getInt("amount");
                                String date = item.getString("edate");

//                                FragmentItemTab1 tab1 = new FragmentItemTab1();
//                                Bundle bundle = new Bundle();
//
//                                bundle.putString("name", name);
//                                bundle.putInt("amount", amount);
//                                bundle.putString("edate", date);
//
//                                tab1.setArguments(bundle);

                                Log.d("bbbbbb", "activity: " + name);
                                Log.d("bbbbbb", "activity: " + amount);
                                Log.d("bbbbbb", "activity: " + date);

//                                itemInfo.setName(name);
//                                itemInfo.setAmount(amount);
//                                itemInfo.setDate(date);
//
//                                itemInfoList.add(itemInfo);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        itemAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
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
