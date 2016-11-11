package ba.biggy.androidbis.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.adapter.spinnerAdapter.NothingSelectedSpinnerAdapter;

public class FragmentServicesheet extends Fragment{

    private EditText etClient, etAddress, etPlace, etPhone1, etPhone2, etSN, etBuyDate, etWarrantyDate, etBuyerRemark, etIntDescription, etNote, etPriceParts, etPriceWork, etPriceTravel, etPriceTotal;
    private Spinner spinnerProductType, spinnerWarrantyYesNo, spinnerPaymentMethod, spinnerPayedYesNo, spinnerTimeWork, spinnerTimeTravel;
    private Button btnSubmitServicesheet;
    private ArrayAdapter<CharSequence> adapterYesNo, adapterPaymentMethod, adapterTime;

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
        etBuyDate.setInputType(InputType.TYPE_NULL); //disable input because a date picker dialog is used for input
        etWarrantyDate = (EditText) getActivity().findViewById(R.id.etWarrantyDate);
        etWarrantyDate.setEnabled(false);
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

        //adapter with time values
        adapterTime = ArrayAdapter.createFromResource(getActivity(), R.array.time_array, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //set adapters to spinners
        spinnerWarrantyYesNo.setAdapter(adapterYesNo);
        spinnerPaymentMethod.setAdapter(adapterPaymentMethod);
        spinnerPayedYesNo.setAdapter(adapterYesNo);
        spinnerTimeWork.setAdapter(adapterTime);
        spinnerTimeTravel.setAdapter(adapterTime);

        //set initaly nothing selected in spinners
        spinnerWarrantyYesNo.setAdapter(new NothingSelectedSpinnerAdapter(adapterYesNo, R.layout.spinner_row_nothing_selected_choose, getActivity()));
        spinnerPaymentMethod.setAdapter(new NothingSelectedSpinnerAdapter(adapterPaymentMethod, R.layout.spinner_row_nothing_selected_choose, getActivity()));
        spinnerPayedYesNo.setAdapter(new NothingSelectedSpinnerAdapter(adapterYesNo, R.layout.spinner_row_nothing_selected_choose, getActivity()));
        spinnerTimeWork.setAdapter(new NothingSelectedSpinnerAdapter(adapterTime, R.layout.spinner_row_nothing_selected_choose, getActivity()));
        spinnerTimeTravel.setAdapter(new NothingSelectedSpinnerAdapter(adapterTime, R.layout.spinner_row_nothing_selected_choose, getActivity()));



        /*
         * set two listeners to buy date editText
         * both listeners are needed
         * on date select editText buyDate is updated with selected date and disabled
         * editText warrantyDate is updated with selected date +2 years (warranty period)
         * editText warrantyDate is initialy disabled
         * the selected date has format dd.MM.yyyy
         */
        etBuyDate.setOnClickListener(new View.OnClickListener() {
            Calendar myCalendar = Calendar.getInstance();
            @Override
            public void onClick(View v) {
                /*
                 * create datePickerDialog
                 * set maximum date to today
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
            // set onDateListener
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    /*
                     * update editText buyDate with selected date
                     * set it to disabled
                     * update editText warrantyDate with date +2 years (warranty period)
                     */
                    String myFormat = Constants.DATE_SHOW_FORMAT;
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    etBuyDate.setText(sdf.format(myCalendar.getTime()));
                    etBuyDate.setEnabled(false);
                    myCalendar.add(Calendar.YEAR, 2);
                    etWarrantyDate.setText(sdf.format(myCalendar.getTime()));
                }
            };
        });
        etBuyDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            Calendar myCalendar = Calendar.getInstance();
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    /*
                     * create datePickerDialog
                     * set maximum date to today
                     */
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                    datePickerDialog.show();
                }
            }
            // set onDateListener
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    /*
                     * update editText buyDate with selected date
                     * set it to disabled
                     * update editText warrantyDate with date +2 years (warranty period)
                     */
                    String myFormat = Constants.DATE_SHOW_FORMAT;
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    etBuyDate.setText(sdf.format(myCalendar.getTime()));
                    etBuyDate.setEnabled(false);
                    myCalendar.add(Calendar.YEAR, 2);
                    etWarrantyDate.setText(sdf.format(myCalendar.getTime()));
                }
            };

        });
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
        spinnerProductType.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_row_nothing_selected_choose, getActivity()));




    }


}
