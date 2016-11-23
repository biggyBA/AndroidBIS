package ba.biggy.androidbis.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ba.biggy.androidbis.R;

@Deprecated
 /*
  * @deprecated Use activities in package faultDetail instead.
  */

public class FragmentFaultsListviewDetail extends Fragment {

    private TextView tvDate, tvTime, tvClient, tvAddress, tvPlace, tvPhone1, tvPhone2, tvDescription, tvNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faults_listview_detail, container, false);
        return rootView;
    }


    @Override
    public void onStart(){
        super.onStart();

        tvDate = (TextView) getActivity().findViewById(R.id.tvDate);
        tvTime = (TextView) getActivity().findViewById(R.id.tvTime);
        tvClient = (TextView) getActivity().findViewById(R.id.tvClient);
        tvAddress = (TextView) getActivity().findViewById(R.id.tvAddress);
        tvPlace = (TextView) getActivity().findViewById(R.id.tvPlace);
        tvPhone1 = (TextView) getActivity().findViewById(R.id.tvPhone1);
        tvPhone2 = (TextView) getActivity().findViewById(R.id.tvPhone2);
        tvDescription = (TextView) getActivity().findViewById(R.id.tvDescription);
        tvNote = (TextView) getActivity().findViewById(R.id.tvNote);

        //get bundle from FaultsServiceSupportFragment
        Bundle bundle = this.getArguments();
        //get strings from bundle
        String date = bundle.getString("datefault");
        String time = bundle.getString("timefault");
        String client = bundle.getString("client");
        String address = bundle.getString("address");
        String place = bundle.getString("place");
        String phone1 = bundle.getString("phone1");
        String phone2 = bundle.getString("phone2");
        String description = bundle.getString("descfault");
        String note = bundle.getString("note");


        tvDate.setText(date);
        tvTime.setText(time);
        tvClient.setText(client);
        tvAddress.setText(address);
        tvPlace.setText(place);
        tvPhone1.setText(phone1);
        tvPhone2.setText(phone2);
        tvDescription.setText(description);
        tvNote.setText(note);






    }


}
