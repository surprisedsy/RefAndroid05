package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.R;
import com.example.jeh80.refandroid05.RefList.RefAdapter;
import com.example.jeh80.refandroid05.RefList.RefInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RefListActivity extends AppCompatActivity {

    private static final String url = "https://api.myjson.com/bins/lwtnc";
    //private static final String url = "http://192.168.1.124:7777/refrigerator/product_num";

    private RequestQueue requestQueue;

    private List<RefInfo> refInfoList = new ArrayList<RefInfo>();
    private RefAdapter refAdapter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_list);

        Init();
        refListParse();
        clickedListView();
    }

    private void Init()
    {
        requestQueue = Volley.newRequestQueue(this);

        listView = (ListView) findViewById(R.id.reflistView);
        refAdapter = new RefAdapter(this, refInfoList);
        listView.setAdapter(refAdapter);
    }

    private void refListParse()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("product");

                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject ref = jsonArray.getJSONObject(i);

                                RefInfo refInfo = new RefInfo();

                                //String refname = ref.getString("Family_refrigerator");
                                String refserial = ref.getString("Family_refrigerator");

                                refInfo.setNo(refserial);

                                refInfoList.add(refInfo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        refAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void clickedListView()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent tempIntent = new Intent(RefListActivity.this, TempActivity.class);
                        startActivity(tempIntent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });
    }
}
