package ba.biggy.androidbis;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import ba.biggy.androidbis.SQLite.AndroidDatabaseManager;
import ba.biggy.androidbis.SQLite.CurrentUserTableController;
import ba.biggy.androidbis.SQLite.DataBaseAdapter;
import ba.biggy.androidbis.SQLite.SparepartListTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;
import ba.biggy.androidbis.fragments.FragmentAddFault;
import ba.biggy.androidbis.fragments.FragmentCheckProduct;
import ba.biggy.androidbis.fragments.FragmentFaultsCardview;
import ba.biggy.androidbis.fragments.FragmentFaultsExpandableListview;
import ba.biggy.androidbis.fragments.FragmentFaultsListview;
import ba.biggy.androidbis.fragments.FragmentMap;
import ba.biggy.androidbis.fragments.FragmentMyServicesheets;
import ba.biggy.androidbis.fragments.FragmentSearchArchive;
import ba.biggy.androidbis.fragments.FragmentServicesheet;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences pref, sPref;
    private FloatingActionButton fab, fab1, fab2;
    private Boolean isFabOpen = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    CurrentUserTableController currentUserTableController = new CurrentUserTableController();
    UsersTableController usersTableController = new UsersTableController();
    SparepartListTableController sparepartListTableController = new SparepartListTableController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        permGPS();



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataBaseAdapter.init(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getApplicationContext().getSharedPreferences(Constants.PREF, 0);
        sPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (pref.getString(Constants.USERNAME,"").equalsIgnoreCase("0")){
            //set the logged in status to false
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(Constants.SP_IS_LOGGED_IN, false);
            editor.apply();
            //start login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }




        displayView(3);



            //if protection level is admin show the fab
            if (pref.getString(Constants.PROTECTION_LEVEL_ONE, "").equalsIgnoreCase(Constants.PROTECTION_LEVEL_ADMIN)){
            //if (usersTableController.getUserProtectionLevel1().equalsIgnoreCase(Constants.PROTECTION_LEVEL_ADMIN)){
                fab = (FloatingActionButton) findViewById(R.id.fab);
                fab1 = (FloatingActionButton)findViewById(R.id.fab1);
                fab2 = (FloatingActionButton)findViewById(R.id.fab2);
                fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
                fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
                rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
                rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);


                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        animateFAB();
                    }
                });


                fab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        animateFAB();
                        showAddFaultFragment();
                    }
                });


                fab2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        animateFAB();
                    }
                });


            }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {


            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

                InputMethodManager inputMethodManager = (InputMethodManager)  MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }else if (id == R.id.action_logout){
            //set the logged in status to false
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(Constants.SP_IS_LOGGED_IN, false);
            editor.apply();
            //start login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;

        }else if (id == R.id.action_db){
            Intent intent = new Intent(this, AndroidDatabaseManager.class);
            startActivity(intent);

        }else if (id == R.id.action_test){
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_item_faults) {
            displayView(1);
        } else if (id == R.id.nav_item_servicesheet) {
            displayView(2);
        } else if (id == R.id.nav_item_checkproduct) {
            displayView(3);
        } else if (id == R.id.nav_item_searcharchive) {
            displayView(4);
        } else if (id == R.id.nav_item_myServicesheets) {
            displayView(5);
        } else if (id == R.id.nav_item_map) {
            displayView(6);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {

            case 1:
                //get the set value from settings
                String view = sPref.getString(Constants.SP_FAULTSVIEW, "");
                int currentValue = 1;
                if (view!=""){
                currentValue = Integer.parseInt(view);}
                //depending on value create the propper fragment
                if (currentValue == 1){
                    //show simple listview
                    fragment = new FragmentFaultsListview();
                    title = getString(R.string.fragment_title_faults);
                }else if (currentValue == 2){
                    //show expandable listview
                    fragment = new FragmentFaultsExpandableListview();
                    title = getString(R.string.fragment_title_faults);
                }else{
                    //show cardview
                    fragment = new FragmentFaultsCardview();
                    title = getString(R.string.fragment_title_faults);
                }


                break;

            case 2:
                fragment = new FragmentServicesheet();
                title = getString(R.string.fragment_title_servicesheet);
                break;

            case 3:
                fragment = new FragmentCheckProduct();
                title = getString(R.string.fragment_title_checkproduct);
                break;

            case 4:
                fragment = new FragmentSearchArchive();
                title = getString(R.string.fragment_title_searcharchive);
                break;

            case 5:
                fragment = new FragmentMyServicesheets();
                title = getString(R.string.fragment_title_myServicesheets);
                break;

            case 6:
                fragment = new FragmentMap();
                title = getString(R.string.fragment_title_map);

            default:
                break;

            }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();

            //set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    private void showAddFaultFragment(){
        Fragment fragment = new FragmentAddFault();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        String title = getString(R.string.fragment_title_addfault);
        //set the toolbar title
        getSupportActionBar().setTitle(title);
    }

    private void permGPS(){
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, Constants.PERMISSIONS_REQUEST_READ_CALL_LOG);

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is less than 6.0 or the permission is already granted.

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == Constants.PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
