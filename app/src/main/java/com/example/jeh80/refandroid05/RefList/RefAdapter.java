package com.example.jeh80.refandroid05.RefList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jeh80.refandroid05.R;

import java.util.List;

public class RefAdapter extends BaseAdapter {

    private Activity activity;
    private List<RefInfo> refInfos;
    private LayoutInflater layoutInflater;

    private TextView name, serial;

    public RefAdapter(Activity activity, List<RefInfo> refInfos)
    {
        this.activity = activity;
        this.refInfos = refInfos;
    }

    @Override
    public int getCount() {
        return refInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return refInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.activity_ref_list, null);

        name = (TextView) convertView.findViewById(R.id.refNameTxt1);
        serial = (TextView) convertView.findViewById(R.id.serialTxt1);

        RefInfo info = refInfos.get(position);

        name.setText(info.getName());
        serial.setText(info.getNo());

        return convertView;
    }

}
