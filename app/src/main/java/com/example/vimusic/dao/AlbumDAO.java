package com.example.vimusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vimusic.database.SongReadDatabase;
import com.example.vimusic.model.Album;
import com.example.vimusic.model.BaiHat;

import java.util.ArrayList;
import java.util.List;

import static com.example.vimusic.database.SongReadDatabase.C_NAMEALBUM;
import static com.example.vimusic.database.SongReadDatabase.T_ALBUM;
import static com.example.vimusic.database.SongReadDatabase.T_PLAYLSIT;
import static com.example.vimusic.database.SongReadDatabase.T_PLAYLSITCT;
import static com.example.vimusic.database.SongReadDatabase.T_SONG;

public class AlbumDAO {

    private SongReadDatabase songReadDatabase;

    public AlbumDAO(Context context) {
        songReadDatabase = new SongReadDatabase(context);
    }

    public long insertAlbum(Album album) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_NAMEALBUM, album.namealbum);


        long result = sqLiteDatabase.insert(T_ALBUM, null, contentValues);

        sqLiteDatabase.close();

        return result;
    }



    public List<Album> getAllAlbum() {

        List<Album> albumList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_ALBUM;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                Album album = new Album();
                album.namealbum = cursor.getString(0);
                albumList.add(album);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return albumList;
    }

    public List<BaiHat> getAllAlbumCT(String namealbum) {

        List<BaiHat> baiHatList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT songTable.location , songTable.title , songTable.artist FROM albumTable INNER JOIN songTable ON songTable.album = albumTable.namealbum WHERE albumTable.namealbum = " + namealbum;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.location = cursor.getString(0);
                baiHat.title = cursor.getString(1);
                baiHat.artist = cursor.getString(2);
                baiHatList.add(baiHat);
                Log.e("checkpl",baiHatList.toString());
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return baiHatList;
    }

}
