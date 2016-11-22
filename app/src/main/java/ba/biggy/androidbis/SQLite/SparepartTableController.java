package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Servicesheet;
import ba.biggy.androidbis.POJO.Sparepart;

import static android.app.SearchManager.QUERY;

public class SparepartTableController {

    /*
     *This table is used to store all used spareparts"
     */

    //Table name
    public static final String tableName = Constants.SPAREPART_TABLE_NAME;


    /*
     *The first 11 columns are identical as on server
     * Columns 12-16 are for local use
     */

    //Table columns
    private static final String idColumn = Constants.SPAREPART_COLUMN_ID;    //1
    private static final String iDfromAddNewFaultColumn = Constants.SPAREPART_COLUMN_ID_FROM;  //2
    private static final String datefaultColumn = Constants.SPAREPART_COLUMN_DATE;  //3
    private static final String partnumberColumn = Constants.SPAREPART_COLUMN_PARTNUMBER;    //4
    private static final String descriptionColumn = Constants.SPAREPART_COLUMN_DESCRIPTION;  //5
    private static final String qtyColumn = Constants.SPAREPART_COLUMN_QTY;  //6
    private static final String nameBuyerColumn = Constants.SPAREPART_COLUMN_NAME_OF_BUYER;  //7
    private static final String serialnumberColumn = Constants.SPAREPART_COLUMN_SERIALNUMBER;    //8
    private static final String priceperunitColumn = Constants.SPAREPART_COLUMN_PRICE_PER_UNIT;    //9
    private static final String measurementUnitsColumn = Constants.SPAREPART_COLUMN_MEASUREMENT_UNIT;    //10
    private static final String partnumber2Column = Constants.SPAREPART_COLUMN_PARTNUMBER_TWO;  //11
    private static final String updateStatusColumn = Constants.SPAREPART_COLUMN_UPDATE_STATUS;
    private static final String colum13 = "column13";
    private static final String colum14 = "column14";
    private static final String colum15 = "column15";
    private static final String colum16 = "column16";



    public static String getSQLiteCreateTableStatement(){
        String createTable = "CREATE TABLE " + tableName + "("
                + idColumn + " TEXT,"
                + iDfromAddNewFaultColumn + " TEXT,"
                + datefaultColumn + " TEXT,"
                + partnumberColumn + " TEXT,"
                + descriptionColumn + " TEXT,"
                + qtyColumn + " TEXT,"
                + nameBuyerColumn + " TEXT,"
                + serialnumberColumn + " TEXT,"
                + priceperunitColumn + " TEXT,"
                + measurementUnitsColumn + " TEXT,"
                + partnumber2Column + " TEXT,"
                + updateStatusColumn + " TEXT,"
                + colum13 + " TEXT,"
                + colum14 + " TEXT,"
                + colum15 + " TEXT,"
                + colum16 + " TEXT" + ")";
        return createTable;
    }

    public static String getSQLiteUpgradeTableStatement(){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }



    public void insertSparepart(Sparepart sparepart){

        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(idColumn, sparepart.getId());
        values.put(iDfromAddNewFaultColumn, sparepart.getiDfromAddNewFault());
        values.put(datefaultColumn, sparepart.getDatefault());
        values.put(partnumberColumn, sparepart.getPartnumber());
        values.put(descriptionColumn, sparepart.getDescription());
        values.put(qtyColumn, sparepart.getQty());
        values.put(nameBuyerColumn, sparepart.getNameBuyer());
        values.put(serialnumberColumn, sparepart.getSerialnumber());
        values.put(priceperunitColumn, sparepart.getPriceperunit());
        values.put(measurementUnitsColumn, sparepart.getMeasurementUnits());
        values.put(partnumber2Column, sparepart.getPartnumber2());
        values.put(updateStatusColumn, sparepart.getUpdateStatus());

        data.insert(tableName, null, values);
        data.close();

    }



    public String partsJSONfromSQLite(String randomString){
        ArrayList<HashMap<String, String>> sparepartList;
        sparepartList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + tableName + " WHERE " + iDfromAddNewFaultColumn + "  = '"+randomString+"'";
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("col1", cursor.getString(1));
                map.put("col2", cursor.getString(2));
                map.put("col3", cursor.getString(3));
                map.put("col4", cursor.getString(4));
                map.put("col5", cursor.getString(5));
                map.put("col6", cursor.getString(6));
                map.put("col7", cursor.getString(7));
                map.put("col8", cursor.getString(8));
                map.put("col9", cursor.getString(9));
                map.put("col10", cursor.getString(10));
                sparepartList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(sparepartList);
    }


    public void updateStatus(String randomString){
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        String status = Constants.UPDATE_STATUS_YES;
        String updateQuery = "Update " + tableName +" set " + updateStatusColumn + " = '"+ status +"' where " + iDfromAddNewFaultColumn + " ="+"'"+ randomString +"'";
        database.execSQL(updateQuery);
        database.close();
    }


}
