package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.AppController;
import com.example.jeh80.refandroid05.R;
import com.example.jeh80.refandroid05.RefList.RefAdapter;
import com.example.jeh80.refandroid05.RefList.RefInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RefListActivity extends AppCompatActivity {

//    private static final String url = "http://192.168.1.124:7777/refrigerator/Android_getData";
    private static final String url = "http://192.168.1.31:7777/refrigerator/Android_getData";

    private RequestQueue requestQueue;

    private List<RefInfo> refInfoList = new ArrayList<RefInfo>();
    private RefAdapter refAdapter;
    private ListView listView;

    private RefInfo refInfo;

    private String name, num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_list);

        Init();
        refListData();
        clickedListView();
    }

    private void Init() {
        requestQueue = Volley.newRequestQueue(this);

        listView = (ListView) findViewById(R.id.reflistView);
        refAdapter = new RefAdapter(this, refInfoList);
        listView.setAdapter(refAdapter);
    }

    private void refListData() {

        String refData = getIntent().getStringExtra("data");
        JSONObject obj = null;
        try {
            obj = new JSONObject(refData);
            JSONArray refArray = obj.getJSONArray("reflist");

            for(int i = 0; i < refArray.length(); i++)
            {
                JSONObject refObject = refArray.getJSONObject(i);

                RefInfo refInfo = new RefInfo();

                name = refObject.getString("ref_name");
                num = refObject.getString("ref_num");

                refInfo.setName(name);
                refInfo.setNo(" (" + num + ")");

                refInfoList.add(refInfo);
            }
            refAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clickedListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                refInfo = refInfoList.get(position);
                switch (position) {
                    case 0:
                        selectedRefList();
                        break;
                    case 1:
                        selectedRefList();
                        break;
                    case 2:
                        selectedRefList();
                        break;
                }
            }
        });
    }

    private void selectedRefList()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ref_name ", refInfo.getName());
                params.put("ref_num", refInfo.getNo());

                return params;
            }
        };
        AppController.getAppInstance().addToRequestQueue(stringRequest);

        Intent tempIntent = new Intent(RefListActivity.this, TempActivity.class);
        startActivity(tempIntent);
    }
}
