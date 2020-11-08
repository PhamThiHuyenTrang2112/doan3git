package com.e.doan3;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class chitiettintuc extends AppCompatActivity {
    public static String DATABASE_NAME = "tintuc.sqlite";
    SQLiteDatabase database1;
    String link;

    Button trove,luutin;
    ArrayList<tintuc> arr;
//    private String filename = "tintuc.txt";
//    private String filepath = "coder.vm";
//    File myInternalFile;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    ArrayList<tintuc> arrluutin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiettt);
        arr=new ArrayList<>();
        WebView chitiet=(WebView) findViewById(R.id.webview);
        Intent intent=getIntent();
//        String link=intent.getStringExtra("link");
        final tintuc tintuc1= (com.e.doan3.tintuc) intent.getSerializableExtra("link");
        link=tintuc1.getLink();
        chitiet.getSettings().setJavaScriptEnabled(true);
        chitiet.loadUrl(link);
        chitiet.setWebViewClient(new WebViewClient());
        trove=(Button) findViewById(R.id.btntrove);
        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(chitiettintuc.this,TrangChu.class);
                startActivity(it);
            }
        });
        luutin=(Button) findViewById(R.id.btnluu);
        luutin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieude=tintuc1.tieude;
                String anh=tintuc1.anh;
                String duongdan=tintuc1.link;
                String tgcapnhat=tintuc1.thoigiancn;

                database1 = Database.initDatabase(chitiettintuc.this, DATABASE_NAME);
                ContentValues ct = new ContentValues();
                ct.put("Tieude",tieude);
                ct.put("Anh",anh);
                ct.put("Link",duongdan);
                ct.put("ThoiGian",tgcapnhat);

                database1.insert("TinTuc", null, ct);
                Toast.makeText(chitiettintuc.this,"Bạn đã lưu tin tức",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Share(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+ link));
        startActivity(browserIntent);
    }
}
