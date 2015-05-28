package com.de.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shafi on 5/21/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, int version) {
        super(context, DBConstants.DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.CREATE_TABLE_EXPENSE);
        db.execSQL(DBConstants.CREATE_TABLE_INCOME);
        db.execSQL(DBConstants.CREATE_TABLE_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL(DBConstants.DROP_TABLE_EXPENSE);
            db.execSQL(DBConstants.DROP_TABLE_INCOME);
            db.execSQL(DBConstants.DROP_TABLE_ACCOUNT);

            db.execSQL(DBConstants.CREATE_TABLE_EXPENSE);
            db.execSQL(DBConstants.CREATE_TABLE_INCOME);
            db.execSQL(DBConstants.CREATE_TABLE_ACCOUNT);

        }
    }
}
