package ba.biggy.androidbis.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.POJO.Fault;
import ba.biggy.androidbis.POJO.retrofitServerObjects.AddFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.AddFaultServerResponse;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.CurrentUserTableController;
import ba.biggy.androidbis.SQLite.FaultsTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;
import ba.biggy.androidbis.global.DateMethods;
import ba.biggy.androidbis.global.PhoneMethods;
import ba.biggy.androidbis.global.TimeMethods;
import ba.biggy.androidbis.receiver.PhoneStateReceiver;
import ba.biggy.androidbis.retrofitInterface.AddFaultRequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentAddFault extends Fragment implements View.OnClickListener{

    private EditText etSN, etClient, etAddress, etPlace, etPhone1, etPhone2, etFaultDescription, etNote;
    private Spinner spinnerProductType, spinnerServiceman;
    private Button btnAddFault;
    private ArrayList<Fault> faultData;
    private ProgressDialog prgDialog;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences pref;
    UsersTableController usersTableController = new UsersTableController();
    CurrentUserTableController currentUserTableController = new CurrentUserTableController();
    FaultsTableController faultsTableController = new FaultsTableController();
    DateMethods dateMethods = new DateMethods();
    TimeMethods timeMethods = new TimeMethods();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_fault, container, false);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        pref = getActivity().getSharedPreferences(Constants.PREF, 0);

        etSN = (EditText) getActivity().findViewById(R.id.etSn);
        etClient = (EditText) getActivity().findViewById(R.id.etClient);
        etAddress = (EditText) getActivity().findViewById(R.id.etAddress);
        etPlace = (EditText) getActivity().findViewById(R.id.etPlace);
        etPhone1 = (EditText) getActivity().findViewById(R.id.etPhone1);
        etPhone2 = (EditText) getActivity().findViewById(R.id.etPhone2);
        etFaultDescription = (EditText) getActivity().findViewById(R.id.etFaultDescription);
        etNote = (EditText) getActivity().findViewById(R.id.etNote);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        spinnerProductType = (Spinner) getActivity().findViewById(R.id.spinnerProductType);
        spinnerServiceman = (Spinner) getActivity().findViewById(R.id.spinnerServiceman);

        btnAddFault = (Button) getActivity().findViewById(R.id.btnAddFault);

        //populate product type spinner with data
        List<String> spinnerProductTypeArray =  new ArrayList<String>();
        spinnerProductTypeArray.add("7,5 kW");
        spinnerProductTypeArray.add("10,5 kW Voda");
        spinnerProductTypeArray.add("10,5 kW Zrak");
        spinnerProductTypeArray.add("11 kW");
        spinnerProductTypeArray.add("18 kW");
        spinnerProductTypeArray.add("33 kW");
        spinnerProductTypeArray.add("20 kW");
        spinnerProductTypeArray.add("35 kW");
        spinnerProductTypeArray.add("50 kW");
        spinnerProductTypeArray.add("75 kW");
        spinnerProductTypeArray.add("100 kW");
        spinnerProductTypeArray.add("150 kW");
        spinnerProductTypeArray.add("200 kW");
        spinnerProductTypeArray.add("300 kW");
        spinnerProductTypeArray.add("400 kW");
        spinnerProductTypeArray.add("500 kW");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerProductTypeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductType.setAdapter(adapter);


        //populate spinner from userstable usernames with protection level serviceman
        List<String> lables = usersTableController.getAllServiceman();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceman.setAdapter(dataAdapter);
        //set the selection of serviceman
        spinnerServiceman.setSelection(lables.indexOf("N/A"));


        btnAddFault.setOnClickListener(this);

        etPhone1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TelephonyManager mtelephony = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                mtelephony.listen(new PhoneStateListener(){
                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {
                        super.onCallStateChanged(state, incomingNumber);
                        switch (state) {
                            case TelephonyManager.CALL_STATE_OFFHOOK:
                                // CALL_STATE_RINGING
                                //Toast.makeText(getActivity(), incomingNumber, Toast.LENGTH_LONG).show();
                                etPhone1.setText(incomingNumber);
                                break;
                            default:
                                break;
                        }
                    }
                },PhoneStateListener.LISTEN_CALL_STATE);




                return false;
            }
        });

        etPhone2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String phone = pref.getString("phone", "");
                etPhone2.setText(phone);


                return false;
            }
        });




    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddFault:
                String date = dateMethods.getDateForMysql();
                String time = timeMethods.getCurrentTime();
                String productType = spinnerProductType.getSelectedItem().toString();
                String serialNumber = etSN.getText().toString().trim();
                String client = etClient.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String place = etPlace.getText().toString().trim();
                String phone1 = etPhone1.getText().toString().trim();
                String phone2 = etPhone2.getText().toString().trim();
                String faultDescription = etFaultDescription.getText().toString().trim();
                String note = etNote.getText().toString().trim();
                String serviceman = spinnerServiceman.getSelectedItem().toString();
                String status = Constants.STATUS_FAULT;
                String priority = Constants.PRIORITY;
                String issuedBy = currentUserTableController.getUsername().toUpperCase();
                String typeOfService = Constants.TYPE_OF_SERVICE;
                //addFault(date, time, productType, serialNumber, client, address, place, phone1, phone2, faultDescription, note, serviceman, status, priority, issuedBy, typeOfService);

               /* TelephonyManager mtelephony = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                mtelephony.listen(new PhoneStateListener(){
                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {
                        super.onCallStateChanged(state, incomingNumber);
                        switch (state) {
                            case TelephonyManager.CALL_STATE_RINGING:
                                // CALL_STATE_RINGING
                                Toast.makeText(getActivity(), incomingNumber, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                    }
                },PhoneStateListener.LISTEN_CALL_STATE);*/



                break;

        }
    }



    private void addFault(String datefault, String timefault, String productType, String serialNumber, String client, String address, String place, String phone1, String phone2,
                          String faultDescription, String note, String serviceman, String status, String priority, String issuedBy, String typeOfService){
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage(getResources().getString(R.string.prgDialog_addingfault));
        prgDialog.setCancelable(false);
        prgDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AddFaultRequestInterface addFaultRequestInterface = retrofit.create(AddFaultRequestInterface.class);
        Fault fault = new Fault();
        fault.setDatefault(datefault);
        fault.setTimefault(timefault);
        fault.setIdent(productType);
        fault.setSerialNumber(serialNumber);
        fault.setBuyer(client);
        fault.setAddress(address);
        fault.setPlacefault(place);
        fault.setPhoneNumber(phone1);
        fault.setPhoneNumber2(phone2);
        fault.setDescFaults(faultDescription);
        fault.setNotesInfo(note);
        fault.setResponsibleforfailure(serviceman);
        fault.setStatus(status);
        fault.setPriorities(priority);
        fault.setOrderIssued(issuedBy);
        fault.setTypeOfService(typeOfService);
        AddFaultServerRequest addFaultServerRequest = new AddFaultServerRequest();
        addFaultServerRequest.setFault(fault);
        Call<AddFaultServerResponse> response = addFaultRequestInterface.operation(addFaultServerRequest);
        response.enqueue(new Callback<AddFaultServerResponse>() {
            @Override
            public void onResponse(Call<AddFaultServerResponse> call, retrofit2.Response<AddFaultServerResponse> response) {
                AddFaultServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){

                    //delete previously stored faults from local sql table
                    faultsTableController.deleteAll();

                    //get all faults and insert into local sql table
                    faultData = new ArrayList<>(Arrays.asList(resp.getFault()));
                    for (int i = 0; i < faultData.size(); i++) {
                        faultsTableController.insertFault(faultData.get(i));
                    }


                    //refresh fragment
                    Fragment newFragment = new FragmentFaultsListview();
                    FragmentTransaction tr = getFragmentManager().beginTransaction();
                    tr.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    tr.replace(R.id.content_main, newFragment);
                    tr.addToBackStack(null);
                    tr.commit();

                    prgDialog.dismiss();

                    Snackbar.make(coordinatorLayout, R.string.snackbar_addfault_success, Snackbar.LENGTH_LONG).show();

                }else if (resp.getResult().equals(Constants.FAILURE)){
                    prgDialog.dismiss();
                    Snackbar.make(coordinatorLayout, R.string.snackbar_addfault_failure, Snackbar.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<AddFaultServerResponse> call, Throwable t) {
                prgDialog.dismiss();
                Snackbar.make(coordinatorLayout, R.string.snackbar_addfault_error, Snackbar.LENGTH_LONG).show();
            }
        });

    }




}
