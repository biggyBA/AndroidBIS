package ba.biggy.androidbis.faultDetail.faultDetailServiceman;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ba.biggy.androidbis.Constants;
import ba.biggy.androidbis.R;
import ba.biggy.androidbis.bottomSheets.ItemPagerAdapter;
import ba.biggy.androidbis.bottomSheets.lib.BottomSheetBehaviorGoogleMapsLike;
import ba.biggy.androidbis.bottomSheets.lib.MergedAppBarLayoutBehavior;
import ba.biggy.androidbis.global.DateMethods;
import ba.biggy.androidbis.global.DisplayMethods;


public class FaultDetailServiceman extends AppCompatActivity {

    private TextView bottomSheetTextView, tvClient, tvPhone, tvPhone2, tvAddress, tvPlace, tvServiceman, tvProductType, tvDate, tvTime, tvFaultDescription, tvNote,
                        tvAddress2, tvPlace2, tvPhone22, tvPhone222;
    DateMethods dateMethods = new DateMethods();
    DisplayMethods displayMethods = new DisplayMethods(FaultDetailServiceman.this);

    int[] mDrawables = {
            R.drawable.ic_ekoline,
            R.drawable.ic_ekoline
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fault_detail_serviceman);

        // check if screen size is less than 5.3 inch, if yes show first layout, otherwise show second layout
        if (displayMethods.getDiagonalScreenSize() < 5.3){
            ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
            stub.setLayoutResource(R.layout.fault_detail_serviceman_bottom_sheet_content_below_5_3_inch);
            stub.inflate();
        }else{
            ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
            stub.setLayoutResource(R.layout.fault_detail_serviceman_bottom_sheet_content_above_5_3_inch);
            stub.inflate();
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(" ");
        }


        GoogleMap mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        LatLng kovan = new LatLng(44.697295, 18.273974);
        //mMap.addMarker(new MarkerOptions().position(kovan).title("Kovan").snippet("Servis"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(kovan).zoom(12).build();
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




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
        mergedAppBarLayoutBehavior.setToolbarTitle("");
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        bottomSheetTextView = (TextView) bottomSheet.findViewById(R.id.bottom_sheet_title);
        ItemPagerAdapter adapter = new ItemPagerAdapter(this,mDrawables);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);


        //get extras from bundle
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {


            tvClient = (TextView) bottomSheet.findViewById(R.id.tvClient);
            tvPhone = (TextView) bottomSheet.findViewById(R.id.tvPhone1);
            tvPhone2 = (TextView) bottomSheet.findViewById(R.id.tvPhone2);
            tvAddress = (TextView) bottomSheet.findViewById(R.id.tvAddress);
            tvPlace = (TextView) bottomSheet.findViewById(R.id.tvPlace);
            //tvServiceman = (TextView) bottomSheet.findViewById(R.id.tvServiceman);
            tvProductType = (TextView) bottomSheet.findViewById(R.id.tvProductType);
            tvDate = (TextView) bottomSheet.findViewById(R.id.tvDate);
            tvTime = (TextView) bottomSheet.findViewById(R.id.tvTime);
            tvFaultDescription = (TextView) bottomSheet.findViewById(R.id.tvFaultDescription);
            tvNote = (TextView) bottomSheet.findViewById(R.id.tvNote);
            tvAddress2 = (TextView) bottomSheet.findViewById(R.id.tvAddress2);
            tvPlace2 = (TextView) bottomSheet.findViewById(R.id.tvPlace2);
            tvPhone22 = (TextView) bottomSheet.findViewById(R.id.tvPhone22);
            tvPhone222 = (TextView) bottomSheet.findViewById(R.id.tvPhone222);

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
            tvPhone2.setText(phone2);
            tvAddress.setText(address);
            tvPlace.setText(place);
            //tvServiceman.setText(serviceman);
            //tvProductType.setText(productType);
            tvDate.setText(dateMethods.formatDateFromMysqlToView(datefault));
            tvTime.setText(timefault);
            tvFaultDescription.setText(descFault);
            tvNote.setText(note);

            //tvAddress2.setText(address);
            //tvPlace2.setText(place);
            //tvPhone22.setText(phone1);
            //tvPhone222.setText(phone2);

            mergedAppBarLayoutBehavior.setToolbarTitle(client);

        }
    }




}
