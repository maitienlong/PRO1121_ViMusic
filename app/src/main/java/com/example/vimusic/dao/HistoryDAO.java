package com.example.vimusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vimusic.database.SongReadDatabase;
import com.example.vimusic.model.History;

import java.util.ArrayList;
import java.util.List;

import static com.example.vimusic.database.SongReadDatabase.C_NAMEHIS;
import static com.example.vimusic.database.SongReadDatabase.T_HISTORY;

public class HistoryDAO {


    private SongReadDatabase songReadDatabase;

    public HistoryDAO(Context context) {
        songReadDatabase = new SongReadDatabase(context);
    }

    public long insertHistory(History history) {
        SQLiteDatabase sqLiteDatabase = songReadDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(C_NAMEHIS, history.namehistory);

        long result = sqLiteDatabase.insert(T_HISTORY, null, contentValues);

        sqLiteDatabase.close();

        return result;
    }

    public List<History> getAllHistory() {

        List<History> historyList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = songReadDatabase.getReadableDatabase();

        String SELECT = "SELECT * FROM " + T_HISTORY;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.namehistory = cursor.getString(0);
                historyList.add(history);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return historyList;
    }

}
