package com.e.doan3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.net.wifi.WifiConfiguration.Status.strings;

public class TrangChu extends AppCompatActivity {
    ListView tk, listViewtrangtru;
    String s;
    CustemAdapter adapter;
    ImageButton bMenu;
    DrawerLayout drawerLayout;
    public TabLayout mtablrlayout;
    private ViewPager mviewpager;
    private Viewpageradapter viewpageradapter;
    EditText timkiem;
    ArrayList<tintuc> arrTimkiem;


    ArrayList<tintuc> arr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        arr = new ArrayList<>();
        listViewtrangtru = findViewById(R.id.listviewtrangtru);
        mtablrlayout = findViewById(R.id.tab);
        mviewpager = findViewById(R.id.view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        bMenu = findViewById(R.id.bMenu);
        bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/tintuctrongngay.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/thoitrang.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/thethao.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/taichinhbatdongsan.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/phithuongkyquac.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/lamdep.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/giaoducduhoc.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/amthuc.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/giaoducduhoc.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/congnghethongtin.rss");
        new MyTask().execute("https://cdn.24h.com.vn/upload/rss/anninhhinhsu.rss");




        mtablrlayout.setupWithViewPager(mviewpager);

        tk = (ListView) findViewById(R.id.list_new);

        addViewpager(mviewpager);
        viewpageradapter = new Viewpageradapter(getSupportFragmentManager());
        tintuc tt = new tintuc();

        timkiem = (EditText) findViewById(R.id.edttimkiem);
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tintrongngayFragment tintrongngayFragment = new tintrongngayFragment();
                s = timkiem.getText().toString().toUpperCase();
                String d = s.toString().toUpperCase();
//                Toast.makeText(TrangChu.this,"có;"+d,Toast.LENGTH_SHORT).show();
                arrTimkiem=new ArrayList<>();
                if (!d.equals("")) {
                    for (int i = 0; i < arr.size(); i++) {
                        tintuc tt = arr.get(i);
                        String ten = tt.tieude.toUpperCase().trim();
                        if (ten.contains(d)) {
                            arrTimkiem.add(tt);
                        }
                    }

                    if (arrTimkiem.size() > 0) {
//                     Toast.makeText(TrangChu.this,"có",Toast.LENGTH_SHORT).show();
                        mviewpager.setVisibility(View.GONE);
                        adapter = new CustemAdapter(TrangChu.this, android.R.layout.simple_list_item_1, arrTimkiem);
                        listViewtrangtru.setAdapter(adapter);
                        listViewtrangtru.setVisibility(View.VISIBLE);
                    }
                    else {
//                        Toast.makeText(TrangChu.this,"không có từ khóa cần tìm",Toast.LENGTH_SHORT).show();
                        listViewtrangtru.setVisibility(View.GONE);
                        mviewpager.setVisibility(View.VISIBLE);
                    }
                } else {
                    listViewtrangtru.setVisibility(View.GONE);
                    mviewpager.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        listViewtrangtru.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TrangChu.this,chitiettintuc.class);
                intent.putExtra("link",arrTimkiem.get(position).getLink());
                startActivity(intent);
            }
        });

    }

    public void Save(View view) {
        Intent it=new Intent(TrangChu.this,luutintuc.class);
        startActivity(it);

    }

    public void thongtin(View view) {
        Intent it= new Intent(TrangChu.this,thongtinud.class);
        startActivity(it);
    }

    public void huongdan(View view) {
        Intent it= new Intent(TrangChu.this,huongdansdung.class);
        startActivity(it);
    }

    public void lichsu(View view) {
        Intent it=new Intent(TrangChu.this,lichsu.class);
        startActivity(it);
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
                    tt.setTieude(elm.select("title").text().replaceAll("&#34;", "\""));
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
    }

    private void addViewpager(ViewPager viewPager) {
        Viewpageradapter viewpageradapter = new Viewpageradapter(getSupportFragmentManager());
        viewpageradapter.addTab(new tintrongngayFragment(), "Tin trong ngày");
        viewpageradapter.addTab(new thoitrangFragment(), "Thời trang");
        viewpageradapter.addTab(new amthucFragment(), "ẩm thực");
        viewpageradapter.addTab(new TheThaoFragment(), "Thể thao");
        viewpageradapter.addTab(new congnttFragment(), "CNTT");
        viewpageradapter.addTab(new AnNinhFragment(), "An ninh-Hình sự");
        viewpageradapter.addTab(new LamdepFragment(), "Làm đẹp");
        viewpageradapter.addTab(new PhithuongFragment(), "Phi thường-Kì quặc");
        viewpageradapter.addTab(new TaiChinhFragment(), "Tài chính–Bất động sản");
        viewpageradapter.addTab(new GiaoducFragment(), "Giáo dục-du học");
        viewPager.setAdapter(viewpageradapter);
    }


}

