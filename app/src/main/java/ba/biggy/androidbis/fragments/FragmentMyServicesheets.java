package ba.biggy.androidbis.fragments;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import ba.biggy.androidbis.R;
import ba.biggy.androidbis.SQLite.ServicesheetTableController;
import ba.biggy.androidbis.adapter.listviewAdapter.FaultListviewExpandedAdapter;
import ba.biggy.androidbis.adapter.listviewAdapter.ServicesheetListviewAdapter;

public class FragmentMyServicesheets extends Fragment {


    private Spinner spinnerKind;
    private SwipeMenuListView swipeMenuListView;
    private ListView listView;
    private ArrayAdapter<CharSequence> adapterKind;
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

        spinnerKind = (Spinner) getActivity().findViewById(R.id.spinnerKind);

        listView = (ListView) getActivity().findViewById(R.id.listView);
        swipeMenuListView = (SwipeMenuListView) getActivity().findViewById(R.id.listView);

        adapterKind = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerKind, android.R.layout.simple_spinner_item);
        adapterKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKind.setAdapter(adapterKind);

        spinnerKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int selected = (int) spinnerKind.getSelectedItemId();

                if (selected == 0) {  // All servicesheets

                    servicesheetListviewAdapter = new ServicesheetListviewAdapter(getActivity(), servicesheetTableController.getAllServicesheets());
                    listView.setAdapter(servicesheetListviewAdapter);

                    listViewOptions(0);

                } else if (selected == 1){  // Sent servicesheets

                    servicesheetListviewAdapter = new ServicesheetListviewAdapter(getActivity(), servicesheetTableController.getSentServicesheets());
                    listView.setAdapter(servicesheetListviewAdapter);

                    listViewOptions(1);

                } else if (selected == 2) { // Not sent servicesheets

                    servicesheetListviewAdapter = new ServicesheetListviewAdapter(getActivity(), servicesheetTableController.getNotSentServicesheets());
                    listView.setAdapter(servicesheetListviewAdapter);

                    listViewOptions(2);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void listViewOptions (int selected){

        switch (selected){
            case 0:
                //this view has no swipe options
                SwipeMenuCreator allSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                    }
                };
                swipeMenuListView.setMenuCreator(allSwipeCreator);
                break;


            case 1:
                //this view has no swipe options
                SwipeMenuCreator sentSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                    }
                };
                swipeMenuListView.setMenuCreator(sentSwipeCreator);
                break;


            case 2:
                //create menu items in swipemenu
                SwipeMenuCreator notSentSwipeCreator = new SwipeMenuCreator() {
                    @Override
                    public void create(SwipeMenu menu) {
                        // create "send" item
                        SwipeMenuItem sendItem = new SwipeMenuItem(getActivity());
                        sendItem.setBackground(R.color.colorArchive);
                        sendItem.setWidth(250);
                        sendItem.setIcon(R.drawable.ic_delete_forever_black);
                        menu.addMenuItem(sendItem);
                    }
                };


                swipeMenuListView.setMenuCreator(notSentSwipeCreator);
                swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

                //handle swipe menu item clicks
                swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

                        /*//get cursor from selected item
                        Cursor c = (Cursor) swipeMenuListView.getItemAtPosition(position);
                        //id string is needed for webservice
                        final String id = c.getString(1);
                        //strings which are shown in the snackbar
                        String client = c.getString(7);

                        c.close();*/


                        switch (index) {

                            case 0:
                                // send
                                Toast.makeText(getActivity(), "send", Toast.LENGTH_SHORT).show();

                                break;



                        }
                        return false;
                    }
                });

                break;

            default:
                break;
        }
    }



}
