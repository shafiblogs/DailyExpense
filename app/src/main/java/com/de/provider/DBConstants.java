package com.de.provider;

/**
 * Created by Shafi on 5/21/2015.
 */
public class DBConstants {
    final static String DB_NAME = "MobileAccount.db";
    final static int DB_VERSION = 1;
    final static String CREATE_TABLE = "create table ";
    final static String DROP_TABLE = "drop table ";

    // Table Names
    final static String TABLE_SYSTEMCONFIG = "SystemConfig";
    final static String TABLE_ACCOUNT = "TableAccount";

    // Column Names
    final static String COLUMN_ID = "_id";
    final static String CATEGORIES_VER = "CategoriesVer";
    final static String FEEDBACK_EMAIL = "FeedbackEmail";
    final static String TRANS_ID = "TransID";
    final static String LANGUAGE_ID = "LanguageID";
    final static String TITLE = "Title";

    // Table Queries
    final static String CREATE_TABLE_SYSTEMCONFIG = CREATE_TABLE + TABLE_SYSTEMCONFIG + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + CATEGORIES_VER + " integer, " + FEEDBACK_EMAIL + " text); ";
    final static String DROP_TABLE_SYSTEMCONFIG = DROP_TABLE.concat(TABLE_SYSTEMCONFIG).concat(";");
    final static String CREATE_TABLE_ACCOUNT = CREATE_TABLE + TABLE_ACCOUNT + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + TRANS_ID + " integer); ";
    final static String DROP_TABLE_ACCOUNT = DROP_TABLE.concat(TABLE_ACCOUNT).concat(";");
}

