package com.example.dinh;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.dinh.adapter.MusicAdapter;
import com.example.dinh.model.Music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String DATABASE_NAME = "db.db";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    ListView lvBHGoc;
    ArrayList<Music> dsBHGoc;
    MusicAdapter musicAdapter;

    ListView lvBHYeuThich;
    ArrayList<Music> dsBHYeuThich;
    MusicAdapter musicYeuThichAdapter;

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xuLySaoChepCSDL();

        addControls();
        addEvents();
    }

    private void xuLySaoChepCSDL() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) { // nếu chưa tồn tại thì sao chép
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "sao chép thành công", Toast.LENGTH_LONG).show();
            } catch
            (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        InputStream myInput;
        try {
            myInput = getAssets().open(DATABASE_NAME);
            String tenFile = layDuongDanLuuTru();
            // xem trong thư mục tồn tại chưa, nếu chưa thì tạo mới
            File duongDan = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!duongDan.exists()) {// hoi xem ton tai hay chua
                duongDan.mkdir(); // tao thu muc
            }
            OutputStream myOutput = new FileOutputStream(tenFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            Log.e("Loi", e.toString());
        }
    }

    private String layDuongDanLuuTru() {
        //getApplicationInfo().dataDir tra ve thu muc goc cai dat : tra ve package
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t1")) {
                    xuLyHienThiBHGoc();
                } else if (tabId.equalsIgnoreCase("t2")) {
                    xuLyHienThiBHYeuThich();
                }
            }
        });
    }

    private void xuLyHienThiBHYeuThich() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query("MyKaraoke", null, "YEUTHICH =?", new String[]{"1"}, null, null, null);
        dsBHYeuThich.clear();
        while (cursor.moveToNext()) {
            String mabh = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String tacgia = cursor.getString(3);
            int yeuthich = cursor.getInt(5);

            Music music = new Music();
            music.setMaBH(mabh);
            music.setTenBH(tenbh);
            music.setCaSi(tacgia);
            music.setThich(yeuthich == 1);

            dsBHYeuThich.add(music);
        }
        cursor.close();
        musicYeuThichAdapter.notifyDataSetChanged();
    }

    private void xuLyHienThiBHGoc() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query("MyKaraoke", null, null, null, null, null, null);
        dsBHGoc.clear();
        while (cursor.moveToNext()) {
            String mabh = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String tacgia = cursor.getString(3);
            int yeuthich = cursor.getInt(5);

            Music music = new Music();
            music.setMaBH(mabh);
            music.setTenBH(tenbh);
            music.setCaSi(tacgia);
            music.setThich(yeuthich == 1);

            dsBHGoc.add(music);
        }
        cursor.close();
        musicAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        //Truyen hinh va goi tab 1
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.hinh1));
        tabHost.addTab(tab1);
        //truyen va goi tab 2
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.hinh2));
        tabHost.addTab(tab2);

        //bai hat goc
        lvBHGoc = findViewById(R.id.lvBHGoc);
        dsBHGoc = new ArrayList<>();
        musicAdapter = new MusicAdapter(MainActivity.this,
                R.layout.item, dsBHGoc);
        lvBHGoc.setAdapter(musicAdapter);
        //bai hat yeu thich
        lvBHYeuThich = findViewById(R.id.lvBHYeuThich);
        dsBHYeuThich = new ArrayList<>();
        musicYeuThichAdapter = new MusicAdapter(MainActivity.this,
                R.layout.item, dsBHYeuThich);
        lvBHYeuThich.setAdapter(musicYeuThichAdapter);

        //Them Bai Hat
        //     giaLapBaiHat();
    }

//    private void giaLapBaiHat() {
//        dsBHGoc.add(new Music("12345","Lòng Me","Huong Giang",true));
//        dsBHGoc.add(new Music("45678","Me Toi","Thu Minh",false));
//        dsBHGoc.add(new Music("89012","Dao Lam Con","Dan Truong",false));
//        dsBHGoc.add(new Music("32453","Tinh Me","Lam Truong",false));
//        dsBHGoc.add(new Music("34342","Tinh Cha","Ung Hoang Phuc",false));
//        musicAdapter.notifyDataSetChanged();
//    }
}
