package com.e.doan3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class thoitrangFragment extends Fragment {
    ListView trangthoitrang;
    CustemAdapter adapter;
    ArrayList<tintuc> arr = new ArrayList<>();

    public thoitrangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thoitrang, container, false);
        trangthoitrang = (ListView) view.findViewById(R.id.list_thoitrang);
        //        MyTask myTask = new MyTask();
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/thoitrang.rss");
//        Toast.makeText(getActivity(),"tieu de"+arr.get(0).tieude,Toast.LENGTH_SHORT).show();
        Log.d("BBB", "doInBackground: "+arr.size());
        trangthoitrang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),chitiettintuc.class);
                intent.putExtra("link",arr.get(position));
                startActivity(intent);
            }
        });

        return view;
    }
    class MyTask extends AsyncTask<String, Void, ArrayList<tintuc>> {

        @Override
        protected ArrayList<tintuc> doInBackground(String... strings) {
//
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements element = document.select("item");
                tintuc tt = null;
                for (Element elm : element) {
                    tt = new tintuc();
                    tt.setTieude(elm.select("title").text().replaceAll("&#34;","\""));
                    tt.setAnh(Jsoup.parse(elm.select("description").text()).select("img").attr("src"));
                    tt.setLink(elm.select("link").text());
                    tt.setThoigiancn(elm.select("pubDate").text());
                    arr.add(tt);


                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return arr;
        }

        @Override
        protected void onPostExecute(ArrayList<tintuc> tintucs) {
            super.onPostExecute(tintucs);
            if(arr.size()>0){
                adapter= new CustemAdapter(getActivity(),R.layout.cutom_list_item,arr);
                trangthoitrang.setAdapter(adapter);
            }
            else {
                Toast.makeText(getActivity(),"Bị lỗi",Toast.LENGTH_SHORT).show();
            }


        }
    }
}
