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
    public static int PERMISSIONS_REQUEST_READ_CALL_LOG = 1;
    public static int PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    public static int PERMISSIONS_REQUEST_VIBRATE = 3;
    public static int PERMISSIONS_REQUEST_INTERNET = 4;
    public static int PERMISSIONS_REQUEST_READ_PHONE_STATE = 5;
    public static int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 6;
    public static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 7;
    public static int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 8;
    public static int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 9;





}
