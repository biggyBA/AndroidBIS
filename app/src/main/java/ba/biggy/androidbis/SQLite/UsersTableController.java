package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.User;

public class UsersTableController {

    //Table Users
    public static final String tableName = "users";

    //Table columns
    private static final String idColumn = "ID";
    private static final String nameColumn = "UserName";
    private static final String pwColumn = "Password";
    private static final String protectionlevel1Column = "ProtectionLevel1";
    private static final String protectionlevel2Column = "ProtectionLevel2";
    private static final String priceperworkColumn = "PricePerWork";
    private static final String pricepertravelColumn = "PricePerTravel";
    private static final String authorizedserviceColumn = "Authorizedservice";
    private static final String countryColumn = "Country";

    public static String getSQLiteCreateTableStatement(){
        String createTable = "CREATE TABLE " + tableName + "("
                + idColumn + " TEXT,"
                + nameColumn + " TEXT,"
                + pwColumn + " TEXT,"
                + protectionlevel1Column + " TEXT,"
                + protectionlevel2Column + " TEXT,"
                + priceperworkColumn + " TEXT,"
                + pricepertravelColumn + " TEXT,"
                + authorizedserviceColumn + " TEXT,"
                + countryColumn + " TEXT" + ")";
        return createTable;
    }

    public static String getSQLiteUpgradeTableStatement(){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    public void insertUser(User user){

        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(nameColumn, user.getUsername());
        values.put(protectionlevel1Column, user.getProtectionLevel1());
        values.put(protectionlevel2Column, user.getProtectionLevel2());
        values.put(priceperworkColumn, user.getPriceHourWork());
        values.put(pricepertravelColumn, user.getPriceHourTravel());
        values.put(authorizedserviceColumn, user.getAuthorizedService());
        values.put(countryColumn, user.getCountry());

        data.insert(tableName, null, values);
        data.close();

    }

    public void deleteAll(){
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        db.delete(tableName, null, null);
        db.close();
    }



    public boolean currentUserExists(){
        CurrentUserTableController currentUserTableController = new CurrentUserTableController();
        String currentUser = currentUserTableController.getUsername();
        String selectQuery = "SELECT * FROM " + tableName + " WHERE " + nameColumn + "  = '"+currentUser+"'";
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        database.close();
        if (count > 0){
            return true;
        }
        return false;
    }

    //get cursor with all details for current user
    public Cursor getCurrentUserDetails() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        CurrentUserTableController currentUserTableController = new CurrentUserTableController();
        String currentUser = currentUserTableController.getUsername();
        String buildSQL = "SELECT * FROM " + tableName + " WHERE " + nameColumn + " = '"+currentUser+"'";
        Cursor cursor = db.rawQuery(buildSQL, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            return cursor;
        }
        return cursor;
    }

    public String getUserProtectionLevel1(){
        String protectionLevel1 = this.getCurrentUserDetails().getString(3).trim();
        return protectionLevel1;
    }

    public String getUserProtectionLevel2(){
        String protectionLevel2 = this.getCurrentUserDetails().getString(4).trim();
        return protectionLevel2;
    }

    public double getPriceHourWork(){
        double priceHourWork = this.getCurrentUserDetails().getDouble(5);
        return priceHourWork;
    }

    public double getPriceHourTravel(){
        double priceHourTravel = this.getCurrentUserDetails().getDouble(5);
        return priceHourTravel;
    }

    public String getAuthorizedService(){
        String authorizedService = this.getCurrentUserDetails().getString(7).trim();
        return authorizedService;
    }

    public String getCountry(){
        String country = this.getCurrentUserDetails().getString(8).trim();
        return country;
    }


    public List<String> getAllServiceman(){
        List<String> labels = new ArrayList<String>();
        String plServiceman = Constants.PROTECTION_LEVEL_USER;
        String selectQuery = "SELECT * FROM " + tableName + " WHERE " + protectionlevel1Column + "  = '"+plServiceman+"'";
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
