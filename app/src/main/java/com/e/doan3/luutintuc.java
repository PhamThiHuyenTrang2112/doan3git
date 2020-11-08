package com.e.doan3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class luutintuc extends AppCompatActivity {
    int pos;
    TextView tieudett;
    public static String DATABASE_NAME = "tintuc.sqlite";
    SQLiteDatabase database1;
    Toolbar toolbar;
    ListView luutt;
    CustemAdapter adapter;
    ArrayList<tintuc> arr=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luutin);
        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Tin tức đã lưu");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        tieudett=(TextView) findViewById(R.id.txttieude);
        luutt=(ListView) findViewById(R.id.list_save);

        readdata();
        registerForContextMenu(luutt);
       luutt.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               pos=position;
               return false;
           }
       });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemxem:
                Intent intent=new Intent(luutintuc.this,chitiettintuc.class);
                intent.putExtra("link",arr.get(pos));
                startActivity(intent);
                break;
            case R.id.itemxoa:
                final AlertDialog.Builder al = new AlertDialog.Builder(luutintuc.this);
                al.setTitle("Thông Báo");
                al.setMessage("Bạn có chắc chắn muốn xóa tin tức này ?");
                al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tieude = arr.get(pos).tieude;
                        database1 = Database.initDatabase(luutintuc.this, DATABASE_NAME);
                        String sql = "Delete from TinTuc where Tieude='" +tieude + "'";
                        database1.execSQL(sql);
                        readdata();
                       // adapter.notifyDataSetChanged();
                        Toast.makeText(luutintuc.this,"Bạn xóa thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                al.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(luutintuc.this,"Bạn đã không xóa!!",Toast.LENGTH_SHORT).show();

                    }
                });
                al.create().show();

        }


        return super.onContextItemSelected(item);
    }

    private void readdata() {

        database1 = Database.initDatabase(luutintuc.this, DATABASE_NAME);
        Cursor cursor = database1.rawQuery("select * from TinTuc", null);
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
        adapter = new CustemAdapter(luutintuc.this, android.R.layout.simple_list_item_1, arr);
        luutt.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        }else {
//            Toast.makeText(luutintuc.this, "không có tin tức nào đươc lưu", Toast.LENGTH_SHORT).show();
//        }
    }

}
