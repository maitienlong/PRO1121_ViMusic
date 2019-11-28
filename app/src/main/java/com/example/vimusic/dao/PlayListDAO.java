package com.example.vimusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vimusic.database.SongReadDatabase;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.PlayList;

import java.util.ArrayList;
import java.util.List;

import static com.example.vimusic.database.SongReadDatabase.C_DETAIL;
import static com.example.vimusic.database.SongReadDatabase.C_NAMEPLAYLIST;
import static com.example.vimusic.database.SongReadDatabase.T_PLAYLSIT;

public class PlayListDAO {
    private SongReadDatabase songReadDatabase;

    public PlayListDAO(Context context) {
        songReadDatabase = new SongReadDatabase(context);
    }

    public long insertPlayList(PlayList playList) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_NAMEPLAYLIST, playList.nameplaylist);
        contentValues.put(C_DETAIL, playList.detail);


        long result = sqLiteDatabase.insert(T_PLAYLSIT, null, contentValues);

        sqLiteDatabase.close();

        return result;
    }

    public List<PlayList> getAllPL() {

        List<PlayList> playListList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_PLAYLSIT;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                PlayList playList = new PlayList();
                playList.nameplaylist = cursor.getString(0);
                playList.detail = cursor.getString(1);
                playListList.add(playList);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return playListList;
    }

    public List<PlayList> checksize() {

        List<PlayList> playListList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_PLAYLSIT;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                PlayList playList = new PlayList();
                playList.nameplaylist = cursor.getString(0);
                playListList.add(playList);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return playListList;
    }




}
