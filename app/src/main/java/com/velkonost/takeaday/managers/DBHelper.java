package com.velkonost.takeaday.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static com.velkonost.takeaday.managers.DBHelper.DBConstants.DONE;

/**
 * @author Velkonost
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);

        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("LOG_DB", "--- onCreate database ---");

        db.execSQL("create table " + DONE + " ("
                + "id integer primary key autoincrement,"
                + "list text" + ");");
        insertEmptyArrayList();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class DBConstants {
        public static final String DONE = "done";
    }

    public Cursor queryDoneChallenges() {}
//    String query = "SELECT ROWID from " + DONE + " order by ROWID DESC limit 1";


    public void insertEmptyArrayList() {

        ArrayList arrayList = new ArrayList();

        ContentValues cvColumn = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        cvColumn.put("list", String.valueOf(arrayList));

        db.insert(DONE, null, cvColumn);

    }
}
