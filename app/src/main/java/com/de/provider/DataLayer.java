package com.de.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Shafi on 5/21/2015.
 */
public class DataLayer {
    private DBHelper _dbHelper;

    public DataLayer(Context c) {
        _dbHelper = new DBHelper(c, DBConstants.DB_VERSION);
    }

    public void deleteAllTableValues() {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            // To delete all table values before adding
            db.execSQL("delete from " + DBConstants.TABLE_ACCOUNT);
        } finally {
            if (db != null)
                db.close();
        }
    }

//    public void AddSysetmConfig(SystemConfigDTO sysDTO) {
//        SQLiteDatabase db = _dbHelper.getWritableDatabase();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(DBConstants.CATEGORIES_VER, sysDTO.getCatVersion());
//            values.put(DBConstants.FEEDBACK_EMAIL, sysDTO.getFeedbackEmail());
//            values.put(DBConstants.FIREBALL_IP, sysDTO.getFirballIp());
//            values.put(DBConstants.FIREBALL_PORT, sysDTO.getFirballPort());
//            values.put(DBConstants.IS_APPMANAGERAVAILABLE, sysDTO.isAppMangaerAvail() == true ? 1 : 0);
//            values.put(DBConstants.IS_FIREBALLAVAILABLE, sysDTO.isFirballAvail() == true ? 1 : 0);
//            values.put(DBConstants.TIME_PERQUESTION, sysDTO.getTimePerQuestion());
//            // To delete all values before adding
//            db.delete(DBConstants.TABLE_SYSTEMCONFIG, "1", null);
//            db.insert(DBConstants.TABLE_SYSTEMCONFIG, "", values);
//        } finally {
//            if (db != null)
//                db.close();
//        }
//    }
//
//    public SystemConfigDTO GetSystemConfig() {
//        SQLiteDatabase db = _dbHelper.getReadableDatabase();
//        try {
//            SystemConfigDTO results = null;
//            Cursor c = db.rawQuery("select * from " + DBConstants.TABLE_SYSTEMCONFIG, null);
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//                do {
//                    results = new SystemConfigDTO();
//                    results.setCatVersion(c.getInt(c.getColumnIndex(DBConstants.CATEGORIES_VER)));
//                    results.setFeedbackEmail(c.getString(c.getColumnIndex(DBConstants.FEEDBACK_EMAIL)));
//                    results.setFirballIp(c.getString(c.getColumnIndex(DBConstants.FIREBALL_IP)));
//                    results.setFirballPort(c.getInt(c.getColumnIndex(DBConstants.FIREBALL_PORT)));
//                    results.setAppMangaerAvail(c.getInt(c.getColumnIndex(DBConstants.IS_APPMANAGERAVAILABLE)) == 1 ? true : false);
//                    results.setFirballAvail(c.getInt(c.getColumnIndex(DBConstants.IS_APPMANAGERAVAILABLE)) == 1 ? true : false);
//                    results.setTimePerQuestion(c.getInt(c.getColumnIndex(DBConstants.TIME_PERQUESTION)));
//                } while (c.moveToNext());
//            }
//            return results;
//        } finally {
//            if (db != null)
//                db.close();
//        }
//    }

    // public int UpdateSystemConfig() {
    // SQLiteDatabase db = _dbHelper.getWritableDatabase();
    // try {
    // ContentValues values = new ContentValues();
    // values.put(DBConstants.COLUMN_ID, 10);
    // int affected = db.update(DBConstants.TABLE_SYSTEMCONFIG, values, null,
    // null);
    // return affected;
    // } finally {
    // if (db != null)
    // db.close();
    // }
    // }
    //
    // public int deleteSystemConfig() {
    // SQLiteDatabase db = _dbHelper.getWritableDatabase();
    // try {
    // int recordsDeleted = db.delete(DBConstants.TABLE_SYSTEMCONFIG, "1",
    // null);
    // return recordsDeleted;
    // } finally {
    // if (db != null)
    // db.close();
    // }
    // }
    //
    // public void AddSysetmConfig(int catVersion, String feedbackEmail, String
    // firballIp, int firballPort, boolean isAppMangaerAvail,
    // boolean isFirballAvail, int timePerQuestion) {
    // SQLiteDatabase db = _dbHelper.getWritableDatabase();
    // try {
    // ContentValues values = new ContentValues();
    // values.put(DBConstants.CATEGORIES_VER, catVersion);
    // values.put(DBConstants.FEEDBACK_EMAIL, feedbackEmail);
    // values.put(DBConstants.FIREBALL_IP, firballIp);
    // values.put(DBConstants.FIREBALL_PORT, firballPort);
    // values.put(DBConstants.IS_APPMANAGERAVAILABLE, isAppMangaerAvail == true
    // ? 1 : 0);
    // values.put(DBConstants.IS_FIREBALLAVAILABLE, isFirballAvail == true ? 1 :
    // 0);
    // values.put(DBConstants.TIME_PERQUESTION, timePerQuestion);
    //
    // db.insert(DBConstants.TABLE_SYSTEMCONFIG, "", values);
    // } finally {
    // if (db != null)
    // db.close();
    // }
    // }

}

