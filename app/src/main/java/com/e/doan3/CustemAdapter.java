package com.e.doan3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustemAdapter extends ArrayAdapter<tintuc> {
    Context context;
    int resource;
    ArrayList<tintuc> arr;

    public CustemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<tintuc> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arr= (ArrayList<tintuc>) objects;
    }



    @Override
    public int getCount() {
        return arr.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.cutom_list_item,null);
        //ánh xạ
        ImageView anhtt = (ImageView) row.findViewById(R.id.anhtrangchu);
        TextView tieudett=(TextView) row.findViewById(R.id.txttieude);
        TextView thoigian=(TextView) row.findViewById(R.id.thoigian);

        tintuc tt=arr.get(position);
        tieudett.setText(tt.tieude);
        thoigian.setText(tt.thoigiancn);
        if (!tt.anh.equals("")){
        Picasso.get().load(tt.anh).into(anhtt);}

        return row;
    }
    public void sortTintuc(String s){
        s=s.toUpperCase();
        int k=0;
        for (int i=0;i<arr.size();i++){
            tintuc a=arr.get(i);
            String ten=a.tieude.toUpperCase();
            if(ten.equals(s)){
                arr.set(k,a);
                k++;
            }
        }
        notifyDataSetChanged();
    }
}
