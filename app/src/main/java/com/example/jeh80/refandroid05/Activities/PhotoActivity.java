package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.AppController;
import com.example.jeh80.refandroid05.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PhotoActivity extends AppCompatActivity {

//    private static final String url = "http://192.168.1.124:7777/refrigerator/Android_img";
    private static final String url = "http://192.168.1.31:7777/refrigerator/Android_img";

    private NetworkImageView imageView;
    private RequestQueue requestQueue;
    private BottomBar bottomBar;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Init();
        photoJsonParse();
        bottomBarClicked();
        refreshPhoto();
    }

    private void Init()
    {
        imageView = (NetworkImageView) findViewById(R.id.imageinref);
        requestQueue = Volley.newRequestQueue(this);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_photo);
    }

    private void photoJsonParse()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String photo = response.getString("img");
                            imageView.setImageUrl(photo, AppController.getAppInstance().getImageLoader());

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

    private void bottomBarClicked()
    {
        bottomBar = (BottomBar) findViewById(R.id.bottombar2);
        bottomBar.setDefaultTabPosition(2);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if(tabId == R.id.btm_home)
                {
                    Intent homeIntent = new Intent(PhotoActivity.this, TempActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                if(tabId == R.id.btm_itemlist)
                {
                    Intent itemIntent = new Intent(PhotoActivity.this, ItemListActivity.class);
                    startActivity(itemIntent);
                    finish();
                }
            }
        });
    }

    private void refreshPhoto()
    {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent photoIntent = getIntent();
                finish();
                startActivity(photoIntent);

                refreshLayout.setRefreshing(false);
            }
        });
    }
}
