package ba.biggy.androidbis.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ba.biggy.androidbis.R;

public class FragmentCheckProduct extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_checkproduct, container, false);
        return rootView;
    }
}
