package ba.biggy.androidbis.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ba.biggy.androidbis.R;

public class FaultListviewExpandedAdapter extends CursorAdapter {


    public FaultListviewExpandedAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.item_faults_listview_expanded_v2, parent, false);
        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvClient = (TextView) view.findViewById(R.id.tvClient);
        tvClient.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7))));

        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvAddress.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(9))));

        TextView tvPlace = (TextView) view.findViewById(R.id.tvPlace);
        tvPlace.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(8))));

        TextView tvPhone1 = (TextView) view.findViewById(R.id.tvPhone1);
        tvPhone1.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10))));

        TextView tvPhone2 = (TextView) view.findViewById(R.id.tvPhone2);
        tvPhone2.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(11))));

        TextView tvProductType = (TextView) view.findViewById(R.id.tvProductType);
        tvProductType.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));

        TextView tvServiceman = (TextView) view.findViewById(R.id.tvServiceman);
        tvServiceman.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(14))));
    }
}
