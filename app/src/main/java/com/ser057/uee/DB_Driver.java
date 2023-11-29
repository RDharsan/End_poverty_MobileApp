package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//Driver Database  table functions
public class DB_Driver extends SQLiteOpenHelper {

//  Properties for employee table
    public static final String DATABASE_NAME = "Emp_man.db";
    public static final String TABLE_NAME = "employee_details";
    public static final String COL_1 = "ReporterNIC";
    public static final String COL_2 = "ReporterName";
    public static final String COL_3 = "PovertyLocation";
    public static final String COL_4 = "TelephoneNo";
    public static final String COL_5 = "ReporterEmailAddress";
    public static final String COL_6 = "individualorOrganization";
    public static final String COL_7 = "Description";

    public DB_Driver(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteOpenHelper=this.getWritableDatabase();
    }

    //Create driver table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ReporterNIC text primary key,ReporterName text, PovertyLocation text  ,TelephoneNo integer, ReporterEmailAddress text  ,individualorOrganization text, Description text)");


    }

//  Drop table
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }//insert Driver Data
    public boolean insertData(String ReporterNIC, String ReporterName, String PovertyLocation, String TelephoneNo,String ReporterEmailAddress, String individualorOrganization, String Description) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,ReporterNIC);
        contentValues.put(COL_2, ReporterName);
        contentValues.put(COL_3, PovertyLocation);
        contentValues.put(COL_4, TelephoneNo);
        contentValues.put(COL_5, ReporterEmailAddress);
        contentValues.put(COL_6, individualorOrganization);
        contentValues.put(COL_7, Description);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Get all driver data
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return result;
    }

    //update driver Data
    public  boolean updateDetail(String ReporterNIC, String ReporterName, String PovertyLocation, String TelephoneNo,String ReporterEmailAddress, String individualorOrganization, String Description){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2, ReporterName);
        contentValues.put(COL_3, PovertyLocation);
        contentValues.put(COL_4, TelephoneNo);
        contentValues.put(COL_5, ReporterEmailAddress);
        contentValues.put(COL_6, individualorOrganization);
        contentValues.put(COL_7, Description);

        sqLiteDatabase.update(TABLE_NAME,contentValues,"ReporterNIC = ?",new String[] {ReporterNIC});
        return true;
    }

    //Delete driver Data
    public Integer deleteDetail (String ReporterNIC){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"ReporterNIC = ?",new String[] {ReporterNIC});
    }

    //Search driver Data
    public Cursor searchData(String ReporterNIC) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + ReporterNIC + "'",null);
        return data;
    };


}
