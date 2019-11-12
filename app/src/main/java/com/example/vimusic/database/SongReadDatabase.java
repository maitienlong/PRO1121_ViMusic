package com.example.vimusic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SongReadDatabase extends SQLiteOpenHelper {

    public SongReadDatabase(Context context) {
        super(context, "SQLiteVer1.db", null, 1);
    }

    public final static String T_SONG = "songTable";
    public final static String C_LOACTION = "location";
    public final static String C_TITLE = "title";
    public final static String C_ARTIST = "artist";
    public final static String C_ALBUM = "album";

    public final static String TABLE_SONG = "CREATE TABLE songTable (location NVARCHAR PRIMARY KEY, title NVARCHAR, artist NVARCHAR, album NVARCHAR)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SONG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
