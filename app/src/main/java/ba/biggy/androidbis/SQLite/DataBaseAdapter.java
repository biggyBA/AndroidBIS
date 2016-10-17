package ba.biggy.androidbis.SQLite;


import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

}
