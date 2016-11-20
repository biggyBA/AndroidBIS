package ba.biggy.androidbis.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Servicesheet;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UploadServicesheetServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UploadServicesheetServerResponse;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.ServicesheetTableController;
import ba.biggy.androidbis.adapter.listviewAdapter.ServicesheetListviewAdapter;
import ba.biggy.androidbis.retrofitInterface.UploadServicesheetRequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ba.biggy.androidbis.R.id.coordinatorLayout;

public class FragmentMyServicesheets extends Fragment {


    private Spinner spinnerKind;
    private SwipeMenuListView swipeMenuListView;
    private ListView listView;
    private ProgressDialog prgDialog;
    private ArrayAdapter<CharSequence> adapterKind;
    private CoordinatorLayout coordinatorLayout;
    ServicesheetListviewAdapter servicesheetListviewAdapter;
    ServicesheetTableController servicesheetTableController = new ServicesheetTableController();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myservicesheets, container, false);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        spinnerKind = (Spinner) getActivity().findViewById(R.id.spinnerKind);

        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        listView = (ListView) getActivity().findViewById(R.id.listView);
        swipeMenuListView = (SwipeMenuListView) getActivity().findViewById(R.id.listView);

        adapterKind = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerKind, android.R.layout.simple_spinner_item);
        adapterKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKind.setAdapter(adapterKind);

        spinnerKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int selected = (int) spinnerKind.getSelectedItemId();

                if (selected == 0) {  // All servicesheets

                    servicesheetListviewAdapter = new ServicesheetListviewAdapter(getActivity(), servicesheetTableController.getAllServicesheets());
                    listView.setAdapter(servicesheetListviewAdapter);

                    listViewOptions(0);

                } else if (selected == 1){  // Sent servicesheets

                    servicesheetListviewAdapter = new ServicesheetListviewAdapter(getActivity(), servicesheetTableController.getSentServicesheets());
                    listView.setAdapter(servicesheetListviewAdapter);

                    listViewOptions(1);

                } else if (selected == 2) { // Not sent servicesheets

                    servicesheetListviewAdapter = new ServicesheetListviewAdapter(getActivity(), servicesheetTableController.getNotSentServicesheets());
                    listView.setAdapter(servicesheetListviewAdapter);

                    listViewOptions(2);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

    }


    private void listViewOptions (int selected){

        switch (selected){
            case 0:
                //this view has no swipe options
                SwipeMenuCreator allSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                    }
                };
                swipeMenuListView.setMenuCreator(allSwipeCreator);
                break;


            case 1:
                //this view has no swipe options
                SwipeMenuCreator sentSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                    }
                };
                swipeMenuListView.setMenuCreator(sentSwipeCreator);
                break;


            case 2:
                //create menu items in swipemenu
                SwipeMenuCreator notSentSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                        // create "upload" item
                        SwipeMenuItem uploadItem = new SwipeMenuItem(getActivity());
                        uploadItem.setBackground(R.color.colorArchive);
                        uploadItem.setWidth(250);
                        uploadItem.setIcon(R.drawable.ic_file_upload_black_24px);
                        menu.addMenuItem(uploadItem);
                    }
                };


                swipeMenuListView.setMenuCreator(notSentSwipeCreator);
                swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

                //handle swipe menu item clicks
                swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {




                        switch (index) {

                            case 0:
                                // upload servicesheet
                                Servicesheet servicesheet = new Servicesheet();

                                //get cursor from selected item
                                Cursor c = (Cursor) swipeMenuListView.getItemAtPosition(position);

                                servicesheet.setDatefault(c.getString(2));
                                servicesheet.setTimefault(c.getString(3));
                                servicesheet.setIdent(c.getString(4));
                                servicesheet.setSerialnumber(c.getString(5));
                                servicesheet.setProductType(c.getString(6));
                                servicesheet.setBuyer(c.getString(7));
                                servicesheet.setAddress(c.getString(8));
                                servicesheet.setPlacefault(c.getString(9));
                                servicesheet.setPhoneNumber(c.getString(10));
                                servicesheet.setPhoneNumber2(c.getString(11));
                                servicesheet.setDescFaults(c.getString(12));
                                servicesheet.setNotesInfo(c.getString(13));
                                servicesheet.setResponsibleforfailure(c.getString(14));
                                servicesheet.setStatus(c.getString(15));
                                servicesheet.setDatearchive(c.getString(18));
                                servicesheet.setAuthorizedservice(c.getString(20));
                                servicesheet.setDescintervention(c.getString(21));
                                servicesheet.setWarrantyYesNo(c.getString(25));
                                servicesheet.setMethodpayment(c.getString(26));
                                servicesheet.setPartsBuyerPrice(c.getString(27));
                                servicesheet.setWorkBuyerPrice(c.getString(28));
                                servicesheet.setTripBuyerPrice(c.getString(29));
                                servicesheet.setTotalBuyerPrice(c.getString(30));
                                servicesheet.setStatusOfPayment(c.getString(31));
                                servicesheet.sethPUTServiceCost(c.getString(33));
                                servicesheet.sethINT(c.getString(34));
                                servicesheet.setTotalcostsum(c.getString(35));
                                servicesheet.setRandomStringParts(c.getString(37));
                                servicesheet.setBuydate(c.getString(38));
                                servicesheet.setEndwarranty(c.getString(39));

                                c.close();

                                uploadServicesheet(servicesheet);




                                break;



                        }
                        return false;
                    }
                });

                break;

            default:
                break;
        }
    }


    private void uploadServicesheet(Servicesheet servicesheet){
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage(getResources().getString(R.string.prgDialog_uploadingServicesheet));
        prgDialog.setCancelable(false);
        prgDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                // TODO replace with base url
                .baseUrl("http://biggy.ba/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UploadServicesheetRequestInterface uploadServicesheetRequestInterface = retrofit.create(UploadServicesheetRequestInterface.class);

        UploadServicesheetServerRequest uploadServicesheetServerRequest = new UploadServicesheetServerRequest();
        uploadServicesheetServerRequest.setServicesheet(servicesheet);

        Call<UploadServicesheetServerResponse> response = uploadServicesheetRequestInterface.operation(uploadServicesheetServerRequest);
        response.enqueue(new Callback<UploadServicesheetServerResponse>() {
            @Override
            public void onResponse(Call<UploadServicesheetServerResponse> call, retrofit2.Response<UploadServicesheetServerResponse> response) {
                UploadServicesheetServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){


                    // TODO updateStatus to yes in sql table
                    String rnd = "MKBLQQMFCEDCRVLULXYFIXMFFECWLEJS";
                    servicesheetTableController.updateStatus(rnd);

                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_uploadServicesheet_success, Snackbar.LENGTH_LONG).show();

                }else if (resp.getResult().equals(Constants.FAILURE)){
                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_uploadServicesheet_failure, Snackbar.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<UploadServicesheetServerResponse> call, Throwable t) {
                prgDialog.dismiss();
                Snackbar.make(coordinatorLayout, R.string.snackbar_uploadServicesheet_error, Snackbar.LENGTH_LONG).show();
            }
        });


    }



}
