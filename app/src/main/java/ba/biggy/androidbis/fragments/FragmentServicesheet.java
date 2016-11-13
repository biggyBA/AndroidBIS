package ba.biggy.androidbis.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.MainActivity;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.SparepartListTableController;
import ba.biggy.androidbis.adapter.spinnerAdapter.NothingSelectedSpinnerAdapter;

public class FragmentServicesheet extends Fragment{

    private EditText etClient, etAddress, etPlace, etPhone1, etPhone2, etSN, etBuyDate, etWarrantyDate, etBuyerRemark, etIntDescription, etNote,
                                etPriceParts, etPriceWork, etPriceTravel, etPriceTotal, dialogEtSparepartQty;
    private TextInputLayout layoutClient, layoutAddress, layoutPlace, layoutPhone1, layoutSN, layoutBuyDate, layoutBuyerRemark, layoutIntDescription,
                            layoutPriceParts, layoutPriceWork, layoutPriceTravel, layoutPriceTotal,
                            dialogLayoutSparepartSNin, dialogLayoutSparepartSNout, dialogLayoutSparepartQty;
    private Spinner spinnerProductType, spinnerWarrantyYesNo, spinnerPaymentMethod, spinnerPayedYesNo, spinnerTimeWork, spinnerTimeTravel,
                    dialogSpinnerSparepartName;
    private TextView tvNoUsedParts;
    private Button btnSubmitServicesheet, btnAddSparePart;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout layoutUsedSpareparts, dialogButtonLayout;
    private ArrayAdapter<CharSequence> adapterYesNo, adapterPaymentMethod, adapterTime, adapterDialogSparepartNames;
    private Vibrator vib;
    SparepartListTableController sparepartListTableController = new SparepartListTableController();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_servicesheet, container, false);
        return rootView;
    }


    @Override
    public void onStart(){
        super.onStart();

        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        layoutUsedSpareparts = (LinearLayout) getActivity().findViewById(R.id.layoutUsedSpareparts);

        etClient = (EditText) getActivity().findViewById(R.id.etClient);
        etAddress = (EditText) getActivity().findViewById(R.id.etAddress);
        etPlace = (EditText) getActivity().findViewById(R.id.etPlace);
        etPhone1 = (EditText) getActivity().findViewById(R.id.etPhone1);
        etPhone2 = (EditText) getActivity().findViewById(R.id.etPhone2);
        etSN = (EditText) getActivity().findViewById(R.id.etSN);
        etBuyDate = (EditText) getActivity().findViewById(R.id.etBuyDate);
        etBuyDate.setInputType(InputType.TYPE_NULL); //disable input because a date picker dialog is used for input
        etWarrantyDate = (EditText) getActivity().findViewById(R.id.etWarrantyDate);
        etWarrantyDate.setEnabled(false);  //this editText can not be edited by the user
        etBuyerRemark = (EditText) getActivity().findViewById(R.id.etBuyerRemark);
        etIntDescription = (EditText) getActivity().findViewById(R.id.etIntDescription);
        etNote = (EditText) getActivity().findViewById(R.id.etNote);
        etPriceParts = (EditText) getActivity().findViewById(R.id.etPriceParts);
        etPriceParts.setEnabled(false);  //initialy this editText is disabled. if spinner Warranty is equal NO, this editText is enabled
        etPriceParts.setText(getString(R.string.fragment_servicesheet_initial_cost)); //initialy set to 0.00
        etPriceWork = (EditText) getActivity().findViewById(R.id.etPriceWork);
        etPriceWork.setEnabled(false);  //initialy this editText is disabled. if spinner Warranty is equal NO, this editText is enabled
        etPriceWork.setText(getString(R.string.fragment_servicesheet_initial_cost)); //initialy set to 0.00
        etPriceTravel = (EditText) getActivity().findViewById(R.id.etPriceTravel);
        etPriceTravel.setEnabled(false);  //initialy this editText is disabled. if spinner Warranty is equal NO, this editText is enabled
        etPriceTravel.setText(getString(R.string.fragment_servicesheet_initial_cost)); //initialy set to 0.00
        etPriceTotal = (EditText) getActivity().findViewById(R.id.etPriceTotal);
        etPriceTotal.setEnabled(false);  //initialy this editText is disabled. if spinner Warranty is equal NO, this editText is enabled
        etPriceTotal.setText(getString(R.string.fragment_servicesheet_initial_cost)); //initialy set to 0.00

        layoutClient = (TextInputLayout) getActivity().findViewById(R.id.layout_client);
        layoutAddress = (TextInputLayout) getActivity().findViewById(R.id.layout_address);
        layoutPlace = (TextInputLayout) getActivity().findViewById(R.id.layout_place);
        layoutPhone1 = (TextInputLayout) getActivity().findViewById(R.id.layout_phone1);
        layoutSN = (TextInputLayout) getActivity().findViewById(R.id.layout_sn);
        layoutBuyDate = (TextInputLayout) getActivity().findViewById(R.id.layout_buyDate);
        layoutBuyerRemark = (TextInputLayout) getActivity().findViewById(R.id.layout_buyerRemark);
        layoutIntDescription = (TextInputLayout) getActivity().findViewById(R.id.layout_intDescription);
        layoutPriceParts = (TextInputLayout) getActivity().findViewById(R.id.layout_priceparts);
        layoutPriceWork = (TextInputLayout) getActivity().findViewById(R.id.layout_pricework);
        layoutPriceTravel = (TextInputLayout) getActivity().findViewById(R.id.layout_pricetravel);
        layoutPriceTotal = (TextInputLayout) getActivity().findViewById(R.id.layout_pricetotal);


        spinnerProductType = (Spinner) getActivity().findViewById(R.id.spinnerProductType);
        spinnerWarrantyYesNo = (Spinner) getActivity().findViewById(R.id.spinnerWarrantyYesNo);
        spinnerPaymentMethod = (Spinner) getActivity().findViewById(R.id.spinnerPaymentMethod);
        spinnerPaymentMethod.setEnabled(false);  //this spinner is initialy disabled
        spinnerPayedYesNo = (Spinner) getActivity().findViewById(R.id.spinnerPayedYesNo);
        spinnerPayedYesNo.setEnabled(false);  //this spinner is intialy disabled
        spinnerTimeWork = (Spinner) getActivity().findViewById(R.id.spinnerTimeWork);
        spinnerTimeTravel = (Spinner) getActivity().findViewById(R.id.spinnerTimeTravel);

        tvNoUsedParts = (TextView) getActivity().findViewById(R.id.tvNoUsedParts);

        btnSubmitServicesheet = (Button) getActivity().findViewById(R.id. btnSubmitServicesheet);
        btnAddSparePart = (Button) getActivity().findViewById(R.id.btnAddSparepart);


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
                    //if soft input is shown, hide it
                    InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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


        spinnerWarrantyYesNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                 * get the selected item position
                 * if position is eqaul 2 (no warranty), price editText gets enabled
                 * otherwise the editText gets disabled and costs are set to 0.00
                 */
                int selectedItem = spinnerWarrantyYesNo.getSelectedItemPosition();
                if (selectedItem == 2){
                    etPriceParts.setEnabled(true);
                    etPriceWork.setEnabled(true);
                    etPriceTravel.setEnabled(true);
                    etPriceTotal.setEnabled(true);

                    //if warranty is NO enable the payment method spinner
                    spinnerPaymentMethod.setEnabled(true);

                }else{
                    etPriceParts.setEnabled(false);
                    etPriceParts.setText(getString(R.string.fragment_servicesheet_initial_cost));
                    etPriceWork.setEnabled(false);
                    etPriceWork.setText(getString(R.string.fragment_servicesheet_initial_cost));
                    etPriceTravel.setEnabled(false);
                    etPriceTravel.setText(getString(R.string.fragment_servicesheet_initial_cost));
                    etPriceTotal.setEnabled(false);
                    etPriceTotal.setText(getString(R.string.fragment_servicesheet_initial_cost));

                    //if warranty is YES disable the spinner and set selected item to -1
                    spinnerPaymentMethod.setEnabled(false);
                    spinnerPaymentMethod.setSelection(0);
                    spinnerPayedYesNo.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get the selected item
                int selectedItem = spinnerPaymentMethod.getSelectedItemPosition();
                if (selectedItem == -1 || selectedItem == 0){
                    //if nothing is selected keep the spinner disabled
                    spinnerPayedYesNo.setEnabled(false);

                }else if (selectedItem == 1){
                    //if payment method is cash enable the spinner
                    spinnerPayedYesNo.setEnabled(true);

                }else if (selectedItem == 2){
                    //if payment method is invoice, disable the payed spinner and set it to NO
                    spinnerPayedYesNo.setEnabled(false);
                    spinnerPayedYesNo.setSelection(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSubmitServicesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateServiceSheet()){
                    return;
                }else{

                }
            }
        });

        btnAddSparePart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddSparepartDialog();
            }
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


    //validate service sheet
    private boolean validateServiceSheet() {
        /*if(!validateClient()){
            return false;
        }
        if(!validateAddress()){
            return false;
        }
        if(!validatePlace()){
            return false;
        }
        if(!validatePhone1()){
            return false;
        }
        if(!validateProductType()){
            return false;
        }
        if(!validateSerialNumber()){
            return false;
        }
        if(!validateBuyDate()){
            return false;
        }
        if(!validateBuyerRemark()){
            return false;
        }
        if (!validateWarrantyYesNo()){
            return false;
        }
        if (!validatePriceParts()){
            return false;
        }
        if (!validatePriceWork()){
            return false;
        }
        if (!validatePriceTravel()){
            return false;
        }
        if (!validatePriceTotal()){
            return false;
        }
        if (!validateTimeWork()){
            return false;
        }*/
        if (!validateTimeTravel()){
            return false;
        }



        return true;
    }

    /*
     *
     * validation methods for service sheet
     *
     */
    private boolean validateClient() {
        if (etClient.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutClient.setError(getString(R.string.fragment_servicesheet_insert_client));
            etClient.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etClient);
            return false;
        }
        layoutClient.setErrorEnabled(false);
        return true;
    }

    private boolean validateAddress() {
        if (etAddress.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutAddress.setError(getString(R.string.fragment_servicesheet_insert_address));
            etAddress.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etAddress);
            return false;
        }
        layoutAddress.setErrorEnabled(false);
        return true;
    }

    private boolean validatePlace() {
        if (etPlace.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutPlace.setError(getString(R.string.fragment_servicesheet_insert_place));
            etPlace.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etPlace);
            return false;
        }
        layoutPlace.setErrorEnabled(false);
        return true;
    }

    private boolean validatePhone1() {
        if (etPhone1.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutPhone1.setError(getString(R.string.fragment_servicesheet_insert_phone));
            etPhone1.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etPhone1);
            return false;
        }
        layoutPhone1.setErrorEnabled(false);
        return true;
    }

    private boolean validateProductType() {
        if (spinnerProductType.getSelectedItemId() == -1) {
            vib.vibrate(120);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.fragment_servicesheet_insert_productType, Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        return true;
    }

    private boolean validateSerialNumber() {
        int lenght = etSN.length();
        if (etSN.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutSN.setError(getString(R.string.fragment_servicesheet_insert_sn));
            etSN.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etSN);
            return false;
        }else if (lenght < 7){
            vib.vibrate(120);
            layoutSN.setError(getString(R.string.fragment_servicesheet_insert_sn_valid));
            requestFocus(etSN);
        }else {
            layoutSN.setErrorEnabled(false);
        }
            return true;

    }

    private boolean validateBuyDate() {
        if (etBuyDate.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutBuyDate.setError(getString(R.string.fragment_servicesheet_insert_buyDate));
            etBuyDate.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etBuyDate);
            return false;
        }
        layoutBuyDate.setErrorEnabled(false);
        return true;
    }

    private boolean validateBuyerRemark() {
        int lenght = etBuyerRemark.length();
        if (etBuyerRemark.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutBuyerRemark.setError(getString(R.string.fragment_servicesheet_insert_buyerRemark));
            etBuyerRemark.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etBuyerRemark);
            return false;
        }else if (lenght < 15){
            vib.vibrate(120);
            layoutBuyerRemark.setError(getString(R.string.fragment_servicesheet_insert_buyerRemark_detail));
            requestFocus(etBuyerRemark);
        }else {
            layoutBuyerRemark.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateIntDescription() {
        int lenght = etIntDescription.length();
        if (etIntDescription.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutIntDescription.setError(getString(R.string.fragment_servicesheet_insert_intDescription));
            etIntDescription.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etIntDescription);
            return false;
        }else if (lenght < 15){
            vib.vibrate(120);
            layoutIntDescription.setError(getString(R.string.fragment_servicesheet_insert_intDescription_detail));
            requestFocus(etIntDescription);
        }else {
            layoutIntDescription.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateWarrantyYesNo() {
        if (spinnerWarrantyYesNo.getSelectedItemId() == -1) {
            vib.vibrate(120);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.fragment_servicesheet_insert_warrantyYesNo, Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        return true;
    }

    private boolean validatePriceParts() {
        if (etPriceParts.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutPriceParts.setError(getString(R.string.fragment_servicesheet_insert_priceParts));
            etPriceParts.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etPriceParts);
            return false;
        }
        layoutPriceParts.setErrorEnabled(false);
        return true;
    }

    private boolean validatePriceWork() {
        if (etPriceWork.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutPriceWork.setError(getString(R.string.fragment_servicesheet_insert_priceWork));
            etPriceWork.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etPriceWork);
            return false;
        }
        layoutPriceWork.setErrorEnabled(false);
        return true;
    }

    private boolean validatePriceTravel() {
        if (etPriceTravel.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutPriceTravel.setError(getString(R.string.fragment_servicesheet_insert_priceTravel));
            etPriceTravel.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etPriceTravel);
            return false;
        }
        layoutPriceTravel.setErrorEnabled(false);
        return true;
    }

    private boolean validatePriceTotal() {
        if (etPriceTotal.getText().toString().trim().isEmpty()) {
            vib.vibrate(120);
            layoutPriceTotal.setError(getString(R.string.fragment_servicesheet_insert_priceTotal));
            etPriceTotal.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(etPriceTotal);
            return false;
        }
        layoutPriceTotal.setErrorEnabled(false);
        return true;
    }

    private boolean validateTimeWork() {
        if (spinnerTimeWork.getSelectedItemId() == -1) {
            vib.vibrate(120);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.fragment_servicesheet_insert_timeWork, Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        return true;
    }

    private boolean validateTimeTravel() {
        if (spinnerTimeTravel.getSelectedItemId() == -1) {
            vib.vibrate(120);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.fragment_servicesheet_insert_timeTravel, Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        return true;
    }




    private void createAddSparepartDialog(){

        // create custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_sparepart);
        dialog.setTitle(R.string.fragment_servicesheet_dialog_title);

        dialogSpinnerSparepartName = (Spinner) dialog.findViewById(R.id.spinnerSparepartName);
        dialogSpinnerSparepartName.setFocusable(true);
        dialogSpinnerSparepartName.setFocusableInTouchMode(true);
        dialogSpinnerSparepartName.requestFocus();
        dialogLayoutSparepartSNin = (TextInputLayout) dialog.findViewById(R.id.layout_sparepart_sn_in);
        dialogLayoutSparepartSNout = (TextInputLayout) dialog.findViewById(R.id.layout_sparepart_sn_out);
        dialogLayoutSparepartQty = (TextInputLayout) dialog.findViewById(R.id.layout_sparepartQty);
        dialogEtSparepartQty = (EditText) dialog.findViewById(R.id.etSparepartQty);

        dialogButtonLayout = (LinearLayout) dialog.findViewById(R.id.dialogButtonLayout);

        final Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        //allign the buttons to the right side
        dialogButtonLayout.setGravity(Gravity.RIGHT);

        //populate spinner from sparepartList sql table
        List<String> lables = sparepartListTableController.getAllSparepartNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinnerSparepartName.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter, R.layout.spinner_row_nothing_selected_choose, getActivity()));

        //initialy set the OK button to disabled
        dialogButtonOK.setEnabled(false);

        dialogSpinnerSparepartName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                 * get the selected item
                 * if no item is selected disable the OK button
                 */
                int currentSelection = dialogSpinnerSparepartName.getSelectedItemPosition();
                if (currentSelection == -1 || currentSelection == 0){
                    dialogButtonOK.setEnabled(false);
                }else{
                    dialogButtonOK.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateSparepart()){
                    return;
                }else{
                    //hide text
                    tvNoUsedParts.setVisibility(View.GONE);
                    //update layout with inserted partname and qty
                    usedSpareparts();
                    //dismiss the dialog
                    dialog.dismiss();
                }
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private boolean validateSparepart(){
        if (!validateSparepartQty()){
            return false;
        }
        return true;
    }

    /*
     * validation method for spareparts
     */
    private boolean validateSparepartQty(){
        if (dialogEtSparepartQty.getText().toString().trim().isEmpty()){
            vib.vibrate(120);
            dialogLayoutSparepartQty.setError(getString(R.string.fragment_servicesheet_insert_dialog_sparepartQty));
            dialogEtSparepartQty.setError(getString(R.string.fragment_servicesheet_empty));
            requestFocus(dialogEtSparepartQty);
            return false;
        }
        dialogLayoutSparepartQty.setErrorEnabled(false);
        return true;
    }


    /*
     * method to add the sparepart name and qty to linear layout usedSpareparts
     */
    public void usedSpareparts(){
        //create the textviews
        TextView tvSparepartName = new TextView(getActivity());
        TextView tvSparepartQty = new TextView(getActivity());
        TextView emptyTv = new TextView(getActivity());

        //get the strings
        String sparepartName = dialogSpinnerSparepartName.getSelectedItem().toString().trim();
        String sparepartQty = dialogEtSparepartQty.getText().toString().trim();

        //set the text to textViews
        tvSparepartName.setText(getString(R.string.fragment_servicesheet_sparepartName) + " " + sparepartName);
        tvSparepartQty.setText(getString(R.string.fragment_servicesheet_sparepartQty) + " " + sparepartQty);

        //add them to the layout
        layoutUsedSpareparts.addView(tvSparepartName);
        layoutUsedSpareparts.addView(tvSparepartQty);
        layoutUsedSpareparts.addView(emptyTv);
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
