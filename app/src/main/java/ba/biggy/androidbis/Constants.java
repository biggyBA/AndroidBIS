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

    public static final String SP_PHONE = "phone";


    //Protection levels
    public static final String PROTECTION_LEVEL_ADMIN = "Admin";
    public static final String PROTECTION_LEVEL_VIEWER = "Viewer";
    public static final String PROTECTION_LEVEL_USER = "Serviceman";


    //MySQL
    public static final String STATUS_FAULT = "INTERVENCIJA";
    public static final String PRIORITY = "Normal";
    public static final String TYPE_OF_SERVICE = "Redovna intervencija";




}
