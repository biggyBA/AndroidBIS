package ba.biggy.androidbis.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.UsersTableController;

public class FragmentAddFault extends Fragment {

    private EditText etSN, etClient, etAddress, etPlace, etPhone1, etPhone2, etFaultDescription, etNote;
    private Spinner spinnerProductType, spinnerServiceman;
    private Button btnAddFault;
    UsersTableController usersTableController = new UsersTableController();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_fault, container, false);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        etSN = (EditText) getActivity().findViewById(R.id.etSn);
        etClient = (EditText) getActivity().findViewById(R.id.etClient);
        etAddress = (EditText) getActivity().findViewById(R.id.etAddress);
        etPlace = (EditText) getActivity().findViewById(R.id.etPlace);
        etPhone1 = (EditText) getActivity().findViewById(R.id.etPhone1);
        etPhone2 = (EditText) getActivity().findViewById(R.id.etPhone2);
        etFaultDescription = (EditText) getActivity().findViewById(R.id.etFaultDescription);
        etNote = (EditText) getActivity().findViewById(R.id.etNote);

        spinnerProductType = (Spinner) getActivity().findViewById(R.id.spinnerProductType);
        spinnerServiceman = (Spinner) getActivity().findViewById(R.id.spinnerServiceman);

        btnAddFault = (Button) getActivity().findViewById(R.id.btnAddFault);


        //populate spinner from userstable usernames with protection level serviceman
        List<String> lables = usersTableController.getAllServiceman();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceman.setAdapter(dataAdapter);
        //set the selection of serviceman
        spinnerServiceman.setSelection(lables.indexOf("N/A"));



    }
}
