package com.example.newstudent.barcode_v12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by new student on 2/1/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static String DB_PATH = "data/data/com.example.newstudent.barcode_v12/databases/";
    public static String DB_NAME = "product_list.db";
    public static final int DB_VERSION = 4;

    //String constant to handle the column names of the database
    public static final String PRODUCT_INFO = "product_info";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PORK = "pork";
    public static final String ALCOHOL = "alchohol";
    public static final String BEEF = "beef";
    public static final String HALAL_BEEF = "halal_beef";
    public static final String SEAFOOD = "seafood";
    public static final String VEGETARIAN = "vegetarian";
    private SQLiteDatabase myDB;
    private Context context;

    // Class constructor
    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void close(){
        if(myDB!=null){
            myDB.close();
        }
        super.close();
    }

    /**
     * As its name indicates, this method is used to get a string that contains the properties
     * of a product from its barcode
     * @param barcode
     * @return
     */
    public String getProductByBarcode(String barcode){
        SQLiteDatabase db = this.getWritableDatabase(); //Get access to the database
        Cursor c;  //declare the cursor to do the SQL query
        String product = "";

        try {
            c = db.rawQuery("SELECT * FROM " + PRODUCT_INFO + " WHERE barcode = '" + barcode + "'", null);
            if(c == null) return null;

            product = getProductProperties(c);
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return product;
    }

    /**
     * This method has the same function as getProductByBarcode, but takes the name of the
     * product as parameter
     * @param productName
     * @return
     */
    public String getProductByName(String productName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String product = "";

        try {
            c = db.rawQuery("SELECT * FROM " + PRODUCT_INFO + " WHERE product_name = '" + productName + "'", null);
            if(c == null) return null;
            product = getProductProperties(c);
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return product;
    }

    /**
     * This method constructs a query based on the marked checkboxes and searches products that
     * satisfy the given conditions
     * @param q
     * @return
     */
    public String getProdcutByCategories(String q){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        String product = "";

        try {
            c = db.rawQuery("SELECT * FROM " + PRODUCT_INFO + " WHERE " + q, null);
            Log.e("query: ", q);
            Log.e("Number of results", Integer.toString(c.getCount()));//);

            if(c == null) return null;

            product = getProductProperties(c);
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return product;
    }

    /**
     * This method constructs the results message string from a SQL cursor (c)
     * @param c
     * @return
     */
    public String getProductProperties(Cursor c){

        String productProperties = new String();

        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append("Product name: "+c.getString(c.getColumnIndex(PRODUCT_NAME))+"\n");
            buffer.append("Contains alcohol?: "+c.getString(c.getColumnIndex(ALCOHOL))+"\n");
            buffer.append("Contains pork? "+c.getString(c.getColumnIndex(PORK))+"\n");
            buffer.append("Contains beef?: "+c.getString(c.getColumnIndex(BEEF))+"\n");
            buffer.append("Contains halal beef?: "+c.getString(c.getColumnIndex(HALAL_BEEF))+"\n");
            buffer.append("Contains seafood?: "+c.getString(c.getColumnIndex(SEAFOOD))+"\n");
            buffer.append("Is this product vegetarian?: "+c.getString(c.getColumnIndex(VEGETARIAN))+"\n\n");
        }

        productProperties = buffer.toString();

        return productProperties;
    }

    /***
     * Open database
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /***
     * Copy database from source code assets to device
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }

    }

    /***
     * Check if the database doesn't exist on device, create new one
     * @throws IOException
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {

        } else {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("tle99 - create", e.getMessage());
            }
        }
    }

    // ---------------------------------------------
    // PRIVATE METHODS
    // ---------------------------------------------

    /***
     * Check if the database is exist on device or not
     * @return
     */
    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        //return tempDB != null ? true : false;
        return tempDB != null;
    }






    // This code is written by Fikri for example purpose //
    //==================================================================//
    /*

    public static final String DATABASE_NAME = "Comment.db";
    public static final String TABLE_NAME = "comment_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "ADDRESS";
    public static final String COL_4 = "COMMENT";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String address, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,comment);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

     //==================================================================//
    */



}
