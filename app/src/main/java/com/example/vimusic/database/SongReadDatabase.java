package com.example.vimusic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongReadDatabase extends SQLiteOpenHelper {

    public SongReadDatabase(Context context) {
        super(context, "SQLiteVer11.db", null, 1);
    }

    public final static String T_SONG = "songTable";
    public final static String C_LOACTION = "location";
    public final static String C_TITLE = "title";
    public final static String C_ARTIST = "artist";
    public final static String C_ALBUM = "album";
    public final static String C_LOVE = "love";

    public final static String T_PLAYLSIT = "playlistTable";
    public final static String C_NAMEPLAYLIST = "namepl";
    public final static String C_DETAIL = "detail";

    public final static String T_PLAYLSITCT = "playlistctTable";
    public final static String C_IDPLAYLSITCT = "idplct";
    public final static String C_NAMEPLAYLSIT = "namepl";
    public final static String C_LOCATIONCT = "location";

    public final static String T_ALBUM = "albumTable";
    public final static String C_NAMEALBUM = "namealbum";

    public final static String T_ARTIST = "artistTable";
    public final static String C_NAMEARTITST = "nameartist";

    public final static String TABLE_SONG = "CREATE TABLE songTable (location NVARCHAR PRIMARY KEY, title NVARCHAR, artist NVARCHAR, album NVARCHAR, love BOOLEAN)";
    public final static String TABLE_PLAYLIST = "CREATE TABLE playlistTable (namepl NVARCHAR PRIMARY KEY, detail NVARCHAR)";
    public final static String TABLE_PLAYLSITCT = "CREATE TABLE playlistctTable (idplct INTEGER PRIMARY KEY AUTOINCREMENT, namepl NVARCHAR, location VARCHAR)";
    public final static String TABLE_ALBUM = "CREATE TABLE albumTable (namealbum NVARCHAR PRIMARY KEY)";
    public final static String TABLE_ARTIST = "CREATE TABLE artistTable (nameartist NVARCHAR PRIMARY KEY)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SONG);
        db.execSQL(TABLE_PLAYLIST);
        db.execSQL(TABLE_PLAYLSITCT);
        db.execSQL(TABLE_ALBUM);
        db.execSQL(TABLE_ARTIST);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
