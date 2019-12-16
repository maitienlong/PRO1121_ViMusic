package com.example.vimusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vimusic.database.SongReadDatabase;
import com.example.vimusic.model.BaiHat;

import java.util.ArrayList;
import java.util.List;

import static com.example.vimusic.database.SongReadDatabase.C_ALBUM;
import static com.example.vimusic.database.SongReadDatabase.C_ARTIST;
import static com.example.vimusic.database.SongReadDatabase.C_LOACTION;
import static com.example.vimusic.database.SongReadDatabase.C_LOVE;
import static com.example.vimusic.database.SongReadDatabase.C_TITLE;
import static com.example.vimusic.database.SongReadDatabase.T_PLAYLSIT;
import static com.example.vimusic.database.SongReadDatabase.T_PLAYLSITCT;
import static com.example.vimusic.database.SongReadDatabase.T_SONG;

public class BaiHatDAO {

    private SongReadDatabase songReadDatabase;

    public BaiHatDAO(Context context) {
        songReadDatabase = new SongReadDatabase(context);
    }

    public long insertSong(BaiHat baiHat) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_LOACTION, baiHat.location);
        contentValues.put(C_TITLE, baiHat.title);
        contentValues.put(C_ARTIST, baiHat.artist);
        contentValues.put(C_ALBUM, baiHat.album);
        contentValues.put(C_LOVE, baiHat.love);

        long result = sqLiteDatabase.insert(T_SONG, null, contentValues);

        sqLiteDatabase.close();

        return result;
    }

    public long updateSong(BaiHat baiHat) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_LOACTION, baiHat.location);
        contentValues.put(C_LOVE, baiHat.love);

        long result = sqLiteDatabase.update(T_SONG, contentValues, C_LOACTION + "=?", new String[]{String.valueOf(baiHat.location)});

        sqLiteDatabase.close();

        return result;
    }


    //------------------------------------------------ LẤY DS THỂ LOẠI  -------------------------------------------------------

    public List<BaiHat> getAllSong() {

        List<BaiHat> baiHatList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_SONG;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.location = cursor.getString(0);
                baiHat.title = cursor.getString(1);
                baiHat.artist = cursor.getString(2);
                baiHat.album = cursor.getString(3);

                baiHatList.add(baiHat);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return baiHatList;
    }


    public List<BaiHat> getAllSongLove() {

        List<BaiHat> baiHatList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_SONG + " WHERE " + " love " + " = " + 1;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.location = cursor.getString(0);
                baiHat.title = cursor.getString(1);
                baiHat.artist = cursor.getString(2);
                baiHat.album = cursor.getString(3);
                baiHatList.add(baiHat);
            } while (cursor.moveToNext());
        }
        Log.e("getAllSongLove", baiHatList.size() + "");
        sqLiteDatabase.close();

        return baiHatList;
    }

    public long updateSongHi(BaiHat baiHat) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_LOACTION, baiHat.location);
        contentValues.put(C_TITLE, baiHat.title);
        contentValues.put(C_ARTIST, baiHat.artist);
        contentValues.put(C_ALBUM, baiHat.album);

        long result = sqLiteDatabase.update(T_SONG, contentValues, C_LOACTION + "=?", new String[]{String.valueOf(baiHat.location)});

        sqLiteDatabase.close();

        return result;
    }


    public List<BaiHat> getAllSearch(String keysearch) {

        List<BaiHat> baiHatList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT location , title , artist FROM songTable WHERE title LIKE " + keysearch;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.location = cursor.getString(0);
                baiHat.title = cursor.getString(1);
                baiHat.artist = cursor.getString(2);
                baiHatList.add(baiHat);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return baiHatList;
    }
    public List<BaiHat> getPlaySearch(String keysearch) {

        List<BaiHat> baiHatList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT location , title , artist FROM songTable WHERE title = " + keysearch;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.location = cursor.getString(0);
                baiHat.title = cursor.getString(1);
                baiHat.artist = cursor.getString(2);
                baiHatList.add(baiHat);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return baiHatList;
    }



}
