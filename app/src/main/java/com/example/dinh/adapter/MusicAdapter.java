package com.example.dinh.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dinh.MainActivity;
import com.example.dinh.R;
import com.example.dinh.model.Music;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {
    Activity context;
    int resource;
    List<Music> objects;
    public MusicAdapter(Activity context, int resource,List<Music> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        TextView txtMaBH = row.findViewById(R.id.txtMaBH);
        TextView txtTen = row.findViewById(R.id.txtTenBH);
        TextView txtCaSi = row.findViewById(R.id.txtCaSi);
        ImageButton btnLike = row.findViewById(R.id.btnLike);
        ImageButton btnDisLike = row.findViewById(R.id.btnDisLike);

        final Music music = this.objects.get(position);
        txtTen.setText(music.getTenBH());
        txtCaSi.setText(music.getCaSi());
        txtMaBH.setText(music.getMaBH());
        if (music.isThich()){
            btnLike.setVisibility(View.INVISIBLE);
            btnDisLike.setVisibility(View.VISIBLE);
        }
        else {
            btnLike.setVisibility(View.VISIBLE);
            btnDisLike.setVisibility(View.INVISIBLE);
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThich(music);
            }
        });
        btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyKoThich(music);
            }
        });
        return row;
    }

    private void xuLyKoThich(Music music) {
        ContentValues row = new ContentValues();
        row.put("YEUTHICH",0);
        MainActivity.database.update("MyKaraoke",row,"MABH=?",new String[]{music.getMaBH()});
    }

    private void xuLyThich(Music music) {
        ContentValues row = new ContentValues();
        row.put("YEUTHICH",1);
        MainActivity.database.update("MyKaraoke",row,"MABH=?",new String[]{music.getMaBH()});

    }
}
