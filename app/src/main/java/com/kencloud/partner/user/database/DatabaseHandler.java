package com.kencloud.partner.user.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kencloud.partner.user.app_model.Address_Info_Comp;

import java.util.ArrayList;

/**
 * Created by suchismita.p on 11/29/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private final ArrayList<Address_Info_Comp> addressList = new ArrayList<Address_Info_Comp>();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 14 ;

    // Database Name
    private static final String DATABASE_NAME = "KenAddress";

    // Contacts table name
    private static final String ADDRESS_TABLE = "NewAddressInfo";

    // Contacts Table Columns names
    private static final String ID = "applicationid";
    private static final String TYPE = "addresstype";
    private static final String ADDRESS1 = "addressstreet1";
    private static final String ADDRESS2 = "addressstreet2";
    private static final String CITY = "addresscity";
    private static final String STATE = "addressstate";
    private static final String COUNTRY = "addresscountry";
    private static final String PIN = "addresszip";
    private static final String COMPANYCONTACT ="companycontact";
    private static final String DESIGNATION = "contactdesignation";
    private static final String COMPANYMOBILE = "companymobile";
    private static final String COMPANYEMAIL = "companyemail";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
         String CREATE_ADDRESS_TABLE = "CREATE TABLE " + ADDRESS_TABLE + "( "
                + ID + " INTEGER PRIMARY KEY, " + TYPE + " TEXT, "
                + ADDRESS1 + " TEXT, " + ADDRESS2 + " TEXT,"+ CITY + " TEXT, "  + STATE + " TEXT, " + COUNTRY +" TEXT, " +
                 PIN + " TEXT, " + COMPANYCONTACT + " TEXT, " + DESIGNATION + " TEXT, " + COMPANYMOBILE + " TEXT, " + COMPANYEMAIL + " TEXT "  + " ) ";
        db.execSQL(CREATE_ADDRESS_TABLE);
        Log.i("createDB=", CREATE_ADDRESS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ADDRESS_TABLE);

        // Create tables again
        onCreate(db);
    }
    public long addAddress(Address_Info_Comp KPApplicationAddressInformation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, KPApplicationAddressInformation.getId());
        values.put(TYPE, KPApplicationAddressInformation.getApl_AddressType());
        values.put(ADDRESS1, KPApplicationAddressInformation.getApl_Address_Street1());
        values.put(ADDRESS2, KPApplicationAddressInformation.getApl_Address_Street2());
        values.put(CITY, KPApplicationAddressInformation.getApl_Address_City());
        values.put(STATE, KPApplicationAddressInformation.getApl_Address_State());
        values.put(COUNTRY, KPApplicationAddressInformation.getApl_Address_Country());
        values.put(PIN, KPApplicationAddressInformation.getApl_Address_ZIP());

        // Inserting Row
        long newRowId = db.insert(ADDRESS_TABLE, null, values);

        db.close(); // Closing database connection
return newRowId;

    }

 public Address_Info_Comp Get_Address(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ADDRESS_TABLE, new String[] { ID,
                        TYPE, ADDRESS1, ADDRESS2, CITY, STATE, COUNTRY, PIN }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Address_Info_Comp form = new Address_Info_Comp(
                cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),
                cursor.getString(5), cursor.getString(6),cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),cursor.getString(12));
        // return contact
        cursor.close();
        db.close();

        return form;
    }
    public ArrayList<Address_Info_Comp> getAddress() {
//        SQLiteDatabase db = this.getReadableDatabase();
        try {
        addressList.clear();

        String selectQuery = "SELECT  * FROM " + ADDRESS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Address_Info_Comp contact = new Address_Info_Comp();

                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setApl_AddressType(cursor.getString(1));
                contact.setApl_Address_Street1(cursor.getString(2));
                contact.setApl_Address_Street2(cursor.getString(3));
                contact.setApl_Address_City(cursor.getString(4));
                contact.setApl_Address_State(cursor.getString(5));
                contact.setApl_Address_Country(cursor.getString(6));
                contact.setApl_Address_ZIP(cursor.getString(7));

                addressList.add(contact);
            } while (cursor.moveToNext());
        }
            cursor.close();
            db.close();
        return addressList;

    }catch (Exception e) {
            // TODO: handle exception
            Log.e("address", "" + e);
        }

        return addressList;
    }
    public void deleteAddress(long id) {
//        Address_Info_Comp form=new Address_Info_Comp();
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(ADDRESS_TABLE, ID + " = ?",
//                new String[] { String.valueOf(id) });
//        db.close();

//        id = form.getId();

        db.delete(ADDRESS_TABLE, ID + " = ?",
                new String[]{Long.toString(id)} );
        db.close();
    }
}
