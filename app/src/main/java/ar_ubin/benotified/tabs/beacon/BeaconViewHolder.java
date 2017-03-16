package ar_ubin.benotified.tabs.beacon;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ar_ubin.benotified.R;
import ar_ubin.benotified.data.models.Beacon;

public class BeaconViewHolder extends RecyclerView.ViewHolder
{
    public Toolbar vBeaconToolbar;
    public TextView vBeaconName;
    public ImageButton vBeaconMenu;
    public TextView vBeaconMinor;
    public TextView vBeaconMajor;
    public TextView vBeaconUuid;
    public TextView vBeaconLocation;
    public TextView vBeaconRadius;

    public BeaconViewHolder( View itemView ) {
        super( itemView );

        vBeaconToolbar = (Toolbar) itemView.findViewById( R.id.beacon_toolbar );
        vBeaconName = (TextView) itemView.findViewById( R.id.beacon_title );
        vBeaconMenu = (ImageButton) itemView.findViewById( R.id.beacon_menu );
        vBeaconMinor = (TextView) itemView.findViewById( R.id.beacon_minor );
        vBeaconMajor = (TextView) itemView.findViewById( R.id.beacon_major );
        vBeaconUuid = (TextView) itemView.findViewById( R.id.beacon_uuid );
        vBeaconLocation = (TextView) itemView.findViewById( R.id.beacon_location );
        vBeaconRadius = (TextView) itemView.findViewById( R.id.beacon_radius );
    }

    public void bindToBeacon( Beacon beacon, View.OnClickListener menuClickListener ) {
        vBeaconToolbar.setBackgroundColor( beacon.getColor() );
        vBeaconName.setText( beacon.getName() );
        vBeaconMinor.setText( String.valueOf( beacon.getMinor() ) );
        vBeaconMajor.setText( String.valueOf( beacon.getMajor() ) );
        vBeaconUuid.setText( beacon.getUuid() );
        vBeaconLocation.setText( beacon.getLocation() );
        vBeaconRadius.setText( String.valueOf( beacon.getRadius() ) );

        vBeaconMenu.setOnClickListener( menuClickListener );
    }
}
