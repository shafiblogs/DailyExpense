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
    final static String TABLE_EXPENSE = "TableExpense";
    final static String TABLE_INCOME = "TableIncome";
    final static String TABLE_ACCOUNT = "TableAccount";
    final static String TABLE_CATEGORY = "TableCategory";

    // Column Names
    final static String COLUMN_ID = "_id";
    final static String CATEGORY = "_category";
    final static String DATE = "_date";
    final static String AMOUNT = "_amount";
    final static String DESCRIPTION = "_description";
    final static String CATEGORY_NAME = "category_name";
    final static String CATEGORY_TYPE = "category_type";
    final static String EXPENSE_ORDER = "expense_order";
    final static String INCOME_ORDER = "income_order";


    final static String TRANS_ID = "TransID";
    final static String LANGUAGE_ID = "LanguageID";
    final static String TITLE = "Title";

    // Table Expense Queries
    final static String CREATE_TABLE_EXPENSE = CREATE_TABLE + TABLE_EXPENSE + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + CATEGORY + " text, " + DATE + " text, " + AMOUNT + " integer, " + DESCRIPTION + " text); ";
    final static String DROP_TABLE_EXPENSE = DROP_TABLE.concat(TABLE_EXPENSE).concat(";");
    // Table Expense Queries
    final static String CREATE_TABLE_INCOME = CREATE_TABLE + TABLE_INCOME + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + CATEGORY + " text, " + DATE + " text, " + AMOUNT + " integer, " + DESCRIPTION + " text); ";
    final static String DROP_TABLE_INCOME = DROP_TABLE.concat(TABLE_INCOME).concat(";");
    // Table Account Queries
    final static String CREATE_TABLE_ACCOUNT = CREATE_TABLE + TABLE_ACCOUNT + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + TRANS_ID + " integer); ";
    final static String DROP_TABLE_ACCOUNT = DROP_TABLE.concat(TABLE_ACCOUNT).concat(";");
    //Table Category Queries
    final static String CREATE_TABLE_CATEGORY = CREATE_TABLE + TABLE_CATEGORY + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + CATEGORY_NAME + " text, " + CATEGORY_TYPE + " text, " + EXPENSE_ORDER + " integer, " + INCOME_ORDER + " integer); ";
    final static String DROP_TABLE_CATEGORY = DROP_TABLE.concat(TABLE_CATEGORY).concat(";");
}
