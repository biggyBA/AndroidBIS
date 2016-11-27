package ba.biggy.androidbis;



public class Constants {

    //base url
    //public static final String BASE_URL = "http://biggy.ba/";
    public static final String BASE_URL = "http://109.205.33.172/";



    //login response
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static final String TAG = "biggy";


    //Shared preferences
    public static final String PREF = "MyPref";
    public static final String SP_IS_LOGGED_IN = "isLoggedIn";
    public static final String SP_USERNAME = "username";
    public static final String SP_FAULTSVIEW = "prefFaultsview"; //used as key for listview
    public static final String SP_LISTVIEW = "1";  //used as value for listview
    public static final String SP_LISTVIEW_POSITION = "lv_position";  //used as key to save listview position

    public static final String USERNAME = "username";
    public static final String PROTECTION_LEVEL_ONE = "protectionLevel1";
    public static final String PROTECTION_LEVEL_TWO = "protectionLevel2";
    public static final String PRICE_HOUR_WORK = "priceHourWork";
    public static final String PRICE_HOUR_TRAVEL = "priceHourTravel";
    public static final String AUTHORIZED_SERVICE = "authorizedService";
    public static final String COUNTRY = "country";

    public static final String SP_PHONE = "phone";


    //Protection levels
    public static final String PROTECTION_LEVEL_ADMIN = "Admin";
    public static final String PROTECTION_LEVEL_VIEWER = "Viewer";
    public static final String PROTECTION_LEVEL_USER = "Serviceman";


    //extras
    public static final String DATEFAULT = "datefault";
    public static final String TIMEFAULT = "timefault";
    public static final String PRODUCT_TYPE = "producttype";
    public static final String CLIENT = "client";
    public static final String ADDRESS = "address";
    public static final String PLACE = "place";
    public static final String PHONE_ONE = "phone1";
    public static final String PHONE_TWO = "phone2";
    public static final String DESCRIPTION_FAULT = "descfault";
    public static final String NOTE = "note";
    public static final String SERVICEMAN = "serviceman";




    //MySQL
    public static final String STATUS_FAULT = "INTERVENCIJA";
    public static final String PRIORITY = "Normal";
    public static final String TYPE_OF_SERVICE = "Redovna intervencija";
    public static final String SERVICESHEET_STATUS = "SERVISNI LIST ANDROID";
    public static final String UPDATE_STATUS_NO = "no";
    public static final String UPDATE_STATUS_YES = "yes";
    public static final String PAYED_STATUS_NO = "NE PLACENO";
    public static final String PAYED_STATUS_YES = "PLACENO";


    //date format strings
    public static final String DATE_SHOW_FORMAT = "dd.MM.yyyy";
    public static final String DATE_MYSQL_FORMAT = "yyyy-MM-dd";


    //permissions
    public static final int PERMISSIONS_REQUEST_READ_CALENDAR = 1;
    public static final int PERMISSIONS_REQUEST_CAMERA = 2;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    public static final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 4;
    public static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 5;
    public static final int PERMISSIONS_REQUEST_BODY_SENSORS = 6;
    public static final int PERMISSIONS_REQUEST_SEND_SMS = 7;
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 8;
    public static final int PERMISSIONS_ALL_PERMISSIONS = 9;



    /*
     *
     *  sql table and column names
     *
     */


    /*
     *  CurrentUserTableController
     */
    public static String CURRENT_USER_TABLE_NAME = "currentUser";
    public static String CURRENT_USER_COLUMN_NAME = "Username";

