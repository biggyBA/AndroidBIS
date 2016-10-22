package ba.biggy.androidbis.SQLite;


import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseAdapter extends SQLiteOpenHelper {

    // database name
    protected static final String DATABASE_NAME = "AndroidBIS";
    // database version
    protected static final int DATABASE_VERSION = 1;

    public static DataBaseAdapter adapterInstance = null;
    private static SQLiteDatabase database = null;

    public DataBaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context){
        if (adapterInstance == null) {
            adapterInstance = new DataBaseAdapter(context.getApplicationContext());
            database = adapterInstance.getWritableDatabase();
        }
    }

    public static DataBaseAdapter getInstance(Context context) {
        return adapterInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();

        try {
            //Creating database tables
            db.execSQL(FaultsTableController.getSQLiteCreateTableStatement());
            db.execSQL(ServicesheetTableController.getSQLiteCreateTableStatement());
            db.execSQL(SparepartTableController.getSQLiteCreateTableStatement());
            db.execSQL(UsersTableController.getSQLiteCreateTableStatement());
            db.execSQL(SparepartListTableController.getSQLiteCreateTableStatement());

            //Commit
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();

        try {
            db.execSQL(FaultsTableController.getSQLiteUpgradeTableStatement());
            db.execSQL(ServicesheetTableController.getSQLiteUpgradeTableStatement());
            db.execSQL(SparepartTableController.getSQLiteUpgradeTableStatement());
            db.execSQL(UsersTableController.getSQLiteUpgradeTableStatement());
            db.execSQL(SparepartListTableController.getSQLiteUpgradeTableStatement());

            //Commit
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            db.endTransaction();
        }

        //
        this.onCreate(database);
    }

    public static SQLiteDatabase getDatabase(){
        if(!database.isOpen()){
            database = adapterInstance.getWritableDatabase();
        }
        return  database;
    }





    //Potrebno za androiddatabasemanager
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }

}
