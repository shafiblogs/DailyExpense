package com.de.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.de.dto.CategoryDTO;
import com.de.dto.ReportDTO;

import java.util.ArrayList;

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

    public void saveExpense(ReportDTO expenseDTODTO) {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.CATEGORY, expenseDTODTO.getCategory());
            values.put(DBConstants.DATE, expenseDTODTO.getDate());
            values.put(DBConstants.AMOUNT, expenseDTODTO.getAmount());
            values.put(DBConstants.DESCRIPTION, expenseDTODTO.getDescription());

            // Inserting values to table
            db.insert(DBConstants.TABLE_EXPENSE, "", values);
        } finally {
            if (db != null)
                db.close();
        }
    }

    public ArrayList<ReportDTO> getExpense() {
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        try {
            ArrayList<ReportDTO> expenseDTOs = new ArrayList<ReportDTO>();
            Cursor c = db.rawQuery("select * from " + DBConstants.TABLE_EXPENSE, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    ReportDTO results = new ReportDTO();
                    results.setCategory(c.getString(c.getColumnIndex(DBConstants.CATEGORY)));
                    results.setDate(c.getString(c.getColumnIndex(DBConstants.DATE)));
                    results.setAmount(c.getInt(c.getColumnIndex(DBConstants.AMOUNT)));
                    results.setDescription(c.getString(c.getColumnIndex(DBConstants.DESCRIPTION)));
                    expenseDTOs.add(results);
                } while (c.moveToNext());
            }
            return expenseDTOs;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void saveIncome(ReportDTO expenseDTODTO) {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.CATEGORY, expenseDTODTO.getCategory());
            values.put(DBConstants.DATE, expenseDTODTO.getDate());
            values.put(DBConstants.AMOUNT, expenseDTODTO.getAmount());
            values.put(DBConstants.DESCRIPTION, expenseDTODTO.getDescription());

            // Inserting values to table
            db.insert(DBConstants.TABLE_INCOME, "", values);
        } finally {
            if (db != null)
                db.close();
        }
    }

    public ArrayList<ReportDTO> getIncome() {
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        try {
            ArrayList<ReportDTO> expenseDTOs = new ArrayList<ReportDTO>();
            Cursor c = db.rawQuery("select * from " + DBConstants.TABLE_INCOME, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    ReportDTO results = new ReportDTO();
                    results.setCategory(c.getString(c.getColumnIndex(DBConstants.CATEGORY)));
                    results.setDate(c.getString(c.getColumnIndex(DBConstants.DATE)));
                    results.setAmount(c.getInt(c.getColumnIndex(DBConstants.AMOUNT)));
                    results.setDescription(c.getString(c.getColumnIndex(DBConstants.DESCRIPTION)));
                    expenseDTOs.add(results);
                } while (c.moveToNext());
            }
            return expenseDTOs;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.CATEGORY_NAME, categoryDTO.getCategoryName());
            values.put(DBConstants.INCOME_ORDER, categoryDTO.getIncomeOrder());
            values.put(DBConstants.EXPENSE_ORDER, categoryDTO.getExpenseOrder());
            values.put(DBConstants.CATEGORY_TYPE, categoryDTO.getCategoryType());
            // Inserting values to table
            db.insert(DBConstants.TABLE_CATEGORY, "", values);
        } finally {
            if (db != null)
                db.close();
        }
    }

    public ArrayList<CategoryDTO> getCategory(String categoryType) {
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        try {
            ArrayList<CategoryDTO> expenseDTOs = new ArrayList<CategoryDTO>();
            Cursor c = db.rawQuery("select * from " + DBConstants.TABLE_CATEGORY + " where " + DBConstants.CATEGORY_TYPE + " IN ('B', " + "'" + categoryType + "')", null);

            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    CategoryDTO results = new CategoryDTO();
                    results.setCategoryName(c.getString(c.getColumnIndex(DBConstants.CATEGORY_NAME)));
                    results.setCategoryType(c.getString(c.getColumnIndex(DBConstants.CATEGORY_TYPE)));
                    results.setExpenseOrder(c.getInt(c.getColumnIndex(DBConstants.EXPENSE_ORDER)));
                    results.setIncomeOrder(c.getInt(c.getColumnIndex(DBConstants.INCOME_ORDER)));
                    expenseDTOs.add(results);
                } while (c.moveToNext());
            }
            return expenseDTOs;
        } finally {
            if (db != null)
                db.close();
        }
    }
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

