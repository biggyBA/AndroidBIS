package ba.biggy.androidbis.SQLite;



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
}
