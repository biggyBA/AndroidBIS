package ba.biggy.androidbis.fragments;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Fault;
import ba.biggy.androidbis.POJO.retrofitServerObjects.ArchiveFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.ArchiveFaultServerResponse;
import ba.biggy.androidbis.POJO.retrofitServerObjects.DeleteFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.DeleteFaultServerResponse;
import ba.biggy.androidbis.POJO.retrofitServerObjects.FaultServerResponse;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UpdateFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UpdateFaultServerResponse;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.FaultsTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;
import ba.biggy.androidbis.faultDetail.faultDetailAdmin.FaultDetailAdmin;
import ba.biggy.androidbis.adapter.listviewAdapter.FaultListviewExpandedAdapter;
import ba.biggy.androidbis.adapter.listviewAdapter.FaultListviewSimpleAdapter;
import ba.biggy.androidbis.adapter.spinnerAdapter.NothingSelectedSpinnerAdapter;
import ba.biggy.androidbis.faultDetail.faultDetailServiceman.FaultDetailServiceman;
import ba.biggy.androidbis.global.DateMethods;
import ba.biggy.androidbis.retrofitInterface.ArchiveFaultRequestInterface;
import ba.biggy.androidbis.retrofitInterface.DeleteFaultRequestInterface;
import ba.biggy.androidbis.retrofitInterface.FaultRequestInterface;
import ba.biggy.androidbis.retrofitInterface.UpdateFaultRequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentFaultsListview extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {


    private ListView listView;
    private SwipeMenuListView swipeMenuListView;
    private TextView faultCount;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String totalFaultCount;
    private ArrayList<Fault> faultData;
    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog prgDialog;
    private AlertDialog updateDialog;
    private SharedPreferences pref;
    private Spinner spinnerFilterServiceman;
    private int lvPosition;
    FaultListviewExpandedAdapter faultListviewExpandedAdapter;
    FaultListviewSimpleAdapter faultListviewSimpleAdapter;
    UsersTableController usersTableController = new UsersTableController();
    FaultsTableController faultsTableController = new FaultsTableController();
    DateMethods dateMethods = new DateMethods();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faults_listview, container, false);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.faults, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_getfaults) {
            getFaults();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Constants.SP_LISTVIEW_POSITION, listView.getFirstVisiblePosition());
        editor.apply();
    }


    @Override
    public void onStart(){
        super.onStart();
        listView = (ListView) getActivity().findViewById(R.id.listView);
        faultCount = (TextView) getActivity().findViewById(R.id.tvCount);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayoutFragmentListview);
        spinnerFilterServiceman = (Spinner) getActivity().findViewById(R.id.spinnerFilterServiceman);
        pref = getActivity().getApplicationContext().getSharedPreferences(Constants.PREF, 0);



        //initialy set this spinner to invisible. only admin protection level can see this spinner
        spinnerFilterServiceman.setVisibility(View.GONE);


        //populate spinner from userstable usernames with protection level serviceman
        List<String> lablesFilter = usersTableController.getAllServiceman();
        final ArrayAdapter<String> dataAdapterFilter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lablesFilter);
        dataAdapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilterServiceman.setAdapter(dataAdapterFilter);



        //get listview position from shared preferences to restore view for last viewed position
        lvPosition = pref.getInt(Constants.SP_LISTVIEW_POSITION, 0);


        //display view depending on users protection level
        displayView(usersTableController.getUserProtectionLevel1());


        //set nothingSelected text to spinner
        spinnerFilterServiceman.setPrompt(getString(R.string.spinner_nothing_selected_prompt));
        spinnerFilterServiceman.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapterFilter, R.layout.spinner_row_nothing_selected_all_faults, getActivity()));


        spinnerFilterServiceman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int currentSelection = (int) spinnerFilterServiceman.getSelectedItemId();
                if (currentSelection != -1){
                String serviceman = spinnerFilterServiceman.getSelectedItem().toString().trim();
                faultCount.setText(faultsTableController.getFilterFaultCountByServiceman(serviceman));
                faultListviewExpandedAdapter = new FaultListviewExpandedAdapter(getActivity(), faultsTableController.getFilterFaultByServiceman(serviceman));
                listView.setAdapter(faultListviewExpandedAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFilterServiceman.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                spinnerFilterServiceman.setPrompt(getString(R.string.spinner_nothing_selected_prompt));
                spinnerFilterServiceman.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapterFilter, R.layout.spinner_row_nothing_selected_all_faults, getActivity()));

                faultCount.setText(totalFaultCount);
                faultListviewExpandedAdapter = new FaultListviewExpandedAdapter(getActivity(), faultsTableController.getAllFaults());
                listView.setAdapter(faultListviewExpandedAdapter);

                return false;
            }
        });


    }


    @Override
    public void onResume(){
        super.onResume();

        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new FragmentFaultsListview();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();*/
    }


    @Override
    public void onRefresh() {
        getFaults();
    }


    private void getFaults(){
        swipeRefreshLayout.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FaultRequestInterface request = retrofit.create(FaultRequestInterface.class);
        Call<FaultServerResponse> call = request.getFaults();
        call.enqueue(new Callback<FaultServerResponse>() {
            @Override
            public void onResponse(Call<FaultServerResponse> call, Response<FaultServerResponse> response) {

                //delete all previous stored faults
                faultsTableController.deleteAll();

                //get the response body and insert faults
                FaultServerResponse jsonResponse = response.body();
                faultData = new ArrayList<>(Arrays.asList(jsonResponse.getFault()));
                for (int i = 0; i < faultData.size(); i++) {
                    faultsTableController.insertFault(faultData.get(i));
                }
                swipeRefreshLayout.setRefreshing(true);

                //refresh fragment
                Fragment newFragment = new FragmentFaultsListview();
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                tr.replace(R.id.content_main, newFragment);
                tr.commit();

            }
            @Override
            public void onFailure(Call<FaultServerResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), R.string.error_nointernet, Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void displayView (String protectionLevel){
        switch (protectionLevel){

            //if the protection level is admin show this view and options
            case Constants.PROTECTION_LEVEL_ADMIN:

                //user with admin level has additional toolbar items
                setHasOptionsMenu(true);

                //disable the swipe refresh layout
                swipeRefreshLayout.setEnabled(false);

                //set spinner used as filter by serviceman to visible
                spinnerFilterServiceman.setVisibility(View.VISIBLE);


                //set the total fault count
                totalFaultCount = faultsTableController.getTotalFaultCount();
                faultCount.setText(totalFaultCount);

                //set the adapter
                faultListviewExpandedAdapter = new FaultListviewExpandedAdapter(getActivity(), faultsTableController.getAllFaults());
                listView.setAdapter(faultListviewExpandedAdapter);

                //listView.setSelection(lvPosition);

                //get details from cursor and show them in FragmentFaultsListviewDetail
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //get cursor from selected item
                Cursor c = (Cursor) listView.getItemAtPosition(position);
                //get strings from cursor
                String datefault = c.getString(2);
                String timefault = c.getString(3);
                String productType = c.getString(4);
                String client = c.getString(7);
                String address = c.getString(8);
                String place = c.getString(9);
                String phone1 = c.getString(10);
                String phone2 = c.getString(11);
                String descFault = c.getString(12);
                String note = c.getString(13);
                String serviceman = c.getString(14);
                c.close();


                Intent intent = new Intent(getActivity(), FaultDetailAdmin.class);
                intent.putExtra(Constants.DATEFAULT, datefault);
                intent.putExtra(Constants.TIMEFAULT, timefault);
                intent.putExtra(Constants.PRODUCT_TYPE, productType);
                intent.putExtra(Constants.CLIENT, client);
                intent.putExtra(Constants.ADDRESS, address);
                intent.putExtra(Constants.PLACE, place);
                intent.putExtra(Constants.PHONE_ONE, phone1);
                intent.putExtra(Constants.PHONE_TWO, phone2);
                intent.putExtra(Constants.DESCRIPTION_FAULT, descFault);
                intent.putExtra(Constants.NOTE, note);
                intent.putExtra(Constants.SERVICEMAN, serviceman);

                startActivity(intent);
                /*Fragment newFragment = new FragmentFaultsListviewDetail();
                Bundle bundle = new Bundle();
                bundle.putString("datefault", datefault);
                bundle.putString("timefault", timefault);
                bundle.putString("client", client);
                bundle.putString("address", address);
                bundle.putString("place", place);
                bundle.putString("phone1", phone1);
                bundle.putString("phone2", phone2);
                bundle.putString("descfault", descfault);
                bundle.putString("note", note);
                newFragment.setArguments(bundle);

                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                tr.replace(R.id.content_main, newFragment);
                tr.addToBackStack(null);
                tr.commit();*/
                    }
                });


                //create menu items in swipemenu
                SwipeMenuCreator adminSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                        deleteItem.setBackground(R.color.colorDelete);
                        deleteItem.setWidth(250);
                        deleteItem.setIcon(R.drawable.ic_delete_forever_black);
                        menu.addMenuItem(deleteItem);

                       //create "update" item
                        SwipeMenuItem updateItem = new SwipeMenuItem(getActivity());
                        updateItem.setBackground(R.color.colorUpdate);
                        updateItem.setWidth(250);
                        updateItem.setIcon(R.drawable.ic_create_black);
                        menu.addMenuItem(updateItem);

                        //create "archive" item
                        SwipeMenuItem archiveItem = new SwipeMenuItem(getActivity());
                        archiveItem.setBackground(R.color.colorArchive);
                        archiveItem.setWidth(250);
                        archiveItem.setIcon(R.drawable.ic_folder_black);
                        menu.addMenuItem(archiveItem);
                    }
                };

                swipeMenuListView = (SwipeMenuListView) getActivity().findViewById(R.id.listView);
                swipeMenuListView.setMenuCreator(adminSwipeCreator);
                swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);



                //handle swipe menu item clicks
                swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

                        //get cursor from selected item
                        Cursor c = (Cursor) swipeMenuListView.getItemAtPosition(position);
                        //id string is needed for webservice
                        final String id = c.getString(1);
                        //strings which are shown in the snackbar
                        String client = c.getString(7);
                        String place = c.getString(9);
                        String phone1 = c.getString(10);
                        String phone2 = c.getString(11);
                        String faultdescription = c.getString(12);
                        String serviceman = c.getString(14);
                        c.close();


                        switch (index) {

                            case 0:
                                // delete
                                StringBuilder delBuilder = new StringBuilder();
                                delBuilder.append(getResources().getString(R.string.snackbar_delete_fault));
                                delBuilder.append("\n" + getResources().getString(R.string.snackbar_delete_fault_client) + " " + client);
                                delBuilder.append("\n" + getResources().getString(R.string.snackbar_delete_fault_place) + " " + place);
                                String deleteString = delBuilder.toString();

                                Snackbar delSnackbar = Snackbar
                                        .make(coordinatorLayout, deleteString, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.snackbar_delete_fault_yes, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                deleteFault(id);
                                            }
                                        });

                                // set action button text color
                                delSnackbar.setActionTextColor(Color.RED);
                                //set snackbar max lines
                                View dSbView = delSnackbar.getView();
                                TextView dSbTv = (TextView) dSbView.findViewById(android.support.design.R.id.snackbar_text);
                                dSbTv.setMaxLines(4);
                                delSnackbar.show();

                                break;


                            case 1:
                                // update
                                showUpdateDialog(id, serviceman, phone1, phone2, faultdescription);

                                break;


                            case 2:
                                // archive
                                StringBuilder arcBuilder = new StringBuilder();
                                arcBuilder.append(getResources().getString(R.string.snackbar_archive_fault));
                                arcBuilder.append("\n" + getResources().getString(R.string.snackbar_archive_fault_client) + " " + client);
                                arcBuilder.append("\n" + getResources().getString(R.string.snackbar_archive_fault_place) + " " + place);
                                String archiveString = arcBuilder.toString();

                                Snackbar arcSnackbar = Snackbar
                                        .make(coordinatorLayout, archiveString, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.snackbar_archive_fault_yes, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String dateArchive = dateMethods.getTodayDateForMysql();
                                                archiveFault(id, dateArchive);
                                            }
                                        });

                                // set action button text color
                                arcSnackbar.setActionTextColor(Color.GREEN);
                                //set snackbar max lines
                                View aSbView = arcSnackbar.getView();
                                TextView aSbTv = (TextView) aSbView.findViewById(android.support.design.R.id.snackbar_text);
                                aSbTv.setMaxLines(4);
                                arcSnackbar.show();

                                break;
                        }
                        return false;
                    }
                });





                break;


            //if the protection level is serviceman show this view and options
            case Constants.PROTECTION_LEVEL_USER:

                //user with serviceman level has additional toolbar items
                setHasOptionsMenu(true);

                //disable the swipe refresh layout
                swipeRefreshLayout.setEnabled(false);

                //set the fault count for normal user
                totalFaultCount = faultsTableController.getFaultCountByServiceman();
                faultCount.setText(totalFaultCount);

                //set the adapter
                faultListviewSimpleAdapter = new FaultListviewSimpleAdapter(getActivity(), faultsTableController.getFaultByServiceman());
                listView.setAdapter(faultListviewSimpleAdapter);

                //get details from cursor and show them in FragmentFaultsListviewDetail
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                        //get cursor from selected item
                        Cursor c = (Cursor) listView.getItemAtPosition(position);
                        //get strings from cursor
                        String datefault = c.getString(2);
                        String timefault = c.getString(3);
                        String productType = c.getString(4);
                        String client = c.getString(7);
                        String address = c.getString(8);
                        String place = c.getString(9);
                        String phone1 = c.getString(10);
                        String phone2 = c.getString(11);
                        String descFault = c.getString(12);
                        String note = c.getString(13);
                        String serviceman = c.getString(14);
                        c.close();

                        Intent intent = new Intent(getActivity(), FaultDetailServiceman.class);
                        intent.putExtra(Constants.DATEFAULT, datefault);
                        intent.putExtra(Constants.TIMEFAULT, timefault);
                        intent.putExtra(Constants.PRODUCT_TYPE, productType);
                        intent.putExtra(Constants.CLIENT, client);
                        intent.putExtra(Constants.ADDRESS, address);
                        intent.putExtra(Constants.PLACE, place);
                        intent.putExtra(Constants.PHONE_ONE, phone1);
                        intent.putExtra(Constants.PHONE_TWO, phone2);
                        intent.putExtra(Constants.DESCRIPTION_FAULT, descFault);
                        intent.putExtra(Constants.NOTE, note);
                        intent.putExtra(Constants.SERVICEMAN, serviceman);
                        startActivity(intent);


                        /*Fragment newFragment = new FragmentFaultsListviewDetail();
                        Bundle bundle = new Bundle();
                        bundle.putString("datefault", datefault);
                        bundle.putString("timefault", timefault);
                        bundle.putString("client", client);
                        bundle.putString("address", address);
                        bundle.putString("place", place);
                        bundle.putString("phone1", phone1);
                        bundle.putString("phone2", phone2);
                        bundle.putString("descfault", descfault);
                        bundle.putString("note", note);
                        newFragment.setArguments(bundle);

                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        tr.replace(R.id.content_main, newFragment);
                        tr.addToBackStack(null);
                        tr.commit();*/
                    }
                });


                //create menu items in swipemenu
                SwipeMenuCreator userSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                        deleteItem.setBackground(R.color.colorDelete);
                        deleteItem.setWidth(250);
                        deleteItem.setIcon(R.drawable.ic_clear_black);
                        menu.addMenuItem(deleteItem);

                        //create "archive" item
                        SwipeMenuItem archiveItem = new SwipeMenuItem(getActivity());
                        archiveItem.setBackground(R.color.colorArchive);
                        archiveItem.setWidth(250);
                        archiveItem.setIcon(R.drawable.ic_done_black);
                        menu.addMenuItem(archiveItem);
                    }
                };

                swipeMenuListView = (SwipeMenuListView) getActivity().findViewById(R.id.listView);
                swipeMenuListView.setMenuCreator(userSwipeCreator);
                swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


                //handle swipe menu item clicks
                swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

                        //get cursor from selected item
                        Cursor c = (Cursor) swipeMenuListView.getItemAtPosition(position);
                        //id string is needed for webservice
                        final String id = c.getString(1);
                        //strings which are shown in the snackbar
                        String client = c.getString(7);
                        String place = c.getString(9);
                        c.close();


                        switch (index) {

                            case 0:
                                // delete
                                StringBuilder userDelBuilder = new StringBuilder();
                                userDelBuilder.append(getResources().getString(R.string.snackbar_delete_fault));
                                userDelBuilder.append("\n" + getResources().getString(R.string.snackbar_delete_fault_client) + " " + client);
                                userDelBuilder.append("\n" + getResources().getString(R.string.snackbar_delete_fault_place) + " " + place);
                                String deleteString = userDelBuilder.toString();

                                Snackbar delSnackbar = Snackbar
                                        .make(coordinatorLayout, deleteString, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.snackbar_delete_fault_yes, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //delete fault from local sql table
                                                faultsTableController.deleteFault(id);

                                                //refresh fragment
                                                Fragment newFragment = new FragmentFaultsListview();
                                                FragmentTransaction tr = getFragmentManager().beginTransaction();
                                                tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                                tr.replace(R.id.content_main, newFragment);
                                                tr.commit();
                                            }
                                        });

                                // set action button text color
                                delSnackbar.setActionTextColor(Color.RED);
                                //set snackbar max lines
                                View dSbView = delSnackbar.getView();
                                TextView dSbTv = (TextView) dSbView.findViewById(android.support.design.R.id.snackbar_text);
                                dSbTv.setMaxLines(4);
                                delSnackbar.show();

                                break;


                            case 1:
                                // archive
                                StringBuilder userArcBuilder = new StringBuilder();
                                userArcBuilder.append(getResources().getString(R.string.snackbar_archive_fault));
                                userArcBuilder.append("\n" + getResources().getString(R.string.snackbar_archive_fault_client) + " " + client);
                                userArcBuilder.append("\n" + getResources().getString(R.string.snackbar_archive_fault_place) + " " + place);
                                String archiveString = userArcBuilder.toString();

                                Snackbar arcSnackbar = Snackbar
                                        .make(coordinatorLayout, archiveString, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.snackbar_archive_fault_yes, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //delete fault from local sql table
                                                faultsTableController.deleteFault(id);

                                                //refresh fragment
                                                Fragment newFragment = new FragmentFaultsListview();
                                                FragmentTransaction tr = getFragmentManager().beginTransaction();
                                                tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                                tr.replace(R.id.content_main, newFragment);
                                                tr.commit();

                                            }
                                        });

                                // set action button text color
                                arcSnackbar.setActionTextColor(Color.GREEN);
                                //set snackbar max lines
                                View aSbView = arcSnackbar.getView();
                                TextView aSbTv = (TextView) aSbView.findViewById(android.support.design.R.id.snackbar_text);
                                aSbTv.setMaxLines(4);
                                arcSnackbar.show();

                                break;

                        }
                        return false;
                    }
                });



                break;


            //if the protection level is viewer show this view and options
            case Constants.PROTECTION_LEVEL_VIEWER:

                //set the total fault count
                totalFaultCount = faultsTableController.getTotalFaultCount();
                faultCount.setText(totalFaultCount);

                //set the adapter
                faultListviewExpandedAdapter = new FaultListviewExpandedAdapter(getActivity(), faultsTableController.getAllFaults());
                listView.setAdapter(faultListviewExpandedAdapter);

                //get details from cursor and show them in FragmentFaultsListviewDetail
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                        //get cursor from selected item
                        Cursor c = (Cursor) listView.getItemAtPosition(position);
                        //get strings from cursor
                        String datefault = c.getString(2);
                        String timefault = c.getString(3);
                        String client = c.getString(7);
                        String address = c.getString(8);
                        String place = c.getString(9);
                        String phone1 = c.getString(10);
                        String phone2 = c.getString(11);
                        String descfault = c.getString(12);
                        String note = c.getString(13);
                        c.close();

                        Fragment newFragment = new FragmentFaultsListviewDetail();
                        Bundle bundle = new Bundle();
                        bundle.putString("datefault", datefault);
                        bundle.putString("timefault", timefault);
                        bundle.putString("client", client);
                        bundle.putString("address", address);
                        bundle.putString("place", place);
                        bundle.putString("phone1", phone1);
                        bundle.putString("phone2", phone2);
                        bundle.putString("descfault", descfault);
                        bundle.putString("note", note);
                        newFragment.setArguments(bundle);

                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        tr.replace(R.id.content_main, newFragment);
                        tr.addToBackStack(null);
                        tr.commit();
                    }
                });


                swipeRefreshLayout.setOnRefreshListener(this);
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                break;


            default:
                break;
        }
    }


    //method to delete fault
    private void deleteFault(final String id){
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage(getResources().getString(R.string.prgDialog_deleting));
        prgDialog.setCancelable(false);
        prgDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DeleteFaultRequestInterface deleteFaultRequestInterface = retrofit.create(DeleteFaultRequestInterface.class);
        DeleteFaultServerRequest deleteFaultServerRequest = new DeleteFaultServerRequest();
        deleteFaultServerRequest.setId(id);
        Call<DeleteFaultServerResponse> response = deleteFaultRequestInterface.operation(deleteFaultServerRequest);
        response.enqueue(new Callback<DeleteFaultServerResponse>() {
            @Override
            public void onResponse(Call<DeleteFaultServerResponse> call, retrofit2.Response<DeleteFaultServerResponse> response) {
                DeleteFaultServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){

                    //delete the archived fault from local sql table
                    faultsTableController.deleteFault(id);

                    //refresh fragment
                    Fragment newFragment = new FragmentFaultsListview();
                    FragmentTransaction tr = getFragmentManager().beginTransaction();
                    tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    tr.replace(R.id.content_main, newFragment);
                    tr.commit();

                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_delete_success, Snackbar.LENGTH_LONG).show();

                }else if (resp.getResult().equals(Constants.FAILURE)){
                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_delete_failure, Snackbar.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<DeleteFaultServerResponse> call, Throwable t) {
                prgDialog.dismiss();
                Snackbar.make(coordinatorLayout, R.string.snackbar_delete_error, Snackbar.LENGTH_LONG).show();
            }
        });

    }


    //method to archive fault
    private void archiveFault(final String id, String dateArchive){
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage(getResources().getString(R.string.prgDialog_archiving));
        prgDialog.setCancelable(false);
        prgDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ArchiveFaultRequestInterface archiveFaultRequestInterface = retrofit.create(ArchiveFaultRequestInterface.class);
        ArchiveFaultServerRequest archiveFaultServerRequest = new ArchiveFaultServerRequest();
        archiveFaultServerRequest.setId(id);
        archiveFaultServerRequest.setDateArchive(dateArchive);
        Call<ArchiveFaultServerResponse> response = archiveFaultRequestInterface.operation(archiveFaultServerRequest);
        response.enqueue(new Callback<ArchiveFaultServerResponse>() {
            @Override
            public void onResponse(Call<ArchiveFaultServerResponse> call, retrofit2.Response<ArchiveFaultServerResponse> response) {
                ArchiveFaultServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){

                    //delete the archived fault from local sql table
                    faultsTableController.deleteFault(id);

                    //refresh fragment
                    Fragment newFragment = new FragmentFaultsListview();
                    FragmentTransaction tr = getFragmentManager().beginTransaction();
                    tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    tr.replace(R.id.content_main, newFragment);
                    tr.commit();

                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_archive_success, Snackbar.LENGTH_LONG).show();

                }else if (resp.getResult().equals(Constants.FAILURE)){
                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_archive_failure, Snackbar.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ArchiveFaultServerResponse> call, Throwable t) {
                prgDialog.dismiss();
                Snackbar.make(coordinatorLayout, R.string.snackbar_archive_error, Snackbar.LENGTH_LONG).show();
            }
        });

    }


    private void showUpdateDialog(final String id, String serviceman, String phone1, String phone2, String faultDescription){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_fault, null);
        final Spinner spinnerServiceman = (Spinner) view.findViewById(R.id.spinnerServiceman);
        final EditText etPhone1 = (EditText) view.findViewById(R.id.etPhone1);
        final EditText etPhone2 = (EditText) view.findViewById(R.id.etPhone2);
        final EditText etFaultDescription = (EditText) view.findViewById(R.id.etFaultDescription);

        //populate spinner from userstable usernames with protection level serviceman
        List<String> lables = usersTableController.getAllServiceman();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceman.setAdapter(dataAdapter);
        //set the selection of serviceman
        spinnerServiceman.setSelection(lables.indexOf(serviceman));
        //set phone numbers and fault description
        etPhone1.setText(phone1);
        etPhone2.setText(phone2);
        etFaultDescription.setText(faultDescription);


        builder.setView(view);
        builder.setTitle(R.string.dialog_update_title);
        builder.setPositiveButton(R.string.dialog_update_positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.dialog_update_negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        updateDialog = builder.create();
        updateDialog.show();
        updateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newServiceman = spinnerServiceman.getSelectedItem().toString().trim();
                String newPhone1 = etPhone1.getText().toString().trim();
                String newPhone2 = etPhone2.getText().toString().trim();
                String newFaultDescription = etFaultDescription.getText().toString().trim();
                updateFault(id, newServiceman, newPhone1, newPhone2, newFaultDescription);
                updateDialog.dismiss();

            }
        });

    }


    //method to update fault
    private void updateFault(final String id, final String serviceman, final String phone1, final String phone2, final String faultDescripton){

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage(getResources().getString(R.string.prgDialog_updating));
        prgDialog.setCancelable(false);
        prgDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpdateFaultRequestInterface updateFaultRequestInterface = retrofit.create(UpdateFaultRequestInterface.class);
        UpdateFaultServerRequest updateFaultServerRequest = new UpdateFaultServerRequest();
        updateFaultServerRequest.setId(id);
        updateFaultServerRequest.setServiceman(serviceman);
        updateFaultServerRequest.setPhone1(phone1);
        updateFaultServerRequest.setPhone2(phone2);
        updateFaultServerRequest.setFaultDescription(faultDescripton);

        Call<UpdateFaultServerResponse> response = updateFaultRequestInterface.operation(updateFaultServerRequest);
        response.enqueue(new Callback<UpdateFaultServerResponse>() {
            @Override
            public void onResponse(Call<UpdateFaultServerResponse> call, retrofit2.Response<UpdateFaultServerResponse> response) {
                UpdateFaultServerResponse resp = response.body();

                if(resp.getResult().equals(Constants.SUCCESS)){

                    faultsTableController.updateFault(id, serviceman, phone1, phone2, faultDescripton);

                    //refresh fragment
                    Fragment newFragment = new FragmentFaultsListview();
                    FragmentTransaction tr = getFragmentManager().beginTransaction();
                    tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    tr.replace(R.id.content_main, newFragment);
                    tr.commit();

                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_update_success, Snackbar.LENGTH_LONG).show();

                }else if (resp.getResult().equals(Constants.FAILURE)){
                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_update_failure, Snackbar.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<UpdateFaultServerResponse> call, Throwable t) {
                prgDialog.dismiss();
                Snackbar.make(coordinatorLayout, R.string.snackbar_update_error, Snackbar.LENGTH_LONG).show();
            }
        });

    }



}
