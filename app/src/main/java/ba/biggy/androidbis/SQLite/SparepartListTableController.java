package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Sparepart;

public class SparepartListTableController {

    /*
     *This table is used to store all available spareparts"
     */

    //Table name
    public static final String tableName = "sparepartsList";


    /*
     *
     *
     */

    //Table columns
    public static final String idColumn = "id"; //1
    private static final String descriptionColumn = "description";    //2


    public static String getSQLiteCreateTableStatement(){
        String createTable = "CREATE TABLE " + tableName + "("
                + idColumn + " TEXT,"
                + descriptionColumn + " TEXT" + ")";
        return createTable;
    }

    public static String getSQLiteUpgradeTableStatement(){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }


    public void insertSparepart(Sparepart sparepart){

        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(descriptionColumn, sparepart.getDescription());

        data.insert(tableName, null, values);
        data.close();

    }

    public void deleteAll(){
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    public int tableRowCount(){
        int count = 0;
        String selectQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public List<String> getAllSparepartNames(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1).toUpperCase());
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return labels;
    }



}
