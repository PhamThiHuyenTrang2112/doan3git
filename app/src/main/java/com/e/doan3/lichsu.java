package com.e.doan3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class lichsu extends AppCompatActivity {
    Toolbar toolbar;
    CustemAdapter adapter;
    public static String DATABASE_NAME = "tintuc.sqlite";
    SQLiteDatabase database1;
    ArrayList<tintuc> arr=new ArrayList<>();
    ListView xemls;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lichsutt);
        xemls=(ListView) findViewById(R.id.list_ls);
        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Lịch sử xem tin tức");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        readata();


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void readata() {
        database1 = Database.initDatabase(lichsu.this, DATABASE_NAME);
        Cursor cursor = database1.rawQuery("select * from history", null);
        arr.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tieude = cursor.getString(0);
            String anh = cursor.getString(2);
            String link = cursor.getString(1);
            String thoigian = cursor.getString(3);
            arr.add(new tintuc(tieude,anh,link,thoigian));
        }
        cursor.close();
        //   if (arr.size()>0) {
//            Log.d("DDD", "readdata: " + arr.size()+"-"+arr.get(1).getLink());
        adapter = new CustemAdapter(lichsu.this, android.R.layout.simple_list_item_1, arr);
        xemls.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        }else {
//            Toast.makeText(luutintuc.this, "không có tin tức nào đươc lưu", Toast.LENGTH_SHORT).show();
//        }
    }

}
