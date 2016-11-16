package ba.biggy.androidbis.SQLite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Servicesheet;

public class ServicesheetTableController {


    /*
     *This table is used to store all servicesheets from current user"
     */

    //Table name
    public static final String tableName = "servicesheets";


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
    private static final String updateStatusColumn = "updateStatus";
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
                + updateStatusColumn + " TEXT,"
                + colum42 + " TEXT,"
                + colum43 + " TEXT,"
                + colum44 + " TEXT,"
                + colum45 + " TEXT" + ")";
        return createTable;
    }

    public static String getSQLiteUpgradeTableStatement(){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    public void insertServicesheet(Servicesheet servicesheet){

        SQLiteDatabase data = DataBaseAdapter.getDatabase();

        ContentValues values = new ContentValues();
        values.put(datefaultColumn, servicesheet.getDatefault());
        values.put(timefaultColumn, servicesheet.getTimefault());
        values.put(identColumn, servicesheet.getIdent());
        values.put(serialnumberColumn, servicesheet.getSerialnumber());
        values.put(productTypeColumn, servicesheet.getProductType());
        values.put(buyerColumn, servicesheet.getBuyer());
        values.put(addressColumn, servicesheet.getAddress());
        values.put(placefaultColumn, servicesheet.getPlacefault());
        values.put(phoneNumberColumn, servicesheet.getPhoneNumber());
        values.put(phoneNumber2Column, servicesheet.getPhoneNumber2());
        values.put(descFaultsColumn, servicesheet.getDescFaults());
        values.put(notesInfoColumn, servicesheet.getNotesInfo());
        values.put(responsibleforfailureColumn, servicesheet.getResponsibleforfailure());
        values.put(statusColumn, servicesheet.getStatus());
        values.put(datearchiveColumn, servicesheet.getDatearchive());
        values.put(authorizedserviceColumn, servicesheet.getAuthorizedservice());
        values.put(descinterventionColumn, servicesheet.getDescintervention());
        values.put(warrantyYesNoColumn, servicesheet.getWarrantyYesNo());
        values.put(methodpaymentColumn, servicesheet.getMethodpayment());
        values.put(partsBuyerPriceColumn, servicesheet.getPartsBuyerPrice());
        values.put(workBuyerPriceColumn, servicesheet.getWorkBuyerPrice());
        values.put(tripBuyerPriceColumn, servicesheet.getTripBuyerPrice());
        values.put(totalBuyerPriceColumn, servicesheet.getTotalBuyerPrice());
        values.put(statusOfPaymentColumn, servicesheet.getStatusOfPayment());
        values.put(hPUTServiceCostColumn, servicesheet.gethPUTServiceCost());
        values.put(hINTColumn, servicesheet.gethINT());
        values.put(totalcostsumColumn, servicesheet.getTotalcostsum());
        values.put(randomStringPartsColum, servicesheet.getRandomStringParts());
        values.put(updateStatusColumn, servicesheet.getUpdateStatus());
        values.put(buydateColumn, servicesheet.getBuydate());
        values.put(endwarrantyColumn, servicesheet.getEndwarranty());

        data.insert(tableName, null, values);
        data.close();

    }

    public Cursor getAllServicesheets() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String buildSQL = "SELECT rowid _id,* FROM " + tableName;
        Cursor cursor = db.rawQuery(buildSQL, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }
        return cursor;
    }

    public Cursor getSentServicesheets() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String status = Constants.UPDATE_STATUS_YES;
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + updateStatusColumn + " = '"+status+"'";
        Cursor cursor = db.rawQuery(buildSQL, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }
        return cursor;
    }

    public Cursor getNotSentServicesheets() {
        SQLiteDatabase db = DataBaseAdapter.getDatabase();
        String status = Constants.UPDATE_STATUS_NO;
        String buildSQL = "SELECT rowid _id,* FROM " + tableName + " WHERE " + updateStatusColumn + " = '"+status+"'";
        Cursor cursor = db.rawQuery(buildSQL, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }
        return cursor;
    }


}
