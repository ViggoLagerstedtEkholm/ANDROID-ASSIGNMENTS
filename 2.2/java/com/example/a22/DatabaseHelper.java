package com.example.a22;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A class containing all logic for the database.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // All columns/table names in the database.
    public static final String PRIMES_TABLE = "PRIMES_TABLE";
    public static final String COLUMN_PRIME = "PRIME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_ID = "ID";

    public static final String ALL_VALUES_TABLE = "ALL_VALUES_TABLE";
    public static final String COLUMN_VALUE_ID = "ID";
    public static final String COLUMN_VALUE = "VALUE";

    /**
     * Constructor that takes the context, this is then used to call the superclass constructor in SQLiteOpenHelper.
     * We pass the context, database name, factory, version.
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, "primes.db", null, 1);
    }

    /**
     * Called once we create this class.
     * We create the query with the help of string concatenation.
     * We execute this query with the help of the db instance.
     * @param db the SQLiteDatabase database instance.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + PRIMES_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRIME + " INTEGER, " + COLUMN_DATE + " TEXT)";
        String createValuesTable = "CREATE TABLE " + ALL_VALUES_TABLE + " (" + COLUMN_VALUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_VALUE + " INTEGER)";
        Log.d("QUERY1: ", createTableQuery);
        Log.d("QUERY2: ", createValuesTable);
        db.execSQL(createTableQuery);
        db.execSQL(createValuesTable);
    }

    /**
     * This method is called if we detect a new version of the database. Then we do the required actions.
     * @Override We override to add specific behavior.
     * @param db the SQLiteDatabase database instance.
     * @param oldVersion the old db version.
     * @param newVersion the new db version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Update if new db found.
    }

    /**
     * This method starts writing to the database.
     * We put our model values into the database.
     * We execute the query and specify what table to insert into.
     * @param primeModel the prime model instance.
     * @return boolean did we succeed inserting into the table?
     */
    public boolean insertPrime(PrimeModel primeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRIME, primeModel.getPrime());
        cv.put(COLUMN_DATE, primeModel.getDate());

        long insert = db.insert(PRIMES_TABLE, null, cv);
        return insert != -1;
    }

    /**
     * This mathod starts writing to the database.
     * We put our long value into the database.
     * We execute the query and specify what table to insert into.
     * This method will store all values generated!
     * @param value the value we want to insert.
     */
    public void insertValue(long value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_VALUE, value);

        db.insert(ALL_VALUES_TABLE, null, cv);
    }

    /**
     * This method gives the most recent value generated.
     * @return long
     */
    public long getMostRecentValue(){
        try{
            String queryString = "SELECT * FROM " + ALL_VALUES_TABLE + " ORDER BY " + COLUMN_VALUE +" DESC Limit 1";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            if(cursor != null){
                cursor.moveToFirst();
            }
            assert cursor != null;
            long value = cursor.getLong(1);
            Log.d("value: ", String.valueOf(value));
            cursor.close();
            db.close();
            return value;
        }catch(Exception e){
            Log.d("NO PREVIOUS VALUES", "NO VALUES FOUND");
        }
        return 0;
    }

    /**
     * Returns all the prime models from the database.
     * @return List<PrimeModel>
     */
    public List<PrimeModel> getAllPrimes(){
        List<PrimeModel> returnList = new ArrayList<>();

        try{
            String queryString = "SELECT * FROM " + PRIMES_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            if(cursor.moveToFirst()){
                do{
                    long prime = cursor.getLong(1);
                    String date = cursor.getString(2);
                    PrimeModel primeModel = new PrimeModel(prime,date);
                    returnList.add(primeModel);
                }while(cursor.moveToNext());
            }

            Log.d("ITEM_COUNT", String.valueOf(returnList.size()));
            cursor.close();
            db.close();
        }catch(Exception e){
            Log.d("NO RECORDS FOUND", "NO RECORDS FOUND IN HISTORY!");
        }

        return returnList;
    }
}
