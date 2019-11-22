package com.example.vimusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vimusic.database.SongReadDatabase;
import com.example.vimusic.model.PlayList;
import com.example.vimusic.model.PlayListChiTiet;

import java.util.ArrayList;
import java.util.List;

import static com.example.vimusic.database.SongReadDatabase.C_IDPLAYLSITCT;
import static com.example.vimusic.database.SongReadDatabase.C_LOCATIONCT;
import static com.example.vimusic.database.SongReadDatabase.C_NAMEPLAYLSIT;
import static com.example.vimusic.database.SongReadDatabase.T_PLAYLSITCT;

public class PlayListCTDAO {
    private SongReadDatabase songReadDatabase;

    public PlayListCTDAO(Context context) {
        songReadDatabase = new SongReadDatabase(context);
    }

    public long insertPlayListCT(PlayListChiTiet playListChiTiet) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_NAMEPLAYLSIT, playListChiTiet.namepl);
        contentValues.put(C_LOCATIONCT, playListChiTiet.location);


        long result = sqLiteDatabase.insert(T_PLAYLSITCT, null, contentValues);

        sqLiteDatabase.close();

        return result;
    }

    public List<PlayListChiTiet> getAllPL() {

        List<PlayListChiTiet> playListChiTietList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_PLAYLSITCT;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                PlayListChiTiet playListChiTiet = new PlayListChiTiet();
                playListChiTiet.idplct = Integer.parseInt(cursor.getString(0));
                playListChiTiet.namepl = cursor.getString(1);
                playListChiTiet.location = cursor.getString(2);
                playListChiTietList.add(playListChiTiet);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return playListChiTietList;
    }


}
