package com.learn2crack;

/**
 * Created by ikram on 19/12/2014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class KLSKPRDbAdapter {


    public static final String KEY_ROWID = "_id";
    public static final String KEY_TNO = "tno";
    public static final String KEY_FROMW = "fromw";
    public static final String KEY_TOW = "tow";
    public static final String KEY_FROMTIME = "ftime";
    public static final String KEY_ARRIVALTIME = "atime";
    public static final String KEY_PRICE = "price";

    private static final String TAG = "KLSKPRDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "Timedb";
    private static final String SQLITE_TABLE = "tbl_klskpr";
    private static final int DATABASE_VERSION = 10;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_TNO + "," +
                    KEY_FROMW + "," +
                    KEY_TOW + "," +
                    KEY_FROMTIME + "," +
                    KEY_ARRIVALTIME + "," +
                    KEY_PRICE + ");" ;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public KLSKPRDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public KLSKPRDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createCountry(String tno, String fromw,
                              String tow, String ftime,
                              String atime, String price ) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TNO, tno);
        initialValues.put(KEY_FROMW, fromw);
        initialValues.put(KEY_TOW, tow);
        initialValues.put(KEY_FROMTIME, ftime);
        initialValues.put(KEY_ARRIVALTIME, atime);
        initialValues.put(KEY_PRICE, price);


        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllCountries() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_TNO, KEY_FROMW, KEY_TOW, KEY_FROMTIME, KEY_ARRIVALTIME
                            ,KEY_PRICE},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_TNO, KEY_FROMW, KEY_TOW, KEY_FROMTIME,KEY_ARRIVALTIME, KEY_PRICE },
                    KEY_FROMW + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCountries() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_TNO, KEY_FROMW, KEY_TOW, KEY_FROMTIME,KEY_ARRIVALTIME, KEY_PRICE },
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCountries() {

        createCountry("EG02","KL Sentral","Kampar","0600","0757","31MYR");
        createCountry("EG04","KL Sentral","Kampar","0900","1057","31MYR");
        createCountry("ES02","KL Sentral","Kampar","1100","1303","22MYR");
        createCountry("EG06","KL Sentral","Kampar","1300","1457","31MYR");
        createCountry("EG08","KL Sentral","Kampar","1400","1557","31MYR");
        createCountry("ES04","KL Sentral","Kampar","1500","1703","22MYR");
        createCountry("EG10","KL Sentral","Kampar","1800","1957","31MYR");
        createCountry("EG12","KL Sentral","Kampar","1900","2057","31MYR");
        createCountry("EG14","KL Sentral","Kampar","2000","2157","31MYR");
        createCountry("ES06","KL Sentral","Kampar","2100","2303","22MYR");

    }


}