    /*
     *  FaultsTableController
     */
    public static String FAULTS_TABLE_NAME = "faults";
    public static String FAULTS_COLUMN_ID = "ID";
    public static String FAULTS_COLUMN_DATE_FAULT = "datefault";
    public static String FAULTS_COLUMN_TIME_FAULT = "timefault";
    public static String FAULTS_COLUMN_IDENT = "ident";
    public static String FAULTS_COLUMN_SERIAL_NUMBER = "serialnumber";
    public static String FAULTS_COLUMN_PRODUCT_TYPE = "ProductType";
    public static String FAULTS_COLUMN_CLIENT = "Buyer";
    public static String FAULTS_COLUMN_ADDRESS = "address";
    public static String FAULTS_COLUMN_PLACE = "Placefault";
    public static String FAULTS_COLUMN_PHONE_ONE = "PhoneNumber";
    public static String FAULTS_COLUMN_PHONE_TWO = "PhoneNumber2";
    public static String FAULTS_COLUMN_FAULT_DESCRIPTION = "DescFaults";
    public static String FAULTS_COLUMN_NOTE_INFO = "NotesInfo";
    public static String FAULTS_COLUMN_SERVICEMAN = "Responsibleforfailure";
    public static String FAULTS_COLUMN_STATUS = "Status";
    public static String FAULTS_COLUMN_SERVICESHEET = "ServiceSheet";
    public static String FAULTS_COLUMN_PRIORITY = "priorities";
    public static String FAULTS_COLUMN_DATE_ARCHIVE = "datearchive";
    public static String FAULTS_COLUMN_ORDER_ISSUED = "OrderIssued";
    public static String FAULTS_COLUMN_AUTHORIZED_SERVICE = "Authorizedservice";
    public static String FAULTS_COLUMN_DESCRIPTION_INTERVENTION = "Descintervention";
    public static String FAULTS_COLUMN_FAULT_CODE_ONE = "idfault";
    public static String FAULTS_COLUMN_FAULT_CODE_TWO = "idfault2";
    public static String FAULTS_COLUMN_NOTESSHEET = "notessheet";
    public static String FAULTS_COLUMN_WARRANTY_YES_NO = "WarrantyYesNo";
    public static String FAULTS_COLUMN_METHOD_PAYMENT = "methodpayment";
    public static String FAULTS_COLUMN_BUYER_PRICE_PARTS = "PartsBuyerPrice";
    public static String FAULTS_COLUMN_BUYER_PRICE_WORK = "WorkBuyerPrice";
    public static String FAULTS_COLUMN_BUYER_PRICE_TRIP = "TripBuyerPrice";
    public static String FAULTS_COLUMN_BUYER_PRICE_TOTAL = "TotalBuyerPrice";
    public static String FAULTS_COLUMN_STATUS_OF_PAYMENT = "StatusOfPayment";
    public static String FAULTS_COLUMN_SERVICE_COST = "PTServiceCost";
    public static String FAULTS_COLUMN_HOURS_TRAVEL = "hPUTServiceCost";
    public static String FAULTS_COLUMN_HOURS_WORK = "hINT";
    public static String FAULTS_COLUMN_TOTAL_COST = "totalcostsum";
    public static String FAULTS_COLUMN_SERVICE_STATUS = "ServiceStatus";
    public static String FAULTS_COLUMN_RANDOM_STRING = "randomStringParts";
    public static String FAULTS_COLUMN_BUY_DATE = "BuyDate";
    public static String FAULTS_COLUMN_END_WARRANTY = "EndWarrenty";
    public static String FAULTS_COLUMN_TYPE_OF_SERVICE = "TypeOfService";





    /*
     *  PhonecallsTableController
     */
    public static String PHONECALLS_TABLE_NAME = "phonecalls";
    public static String PHONECALLS_COLUMN_ID = "ID";
    public static String PHONECALLS_COLUMN_PHONE_NUMBER = "phoneNumber";
    public static String PHONECALLS_COLUMN_CALL_DURATION = "callDuration";
    public static String PHONECALLS_COLUMN_DATE = "date";


    /*
     *  ServicesheetTableController
     */


    /*
     *  SparepartListTableController
     */
    public static String SPAREPART_LIST_TABLE_NAME = "sparepartsList";
    public static String SPAREPART_LIST_COLUMN_ID = "id";
    public static String SPAREPART_LIST_DESCRIPTION = "description";


    /*
     *  SparepartTableController
     */
    public static String SPAREPART_TABLE_NAME = "spareparts";
    public static String SPAREPART_COLUMN_ID = "ID";
    public static String SPAREPART_COLUMN_ID_FROM = "IDfromAddNewFault";
    public static String SPAREPART_COLUMN_DATE = "datefault";
    public static String SPAREPART_COLUMN_PARTNUMBER = "partnumber";
    public static String SPAREPART_COLUMN_DESCRIPTION = "description";
    public static String SPAREPART_COLUMN_QTY = "Qty";
    public static String SPAREPART_COLUMN_NAME_OF_BUYER = "NameBuyer";
    public static String SPAREPART_COLUMN_SERIALNUMBER = "serialnumber";
    public static String SPAREPART_COLUMN_PRICE_PER_UNIT = "priceperunit";
    public static String SPAREPART_COLUMN_MEASUREMENT_UNIT = "MeasurementUnits";
    public static String SPAREPART_COLUMN_PARTNUMBER_TWO = "partnumber2";
    public static String SPAREPART_COLUMN_UPDATE_STATUS = "updateStatus";


    /*
     *  UsersTableController
     */
    public static String USERS_TABLE_NAME = "users";
    public static String USERS_COLUMN_ID = "ID";
    public static String USERS_COLUMN_USERNAME = "UserName";
    public static String USERS_COLUMN_PASSWORD = "Password";
    public static String USERS_COLUMN_PROTECTION_LEVEL_ONE = "ProtectionLevel1";
    public static String USERS_COLUMN_PROTECTION_LEVEL_TWO = "ProtectionLevel2";
    public static String USERS_COLUMN_PRICE_HOUR_WORK = "PricePerWork";
    public static String USERS_COLUMN_PRICE_HOUR_TRAVEL = "PricePerTravel";
    public static String USERS_COLUMN_AUTHORIZED_SERVICE = "Authorizedservice";
    public static String USERS_COLUMN_COUNTRY = "Country";






}
