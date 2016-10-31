package ba.biggy.androidbis.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.OnSwipeTouchListener;
import ba.biggy.androidbis.POJO.Fault;
import ba.biggy.androidbis.POJO.retrofitServerObjects.FaultServerResponse;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UserServerResponse;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.FaultsTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;
import ba.biggy.androidbis.adapter.FaultListviewExpandedAdapter;
import ba.biggy.androidbis.adapter.FaultListviewSimpleAdapter;
import ba.biggy.androidbis.retrofitInterface.FaultRequestInterface;
import ba.biggy.androidbis.retrofitInterface.UserRequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentFaultsListview extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private TextView faultCount;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String protectionLevel, totalFaultCount;
    private ArrayList<Fault> faultData;
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
    public void onStart(){
        super.onStart();

        listView = (ListView) getActivity().findViewById(R.id.listView);
        faultCount = (TextView) getActivity().findViewById(R.id.tvCount);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);


        //display view depending on users protection level
        displayView(protectionLevel = usersTableController.getUserProtectionLevel1());




        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

            }
        });









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

                //set the total fault count
                totalFaultCount = faultsTableController.getTotalFaultCount();
                faultCount.setText(totalFaultCount);

                //set the adapter
                faultListviewExpandedAdapter = new FaultListviewExpandedAdapter(getActivity(), faultsTableController.getAllFaults());
                listView.setAdapter(faultListviewExpandedAdapter);


                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                });*/


                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });



                /*listView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

                    @Override
                    public void onClick() {
                        super.onClick();
                        Toast.makeText(getActivity(), "On click listener", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDoubleClick() {
                        super.onDoubleClick();
                        Toast.makeText(getActivity(), "On double click listener", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick() {
                        super.onLongClick();
                        Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_SHORT).show();
                    }

                    *//*@Override
                    public void onSwipeUp() {
                        super.onSwipeUp();
                        Toast.makeText(getActivity(), "On swipe up", Toast.LENGTH_SHORT).show();
                    }*//*

                    *//*@Override
                    public void onSwipeDown() {
                        super.onSwipeDown();
                        Toast.makeText(getActivity(), "On swipe d", Toast.LENGTH_SHORT).show();
                    }*//*

                    @Override
                    public void onSwipeLeft() {
                        super.onSwipeLeft();
                        Toast.makeText(getActivity(), "On swipe l", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSwipeRight() {
                        super.onSwipeRight();
                        Toast.makeText(getActivity(), "On swipe r", Toast.LENGTH_SHORT).show();
                    }
                });*/


                listView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                historicX = event.getX();
                                historicY = event.getY();
                                break;

                            case MotionEvent.ACTION_UP:
                                if (event.getX() - historicX < -DELTA) {
                                    //FunctionDeleteRowWhenSlidingLeft();
                                    Toast.makeText(getActivity(), "FunctionDeleteRowWhenSlidingLeft", Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                                else if (event.getX() - historicX > DELTA) {
                                    //FunctionDeleteRowWhenSlidingRight();
                                    Toast.makeText(getActivity(), "FunctionDeleteRowWhenSlidingRight", Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                                break;

                            default:
                                return false;
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
                break;


            case Constants.PROTECTION_LEVEL_VIEWER:

                break;


            default:
                break;
        }
    }

















}
