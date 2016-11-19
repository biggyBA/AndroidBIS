package ba.biggy.androidbis.activities.faultDetailAdmin;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.bottomSheets.ItemPagerAdapter;
import ba.biggy.androidbis.bottomSheets.lib.BottomSheetBehaviorGoogleMapsLike;
import ba.biggy.androidbis.bottomSheets.lib.MergedAppBarLayoutBehavior;

public class FaultDetailAdmin extends AppCompatActivity {

    private TextView bottomSheetTextView, tvClient, tvPhone, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_detail_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(" ");
        }

        /**
         * If we want to listen for states callback
         */
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:

                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:

                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED:

                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT:

                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        AppBarLayout mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Fault details");
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        bottomSheetTextView = (TextView) bottomSheet.findViewById(R.id.bottom_sheet_title);
        /*ItemPagerAdapter adapter = new ItemPagerAdapter(this,mDrawables);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);*/


        //get extras from bundle
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {


            tvClient = (TextView) bottomSheet.findViewById(R.id.tvClient);
            tvPhone = (TextView) bottomSheet.findViewById(R.id.tvPhone1);
            tvAddress = (TextView) bottomSheet.findViewById(R.id.tvAddress);

            String datefault = (String) bundle.get(Constants.DATEFAULT);
            String timefault = (String) bundle.get(Constants.TIMEFAULT);
            String productType = (String) bundle.get(Constants.PRODUCT_TYPE);
            String client = (String) bundle.get(Constants.CLIENT);
            String address = (String) bundle.get(Constants.ADDRESS);
            String place = (String) bundle.get(Constants.PLACE);
            String phone1 = (String) bundle.get(Constants.PHONE_ONE);
            String phone2 = (String) bundle.get(Constants.PHONE_TWO);
            String descFault = (String) bundle.get(Constants.DESCRIPTION_FAULT);
            String note = (String) bundle.get(Constants.NOTE);
            String serviceman = (String) bundle.get(Constants.SERVICEMAN);

            tvClient.setText(client);
            tvPhone.setText(phone1);
            tvAddress.setText(address);

        }
    }
}
