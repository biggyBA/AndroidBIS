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

import java.util.ArrayList;
import java.util.List;

import ba.biggy.androidbis.R;
import ba.biggy.androidbis.adapter.spinnerAdapter.NothingSelectedSpinnerAdapter;

public class FragmentServicesheet extends Fragment{

    private EditText etClient, etAddress, etPlace, etPhone1, etPhone2, etSN, etBuyDate, etBuyerRemark, etIntDescription, etNote, etPriceParts, etPriceWork, etPriceTravel, etPriceTotal;
    private Spinner spinnerProductType, spinnerWarrantyYesNo, spinnerPaymentMethod, spinnerPayedYesNo, spinnerTimeWork, spinnerTimeTravel;
    private Button btnSubmitServicesheet;
    private ArrayAdapter<CharSequence> adapterYesNo, adapterPaymentMethod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_servicesheet, container, false);
        return rootView;
    }



    @Override
    public void onStart(){
        super.onStart();
        etClient = (EditText) getActivity().findViewById(R.id.etClient);
        etAddress = (EditText) getActivity().findViewById(R.id.etAddress);
        etPlace = (EditText) getActivity().findViewById(R.id.etPlace);
        etPhone1 = (EditText) getActivity().findViewById(R.id.etPhone1);
        etPhone2 = (EditText) getActivity().findViewById(R.id.etPhone2);
        etSN = (EditText) getActivity().findViewById(R.id.etSN);
        etBuyDate = (EditText) getActivity().findViewById(R.id.etBuyDate);
        etBuyerRemark = (EditText) getActivity().findViewById(R.id.etBuyerRemark);
        etIntDescription = (EditText) getActivity().findViewById(R.id.etIntDescription);
        etNote = (EditText) getActivity().findViewById(R.id.etNote);
        etPriceParts = (EditText) getActivity().findViewById(R.id.etPriceParts);
        etPriceWork = (EditText) getActivity().findViewById(R.id.etPriceWork);
        etPriceTravel = (EditText) getActivity().findViewById(R.id.etPriceTravel);
        etPriceTotal = (EditText) getActivity().findViewById(R.id.etPriceTotal);


        spinnerProductType = (Spinner) getActivity().findViewById(R.id.spinnerProductType);
        spinnerWarrantyYesNo = (Spinner) getActivity().findViewById(R.id.spinnerWarrantyYesNo);
        spinnerPaymentMethod = (Spinner) getActivity().findViewById(R.id.spinnerPaymentMethod);
        spinnerPayedYesNo = (Spinner) getActivity().findViewById(R.id.spinnerPayedYesNo);
        spinnerTimeWork = (Spinner) getActivity().findViewById(R.id.spinnerTimeWork);
        spinnerTimeTravel = (Spinner) getActivity().findViewById(R.id.spinnerTimeTravel);

        btnSubmitServicesheet = (Button) getActivity().findViewById(R.id. btnSubmitServicesheet);

        //adapter for spinner - YES NO option
        adapterYesNo = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerYesNo, android.R.layout.simple_spinner_item);
        adapterYesNo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //adapter for spinner PAYMENT METHOD
        adapterPaymentMethod = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerMethodPayment, android.R.layout.simple_spinner_item);
        adapterPaymentMethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        spinnerWarrantyYesNo.setAdapter(new NothingSelectedSpinnerAdapter(adapterYesNo, R.layout.spinner_row_nothing_selected_waranty_yes_no, getActivity()));
        spinnerPaymentMethod.setAdapter(new NothingSelectedSpinnerAdapter(adapterPaymentMethod, R.layout.spinner_row_nothing_selected_payment_method, getActivity()));
        spinnerPayedYesNo.setAdapter(new NothingSelectedSpinnerAdapter(adapterYesNo, R.layout.spinner_row_nothing_selected_payed_yes_no, getActivity()));

    }






    @Override
    public void onResume(){
        super.onResume();

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




    }

}
