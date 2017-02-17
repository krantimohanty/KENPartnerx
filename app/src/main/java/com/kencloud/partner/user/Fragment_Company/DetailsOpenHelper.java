//package com.kencloud.partner.user.Fragment_Company;
//
///**
// * Created by suchismita.p on 11/29/2016.
// */
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//
//public class DetailsOpenHelper extends SQLiteOpenHelper{
//
//    private static String DATABASE_NAME="employeedatabase";
//
//    private static final int DATABASE_VERSION = 2;
//    private static final String DETAILS_TABLE_NAME = "employeedetails";
//
//    private static String nam;
//    private static String ag;
//
//    static String NAME=nam;
//    static String AGE=ag;
//    private String KEY_ROWID;
//
//    private static final String DETAILS_TABLE_CREATE =
//            "CREATE TABLE " + DETAILS_TABLE_NAME + " (" +
//                    NAME + " TEXT, " +
//                    AGE + " TEXT);";
//
//    private SQLiteOpenHelper mSQL;
//    private SQLiteDatabase db;
//
//
//
//    public DetailsOpenHelper(hjhj hjhj){
//        super(hjhj, DATABASE_NAME, null,DATABASE_VERSION );
//        // TODO Auto-generated constructor stub
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // TODO Auto-generated method stub
//        db.execSQL(DETAILS_TABLE_CREATE);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // TODO Auto-generated method stub
//        db.execSQL("DETAILS_TABLE_IF_NOT_EXISTS");
//        onCreate(db);
//    }
//
//
//    public void open() throws SQLException
//    {
//        DetailsOpenHelper mSQL = new DetailsOpenHelper(null);
//        db = mSQL.getWritableDatabase();
//    }
//
//
//    private void addDetails( String nam, String ag) {
//
//        SQLiteDatabase db = mSQL.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(NAME, nam);
//        //values.put(TIME, System.currentTimeMillis());
//        values.put(AGE, ag);
//        db.insert(DETAILS_TABLE_NAME, null, values);
//    }
//
//    public Cursor getAllDetails()
//    {
//        return db.query(DETAILS_TABLE_NAME, new String[] {
//                KEY_ROWID,
//                NAME,
//                AGE  },  null, null, null, null, null, null);
//    }
//
//
//    public boolean updateDetails(long rowId, String nam,
//                                 String ag)
//    {
//        ContentValues args = new ContentValues();
//        args.put(NAME, nam);
//        args.put(AGE, ag);
//
//        return db.update(DETAILS_TABLE_NAME, args,
//                KEY_ROWID + "=" + rowId, null) > 0;
//    }
//
//}