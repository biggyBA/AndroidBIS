package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Fault;

public class FaultsTableController {


    /*
     *This table is used to store all faults from mysql table "serviceaddnewfaults"
     */

    //Table name
    public static final String tableName = Constants.FAULTS_TABLE_NAME;


    /*
     *The first 40 columns are identical as on server
     * Columns 41-45 are used only local
     */

    //Table columns
    private static final String idColumn = Constants.FAULTS_COLUMN_ID;  //1
    private static final String datefaultColumn = Constants.FAULTS_COLUMN_DATE_FAULT;  //2
    private static final String timefaultColumn = Constants.FAULTS_COLUMN_TIME_FAULT;  //3
    private static final String identColumn = Constants.FAULTS_COLUMN_IDENT;  //4
    private static final String serialnumberColumn = Constants.FAULTS_COLUMN_SERIAL_NUMBER;  //5
    private static final String productTypeColumn = Constants.FAULTS_COLUMN_PRODUCT_TYPE;  //6
    private static final String buyerColumn = Constants.FAULTS_COLUMN_CLIENT;  //7
    private static final String addressColumn = Constants.FAULTS_COLUMN_ADDRESS;  //8
    private static final String placefaultColumn = Constants.FAULTS_COLUMN_PLACE;  //9
    private static final String phoneNumberColumn = Constants.FAULTS_COLUMN_PHONE_ONE;  //10
    private static final String phoneNumber2Column = Constants.FAULTS_COLUMN_PHONE_TWO;  //11
    private static final String descFaultsColumn = Constants.FAULTS_COLUMN_FAULT_DESCRIPTION;  //12
    private static final String notesInfoColumn = Constants.FAULTS_COLUMN_NOTE_INFO;  //13
    private static final String responsibleforfailureColumn = Constants.FAULTS_COLUMN_SERVICEMAN;  //14
    private static final String statusColumn = Constants.FAULTS_COLUMN_STATUS;  //15
    private static final String serviceSheetColumn = Constants.FAULTS_COLUMN_SERVICESHEET;  //16
    private static final String prioritiesColumn = Constants.FAULTS_COLUMN_PRIORITY;  //17
    private static final String datearchiveColumn = Constants.FAULTS_COLUMN_DATE_ARCHIVE;  //18
    private static final String orderIssuedColumn = Constants.FAULTS_COLUMN_ORDER_ISSUED;  //19
    private static final String authorizedserviceColumn = Constants.FAULTS_COLUMN_AUTHORIZED_SERVICE;  //20
    private static final String descinterventionColumn = Constants.FAULTS_COLUMN_DESCRIPTION_INTERVENTION;  //21
    private static final String idfaultColumn = Constants.FAULTS_COLUMN_FAULT_CODE_ONE;  //22
    private static final String idfault2Column = Constants.FAULTS_COLUMN_FAULT_CODE_TWO;  //23
    private static final String notessheetColumn = Constants.FAULTS_COLUMN_NOTESSHEET;  //24
    private static final String warrantyYesNoColumn = Constants.FAULTS_COLUMN_WARRANTY_YES_NO;  //25
    private static final String methodpaymentColumn = Constants.FAULTS_COLUMN_METHOD_PAYMENT;  //26
    private static final String partsBuyerPriceColumn = Constants.FAULTS_COLUMN_BUYER_PRICE_PARTS;  //27
    private static final String workBuyerPriceColumn = Constants.FAULTS_COLUMN_BUYER_PRICE_WORK;  //28
    private static final String tripBuyerPriceColumn = Constants.FAULTS_COLUMN_BUYER_PRICE_TRIP;  //29
    private static final String totalBuyerPriceColumn = Constants.FAULTS_COLUMN_BUYER_PRICE_TOTAL;  //30
    private static final String statusOfPaymentColumn = Constants.FAULTS_COLUMN_STATUS_OF_PAYMENT;  //31
    private static final String ptServiceCostColumn = Constants.FAULTS_COLUMN_SERVICE_COST;  //32
    private static final String hPUTServiceCostColumn = Constants.FAULTS_COLUMN_HOURS_TRAVEL;  //33
    private static final String hINTColumn = Constants.FAULTS_COLUMN_HOURS_WORK;  //34
    private static final String totalcostsumColumn = Constants.FAULTS_COLUMN_TOTAL_COST;  //35
    private static final String serviceStatusColumn = Constants.FAULTS_COLUMN_SERVICE_STATUS;  //36
    private static final String randomStringPartsColum = Constants.FAULTS_COLUMN_RANDOM_STRING;  //37
    private static final String buydateColumn = Constants.FAULTS_COLUMN_BUY_DATE;  //38
    private static final String endwarrantyColumn = Constants.FAULTS_COLUMN_END_WARRANTY;  //39
    private static final String typeOfServiceColumn = Constants.FAULTS_COLUMN_TYPE_OF_SERVICE;   //40
    private static final String colum41 = "column41";
    private static final String colum42 = "column42";
    private static final String colum43 = "column43";
    private static final String colum44 = "column44";
    private static final String colum45 = "column45";


