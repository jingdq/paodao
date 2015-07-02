package com.example.paodao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jingdongqi on 7/1/15.
 */
public class SlideAdapter extends BaseAdapter {

    private ArrayList<SlideModel> datas ;
    private Context mContext;
    private LayoutInflater inflater;

    public SlideAdapter(Context mContext,ArrayList<SlideModel> datas) {
        this.datas = datas;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public SlideModel getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.historylist_item,null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(datas.get(position).showSlideStr());


        return convertView;
    }
}
