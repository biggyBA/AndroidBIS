package ba.biggy.androidbis;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ba.biggy.androidbis.POJO.Fault;
import ba.biggy.androidbis.POJO.Servicesheet;
import ba.biggy.androidbis.POJO.retrofitServerObjects.FaultServerResponse;
import ba.biggy.androidbis.SQLite.DataBaseAdapter;
import ba.biggy.androidbis.SQLite.FaultsTableController;
import ba.biggy.androidbis.SQLite.ServicesheetTableController;
import ba.biggy.androidbis.TESTPACKAGE.TestsheetRequestInterface;
import ba.biggy.androidbis.TESTPACKAGE.TestsheetServerResponse;
import ba.biggy.androidbis.bottomSheets.FaultDetail;
import ba.biggy.androidbis.retrofitInterface.FaultRequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TestActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private ArrayList<Servicesheet> sheetData;
    private ArrayList<Fault> faultData;
    ServicesheetTableController servicesheetTableController = new ServicesheetTableController();
    FaultsTableController faultsTableController = new FaultsTableController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        permGPS();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataBaseAdapter.init(this);


        pref = getApplicationContext().getSharedPreferences(Constants.PREF, 0);

       /* String phone = pref.getString("phone","");
        Toast.makeText(TestActivity.this, phone, Toast.LENGTH_LONG).show();

        TextView tv = (TextView) findViewById(R.id.tv);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        tv.setText(LastCall());
        tv2.setText(phone);*/



        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    getSheets();
            }
        });


        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestActivity.this, FaultDetail.class);
                startActivity(intent);
            }
        });

    }


    private void permGPS(){
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, Constants.PERMISSIONS_REQUEST_READ_CALL_LOG);

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is less than 6.0 or the permission is already granted.

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == Constants.PERMISSIONS_REQUEST_READ_CALL_LOG) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                Toast.makeText(this, "Granted CALL", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String LastCall() {
        StringBuffer sb = new StringBuffer();
        Cursor cur = getContentResolver().query( CallLog.Calls.CONTENT_URI,null, null,null, android.provider.CallLog.Calls.DATE + " DESC");

        int number = cur.getColumnIndex( CallLog.Calls.NUMBER );
        int duration = cur.getColumnIndex( CallLog.Calls.DURATION);
        int countryISO = cur.getColumnIndex(CallLog.Calls.COUNTRY_ISO);
        int dat = cur.getColumnIndex(CallLog.Calls.DATE);
        sb.append("Call Details : \n");
        while ( cur.moveToNext() ) {
            String phNumber = cur.getString( number );
            String callDuration = cur.getString( duration );
            String countryIS = cur.getString(countryISO);
            String da = cur.getString(dat);

            long date1 = Long.valueOf(da).longValue();
            Date date = new Date(date1);
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String reportDate = df.format(date);




            sb.append( "\nPhone Number:"+phNumber);
            sb.append( "\nDuration:"+callDuration);
            sb.append( "\nCountry ISO:"+countryIS);
            sb.append( "\nDate:"+reportDate);
            break;
        }
        cur.close();
        String str=sb.toString();
        return str;
    }

    private void getFaults(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FaultRequestInterface request = retrofit.create(FaultRequestInterface.class);
        Call<FaultServerResponse> call = request.getFaults();
        call.enqueue(new Callback<FaultServerResponse>() {
            @Override
            public void onResponse(Call<FaultServerResponse> call, Response<FaultServerResponse> response) {



                //get the response body and insert faults
                FaultServerResponse jsonResponse = response.body();
                /*faultData = new ArrayList<>(Arrays.asList(jsonResponse.getFault()));
                for (int i = 0; i < faultData.size(); i++) {
                    faultsTableController.insertFault(faultData.get(i));
                }*/

                String test = jsonResponse.toString();
                Toast.makeText(TestActivity.this, test, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<FaultServerResponse> call, Throwable t) {
                Toast.makeText(TestActivity.this, R.string.error_nointernet, Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void getSheets(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://biggy.ba/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestsheetRequestInterface request = retrofit.create(TestsheetRequestInterface.class);
        Call<TestsheetServerResponse> call = request.getFaults();
        call.enqueue(new Callback<TestsheetServerResponse>() {
            @Override
            public void onResponse(Call<TestsheetServerResponse> call, Response<TestsheetServerResponse> response) {



                //get the response body and insert faults
                TestsheetServerResponse jsonResponse = response.body();
                sheetData = new ArrayList<>(Arrays.asList(jsonResponse.getFault()));
                for (int i = 0; i < sheetData.size(); i++) {
                    servicesheetTableController.insertServicesheet(sheetData.get(i));
                }



            }
            @Override
            public void onFailure(Call<TestsheetServerResponse> call, Throwable t) {
                Toast.makeText(TestActivity.this, R.string.error_nointernet, Toast.LENGTH_SHORT).show();
            }

        });
    }
}