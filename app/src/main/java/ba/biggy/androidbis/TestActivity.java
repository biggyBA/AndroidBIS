package ba.biggy.androidbis;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.CallLog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ba.biggy.androidbis.SQLite.CurrentUserTableController;
import ba.biggy.androidbis.SQLite.DataBaseAdapter;
import ba.biggy.androidbis.SQLite.SparepartListTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;


public class TestActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataBaseAdapter.init(this);


        pref = getApplicationContext().getSharedPreferences(Constants.PREF, 0);

        String phone = pref.getString("phone","");
        Toast.makeText(TestActivity.this, phone, Toast.LENGTH_LONG).show();

        TextView tv = (TextView) findViewById(R.id.tv);
        TextView tv2 = (TextView) findViewById(R.id.tv2);

        tv.setText(LastCall());

        tv2.setText(phone);

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
}