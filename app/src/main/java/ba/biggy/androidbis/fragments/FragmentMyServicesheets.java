package ba.biggy.androidbis.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.ServicesheetTableController;
import ba.biggy.androidbis.adapter.listviewAdapter.FaultListviewExpandedAdapter;
import ba.biggy.androidbis.adapter.listviewAdapter.ServicesheetListviewAdapter;

public class FragmentMyServicesheets extends Fragment {


    private SwipeMenuListView swipeMenuListView;
    ServicesheetListviewAdapter servicesheetListviewAdapter;
    ServicesheetTableController servicesheetTableController = new ServicesheetTableController();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myservicesheets, container, false);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        swipeMenuListView = (SwipeMenuListView) getActivity().findViewById(R.id.listView);

        servicesheetListviewAdapter = new ServicesheetListviewAdapter(getActivity(), servicesheetTableController.getAllServicesheets());
        swipeMenuListView.setAdapter(servicesheetListviewAdapter);

    }
}