    public static String getSQLiteCreateTableStatement(){
        String createTable = "CREATE TABLE " + tableName + "("
                + idColumn + " TEXT,"
                + datefaultColumn + " TEXT,"
                + timefaultColumn + " TEXT,"
                + identColumn + " TEXT,"
                + serialnumberColumn + " TEXT,"
                + productTypeColumn + " TEXT,"
                + buyerColumn + " TEXT,"
                + addressColumn + " TEXT,"
                + placefaultColumn + " TEXT,"
                + phoneNumberColumn + " TEXT,"
                + phoneNumber2Column + " TEXT,"
                + descFaultsColumn + " TEXT,"
                + notesInfoColumn + " TEXT,"
                + responsibleforfailureColumn + " TEXT,"
                + statusColumn + " TEXT,"
                + serviceSheetColumn + " TEXT,"
                + prioritiesColumn + " TEXT,"
                + datearchiveColumn + " TEXT,"
                + orderIssuedColumn + " TEXT,"
                + authorizedserviceColumn + " TEXT,"
                + descinterventionColumn + " TEXT,"
                + idfaultColumn + " TEXT,"
                + idfault2Column + " TEXT,"
                + notessheetColumn + " TEXT,"
                + warrantyYesNoColumn + " TEXT,"
                + methodpaymentColumn + " TEXT,"
                + partsBuyerPriceColumn + " TEXT,"
                + workBuyerPriceColumn + " TEXT,"
                + tripBuyerPriceColumn + " TEXT,"
                + totalBuyerPriceColumn + " TEXT,"
                + statusOfPaymentColumn + " TEXT,"
                + ptServiceCostColumn + " TEXT,"
                + hPUTServiceCostColumn + " TEXT,"
                + hINTColumn + " TEXT,"
                + totalcostsumColumn + " TEXT,"
                + serviceStatusColumn + " TEXT,"
                + randomStringPartsColum + " TEXT,"
                + buydateColumn + " TEXT,"
                + endwarrantyColumn + " TEXT,"
                + typeOfServiceColumn + " TEXT,"
                + colum41 + " TEXT,"
                + colum42 + " TEXT,"
                + colum43 + " TEXT,"
                + colum44 + " TEXT,"
                + colum45 + " TEXT" + ")";
        return createTable;
    }

    public static String getSQLiteUpgradeTableStatement(){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }


    //method to insert fault into table got from mysql
    public void insertFault(Fault fault){
        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(idColumn, fault.getId());
        values.put(datefaultColumn, fault.getDatefault());
        values.put(timefaultColumn, fault.getTimefault());
        values.put(identColumn, fault.getIdent());
        values.put(serialnumberColumn, fault.getSerialNumber());
        values.put(buyerColumn, fault.getBuyer());
        values.put(addressColumn, fault.getAddress());
        values.put(placefaultColumn, fault.getPlacefault());
        values.put(phoneNumberColumn, fault.getPhoneNumber());
        values.put(phoneNumber2Column, fault.getPhoneNumber2());
        values.put(descFaultsColumn, fault.getDescFaults());
        values.put(notesInfoColumn, fault.getNotesInfo());
        values.put(responsibleforfailureColumn, fault.getResponsibleforfailure());
        values.put(statusColumn, fault.getStatus());
        values.put(prioritiesColumn, fault.getPriorities());
        values.put(orderIssuedColumn, fault.getOrderIssued());
        values.put(typeOfServiceColumn, fault.getTypeOfService());

        data.insert(tableName, null, values);
        data.close();
    }



    public void deleteAll(){
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    public void deleteFault(String id){
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String buildSQL = "DELETE FROM " + tableName + " WHERE " + idColumn + " = '"+id+"'";
        db.execSQL(buildSQL);
        db.close();
    }


    public String getTotalFaultCount(){
        int count = 0;
        String status = Constants.STATUS_FAULT;
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + statusColumn + " = '"+status+"'";
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(buildSQL, null);
        count = cursor.getCount();
        String faultsCount = Integer.toString(count);
        return faultsCount;
    }

    public Cursor getAllFaults() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String status = Constants.STATUS_FAULT;
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + statusColumn + " = '"+status+"' ORDER BY " + idColumn + " DESC";
        Cursor cursor = db.rawQuery(buildSQL, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }
        return cursor;
    }

    public String getFaultCountByServiceman(){
        int count = 0;
        CurrentUserTableController currentUserTableController = new CurrentUserTableController();
        String currentuser = currentUserTableController.getUsername().toUpperCase();
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + responsibleforfailureColumn + " = '"+currentuser+"'";
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(buildSQL, null);
        count = cursor.getCount();
        String faultsCount = Integer.toString(count);
        return faultsCount;
    }

    public Cursor getFaultByServiceman() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        CurrentUserTableController currentUserTableController = new CurrentUserTableController();
        String currentUser = currentUserTableController.getUsername().toUpperCase();
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + responsibleforfailureColumn + " = '"+currentUser+"' ORDER BY " + idColumn + " DESC";
        Cursor cursor = db.rawQuery(buildSQL, null);
        return cursor;
    }

    public Cursor getFilterFaultByServiceman(String serviceman) {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + responsibleforfailureColumn + " = '"+serviceman+"' ORDER BY " + idColumn + " DESC";
        Cursor cursor = db.rawQuery(buildSQL, null);
        return cursor;
    }

    public String getFilterFaultCountByServiceman(String serviceman){
        int count = 0;
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + responsibleforfailureColumn + " = '"+serviceman+"'";
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(buildSQL, null);
        count = cursor.getCount();
        String faultsCount = Integer.toString(count);
        return faultsCount;
    }



    public void updateFault(String id, String serviceman, String phone1, String phone2, String faultDescripton){
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String buildSQL = "UPDATE " + tableName + " SET " + responsibleforfailureColumn + " = '"+serviceman+"' ,"
                                                            + phoneNumberColumn + " = '"+phone1+"' ,"
                                                            + phoneNumber2Column + " = '"+phone2+"' ,"
                                                            + descFaultsColumn + " = '"+faultDescripton+"' "
                                                            + " WHERE " + idColumn + " = '"+id+"'";
        db.execSQL(buildSQL);
        db.close();
    }



}
