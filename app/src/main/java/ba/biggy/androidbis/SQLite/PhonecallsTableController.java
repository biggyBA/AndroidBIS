package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Phonecall;

public class PhonecallsTableController {

    //Table Phonecalls
    public static final String tableName = Constants.PHONECALLS_TABLE_NAME;

    //Table columns
    private static final String idColumn = Constants.PHONECALLS_COLUMN_ID;
    private static final String phoneNumberColumn = Constants.PHONECALLS_COLUMN_PHONE_NUMBER;
    private static final String callDurationColumn = Constants.PHONECALLS_COLUMN_CALL_DURATION;
    private static final String dateColumn = Constants.PHONECALLS_COLUMN_DATE;

    public static String getSQLiteCreateTableStatement(){
        String createTable = "CREATE TABLE " + tableName + "("
                + idColumn + " INTEGER PRIMARY KEY,"
                + phoneNumberColumn + " TEXT,"
                + callDurationColumn + " TEXT,"
                + dateColumn + " TEXT" + ")";
        return createTable;
    }

    public static String getSQLiteUpgradeTableStatement(){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }


    public void insertPhonecall(Phonecall phonecall){

        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(phoneNumberColumn, phonecall.getPhoneNumber());
        values.put(callDurationColumn, phonecall.getDuration());
        values.put(dateColumn, phonecall.getDate());

        data.insert(tableName, null, values);
        data.close();

    }



}
