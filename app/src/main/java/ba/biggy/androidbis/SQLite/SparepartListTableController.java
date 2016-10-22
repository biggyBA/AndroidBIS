package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

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



}
