package com.velkonost.takeaday.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.velkonost.takeaday.R;

import java.util.ArrayList;
import java.util.Arrays;

import static com.velkonost.takeaday.Constants.MAX;
import static com.velkonost.takeaday.managers.DBHelper.DBConstants.CHALLENGES;
import static com.velkonost.takeaday.managers.DBHelper.DBConstants.DONE;

/**
 * @author Velkonost
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB15", null, 1);

        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("LOG_DB", "--- onCreate database ---");

        db.execSQL("create table if not exists " + DONE + " ("
                + "id integer primary key autoincrement,"
                + "list text" + ");");

        db.execSQL("create table if not exists " + CHALLENGES + " ("
                + "id integer primary key autoincrement,"
                + "title text,"
                + "desc text" + ");");
//        insertEmptyArrayList();

        ArrayList arrayList = new ArrayList();
        arrayList.add(9999999);
        ContentValues cvColumn = new ContentValues();
        cvColumn.put("list", String.valueOf(arrayList));
        cvColumn.put("id", 0);
        db.insert(DONE, null, cvColumn);


        ArrayList<String> arrayTitles = new ArrayList<String>();
        ArrayList<String> arrayTexts = new ArrayList<String>();

        arrayTitles.add(mContext.getResources().getString(R.string.t1));
        arrayTitles.add(mContext.getResources().getString(R.string.t2));
        arrayTitles.add(mContext.getResources().getString(R.string.t3));
        arrayTitles.add(mContext.getResources().getString(R.string.t4));
        arrayTitles.add(mContext.getResources().getString(R.string.t5));
        arrayTitles.add(mContext.getResources().getString(R.string.t6));
        arrayTitles.add(mContext.getResources().getString(R.string.t7));
        arrayTitles.add(mContext.getResources().getString(R.string.t8));
        arrayTitles.add(mContext.getResources().getString(R.string.t9));
        arrayTitles.add(mContext.getResources().getString(R.string.t10));
        arrayTitles.add(mContext.getResources().getString(R.string.t11));
        arrayTitles.add(mContext.getResources().getString(R.string.t12));
        arrayTitles.add(mContext.getResources().getString(R.string.t13));
        arrayTitles.add(mContext.getResources().getString(R.string.t14));
        arrayTitles.add(mContext.getResources().getString(R.string.t15));
        arrayTitles.add(mContext.getResources().getString(R.string.t16));
        arrayTitles.add(mContext.getResources().getString(R.string.t17));
        arrayTitles.add(mContext.getResources().getString(R.string.t18));
        arrayTitles.add(mContext.getResources().getString(R.string.t19));
        arrayTitles.add(mContext.getResources().getString(R.string.t20));
        arrayTitles.add(mContext.getResources().getString(R.string.t21));
        arrayTitles.add(mContext.getResources().getString(R.string.t22));
        arrayTitles.add(mContext.getResources().getString(R.string.t23));
        arrayTitles.add(mContext.getResources().getString(R.string.t24));
        arrayTitles.add(mContext.getResources().getString(R.string.t25));
        arrayTitles.add(mContext.getResources().getString(R.string.t26));
        arrayTitles.add(mContext.getResources().getString(R.string.t27));
        arrayTitles.add(mContext.getResources().getString(R.string.t28));
        arrayTitles.add(mContext.getResources().getString(R.string.t29));
        arrayTitles.add(mContext.getResources().getString(R.string.t30));
        arrayTitles.add(mContext.getResources().getString(R.string.t31));
        arrayTitles.add(mContext.getResources().getString(R.string.t32));
        arrayTitles.add(mContext.getResources().getString(R.string.t33));
        arrayTitles.add(mContext.getResources().getString(R.string.t34));
        arrayTitles.add(mContext.getResources().getString(R.string.t35));
        arrayTitles.add(mContext.getResources().getString(R.string.t36));
        arrayTitles.add(mContext.getResources().getString(R.string.t37));
        arrayTitles.add(mContext.getResources().getString(R.string.t38));
        arrayTitles.add(mContext.getResources().getString(R.string.t39));
        arrayTitles.add(mContext.getResources().getString(R.string.t40));
        arrayTitles.add(mContext.getResources().getString(R.string.t41));
        arrayTitles.add(mContext.getResources().getString(R.string.t42));
        arrayTitles.add(mContext.getResources().getString(R.string.t43));
        arrayTitles.add(mContext.getResources().getString(R.string.t44));

        arrayTexts.add(mContext.getResources().getString(R.string.d1));
        arrayTexts.add(mContext.getResources().getString(R.string.d2));
        arrayTexts.add(mContext.getResources().getString(R.string.d3));
        arrayTexts.add(mContext.getResources().getString(R.string.d4));
        arrayTexts.add(mContext.getResources().getString(R.string.d5));
        arrayTexts.add(mContext.getResources().getString(R.string.d6));
        arrayTexts.add(mContext.getResources().getString(R.string.d7));
        arrayTexts.add(mContext.getResources().getString(R.string.d8));
        arrayTexts.add(mContext.getResources().getString(R.string.d9));
        arrayTexts.add(mContext.getResources().getString(R.string.d10));
        arrayTexts.add(mContext.getResources().getString(R.string.d11));
        arrayTexts.add(mContext.getResources().getString(R.string.d12));
        arrayTexts.add(mContext.getResources().getString(R.string.d13));
        arrayTexts.add(mContext.getResources().getString(R.string.d14));
        arrayTexts.add(mContext.getResources().getString(R.string.d15));
        arrayTexts.add(mContext.getResources().getString(R.string.d16));
        arrayTexts.add(mContext.getResources().getString(R.string.d17));
        arrayTexts.add(mContext.getResources().getString(R.string.d18));
        arrayTexts.add(mContext.getResources().getString(R.string.d19));
        arrayTexts.add(mContext.getResources().getString(R.string.d20));
        arrayTexts.add(mContext.getResources().getString(R.string.d21));
        arrayTexts.add(mContext.getResources().getString(R.string.d22));
        arrayTexts.add(mContext.getResources().getString(R.string.d23));
        arrayTexts.add(mContext.getResources().getString(R.string.d24));
        arrayTexts.add(mContext.getResources().getString(R.string.d25));
        arrayTexts.add(mContext.getResources().getString(R.string.d26));
        arrayTexts.add(mContext.getResources().getString(R.string.d27));
        arrayTexts.add(mContext.getResources().getString(R.string.d28));
        arrayTexts.add(mContext.getResources().getString(R.string.d29));
        arrayTexts.add(mContext.getResources().getString(R.string.d30));
        arrayTexts.add(mContext.getResources().getString(R.string.d31));
        arrayTexts.add(mContext.getResources().getString(R.string.d32));
        arrayTexts.add(mContext.getResources().getString(R.string.d33));
        arrayTexts.add(mContext.getResources().getString(R.string.d34));
        arrayTexts.add(mContext.getResources().getString(R.string.d35));
        arrayTexts.add(mContext.getResources().getString(R.string.d36));
        arrayTexts.add(mContext.getResources().getString(R.string.d37));
        arrayTexts.add(mContext.getResources().getString(R.string.d38));
        arrayTexts.add(mContext.getResources().getString(R.string.d39));
        arrayTexts.add(mContext.getResources().getString(R.string.d40));
        arrayTexts.add(mContext.getResources().getString(R.string.d41));
        arrayTexts.add(mContext.getResources().getString(R.string.d42));
        arrayTexts.add(mContext.getResources().getString(R.string.d43));
        arrayTexts.add(mContext.getResources().getString(R.string.d44));

        for (int i = 0; i < MAX + 1; i++) {
            ContentValues cv = new ContentValues();
            cv.put("title", arrayTitles.get(i));
            cv.put("desc", arrayTexts.get(i));
            cv.put("id", i);
            db.insert(CHALLENGES, null, cv);
        }


    }

    public Cursor queryByIdChallenge(int id) {
        return this.getWritableDatabase().query(CHALLENGES,
                null,
                "id = ?",
                new String[] {String.valueOf(id)},
                null, null, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class DBConstants {
        public static final String DONE = "donee";
        public static final String CHALLENGES = "challenges";
    }

    public Cursor queryDoneChallenges() {

        return this.getWritableDatabase().query(DONE,
                null,
                "id = ?",
                new String[] {String.valueOf(0)},
                null, null, null);


    }

    public void updateDoneChallenges(int newId) {
        ArrayList dones = new ArrayList();

        Cursor c = this.getWritableDatabase().query(DONE,
                null,
                "id = ?",
                new String[] {String.valueOf(0)},
                null, null, null);

        if (c.moveToFirst()) {

            int challengeTaskIndex = c.getColumnIndex("list");

            String name = c.getString(challengeTaskIndex);

            dones = new ArrayList<String>(Arrays.asList(name.substring(1, name.length() - 1)));


        }

        dones.add(newId);

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put("list", String.valueOf(dones));

        db.update(DONE, cv, "id = ?", new String[] {String.valueOf(0)});
    }

//    String query = "SELECT ROWID from " + DONE + " order by ROWID DESC limit 1";


    public void insertEmptyArrayList() {



    }

    public void resetChallenges() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(9999999);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put("list", String.valueOf(arrayList));

        db.update(DONE, cv, "id = ?", new String[] {String.valueOf(0)});
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
