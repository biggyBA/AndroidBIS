package ba.biggy.androidbis.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuLayout;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Arrays;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.LoginActivity;
import ba.biggy.androidbis.OnSwipeTouchListener;
import ba.biggy.androidbis.POJO.Fault;
import ba.biggy.androidbis.POJO.retrofitServerObjects.DeleteFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.DeleteFaultServerResponse;
import ba.biggy.androidbis.POJO.retrofitServerObjects.FaultServerResponse;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UserServerResponse;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.AndroidDatabaseManager;
import ba.biggy.androidbis.SQLite.CurrentUserTableController;
import ba.biggy.androidbis.SQLite.FaultsTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;
import ba.biggy.androidbis.SettingsActivity;
import ba.biggy.androidbis.TestActivity;
import ba.biggy.androidbis.adapter.FaultListviewExpandedAdapter;
import ba.biggy.androidbis.adapter.FaultListviewSimpleAdapter;
import ba.biggy.androidbis.retrofitInterface.DeleteFaultRequestInterface;
import ba.biggy.androidbis.retrofitInterface.FaultRequestInterface;
import ba.biggy.androidbis.retrofitInterface.UserRequestInterface;
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
    private String protectionLevel, totalFaultCount;
    private ArrayList<Fault> faultData;
    private CoordinatorLayout coordinatorLayout;
    FaultListviewExpandedAdapter faultListviewExpandedAdapter;
    FaultListviewSimpleAdapter faultListviewSimpleAdapter;
    UsersTableController usersTableController = new UsersTableController();
    FaultsTableController faultsTableController = new FaultsTableController();
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;
    enum Direction {LEFT, RIGHT;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faults_listview, container, false);
        return rootView;
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
    public void onStart(){
        super.onStart();

        listView = (ListView) getActivity().findViewById(R.id.listView);
        faultCount = (TextView) getActivity().findViewById(R.id.tvCount);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        //display view depending on users protection level
        displayView(protectionLevel = usersTableController.getUserProtectionLevel1());


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
                tr.addToBackStack(null);
                tr.commit();

            }
            @Override
            public void onFailure(Call<FaultServerResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(true);
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


                //create menu items in swipemenu
                SwipeMenuCreator swipeCreator = new SwipeMenuCreator() {
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
                swipeMenuListView.setMenuCreator(swipeCreator);
                swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

                //handle swipe menu item clicks
                swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                        switch (index) {

                            case 0:
                                // delete
                                Snackbar.make(coordinatorLayout, R.string.snackbar_delete_fault, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.snackbar_delete_fault_yes, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //deleteFault();
                                            }
                                        }).show();
                                break;


                            case 1:
                                // update
                                Snackbar.make(coordinatorLayout, "Update", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                break;
                            case 2:
                                // archive
                                //Toast.makeText(getActivity(), "Index: " + index + " " + "Position: " + position, Toast.LENGTH_LONG).show();
                                Cursor c = (Cursor) swipeMenuListView.getItemAtPosition(position);
                                String datefault = c.getString(7);
                                Snackbar.make(coordinatorLayout, datefault, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                break;
                        }
                        return false;
                    }
                });





                break;


            case Constants.PROTECTION_LEVEL_USER:

                //set the fault count for normal user
                totalFaultCount = faultsTableController.getFaultCountByServiceman();
                faultCount.setText(totalFaultCount);

                //set the adapter
                //faultListviewSimpleAdapter = new FaultListviewSimpleAdapter(getActivity(), faultsTableController.getFaultByServiceman());
                //listView.setAdapter(faultListviewSimpleAdapter);


                swipeRefreshLayout.setOnRefreshListener(this);
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                break;


            case Constants.PROTECTION_LEVEL_VIEWER:

                break;


            default:
                break;
        }
    }





    //method to delete fault
    public void deleteFault(String id){

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



                }else if (resp.getResult().equals(Constants.FAILURE)){
                    Snackbar.make(coordinatorLayout, "Neispravni pristupni podaci", Snackbar.LENGTH_LONG).show();
                }
                //prgDialog.dismiss();
            }
            @Override
            public void onFailure(Call<DeleteFaultServerResponse> call, Throwable t) {
                //prgDialog.dismiss();
                Snackbar.make(coordinatorLayout, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }












}
