package com.e.doan3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
public class tintrongngayFragment extends Fragment {
    public static String DATABASE_NAME = "tintuc.sqlite";
    SQLiteDatabase database1;
    public static ListView trangtttrongngay;
    public static CustemAdapter adapter;
    ArrayList<tintuc> arr = new ArrayList<>();

    public tintrongngayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tintuctn, container, false);
        trangtttrongngay = (ListView) view.findViewById(R.id.list_new);
        //        MyTask myTask = new MyTask();
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/tintuctrongngay.rss");
//        Toast.makeText(getActivity(),"tieu de"+arr.get(0).tieude,Toast.LENGTH_SHORT).show();
        Log.d("BBB", "doInBackground: "+arr.size());
        trangtttrongngay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),chitiettintuc.class);
                intent.putExtra("link",arr.get(position));
//                intent.putExtra("link",arr.get(position).getLink());
                startActivity(intent);
                final tintuc tintuc1= (com.e.doan3.tintuc) intent.getSerializableExtra("link");
                String tieude=tintuc1.tieude;
                String anh=tintuc1.anh;
                String duongdan=tintuc1.link;
                String tgcapnhat=tintuc1.thoigiancn;

                database1 = Database.initDatabase(getActivity(), DATABASE_NAME);
                ContentValues ct = new ContentValues();
                ct.put("Tieude",tieude);
                ct.put("Anh",anh);
                ct.put("Link",duongdan);
                ct.put("ThoiGian",tgcapnhat);

                database1.insert("history", null, ct);
//               // Toast.makeText(chitiettintuc.this,"Bạn đã lưu tin tức",Toast.LENGTH_SHORT).show();

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
            adapter= new CustemAdapter(getActivity(),R.layout.cutom_list_item,arr);
            trangtttrongngay.setAdapter(adapter);

        }
    }
}





