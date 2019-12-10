package com.example.vimusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vimusic.database.SongReadDatabase;
import com.example.vimusic.model.Album;
import com.example.vimusic.model.Artist;
import com.example.vimusic.model.BaiHat;

import java.util.ArrayList;
import java.util.List;

import static com.example.vimusic.database.SongReadDatabase.C_NAMEALBUM;
import static com.example.vimusic.database.SongReadDatabase.C_NAMEARTITST;
import static com.example.vimusic.database.SongReadDatabase.T_ALBUM;
import static com.example.vimusic.database.SongReadDatabase.T_ARTIST;

public class ArtistDAO {
    private SongReadDatabase songReadDatabase;

    public ArtistDAO(Context context) {
        songReadDatabase = new SongReadDatabase(context);
    }

    public long insertArtist(Artist artist) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_NAMEARTITST, artist.nameartist);


        long result = sqLiteDatabase.insert(T_ARTIST, null, contentValues);

        sqLiteDatabase.close();

        return result;
    }

    public List<Artist> getAllArtist() {

        List<Artist> artistList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_ARTIST;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                Artist artist = new Artist();
                artist.nameartist = cursor.getString(0);
                artistList.add(artist);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return artistList;
    }

    public List<BaiHat> getAllAlbumCT(String nameartist) {

        List<BaiHat> baiHatList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT songTable.location , songTable.title , songTable.artist FROM artistTable INNER JOIN songTable ON songTable.artist = artistTable.nameartist WHERE artistTable.nameartist = " + nameartist;
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
