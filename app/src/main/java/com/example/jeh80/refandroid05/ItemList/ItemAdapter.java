package com.example.jeh80.refandroid05.ItemList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
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

public class ItemAdapter extends BaseAdapter {

    private Activity activity;
    private List<ItemInfo> itemInfos;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader = AppController.getAppInstance().getImageLoader();

    private TextView name, date, amount;

    public ItemAdapter(Activity activity, List<ItemInfo> itemInfos)
    {
        this.activity = activity;
        this.itemInfos = itemInfos;
    }

    @Override
    public int getCount() {
        return itemInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return itemInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.fragment_tab1, parent, false);
//        if(imageLoader != null)
//        {
//            imageLoader = AppController.getAppInstance().getImageLoader();
//            NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.itemimage);
//            name = (TextView) convertView.findViewById(R.id.nameTxt);
//            date = (TextView) convertView.findViewById(R.id.dateTxt);
//            amount = (TextView) convertView.findViewById(R.id.amountTxt);
//
//            ItemInfo itemInfo = itemInfos.get(position);
//
//            imageView.setImageUrl(itemInfo.getImg(), imageLoader);
//            name.setText(itemInfo.getName());
//            date.setText(itemInfo.getDate());
//            amount.setText(itemInfo.getAmount());
//        }

        name = (TextView) convertView.findViewById(R.id.nameTxt);
        date = (TextView) convertView.findViewById(R.id.dateTxt);
        amount = (TextView) convertView.findViewById(R.id.amountTxt);

        ItemInfo itemInfo = itemInfos.get(position);

        name.setText(itemInfo.getName());
        date.setText(itemInfo.getDate());
        amount.setText(String.valueOf(itemInfo.getAmount()));

        return convertView;
    }
}
