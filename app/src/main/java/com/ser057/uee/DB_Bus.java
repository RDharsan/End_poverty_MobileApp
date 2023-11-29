package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Database bus table
public class DB_Bus extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Bus_manager.db";
    public static final String TABLE_NAME = "Blog";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Topic";
    public static final String COL_3 = "Blogger_Name";
    public static final String COL_4 = "Date";
    public static final String COL_5 = "Content";

    public DB_Bus(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


    }

    //Create table - Bus
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER primary key ,Topic text,Blogger_Name interger,Date interger,Content text)");

    }

    //Drop Table Bus
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    //Insert Bus data
    public boolean insertData(String ID, String Topic, String Blogger_Name, String Date, String Content) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, Topic);
        contentValues.put(COL_3, Blogger_Name);
        contentValues.put(COL_4, Date);
        contentValues.put(COL_5, Content);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Get all bus data
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    //Update Bus data
    public  boolean updateData(String ID,String Topic, String Blogger_Name, String Date, String Content){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(COL_2, Topic);
        contentValues.put(COL_3, Blogger_Name);
        contentValues.put(COL_4, Date);
        contentValues.put(COL_5, Content);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"ID = ?",new String[] {ID});
        return true;
    }

    //delete Bus data
    public Integer deleteData (String ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"ID = ?",new String[]{ID});
    }

    //Search Bus data
    public Cursor searchData(String ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + ID + "'",null);
        return data;
    };

}
