package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.User;

public class CurrentUserTableController {

    //Table current user
    public static final String tableName = Constants.CURRENT_USER_TABLE_NAME;

    //Table columns
    private static final String nameColumn = Constants.CURRENT_USER_COLUMN_NAME;

    public static String getSQLiteCreateTableStatement(){
        String createTable = "CREATE TABLE " + tableName + "("
                + nameColumn + " TEXT" + ")";
        return createTable;
    }

    public static String getSQLiteUpgradeTableStatement(){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    public void insertCurrentUser(String username){

        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(nameColumn, username);

        data.insert(tableName, null, values);
        data.close();

    }

    public void deleteAll(){
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    public int rowCount(){
        int count = 0;
        String selectQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        return count;
    }

    public Cursor getCurrentUserDetails() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String buildSQL = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(buildSQL, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }
        return cursor;
    }


    public String getUsername(){
        //String user = this.getCurrentUserDetails().getString(0).trim();
        String user = "";
        if (this.getCurrentUserDetails().isBeforeFirst()){
            return user="0";
        }else{
        return user = this.getCurrentUserDetails().getString(0).trim();
        }
    }


}
