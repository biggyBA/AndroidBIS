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
    public static final String tableName = "faults";


    /*
     *The first 40 columns are identical as on server
     * Columns 41-45 are used only local
     */

    //Table columns
    private static final String idColumn = "ID";  //1
    private static final String datefaultColumn = "datefault";  //2
    private static final String timefaultColumn = "timefault";  //3
    private static final String identColumn = "ident";  //4
    private static final String serialnumberColumn = "serialnumber";  //5
    private static final String productTypeColumn = "ProductType";  //6
    private static final String buyerColumn = "Buyer";  //7
    private static final String addressColumn = "address";  //8
    private static final String placefaultColumn = "Placefault";  //9
    private static final String phoneNumberColumn = "PhoneNumber";  //10
    private static final String phoneNumber2Column = "PhoneNumber2";  //11
    private static final String descFaultsColumn = "DescFaults";  //12
    private static final String notesInfoColumn = "NotesInfo";  //13
    private static final String responsibleforfailureColumn = "Responsibleforfailure";  //14
    private static final String statusColumn = "Status";  //15
    private static final String serviceSheetColumn = "ServiceSheet";  //16
    private static final String prioritiesColumn = "priorities";  //17
    private static final String datearchiveColumn = "datearchive";  //18
    private static final String orderIssuedColumn = "OrderIssued";  //19
    private static final String authorizedserviceColumn = "Authorizedservice";  //20
    private static final String descinterventionColumn = "Descintervention";  //21
    private static final String idfaultColumn = "idfault";  //22
    private static final String idfault2Column = "idfault2";  //23
    private static final String notessheetColumn = "notessheet";  //24
    private static final String warrantyYesNoColumn = "WarrantyYesNo";  //25
    private static final String methodpaymentColumn = "methodpayment";  //26
    private static final String partsBuyerPriceColumn = "PartsBuyerPrice";  //27
    private static final String workBuyerPriceColumn = "WorkBuyerPrice";  //28
    private static final String tripBuyerPriceColumn = "TripBuyerPrice";  //29
    private static final String totalBuyerPriceColumn = "TotalBuyerPrice";  //30
    private static final String statusOfPaymentColumn = "StatusOfPayment";  //31
    private static final String ptServiceCostColumn = "PTServiceCost";  //32
    private static final String hPUTServiceCostColumn = "hPUTServiceCost";  //33
    private static final String hINTColumn = "hINT";  //34
    private static final String totalcostsumColumn = "totalcostsum";  //35
    private static final String serviceStatusColumn = "ServiceStatus";  //36
    private static final String randomStringPartsColum = "randomStringParts";  //37
    private static final String buydateColumn = "BuyDate";  //38
    private static final String endwarrantyColumn = "EndWarrenty";  //39
    private static final String typeOfServiceColumn = "TypeOfService";   //40
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


    public void insertFault(Fault fault){
        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(idColumn, fault.getId());
        values.put(datefaultColumn, fault.getDatefault());
        values.put(timefaultColumn, fault.getTimefault());
        values.put(identColumn, fault.getIdent());
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
        database.close();
        String faultsCount = Integer.toString(count);
        return faultsCount;
    }

    public Cursor getAllFaults() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String status = Constants.STATUS_FAULT;
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + statusColumn + " = '"+status+"'";
        return db.rawQuery(buildSQL, null);
    }

    public String getFaultCountByServiceman(){
        int count = 0;
        CurrentUserTableController currentUserTableController = new CurrentUserTableController();
        String currentuser = currentUserTableController.getUsername().toUpperCase();
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + responsibleforfailureColumn + " = '"+currentuser+"'";
        SQLiteDatabase database = DataBaseAdapter.getDatabase();
        Cursor cursor = database.rawQuery(buildSQL, null);
        count = cursor.getCount();
        database.close();
        String faultsCount = Integer.toString(count);
        return faultsCount;
    }

    public Cursor getFaultByServiceman() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        CurrentUserTableController currentUserTableController = new CurrentUserTableController();
        String currentUser = currentUserTableController.getUsername().toUpperCase();
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + responsibleforfailureColumn + " = '"+currentUser+"'";
        return db.rawQuery(buildSQL, null);
    }




}
