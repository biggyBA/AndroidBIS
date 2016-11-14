package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import ba.biggy.androidbis.POJO.Sparepart;

public class SparepartTableController {

    /*
     *This table is used to store all used spareparts"
     */

    //Table name
    public static final String tableName = "spareparts";


    /*
     *The first 11 columns are identical as on server
     * Columns 12-16 are for local use
     */

    //Table columns
    private static final String idColumn = "ID";    //1
    private static final String iDfromAddNewFaultColumn = "IDfromAddNewFault";  //2
    private static final String datefaultColumn = "datefault";  //3
    private static final String partnumberColumn = "partnumber";    //4
    private static final String descriptionColumn = "description";  //5
    private static final String qtyColumn = "Qty";  //6
    private static final String nameBuyerColumn = "NameBuyer";  //7
    private static final String serialnumberColumn = "serialnumber";    //8
    private static final String priceperunitColumn = "priceperunit";    //9
    private static final String measurementUnitsColumn = "MeasurementUnits";    //10
    private static final String partnumber2Column = "partnumber2";  //11
    private static final String updateStatusColumn = "updateStatus";
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



}
