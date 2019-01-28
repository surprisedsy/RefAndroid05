package com.example.jeh80.refandroid05.ItemList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.jeh80.refandroid05.AppController;
import com.example.jeh80.refandroid05.R;

import java.util.List;

public class ItemTab3Adapter extends BaseAdapter {

    private Activity activity;

    private List<ItemInfo> iteminfos;
    private LayoutInflater layoutInflater;

    private ImageLoader imageLoader = AppController.getAppInstance().getImageLoader();
    private TextView name, date, amount, edate;

    public ItemTab3Adapter(Activity activity, List<ItemInfo> itemInfos) {
        this.activity = activity;
        this.iteminfos = itemInfos;
    }

    @Override
    public int getCount() {
        return iteminfos.size();
    }

    @Override
    public Object getItem(int position) {
        return iteminfos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.fragment_tab3, null);
        if (imageLoader != null) {
            imageLoader = AppController.getAppInstance().getImageLoader();

            NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.tab3itemimage);
            name = (TextView) convertView.findViewById(R.id.nameTxt3);
            date = (TextView) convertView.findViewById(R.id.dateTxt3);
            amount = (TextView) convertView.findViewById(R.id.amountTxt3);
            edate = (TextView) convertView.findViewById(R.id.edateTxt);

            ItemInfo itemInfo = iteminfos.get(position);

            imageView.setImageUrl(itemInfo.getImg(), imageLoader);
            name.setText(itemInfo.getName());
            date.setText(itemInfo.getDate());
            amount.setText(String.valueOf(itemInfo.getAmount()));
            edate.setText(itemInfo.getEdate());

        }

        return convertView;
    }
}
