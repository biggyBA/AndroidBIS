package ba.biggy.androidbis;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;


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

        pref = getApplicationContext().getSharedPreferences(Constants.PREF, 0);
        sPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Fixing Later Map loading Delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                }catch (Exception ignored){

                }
            }
        }).start();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataBaseAdapter.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (currentUserTableController.rowCount() == 0 || usersTableController.rowCount() == 0){
            //set the logged in status to false
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(Constants.SP_IS_LOGGED_IN, false);
            editor.apply();
            //start login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


        // check for android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // if it's M or above request needed permissions
            if (!checkPermission()) {

                requestPermission();

            } else {

                displayView(1);

            }
        }else{
            // otherwise show View
            displayView(1);
        }


            //if protection level is admin show the fab
            if (usersTableController.getUserProtectionLevel1().equalsIgnoreCase(Constants.PROTECTION_LEVEL_ADMIN)){
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

                // if there are refused permissions or marked with never ask again, the user will be informed every time the drawer state changes
                if (!checkPermission()) {
                    requestPermission();
                }





            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //getSupportFragmentManager().putFragment(outState, "mContent", mContent);
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
            fragmentTransaction.commitAllowingStateLoss();

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

    private void requestPermission(){
        // build an alertDialog with initial explanation to the user
        new AlertDialog.Builder(MainActivity.this)

                .setTitle(R.string.permission_initial_explanation_title)
                .setMessage(R.string.permission_initial_explanation_message)

                .setPositiveButton(R.string.permission_initial_explanation_positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // request permissions
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA,
                                                                                            Manifest.permission.READ_CONTACTS,
                                                                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                                                                            Manifest.permission.READ_PHONE_STATE,
                                                                                            Manifest.permission.SEND_SMS,
                                                                                            Manifest.permission.READ_EXTERNAL_STORAGE
                                                                                    }, Constants.PERMISSIONS_ALL_PERMISSIONS);
                    }
                })

                .setNegativeButton(R.string.permission_initial_explanation_negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        handleRefusedPermission();
                    }
                })

                .setIcon(R.drawable.ic_announcement_black)
                .setCancelable(false)
                .show();


    }

    private boolean checkPermission() {
        int permCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int permReadContacts = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);
        int permCoarseLocation = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int permReadPhoneState = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int permSendSMS = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        int permReadExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        return permCamera == PackageManager.PERMISSION_GRANTED
                && permReadContacts == PackageManager.PERMISSION_GRANTED
                && permCoarseLocation == PackageManager.PERMISSION_GRANTED
                && permReadPhoneState == PackageManager.PERMISSION_GRANTED
                && permSendSMS == PackageManager.PERMISSION_GRANTED
                && permReadExternalStorage == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_ALL_PERMISSIONS:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readContactsAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseLocationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean readPhoneStateAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean sendSMSAccepted = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalStorageAccepted  = grantResults[5] == PackageManager.PERMISSION_GRANTED;


                    if (cameraAccepted) {
                        // do nothing
                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.permission_camera_title)
                                        .setMessage(R.string.permission_camera_message)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                            Constants.PERMISSIONS_REQUEST_CAMERA);
                                                }
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                handleRefusedPermission();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_camera_black)
                                        .setCancelable(false)
                                        .show();
                                return;

                            }
                        }
                    }

                    if (readContactsAccepted) {
                        // do nothing
                    }else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.permission_read_contacts_title)
                                        .setMessage(R.string.permission_read_contacts_message)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                                            Constants.PERMISSIONS_REQUEST_READ_CONTACTS);
                                                }
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                handleRefusedPermission();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_contact_black)
                                        .setCancelable(false)
                                        .show();

                                return;
                            }
                        }
                    }


                    if (coarseLocationAccepted){
                        // do nothing
                    }else{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.permission_access_coarse_location_title)
                                        .setMessage(R.string.permission_access_coarse_location_message)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                                            Constants.PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                                                }
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                handleRefusedPermission();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_location_black)
                                        .setCancelable(false)
                                        .show();



                                return;
                            }
                        }
                    }

                    if (readPhoneStateAccepted){
                        // do nothing
                    }else{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)){
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.permission_read_phone_state_title)
                                        .setMessage(R.string.permission_read_phone_state_message)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                                                            Constants.PERMISSIONS_REQUEST_READ_PHONE_STATE);
                                                }
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                handleRefusedPermission();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_phone_black)
                                        .setCancelable(false)
                                        .show();



                                return;
                            }
                        }
                    }


                    if (sendSMSAccepted){
                        // do nothing
                    }else{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)){
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.permission_send_sms_title)
                                        .setMessage(R.string.permission_send_sms_message)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                                                            Constants.PERMISSIONS_REQUEST_SEND_SMS);
                                                }
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                handleRefusedPermission();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_sms_black)
                                        .setCancelable(false)
                                        .show();



                                return;
                            }
                        }
                    }

                    if (readExternalStorageAccepted){
                        // do nothing
                    }else{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.permission_read_external_storage_title)
                                        .setMessage(R.string.permission_read_external_storage_message)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                            Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                                                }
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                handleRefusedPermission();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_storage_black)
                                        .setCancelable(false)
                                        .show();



                                return;
                            }
                        }
                    }





                }


                break;
        }
    }

    private void handleRefusedPermission(){
        //set the logged in status to false
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.SP_IS_LOGGED_IN, false);
        editor.apply();
        //finish the MainActivity, so we can not go back to it with back button from loginActivity
        finish();
        //start login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
