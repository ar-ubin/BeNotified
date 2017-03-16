package ar_ubin.benotified.tabs.adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.data.models.Beacon;

public class BeaconSpinnerAdapter extends ArrayAdapter<Beacon>
{
    private Context mContext;
    private Beacon[] mBeacons;

    @Inject
    public BeaconSpinnerAdapter( Context context ) {
        super( context, R.layout.spinner_beacon_item, new ArrayList<Beacon>() );
        this.mContext = context;
    }

    public void setData( List<Beacon> beacons ) {
        this.mBeacons = beacons.toArray( new Beacon[beacons.size()] );
    }

    public int getCount() {
        return mBeacons.length;
    }

    public Beacon getItem( int position ) {
        return mBeacons[position];
    }

    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        return getCustomView( position, convertView, parent );
    }

    @Override
    public View getDropDownView( int position, View convertView, ViewGroup parent ) {
        return getCustomView( position, convertView, parent );
    }

    private View getCustomView( int position, View convertView, ViewGroup parent ) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate( R.layout.spinner_beacon_item, parent, false );

        ImageView beaconColor = (ImageView) row.findViewById( R.id.beacon_color );
        TextView beaconName = (TextView) row.findViewById( R.id.beacon_name );
        beaconColor.setBackgroundTintList( ColorStateList.valueOf( mBeacons[position].getColor() ) );
        beaconName.setText( mBeacons[position].getName() );

        return row;
    }

    public int getPosition( @NonNull int beaconMinor ) {
        int beaconPosition = 0;

        for( int i = 0; i < mBeacons.length; i++ ) {
            if( mBeacons[i].getMinor() == beaconMinor ) {
                beaconPosition = i;
            }
        }
        return beaconPosition;
    }
}
