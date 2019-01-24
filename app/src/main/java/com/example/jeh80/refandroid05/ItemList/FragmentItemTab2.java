package com.example.jeh80.refandroid05.ItemList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentItemTab2 extends Fragment {

    private static final String TAB2_url = "http://192.168.1.31:7777/refrigerator/Android_ldate";
//    private static final String TAB2_url = "http://192.168.1.124:7777/refrigerator/Android_list";

    private ListView listView;
    private List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
    private ItemAdapter itemAdapter;
    private RequestQueue requestQueue;

    private String key, responseKey;

    public FragmentItemTab2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        listView = (ListView) view.findViewById(R.id.itemlistview2);
        itemAdapter = new ItemAdapter(getActivity(), itemInfoList);
        listView.setAdapter(itemAdapter);

        ldateItemsListParse();

        return view;
    }

    private void ldateItemsListParse() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, TAB2_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Iterator iterator = response.keys();
                            while (iterator.hasNext()) {
                                responseKey = (String) iterator.next();
                            }

                            if (responseKey.equals("Object")) {
                                TextView nullTxt = (TextView) getActivity().findViewById(R.id.nullText);
                                nullTxt.setText("비어있음");
                            } else {
                                JSONArray jsonArray = response.getJSONArray("list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject item = jsonArray.getJSONObject(i);

                                    Iterator iter = item.keys();
                                    List<String> keys = new ArrayList<String>();
                                    while (iter.hasNext()) {
                                        key = (String) iter.next();
                                        keys.add(key);
                                    }

                                    Log.d("frag2 items", "경과일 정보: " + item);

                                    ItemInfo itemInfo = new ItemInfo();

                                    if (keys.contains("ldate")) {
                                        String name = item.getString("name");
                                        String img = item.getString("img");
                                        int amount = item.getInt("amount");
                                        int ldate = item.getInt("ldate");

                                        itemInfo.setDate(ldate + "일 경과");
                                        itemInfo.setName(name);
                                        itemInfo.setAmount(String.valueOf(amount) + "개");
                                        itemInfo.setImg(img);
                                    }

                                    itemInfoList.add(itemInfo);
                                }
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
}