package ba.biggy.androidbis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ba.biggy.androidbis.SQLite.AndroidDatabaseManager;
import ba.biggy.androidbis.SQLite.CurrentUserTableController;
import ba.biggy.androidbis.SQLite.DataBaseAdapter;
import ba.biggy.androidbis.SQLite.SparepartListTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;
import ba.biggy.androidbis.fragments.FragmentCheckProduct;
import ba.biggy.androidbis.fragments.FragmentFaultsListview;
import ba.biggy.androidbis.fragments.FragmentSearchArchive;
import ba.biggy.androidbis.fragments.FragmentServicesheet;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences pref;
    private FloatingActionButton fab;
    CurrentUserTableController currentUserTableController = new CurrentUserTableController();
    UsersTableController usersTableController = new UsersTableController();
    SparepartListTableController sparepartListTableController = new SparepartListTableController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataBaseAdapter.init(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getApplicationContext().getSharedPreferences(Constants.PREF, 0);

        displayView(1);


        //if protection level is admin show the fab
        if (usersTableController.getUserProtectionLevel1().equalsIgnoreCase(Constants.PROTECTION_LEVEL_ADMIN)){
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            //clear tables
            currentUserTableController.deleteAll();
            usersTableController.deleteAll();
            sparepartListTableController.deleteAll();
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
                fragment = new FragmentFaultsListview();
                title = getString(R.string.fragment_title_faults);
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
}
